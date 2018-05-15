package org.sterling.intranet.testing.purpose;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.UserServices;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
public class TestingController 
{
    @Autowired
    private UserServices userServices;
    
    @RequestMapping(value = "/campaign-admin/**", method = RequestMethod.GET)
    public ResponseEntity<?> getTestData()
    {
        Object name = "Name";
        Map<String, Object> response = new HashMap<String, Object>();
        
        response.put("naaaaaaaaaaaaaaaaaaaaaaame", userServices.SelectUserById(6));
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
