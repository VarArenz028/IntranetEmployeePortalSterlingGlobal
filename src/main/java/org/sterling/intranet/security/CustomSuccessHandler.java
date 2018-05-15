package org.sterling.intranet.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.security.SecurityUtils;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
    @Autowired
    private UserServices userServices;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    throws ServletException, IOException 
    {
       org.sterling.intranet.models.User user = userServices.selectUserByUsername(authentication.getName());
       SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, user);
    }
    
}
