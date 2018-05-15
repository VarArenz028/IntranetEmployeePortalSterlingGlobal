package org.sterling.intranet.system.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/system-admin")
public class SystemAdminPageController 
{
    @RequestMapping(method = RequestMethod.GET)
    public String getSystemAdminPage()
    {
        return "systemAdminPage";
    }
}
//
//
//
//
//
//
//
//
//
//
//
//
//
////    @RequestMapping(value = "/dashboard/logout", method = RequestMethod.GET)
////    public void logoutPage (HttpServletRequest request, HttpServletResponse response) 
////    {
////        HttpSession httpSession = request.getSession(false);
////        
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        if (auth != null)
////        {    
////            httpSession.invalidate();
////            new SecurityContextLogoutHandler().logout(request, response, auth);
////        }
////    }
