package org.sterling.intranet.campaign.admin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.exceptions.DataExistException;
import org.sterling.intranet.interfaces.AccountServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserAvatarServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.Account;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.UserAvatar;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class MyProfileController
{
//    
//    @Autowired
//    private ProfilePictureServices profilePictureServices;
    
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
    
    @RequestMapping(value = "/my-profile", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllMyEmpDetails()
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
        response.put("userDetails", user);
        response.put("empDetails", employeeDetails);
        
        
        UserAvatar userAvatar = userAvatarServices.selectUserAvatarByEmpId(employeeDetails.getEmpDetailsId());
        
        if(userAvatar != null)
        {
            response.put("employeeAvatar", userAvatar);
            response.put("hasAnAvatar", HttpStatus.OK);
        }
        else
        {
            response.put("noAvatar", HttpStatus.OK);
            response.put("hasAnAvatar", HttpStatus.NO_CONTENT);
        }
        
        List<Account> accounts = accountServices.selectAllUserAccount(user.getUserId());
        if(!accounts.isEmpty())
        {
            response.put("accounts", accounts);
        }
        else if(!accounts.isEmpty())
        {
            response.put("noAccount", HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/my-profile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadAvatar(@RequestBody UserAvatar userAvatar)
    {
        
        Map<String, Object> response = new HashMap<String, Object>();
        
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
        
        UserAvatar uAvatar = userAvatarServices.selectUserAvatarByEmpId(employeeDetails.getEmpDetailsId());
        
        if(uAvatar != null)
        {
            response = userAvatarServices.updateAvatar(userAvatar.getFileType(), userAvatar.getFileName(), userAvatar.getBase64(), uAvatar.getAvatarId());
            UserAvatar responseAvatar = userAvatarServices.selectUserAvatarByEmpId(employeeDetails.getEmpDetailsId());
            response.put("userAvatar", responseAvatar);
        }
        else
        {
            UserAvatar newUserAvatar = new UserAvatar();
            newUserAvatar.setFileName(userAvatar.getFileName());
            newUserAvatar.setBase64(userAvatar.getBase64());
            newUserAvatar.setFileType(userAvatar.getFileType());
            newUserAvatar.setEmployeeDetails(employeeDetails);
            userAvatarServices.saveAvatar(newUserAvatar);
            
            employeeDetails.setUserAvatar(newUserAvatar);
            employeeDetailsServices.setAvatar(newUserAvatar, employeeDetails.getEmpDetailsId());
            response.put("userAvatar", newUserAvatar);
        }
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/my-profile", params = "password", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validatePassword(@RequestParam(value = "password") String password) throws DataExistException
    {
        Map<String, Object> response = new HashMap<String, Object>();
        boolean result = userServices.findByPassword(password);
        if(result == true)
        {
            response.put("validateUsername", HttpStatus.CONFLICT);
        }
        else
        {
            response.put("validateUsername", HttpStatus.OK);
        }
        
        return new ResponseEntity(response, HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/my-profile", params = "password", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updatePassword(@RequestParam(name = "password", required = false) String password)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        response = userServices.updatePassword(user.getUserId(), password);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/my-profile", params = "position", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updatePosition(@RequestParam(name = "position") String position)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        response = employeeDetailsServices.updatePosition(user.getUserId(), position);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/my-profile", params = "email", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updateEmail(@RequestParam(name = "email") String email)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        response = employeeDetailsServices.updateEmail(user.getUserId(), email);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/my-profile", params = "contactNumber", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updateContactNumber(@RequestParam(name = "contactNumber") String contactNumber)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        response = employeeDetailsServices.updateContactNumber(user.getUserId(), contactNumber);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/my-profile", params = "gender", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updateGender(@RequestParam(name = "gender") String gender)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        response = employeeDetailsServices.updateGender(user.getUserId(), gender);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//    @RequestMapping(value = "/my-profile", method = RequestMethod.POST)
//    public ResponseEntity<ProfilePicture> updateProfilePicture(@RequestBody ProfilePicture profilePicture, UriComponentsBuilder builder)
//    {
//        
//        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
//        
//        user.setProfilePicture(profilePicture);
//        
//        profilePictureServices.saveProfilePicture(profilePicture);
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(builder.path("/my-profile/{pictureId}").buildAndExpand(profilePicture.getPictureId()).toUri());
//        
//        return new ResponseEntity<ProfilePicture>(headers, HttpStatus.OK);
//    }
    
}
