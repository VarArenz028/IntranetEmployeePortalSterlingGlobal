package org.sterling.intranet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// This controlls the log-in templates
@Controller
public class LoginTemplatesController 
{
    @RequestMapping(value = "/login-page-layout")
    public String getLoginPageLayout()
    {
        return "loginPageTemplates/login-page-layout";
    }
    @RequestMapping(value = "/userLoginTemplate")
    public String getLoginForm()
    {
        return "loginPageTemplates/userLoginTemplate";
    }
    @RequestMapping(value = "/userCampaignTemplate")
    public String getCampaignTemplate()
    {
        return "loginPageTemplates/userCampaignTemplate";
    }
    @RequestMapping(value = "empDetailsFormLayout")
    public String getEmpDetailsFormLayout()
    {
        return "loginPageTemplates/empDetailsFormLayout";
    }
    @RequestMapping(value = "yourDetails")
    public String getYourDetails()
    {
        return "loginPageTemplates/yourDetails";
    }
}
