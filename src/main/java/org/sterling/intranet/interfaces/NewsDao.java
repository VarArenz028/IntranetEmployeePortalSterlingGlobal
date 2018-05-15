package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.News;

/**
 *
 * @author Var Arenz Villarino
 */
public interface NewsDao
{
    
    Integer selectEmpIdByNewsId(String newsId);
    
    News selectNewsByCampaignAndNewsId(String campaign, String newsId);
    
    List<News> selectAllNewsByCampaignName(String campaignName);
    
    List<News> selectAllNewsByCampaignNameAndState(String campaignName, String state);
    
    List<News> selectAllSpecificAttbsByCampaignName(String campaign);
    
    void saveNews(News news);
    
    Map<String, Object> inactiveNewsByNewsIdAndCampaign(String newsId, String state, String campaign);
    
}
