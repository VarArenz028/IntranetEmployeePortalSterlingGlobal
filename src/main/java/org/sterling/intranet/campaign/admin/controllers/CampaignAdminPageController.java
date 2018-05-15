package org.sterling.intranet.campaign.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Var Arenz G. Villarino
 */

// This controller will load the campaign admin main pages
@Controller
@RequestMapping("/campaign-admin")
public class CampaignAdminPageController
{
    @RequestMapping(method = RequestMethod.GET)
    public String campaignAdminPage()
    {
        return "campaignAdminPage";
    }
}
