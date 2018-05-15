package org.sterling.intranet.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.CampaignDao;
import org.sterling.intranet.interfaces.CampaignServices;
import org.sterling.intranet.models.Campaign;

@Service("campaignServices")
public class CampaignServicesImpl implements CampaignServices
{
    @Autowired
    private CampaignDao campaignDao;
    
    @Transactional(readOnly = true)
    @Override
    public Campaign findCampaignById(Integer id)
    {
        return campaignDao.findCampaignById(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean findByCampaigName(String campaign) 
    {
        return campaignDao.findByCampaigName(campaign);
    }
    @Transactional(readOnly = true)
    @Override
    public Campaign findByCampaignName(Campaign campaign) 
    {
        return campaignDao.findByCampaignName(campaign);
    }
    @Transactional(readOnly = true)
    @Override
    public Campaign selectCampaignByName(String campaign) 
    {
        return campaignDao.selectCampaignByName(campaign);
    }
    @Transactional(readOnly = false)
    @Override
    public void saveCampaign(Campaign campaign)
    {
        campaignDao.saveCampaign(campaign);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void updateCampaign(Campaign campaign)
    {
//        Campaign camp = campaignDao.findCampaignById(campaign.getCampaignId());
//        if(camp != null)
//        {
//            camp.setCampaignName(campaign.getCampaignName());
//            camp.setUsers(campaign.getUsers());
//        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Campaign> getCampaignList() 
    {
        return campaignDao.getCampaignList();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Campaign> getSelectedCampaign(Integer id)
    {
        return campaignDao.getSelectedCampaign(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Campaign> getSelectedCampaigns(List<Integer> list)
    {
        return campaignDao.getSelectedCampaigns(list);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Campaign> selectAllCampaignName() 
    {
        return campaignDao.selectAllCampaignName();
    }

    
    
}
