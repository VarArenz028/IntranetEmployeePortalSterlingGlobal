package org.sterling.intranet.system.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemAdminTemplateController 
{
    @RequestMapping(value = "saDashboard")
    public String getSystemAdminDashboardTemplate()
    {
        return "systemAdminTemplates/saDashboard";
    }
    @RequestMapping(value = "saCampaign")
    public String getSystemAdminCampaignTemplate()
    {
        return "systemAdminTemplates/saCampaign";
    }
    @RequestMapping(value = "user-layouts")
    public String getSystemAdminPeopleTemplate()
    {
        return "systemAdminTemplates/peopleLayoutFolder/user-layouts";
    }
    @RequestMapping(value = "user-list")
    public String getSystemAdminUserListTemplate()
    {
        return "systemAdminTemplates/peopleLayoutFolder/user-list";
    }
    @RequestMapping(value = "saEditUser")
    public String getSystemAdminEditPeopleTemplate()
    {
        return "systemAdminTemplates/peopleLayoutFolder/saEditUser";
    }
    @RequestMapping(value = "saFile")
    public String getSystemAdminFileTemplate()
    {
        return "systemAdminTemplates/saFile";
    }
    @RequestMapping(value = "saArticle")
    public String getSystemAdminArticleTemplate()
    {
        return "systemAdminTemplates/saArticle";
    }
}
