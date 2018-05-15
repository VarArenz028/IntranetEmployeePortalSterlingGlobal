package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.Campaign;

public interface CampaignDao
{
    
    Campaign findCampaignById(Integer id);
    
    Campaign findByCampaignName(Campaign campaign);
    
    Campaign selectCampaignByName(String campaign);
    
    List<Campaign> getSelectedCampaign(Integer id);
    
    List<Campaign> getSelectedCampaigns(List<Integer> list);
    
    boolean findByCampaigName(String campaign);
    
    void saveCampaign(Campaign campaign);
    
    List<Campaign> getCampaignList();
    
    int numbersOfCampaign();
    
    List<Campaign> selectAllCampaignName();
    
}
