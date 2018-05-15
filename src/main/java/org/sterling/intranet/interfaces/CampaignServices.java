package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.Campaign;

public interface CampaignServices 
{
    
    Campaign findCampaignById(Integer id);
    
    Campaign findByCampaignName(Campaign campaign);
    
    Campaign selectCampaignByName(String campaign);
    
    List<Campaign> getSelectedCampaign(Integer id);
    
    List<Campaign> getSelectedCampaigns(List<Integer> list);
    
    boolean findByCampaigName(String campaign);
    
    void saveCampaign(Campaign campaign);
    
    void updateCampaign(Campaign campaign);
    
    List<Campaign> getCampaignList();
    
    List<Campaign> selectAllCampaignName();
    
}
