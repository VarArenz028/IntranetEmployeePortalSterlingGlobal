package org.sterling.intranet.campaign.admin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.DraftNewsServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.NewsServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.DraftNews;
import org.sterling.intranet.models.EmployeeDetails;

/**
 *
 * @author Var Arenz Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class MyDraftNewsController 
{
    @Autowired
    private NewsServices newsServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private DraftNewsServices draftNewsServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @RequestMapping(value = "/draft-news", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllDraftNews(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        org.sterling.intranet.models.User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");

            if(user != null)
            {
                EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
                if(employeeDetails != null)
                {
                    List<DraftNews> draftNewses = draftNewsServices.selectAllDraftNewsByCampaignName(campaign, employeeDetails.getEmpDetailsId());
                    if(!draftNewses.isEmpty())
                    {
                        response.put("numberOfDraftNews", draftNewses.size());
                        response.put("draftNewses", draftNewses);
                        response.put("hasDraftNews", HttpStatus.OK);

                    }
                }
            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
