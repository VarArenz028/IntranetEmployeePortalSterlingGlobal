package org.sterling.intranet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Controller
@RequestMapping(value = "/")
public class LoginPageController
{
    @RequestMapping(method = RequestMethod.GET)
    public String getloginPage()
    {
        return "loginPage";
    }
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage()
    {
        return "accessDenied";
    }
}
