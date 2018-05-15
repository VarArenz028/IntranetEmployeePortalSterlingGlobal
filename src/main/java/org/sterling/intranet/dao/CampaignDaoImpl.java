package org.sterling.intranet.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.CampaignDao;
import org.sterling.intranet.models.Campaign;
import org.sterling.intranet.models.User;

@Repository("campaignDao")
public class CampaignDaoImpl extends AbstractDao<Integer, Campaign> implements CampaignDao
{

    @Override
    public Campaign findCampaignById(Integer id) 
    {
        Query query = getSession().createQuery("from Campaign where campaignId = :campaignId");
        query.setParameter("campaignId", id);
        
        Campaign campaign = (Campaign) query.uniqueResult();
        
        return campaign;
    }
    @Override
    public boolean findByCampaigName(String campaign)
    {
        boolean result = false;

        Query query = getSession().createQuery("from Campaign where campaignName = :campaignName");
        query.setParameter("campaignName", campaign);
        
        Campaign campResult = (Campaign) query.uniqueResult();

        if(campResult != null)
        {
            result = true;
        }
        else
        {
            result = false;
        }
        
        return result;
    }
    
    @Override
    public Campaign selectCampaignByName(String campaign) 
    {
        Query query = getSession().createQuery("from Campaign where campaignName = :campaignName");
        query.setParameter("campaignName", campaign);
        
        Campaign campResult = (Campaign) query.uniqueResult();
        return campResult;
    }
    
    @Override
    public Campaign findByCampaignName(Campaign campaign) 
    {
        Query query = getSession().createQuery("from Campaign where campaignName = :campaignName");
        query.setParameter("campaignName", campaign.getCampaignName());
        
        Campaign result = (Campaign) query.uniqueResult();
        
        return result;
    }
    @Override
    public void saveCampaign(Campaign campaign) 
    {
        save(campaign);
    }

    @Override
    public List<Campaign> getCampaignList() 
    {
        
        Criteria criteria = createCriteria();
        List<Campaign> list = criteria.list();
  
        return list;
    }

    @Override
    public List<Campaign> getSelectedCampaign(Integer id)
    {
        List<Campaign> result = new ArrayList();
        
        Query query = getSession().createQuery("from Campaign where campaignId = :campaignId");
        query.setParameter("campaignId", id);
        
        Campaign campaign = (Campaign) query.uniqueResult();
        
        result.add(campaign);
        
        return result;
    }
    
    @Override
    public List<Campaign> getSelectedCampaigns(List<Integer> list)
    {
        List<Campaign> result = new ArrayList();
        
//        Criteria criteria = getSession().createCriteria("");
//        criteria.add(Restrictions.in("campaignId", list));
//        result = criteria.list();
        
        return result;
    }

    @Override
    public int numbersOfCampaign()
    {
        int size;
        Query query = getSession().createQuery("select c from Campaign c");
        List<Campaign> list = query.list();
        size = list.size();
        return size;
    }

    @Override
    public List<Campaign> selectAllCampaignName()
    {
        Criteria criteria = getSession().createCriteria(Campaign.class)
                          .setProjection(Projections.projectionList()
                          .add(Projections.property("campaignName"), "campaignName"))
                          .setResultTransformer(Transformers.aliasToBean(Campaign.class));
        List<Campaign> list = criteria.list();
        return list;
    }

}
