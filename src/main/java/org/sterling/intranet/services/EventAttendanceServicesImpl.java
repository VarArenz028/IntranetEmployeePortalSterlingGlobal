package org.sterling.intranet.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.EventAttendanceDao;
import org.sterling.intranet.interfaces.EventAttendanceServices;
import org.sterling.intranet.models.EventAttendance;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("eventAttendanceServices")
public class EventAttendanceServicesImpl implements EventAttendanceServices
{
    
    @Autowired
    private EventAttendanceDao eventAttendanceDao;

    @Transactional(readOnly = true)
    @Override
    public EventAttendance selectEventAttendanceByEmpIdAndEventId(String eventId, Integer empId) 
    {
        return eventAttendanceDao.selectEventAttendanceByEmpIdAndEventId(eventId, empId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<EventAttendance> selectAllAttendanceByEventId(String eventId) 
    {
        return eventAttendanceDao.selectAllAttendanceByEventId(eventId);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveEventAttendance(EventAttendance eventAttendance) 
    {
        eventAttendanceDao.saveEventAttendance(eventAttendance);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateAttendance(String eventId, Integer empid) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        EventAttendance eventAttendance = eventAttendanceDao.selectEventAttendanceByEmpIdAndEventId(eventId, empid);
        
        if(eventAttendance.getState().equalsIgnoreCase("Going"))
        {
            eventAttendance.setState("Not going");
            response.put("isGoing", "Not going");
        }
        else if(eventAttendance.getState().equalsIgnoreCase("Not going"))
        {
            eventAttendance.setState("Going");
            response.put("isGoing", "Going");
        }
        response.put("attendanceUpdated", HttpStatus.OK);
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public EventAttendance selectStateInEventByEmpDetailsIdAndEventId(Integer empDetailsId, String eventId)
    {
        return eventAttendanceDao.selectStateInEventByEmpDetailsIdAndEventId(empDetailsId, eventId);
    }
    
}
