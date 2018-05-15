package org.sterling.intranet.controllers;

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
import org.sterling.intranet.exceptions.LoginAuthenticationException;
import org.sterling.intranet.interfaces.AccountServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.LoginServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserAvatarServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.model.json.EmpDetailsAvatarWrapper;
import org.sterling.intranet.models.Account;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.UserAvatar;

/**
 * 
 * @author Var Arenz G. Villarino
 */

// login controller
@RestController
@RequestMapping(value = "/")
public class LoginController 
{
    // access to log-in services
    @Autowired
    private LoginServices loginServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private AccountServices accountServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @Autowired
    private UserAvatarServices userAvatarServices;
    

    @RequestMapping(value = "/", params = "userId", method = RequestMethod.GET)
    public ResponseEntity<?> getUserAccounts(@RequestParam(name = "userId") Integer userId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Account> accounts = accountServices.selectAllUserAccount(userId);
        EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(userId);
        if(!accounts.isEmpty())
        {
            response.put("accounts", accounts);
            UserAvatar userAvatar = userAvatarServices.selectUserAvatarByEmpId(employeeDetails.getEmpDetailsId());
            response.put("userAvatar", userAvatar);
            
            String empName = employeeDetails.getLastName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getLastName().substring(1).toLowerCase()
                                                    + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getFirstName().substring(1).toLowerCase();
            response.put("empName", empName);
            
        }
        else if(accounts.isEmpty())
        {
            response.put("noAccount", HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/employee/details/", params = "username", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateByUsername(@RequestParam(value = "username") String username) throws DataExistException
    {
        
        boolean result = userServices.findByUsername(username);
        if(result == true)
        {
            throw new DataExistException(username + " already exist");
        }
        
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/employee/details/", params = "password", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateByPassword(@RequestParam(value = "password") String password) throws DataExistException
    {
        
        boolean result = userServices.findByPassword(password);
        
        if(result == true)
        {
            throw new DataExistException(password + " already exist");
        }
        
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/employee/details", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNewEmployeeDetails(@RequestBody EmpDetailsAvatarWrapper empDetailsAvatarWrapper)
    {
        
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
        UserAvatar userAvatar = new UserAvatar();
        if(user == null)
        {
            // direct to login page, note: not implemeted
            //
            response.put("notFound", HttpStatus.NOT_FOUND);
        }
        else
        {
            employeeDetailsServices.updateEmployeeDetails(user.getUserId(), empDetailsAvatarWrapper.getEmployeeDetails().getEmail(),
                            empDetailsAvatarWrapper.getEmployeeDetails().getPosition(), 
                              empDetailsAvatarWrapper.getEmployeeDetails().getGender(),
                              empDetailsAvatarWrapper.getEmployeeDetails().getContactNumber(), 
                              empDetailsAvatarWrapper.getEmployeeDetails().getAge(), user);
            
            userAvatar.setBase64(empDetailsAvatarWrapper.getUserAvatar().getBase64());
            userAvatar.setFileName(empDetailsAvatarWrapper.getUserAvatar().getFileName());
            userAvatar.setEmployeeDetails(employeeDetails);
            userServices.updateUserPassword(empDetailsAvatarWrapper.getUser().getPassword(), user.getUserId());
            userAvatarServices.saveAvatar(userAvatar);
            
            
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    
    // no user exist handler for log-in services
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> nullHandler(Exception e)
    {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setErrorMessage(e.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }
    @ExceptionHandler(LoginAuthenticationException.class)
    public ResponseEntity<ErrorResponse> inactiveException(Exception e)
    {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.FORBIDDEN.value());
        error.setErrorMessage(e.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
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





















































// if password is incorrect, unathorized in showing
//    @RequestMapping(value = "/{username}/{password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> login(@PathVariable("username") String username, @PathVariable("password") String password) throws NullPointerException,
//                      LoginAuthenticationException
//    {
//        User user  = loginServices.loginAuthentication(username.trim(), password.trim());
//        Map<String, Object> response = new HashMap<String, Object>();
//        if(user != null)
//        {
//            if(user.getRole().equalsIgnoreCase("System Admin"))
//            {
//                response.put("user", user);
//            }
//            else if(employeeDetailsServices.selectEmployeeDetailsById(user.getUserId()) == false && user.getRole().equalsIgnoreCase("Campaign Admin"))
//            {
//                response.put("user", user);
//                response.put("notFound", HttpStatus.NOT_FOUND);
//            }
//            else
//            {
//                response.put("user", user);
//                List<Account> accounts = accountServices.selectAllUserAccount(user.getUserId());
//                response.put("accounts", accounts);
//            }
//        }
//        else if(user == null)
//        {
//            throw new NullPointerException("Invalid username or password");
//        }
//        else if(user.getState().equalsIgnoreCase("Inactive"))
//        {
//            throw new LoginAuthenticationException("Your account is inactive");
//        }
//        
//        
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }



















































//     private String getPrincipal()
//     {
//        String userName = null;
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
// 
//        if (principal instanceof UserDetails) {
//            userName = ((UserDetails)principal).getUsername();
//        } else {
//            userName = principal.toString();
//        }
//        return userName;
//    }
//    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> loginAuthentication(@RequestBody User user) throws NullPointerException,
//                      LoginAuthenticationException
//    {
//        User userAuthentication = loginServices.userLoginAuthentication(user);
//        if(userAuthentication == null)
//        {
//            throw new NullPointerException("Invalid username or password");
//        }
//        else if(user.getState().equalsIgnoreCase("Inactive"))
//        {
//            throw new LoginAuthenticationException("Your account is inactive");
//        }
//        HttpHeaders headers = new HttpHeaders();
//        
//        return new ResponseEntity<User>(user, HttpStatus.OK);
//    }
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public void logoutPage (HttpServletRequest request, HttpServletResponse response) 
//    {
//        HttpSession httpSession = request.getSession(false);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth);
//        System.out.println(auth);
//        System.out.println(auth);
//        System.out.println(auth);
//        System.out.println(auth);
//        if (auth != null)
//        {    
//            if(httpSession != null)
//            {
//                httpSession.invalidate();
//            }
//            System.out.println("User has benn logout");
//            System.out.println("User has benn logout");
//            System.out.println("User has benn logout");
//            System.out.println("User has benn logout");
//            System.out.println("User has benn logout");
//            System.out.println("User has benn logout");
//            SecurityContextHolder.clearContext();
//            auth.setAuthenticated(false);
//            SecurityContextHolder.getContext().setAuthentication(null);
//            System.out.println(auth.getPrincipal());
//            System.out.println(auth.getPrincipal());
//            System.out.println(auth.getPrincipal());
//            System.out.println(auth.getPrincipal());
//            System.out.println(auth.getPrincipal());
//            //httpSession.invalidate();
//            new SecurityContextLogoutHandler().logout(request, response, null);
//        }
//    }
