package org.sterling.intranet.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.NewsDao;
import org.sterling.intranet.interfaces.NewsServices;
import org.sterling.intranet.models.News;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("newsServices")
public class NewsServicesImpl implements NewsServices
{

    @Autowired
    private NewsDao newsDao;
    
    @Transactional(readOnly = true)
    @Override
    public News selectNewsByCampaignAndNewsId(String campaign, String newsId) 
    {
        return newsDao.selectNewsByCampaignAndNewsId(campaign, newsId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<News> selectAllNewsByCampaignName(String campaignName) 
    {
        return newsDao.selectAllNewsByCampaignName(campaignName);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveNews(News news)
    {
        newsDao.saveNews(news);
    }

    @Transactional(readOnly = true)
    @Override
    public List<News> selectAllNewsByCampaignNameAndState(String campaignName, String state) 
    {
        return newsDao.selectAllNewsByCampaignNameAndState(campaignName, state);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> inactiveNewsByNewsIdAndCampaign(String newsId, String state, String campaign) 
    {
        return newsDao.inactiveNewsByNewsIdAndCampaign(newsId, state, campaign);
    }
    
}
