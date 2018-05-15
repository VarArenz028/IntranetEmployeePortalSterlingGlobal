package org.sterling.intranet.system.admin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.exceptions.DataExistException;
import org.sterling.intranet.exceptions.ErrorResponse;
import org.sterling.intranet.interfaces.AccountServices;
import org.sterling.intranet.interfaces.CampaignServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.Campaign;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.model.json.NewUser;
import org.sterling.intranet.models.Account;
/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/system-admin")
public class SystemAdminPeopleController
{
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @Autowired
    private AccountServices accountServices;
    
    @Autowired
    private CampaignServices campaignServices;
    
    @RequestMapping(value = "/people", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDetails()
    {
        Map<String, Object> response = new HashMap<String, Object>();
        List<User> user = userServices.getAllUser();
        List<Campaign> campaigns = campaignServices.getCampaignList();
//        List<EmployeeDetails> employeeDetails = employeeDetailsServices.getAllEmployeeDetails();
        if(!user.isEmpty())
        {
            response.put("users", user);
            response.put("usersResponse", HttpStatus.OK);
        }
        else if(user.isEmpty())
        {
            response.put("usersResponse", HttpStatus.NO_CONTENT);
        }
        if(!campaigns.isEmpty())
        {
            response.put("campaigns", campaigns);
            response.put("campaignResponse", HttpStatus.OK);
        }
        else if(campaigns.isEmpty())
        {
            response.put("campaignResponse", HttpStatus.NO_CONTENT);
        }
        if(!campaigns.isEmpty())
        {
//            response.put("employeeDetails", employeeDetails);
            response.put("employeeDetailsResponse", HttpStatus.OK);
        }
        else if(campaigns.isEmpty())
        {
            response.put("employeeDetailsResponse", HttpStatus.NO_CONTENT);
        }
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/people/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSelectedUser(@PathVariable("id") Integer userId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.SelectUserById(userId);
        if(user != null)
        {
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(userId);
            if(employeeDetails != null)
            {
                List<Account> accounts = accountServices.selectAllUserAccount(userId);
                List<Campaign> campaigns = campaignServices.getCampaignList();
                response.put("user", user);
                response.put("employeeDetails", employeeDetails);
                if(!accounts.isEmpty())
                {
                    response.put("accounts", accounts);
                    response.put("hasAccounts", HttpStatus.OK);
                }
                else if(accounts.isEmpty())
                {
                    response.put("noAccount", HttpStatus.NO_CONTENT);
                }
                if(!campaigns.isEmpty())
                {
                    response.put("campaigns", campaigns);
                    response.put("campaignResponse", HttpStatus.OK);
                }
                else if(campaigns.isEmpty())
                {
                    response.put("campaignResponse", HttpStatus.NO_CONTENT);
                }
            }
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/people/", "/people/{id}/"}, params = "username", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateByUsername(@RequestParam(value = "username") String username) throws DataExistException
    {
        
        boolean result = userServices.findByUsername(username);
        if(result == true)
        {
            throw new DataExistException(username + " already exist");
        }
        
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = {"/people/", "/people/{id}/"}, params = "password", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateByPassword(@RequestParam(value = "password") String password) throws DataExistException
    {
        
        boolean result = userServices.findByPassword(password);
        
        if(result == true)
        {
            throw new DataExistException(password + " already exist");
        }
        
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/people/{id}/", params = {"account", "userId"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateAccount(@RequestParam(value = "account") String account, @RequestParam(value = "userId") Integer userId) throws DataExistException
    {
        
        boolean result = accountServices.selectAcountByUserId(account, userId);
        Map<String, Object> response = new HashMap<String, Object>();
        if(result == true)
        {
            response.put("validatingAccount", HttpStatus.CONFLICT);
        }
        else
        {
            response.put("validatingAccount", HttpStatus.OK);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/people", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody NewUser newUser)
    {
        User user = new User();
        EmployeeDetails employeeDetails = new EmployeeDetails();
        Account account = new Account();
        
        
        
        if(newUser.getUser().getRole().equalsIgnoreCase("System Admin"))
        {
            employeeDetails.setLastName(newUser.getEmployeeDetails().getLastName());
            employeeDetails.setFirstName(newUser.getEmployeeDetails().getFirstName());
            user.setUsername(newUser.getUser().getUsername());
            user.setPassword(newUser.getUser().getPassword());
            user.setRole(newUser.getUser().getRole());
            userServices.saveUser(user);
            employeeDetails.setUser(user);
            employeeDetailsServices.saveUserDetails(employeeDetails);
        }
        else if(newUser.getUser().getRole().equalsIgnoreCase("Campaign Admin") || newUser.getUser().getRole().equalsIgnoreCase("Agent"))
        {
            employeeDetails.setLastName(newUser.getEmployeeDetails().getLastName());
            employeeDetails.setFirstName(newUser.getEmployeeDetails().getFirstName());
            user.setUsername(newUser.getUser().getUsername());
            user.setPassword(newUser.getUser().getPassword());
            user.setRole(newUser.getUser().getRole());
            account.setAccountName(newUser.getCampaign());
            userServices.saveUser(user);
            employeeDetails.setUser(user);
            account.setUser(user);

            employeeDetailsServices.saveUserDetails(employeeDetails);
            accountServices.saveAccount(account);
        }
        
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
//    @RequestMapping(value = "/people/{id}", params = {"campaign", "userId"}, method = RequestMethod.POST)
//    public ResponseEntity<?> addCampaignForUser(@RequestParam(name = "campaign") String Campaign, @RequestParam(name = "userId") Integer userId)
//    {
//        return null;
//    }
    @RequestMapping(value = "/people/{id}", params = {"campaign", "userId"}, method = RequestMethod.POST)
    public ResponseEntity<?> addCampaign(@RequestParam(name = "campaign", required = false) String Campaign, @RequestParam(name = "userId", required = false) Integer userId)
    {
        User user = userServices.SelectUserById(userId);
        Map<String, Object> response = new HashMap<String, Object>();
        if(user != null)
        {
            Account account = new Account();
            account.setAccountName(Campaign);
            account.setUser(user);
            user.getAccounts().add(account);
            accountServices.saveAccount(account);
            response.put("successfullyAdded", HttpStatus.OK);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/people/{id}", params = {"account", "userId"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updateAccountState(@RequestParam(name = "account") String account, @RequestParam(name = "userId") Integer userId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        response = accountServices.updateAccountState(account, userId);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/people/{id}", params = {"username", "userId"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updateUsername(@RequestParam(name = "username") String username, @RequestParam(name = "userId") Integer userId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        response = userServices.updateUsername(userId, username);
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/people/{id}", params = {"password", "userId"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updatePassword(@RequestParam(name = "password", required = false) String password, @RequestParam(name = "userId", required = false) Integer userId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        response = userServices.updatePassword(userId, password);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/people/{id}", params = {"role", "userId"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updateRole(@RequestParam(name = "role") String role, @RequestParam(name = "userId") Integer userId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        response = userServices.updateRole(userId, role);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/people/{id}", params = {"lastName", "userId"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updateLastName(@RequestParam(name = "lastName") String lastName, @RequestParam(name = "userId") Integer userId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        response = employeeDetailsServices.updateLastName(userId, lastName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/people/{id}", params = {"firstName", "userId"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updateFirstName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "userId") Integer userId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        response = employeeDetailsServices.updateFirstName(userId, firstName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/people/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User user)
    {
        
//        User u = userServices.SelectUserById(user.getUserId());
//        
//        u.setUsername(user.getUsername());
//        u.setPassword(user.getPassword());
//        u.setRole(user.getRole());
//        u.setCampaigns(user.getCampaigns());
//
//        userServices.updateUser(u);
        
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @ExceptionHandler(DataExistException.class)
    public ResponseEntity<ErrorResponse> dataExist(Exception e)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        
        errorResponse.setErrorCode(HttpStatus.CONFLICT.value());
        errorResponse.setErrorMessage(e.getMessage());
        
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
    }
}











//    @RequestMapping(value = "/people", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<UserCampaignWrapper>> listAllDetails()
//    {
//        
//        List<User> users = userServices.getAllUser();
//        List<Campaign> campaigns = campaignServices.getCampaignList();
//        
//        UserCampaignWrapper userCampaignWrapper = new UserCampaignWrapper();
//        userCampaignWrapper.setUsers(users);
//        userCampaignWrapper.setCampaigns(campaigns);
//        List<UserCampaignWrapper> list = new ArrayList();
//        list.add(userCampaignWrapper);
//        System.out.println(pricipalSecurityServices.getPrincipal() + " System Admin");
//        
//        return new ResponseEntity<List<UserCampaignWrapper>>(list, HttpStatus.OK);
//        
//    }





//    @RequestMapping(value = "/people", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> saveUser(@RequestBody UserRegistration userRegistration, UriComponentsBuilder builder) throws DataExistException
//    {
//        UserRegistrationBuilder handler = new UserRegistrationBuilder(userRegistration);
//        handler.setUserInformation();
//        handler.getUser().setCampaigns(campaignServices.getSelectedCampaign(userRegistration.getCampaignsId()));
//        
//        userServices.saveUser(handler.getUser());
//
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(builder.path("/people/{userId}").buildAndExpand(handler.getUser().getUserId()).toUri());
//        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//    }