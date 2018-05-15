package org.sterling.intranet.campaign.admin.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.EventAttendanceServices;
import org.sterling.intranet.interfaces.EventsServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.Event;
import org.sterling.intranet.models.EventAttendance;
import org.sterling.intranet.models.User;
import org.sterling.intranet.utils.DateStamp;

/**
 *
 * @author Var Arenz Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class EventController 
{
    
    @Autowired
    private EventsServices eventsServices;
    
    @Autowired
    private EventAttendanceServices eventAttendanceServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllEvents(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            List<Event> events = eventsServices.selectAllEventByCampaignName(campaign);
            
            if(!events.isEmpty())
            {
                
                response.put("events", events);
                response.put("hasEvents", HttpStatus.OK);
            }
            else if(events.isEmpty())
            {
                response.put("hasEvents", HttpStatus.NO_CONTENT);
            }
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/events", params = "eventId", method = RequestMethod.GET)
    public ResponseEntity<?> selectEvent(@RequestParam(name = "eventId", required = false) String eventId, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            Event event = eventsServices.selectEventByEventIdAndCapaign(eventId, campaign);
            
            if(event != null)
            {
                response.put("selectedEvent", event);
                
                Integer userId = userServices.selectUserIdByUsername(pricipalSecurityServices.getPrincipal());
                
                Integer empDetailsId = employeeDetailsServices.selectEmpDetailsIdByUserId(userId);
                
                EventAttendance attendance = eventAttendanceServices.selectStateInEventByEmpDetailsIdAndEventId(empDetailsId, event.getEventId());
                
                if(attendance != null)
                {
                    if(attendance.getState().equalsIgnoreCase("Going"))
                    {
                        response.put("isGoing", "Going");
                    }
                    else if(attendance.getState().equalsIgnoreCase("Not going"))
                    {
                        response.put("isGoing", "Not going");
                    }
                }
                
                List<EventAttendance> eventAttendances = eventAttendanceServices.selectAllAttendanceByEventId(eventId);
                if(!eventAttendances.isEmpty())
                {
                    response.put("numOfGoing", eventAttendances.size());
                }
                else if(eventAttendances.isEmpty())
                {
                    response.put("numOfGoing", 0);
                }
                
            }
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public ResponseEntity<?> saveEvent(@CookieValue("currentCampaign") String cookie, @RequestBody Event event) throws ParseException
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            
            User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
            if(user != null)
            {
                EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
                if(employeeDetails != null)
                {
                    DateStamp dateStamp = new DateStamp();
                    Event evt = new Event();
                    evt.setEventName(event.getEventName());
                    evt.setLocation(event.getLocation());
                    evt.setEventDate(event.getEventDate());
                    evt.setDateCreated(dateStamp.getDate());
                    evt.setCampaign(campaign);
                    evt.setBase64(event.getBase64());
                    evt.setFileType(event.getFileType());
                    evt.setDetails(event.getDetails());
                    evt.setEmployeeDetails(employeeDetails);
                    eventsServices.saveEvent(evt);
                    
                    response.put("eventSaved", HttpStatus.OK);
                    
                    
                }
            }
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/events", params = "evtId", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> eventAttendance(@CookieValue("currentCampaign") String cookie, @RequestParam(name = "evtId") String eventId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        if(user != null)
        {
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
            if(employeeDetails != null)
            {
                EventAttendance eventAttendance = eventAttendanceServices.selectEventAttendanceByEmpIdAndEventId(eventId, employeeDetails.getEmpDetailsId());
                
                if(eventAttendance != null)
                {
                    response = eventAttendanceServices.updateAttendance(eventId, employeeDetails.getEmpDetailsId());
                }
                else
                {
                    String campaign = cookie.replace("\"", "");
                    Event event = eventsServices.selectEventByEventIdAndCapaign(eventId, campaign);
                    
                    EventAttendance eAttendance = new EventAttendance();
                    eAttendance.setEmployeeDetails(employeeDetails);
                    eAttendance.setState("Going");
                    eAttendance.setEvent(event);
                    
                    eventAttendanceServices.saveEventAttendance(eAttendance);
                    event.getEventAttendance().add(eAttendance);
                    response.put("isGoing", "Going");
                    response.put("eventAttendanceCreated", HttpStatus.OK);
                    
                }
                
            }
        }
        
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
