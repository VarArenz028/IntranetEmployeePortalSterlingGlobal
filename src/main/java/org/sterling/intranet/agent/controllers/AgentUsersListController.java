package org.sterling.intranet.agent.controllers;

import org.sterling.intranet.campaign.admin.controllers.*;
import java.util.ArrayList;
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
import org.sterling.intranet.interfaces.AccountServices;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.NewsFeedServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserAvatarServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.Account;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.NewsFeed;
import org.sterling.intranet.models.User;

/**
 *
 * @author  Var Arenz Villarino
 */
@RestController
@RequestMapping("/agent")
public class AgentUsersListController
{
    
    
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @Autowired
    private UserAvatarServices userAvatarServices;
    
    @Autowired
    private AccountServices accountServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @RequestMapping(value = "/people", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> responseAllData(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            
            User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
            if(user != null)
            {
                EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
                response.put("selectedEmpDetails", employeeDetails);
            }
            
            List<Account> accounts = accountServices.selectAllCampaignByCampaignName(campaign);
            
            if(!accounts.isEmpty())
            {
                response.put("accounts", accounts);
                
                List<EmployeeDetails> listOfEmployee = new ArrayList();
                
                for(Account account : accounts)
                {
                    EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(account.getUser().getUserId());
                    
                    listOfEmployee.add(employeeDetails);
                    
                }
                response.put("listOfEmployee", listOfEmployee);
                
            }
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/people", params = "empDetailsId", method = RequestMethod.GET)
    public ResponseEntity<?> selectOneEmployeeDetails(@RequestParam(name = "empDetailsId") Integer empDetailsId)
    {
        
        Map<String, Object> response = new HashMap<String, Object>();
        
        EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetalsByEmpId(empDetailsId);
        
        if(employeeDetails != null)
        {
            response.put("selectedEmpDetails", employeeDetails);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
