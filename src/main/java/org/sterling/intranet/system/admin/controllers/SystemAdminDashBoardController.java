package org.sterling.intranet.system.admin.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.SystemAdminDashboardServices;
import org.sterling.intranet.model.json.CampaignStatistics;

@RestController
@RequestMapping("/system-admin")
public class SystemAdminDashBoardController 
{
    
    @Autowired
    private SystemAdminDashboardServices systemAdminDashboardServices;
    
    
    
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllDetails()
    {
        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("numberOfUser", systemAdminDashboardServices.numbersOfusers());
        response.put("numberOfCampaign", systemAdminDashboardServices.numbersOfCampaign());
        response.put("numberOfSystemAdmins", systemAdminDashboardServices.numberOfSystemAdmins());
        response.put("numberOfCampaignAdmin", systemAdminDashboardServices.numberOfCampaignAdmins());
        response.put("numberOfAgent", systemAdminDashboardServices.numberOfAgents());
        response.put("campaignAndNumberOfUser", systemAdminDashboardServices.getCampaignsAndUsers());
        
        List<CampaignStatistics> campaignStatisticses = systemAdminDashboardServices.getCampaignStatistics();
        
        if(!campaignStatisticses.isEmpty())
        {
            response.put("campaignStatistics", campaignStatisticses);
            response.put("hasCampaignStatistics", HttpStatus.OK);
        }
        else if(campaignStatisticses.isEmpty())
        {
            response.put("hasCampaignStatistics", HttpStatus.NO_CONTENT);
        }
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

}
