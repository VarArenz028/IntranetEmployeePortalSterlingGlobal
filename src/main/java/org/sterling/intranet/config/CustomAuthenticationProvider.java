package org.sterling.intranet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.User;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    private UserServices userServices;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException 
    {
        String username = a.getName();
        String password = a.getCredentials().toString().trim();
        System.out.println("Authentication provider " + username);
        System.out.println("Authentication provider " + username);
        System.out.println("Authentication provider " + username);
        System.out.println("Authentication provider " + username);
        System.out.println("Authentication provider " + username);
        System.out.println("Authentication provider " + username);
        System.out.println("Authentication provider " + username);
        System.out.println("Authentication provider " + username);
        System.out.println("Authentication provider " + username);
        
        User user = userServices.selectUserByUsername(username);
        
        return null;
    }

    @Override
    public boolean supports(Class<?> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
