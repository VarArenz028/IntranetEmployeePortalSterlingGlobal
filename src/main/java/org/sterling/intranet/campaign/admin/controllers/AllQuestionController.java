package org.sterling.intranet.campaign.admin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.QuestionServices;
import org.sterling.intranet.models.Question;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class AllQuestionController 
{
    
    @Autowired
    private QuestionServices questionServices;
    
    @RequestMapping(value = "/all-question", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllQuestion(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            List<Question> questions = questionServices.selectAllQuestionByCampaignNameAndState(campaign, "Active");
            if(!questions.isEmpty())
            {
                response.put("questions", questions);
            }
            else if(questions.isEmpty())
            {
                response.put("noQuestions", HttpStatus.NO_CONTENT);
            }
        }
        else if(cookie.isEmpty())
        {
            response.put("emptyCampaignCookie", HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/all-question", params = "inactiveQuestion", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> inactiveQuestion(@RequestParam(name = "inactiveQuestion", required = false) String inactiveQuestion, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            response = questionServices.inactiveQuestionByQuestionIdStateAndCampaign(inactiveQuestion, "Inactive", campaign);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
