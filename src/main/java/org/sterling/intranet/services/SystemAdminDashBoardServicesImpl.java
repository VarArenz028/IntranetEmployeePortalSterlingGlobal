package org.sterling.intranet.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.AccountDao;
import org.sterling.intranet.interfaces.CampaignDao;
import org.sterling.intranet.interfaces.SystemAdminDashboardServices;
import org.sterling.intranet.interfaces.UserDao;
import org.sterling.intranet.model.json.CampaignStatistics;
import org.sterling.intranet.models.Account;
import org.sterling.intranet.models.Campaign;
import org.sterling.intranet.servicesBuilder.SystemAdminPieBuilder;

@Service("systemAdminDashboardServices")
public class SystemAdminDashBoardServicesImpl implements SystemAdminDashboardServices
{
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private CampaignDao campaignDao;
    
    @Autowired
    private AccountDao accountDao;

    @Transactional(readOnly = true)
    @Override
    public int numbersOfusers() 
    {
        return userDao.getNumberOfUser();
    }

    @Transactional(readOnly = true)
    @Override
    public int numbersOfCampaign()
    {
        return campaignDao.numbersOfCampaign();
    }

    @Transactional(readOnly = true)
    @Override
    public int numberOfSystemAdmins()
    {
        return userDao.numberOfSystemAdmins();
    }

    @Transactional(readOnly = true)
    @Override
    public int numberOfCampaignAdmins() 
    {
        return userDao.numberOfCampaignAdmins();
    }

    @Transactional(readOnly = true)
    @Override
    public int numberOfAgents() 
    {
        return userDao.numberOfAgents();
    }

    @Transactional(readOnly = true)
    @Override
    public List<SystemAdminPieBuilder> getCampaignsAndUsers() 
    {
        List<SystemAdminPieBuilder> caUserList = new ArrayList();
        List<Campaign> campListDetails = campaignDao.getCampaignList();
        
        for(Campaign l : campListDetails)
        {
            
//            SystemAdminPieBuilder pieBuilder = new SystemAdminPieBuilder();
//            pieBuilder.setCampaignname(l.getCampaignName());
//            pieBuilder.setSizeOfUser(l.getUsers().size());
//            caUserList.add(pieBuilder);

        }

        return caUserList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CampaignStatistics> getCampaignStatistics() 
    {
        
        List<Campaign> campaigns = campaignDao.selectAllCampaignName();
        
        List<CampaignStatistics> campaignStatisticses = new ArrayList();
        
        for(Campaign campaign : campaigns)
        {
            List<Account> numOfUser = accountDao.selectAllCampaignName(campaign.getCampaignName());
            CampaignStatistics campaignStatistics = new CampaignStatistics();
            campaignStatistics.setCampaignName(campaign.getCampaignName());
            campaignStatistics.setNumberOfUser(numOfUser.size());
            
            campaignStatisticses.add(campaignStatistics);
            
        }
        
        
        return campaignStatisticses;
    }
    
    
    
}
