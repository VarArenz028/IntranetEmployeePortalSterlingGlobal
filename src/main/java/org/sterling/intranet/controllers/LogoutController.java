package org.sterling.intranet.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Controller
public class LogoutController 
{
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) 
    {
        HttpSession httpSession = request.getSession(false);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        System.out.println(auth.getPrincipal());
        System.out.println(auth.getPrincipal());
        System.out.println(auth.getPrincipal());
        System.out.println(auth.getPrincipal());
        if (auth != null)
        {    
            if(httpSession != null)
            {
                httpSession.invalidate();
            }
            System.out.println("User has benn logout");
            System.out.println("User has benn logout");
            System.out.println("User has benn logout");
            System.out.println("User has benn logout");
            System.out.println("User has benn logout");
            System.out.println("User has benn logout");
            SecurityContextHolder.clearContext();
            auth.setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(null);
            System.out.println(auth.getPrincipal() + " cleared");
            System.out.println(auth.getPrincipal() + " cleared");
            System.out.println(auth.getPrincipal() + " cleared");
            System.out.println(auth.getPrincipal() + " cleared");
            System.out.println(auth.getPrincipal() + " cleared");
            
            
            new SecurityContextLogoutHandler().logout(request, response, auth);
            
        }
        return "redirect:/?logout";
    }
}
