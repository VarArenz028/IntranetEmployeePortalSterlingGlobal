package org.sterling.intranet.agent.controllers;

import org.sterling.intranet.campaign.admin.controllers.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.QuestionServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.Question;
import org.sterling.intranet.models.User;
import org.sterling.intranet.utils.DateStamp;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/agent/questions")
public class AgentMyQuestionController 
{
    
    @Autowired
    private QuestionServices questionServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @RequestMapping(value = "/my-questions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMyQuestionData(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        if(user != null || !cookie.isEmpty())
        {
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
            if(employeeDetails != null)
            {
                String campaign = cookie.replace("\"", "");
                List<Question> questions = questionServices.selectAllQuestionByEmpIdStateAndCampaignName(employeeDetails.getEmpDetailsId(), "Active", campaign);
                if(!questions.isEmpty())
                {
                    response.put("myQuestions", questions);
                    response.put("sizeOfUserQuestions", questions.size());
                }
                else if(questions.isEmpty())
                {
                    response.put("noQuestion", HttpStatus.NO_CONTENT);
                }
                
            }
            else
            {
                response.put("noCurrentUser", HttpStatus.NOT_FOUND);
            }
        }
        else if(user == null)
        {
            response.put("noCurrentUser", HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/my-questions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question, @CookieValue("currentCampaign") String cookie, UriComponentsBuilder builder) throws ParseException, ParseException
    {
        System.out.println(pricipalSecurityServices.getPrincipal());
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
        String campaign = cookie.replace("\"", "");
        DateStamp dateStamp = new DateStamp();
   
        employeeDetails.getQuestions().add(question);
        question.setEmployeeDetails(employeeDetails);
        question.setLocalDateAsked(dateStamp.getLocalDateTime());
        question.setDateCreated(dateStamp.getDate());
        question.setDateString(dateStamp.getDateString());
        question.setCampaign(campaign);
        question.setState("Active");
        questionServices.saveQuestion(question);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/my-questions/{questionId}").buildAndExpand(question.getQuestionId()).toUri());
        
        return new ResponseEntity<Question>(headers, HttpStatus.OK);
    }
}
