package org.sterling.intranet.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.NewsDao;
import org.sterling.intranet.models.News;

/**
 *
 * @author Var Arenz Villarino
 */
@Repository("newsDao")
public class NewsDaoImpl extends AbstractDao<Integer, News> implements NewsDao
{

    @Override
    public News selectNewsByCampaignAndNewsId(String campaign, String newsId) 
    {
        Query query = getSession().createQuery("from News where campaign = :campaign and newsId = :newsId");
        query.setParameter("campaign", campaign);
        query.setParameter("newsId", newsId);
        
        News news = (News) query.uniqueResult();
        
        return news;
    }
    
    @Override
    public List<News> selectAllNewsByCampaignName(String campaignName) 
    {
        Query query = getSession().createQuery("from News where campaign = :campaignName");
        query.setParameter("campaignName", campaignName);
        
        List<News> list = query.list();
        
        return list;
    }
    
    @Override
    public List<News> selectAllSpecificAttbsByCampaignName(String campaign) 
    {
        Criteria criteria = getSession().createCriteria(News.class)
            .add(Restrictions.eq("campaign", campaign))
                .setProjection(Projections.projectionList()
                .add(Projections.property("newsId"), "newsId")
                .add(Projections.property("newsTitle"), "newsTitle")
                .add(Projections.property("base64"), "base64")
                .add(Projections.property("fileType"), "fileType")
                .add(Projections.property("dateCreated"), "dateCreated"))
                .setResultTransformer(Transformers.aliasToBean(News.class));
        
        List<News> list = criteria.list();
        return list;
    }
    
    @Override
    public void saveNews(News news)
    {
        save(news);
    }

    @Override
    public Integer selectEmpIdByNewsId(String newsId) 
    {
        Query query = getSession().createQuery("Select n.employeeDetails.empDetailsId from News n where n.newsId = :newsId");
        query.setParameter("newsId", newsId);
        
        Integer empDetailsIs = (Integer) query.uniqueResult();
        
        return empDetailsIs;
    }

    @Override
    public List<News> selectAllNewsByCampaignNameAndState(String campaignName, String state)
    {
        Query query = getSession().createQuery("from News where state = :state and campaign = :campaignName");
        query.setParameter("campaignName", campaignName);
        query.setParameter("state", state);
        
        List<News> list = query.list();
        
        return list;
    }

    @Override
    public Map<String, Object> inactiveNewsByNewsIdAndCampaign(String newsId, String state, String campaign) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        Query query = getSession().createQuery("update News n set n.state = :state where n.newsId = :newsId and n.campaign = :campaign");
        query.setParameter("newsId", newsId);
        query.setParameter("state", state);
        query.setParameter("campaign", campaign);
        
        int update = query.executeUpdate();
        
        if(update != 0)
        {
            response.put("newsInactive", HttpStatus.OK);
        }
        else
        {
            response.put("newsInactive", HttpStatus.NOT_MODIFIED);
        }
        
        return response;
    }
    
}
