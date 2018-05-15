package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.model.json.CampaignStatistics;
import org.sterling.intranet.servicesBuilder.SystemAdminPieBuilder;

public interface SystemAdminDashboardServices
{
    int numbersOfusers();
    int numbersOfCampaign();
    int numberOfSystemAdmins();
    int numberOfCampaignAdmins();
    int numberOfAgents();
    List<SystemAdminPieBuilder> getCampaignsAndUsers();
    
    List<CampaignStatistics> getCampaignStatistics();
    
}
