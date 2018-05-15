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
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.DraftArticleServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.DraftArticle;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.User;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class MyDraftArticlesController
{
    
    @Autowired
    private DraftArticleServices draftArticleServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @RequestMapping(value = "/my-draft-articles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllDraftArticles(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        if(user != null)
        {
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
            if(employeeDetails != null)
            {
                if(!cookie.isEmpty())
                {
                    String campaign = cookie.replace("\"", "");
                    List<DraftArticle> draftArticles = draftArticleServices.selectAllDraftArticles(employeeDetails.getEmpDetailsId(), campaign);
                    if(!draftArticles.isEmpty())
                    {
                        response.put("numberOfDraftArticle", draftArticles.size());
                        response.put("draftArticles", draftArticles);
                        response.put("responsedDraftArticle", HttpStatus.OK);
                    }
                    else if(draftArticles.isEmpty())
                    {
                        response.put("responsedDraftArticle", HttpStatus.NO_CONTENT);
                    }
                }
            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
