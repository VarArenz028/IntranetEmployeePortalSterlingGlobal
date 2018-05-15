package org.sterling.intranet.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.Article;

/**
 *
 * @author Var Arenz Villarino
 */
public interface ArticleWritingDao 
{
    
    Integer selectEmpIdByArticleId(String articleId);
    
    List<Article> getAllArticle();
    
    Map<String, Object> selectAllAndUpdateArticleByArticleIdListAndCampaign(List<String> articleId, String category, String campaign);
    
    List<Article> selectAllArticleByCampaignAndState(String campaign, String state);
    
    List<Article> getAllArticleForCurrentUser(Integer empId, String campaign);
    
    List<Article> getAllArticleNewsFeedData();
    
    List<Article> getAllArticleByCampaignName(String campaign);
    
    Article selectArticleById(String articleId);
    
    Article selectPendingArticle(String articleId, String state, String campaign);
    
    Article findArticleById(String articleId);
    
    Article selectOnlyStateByArticleId(String articleId);
    
    Article selectOneArticleByArticleId(String articleId);
    
    Article selectToBeUpdateByArticleId(String articleId);
    
    List<Article> selectArticleByCampaignAndState(String campaign, String state);
    
    List<Article> selectAllArticleByCampaignStateAndCategory(String campaign, String state, String category);
    
    List<Article> selectAllPinToTopArticle(String campaign, String pushpin);
    
    List<Article> selectAllNumOfPendingArticleByCampaignAndState(String campaign, String state);
    
    Article selectArticleByIdCampaignAndState(String articleId, String campaign, String state);
    
    Article selectTitleByArticleId(String articleId);
    
    void saveArticle(Article article);
    
    List<Article> getArticleRecentlyAdded();
   
    List<Article> articleStatistics(String campaignName, String state, String category);
    
    List<Article> selectTopFiveMostRecentlyAdded(String campaignName);
    
    List<Article> listArticleByCampaignNameState(String campaign, String state);
    
    List<Article> listArticleByCampaignNameStateAndEmpId(String campaign, String state, Integer empId);
    
    List<Article> listAllPendingArticleByCampaignName(String campaign, String state);
    
    List<Article> articleSearch(String articleSearch);
    
    void index() throws Exception;
    
    Map<String, Object> updateArticleCategoryByCampaignAndCatagoryName(String campaign, String newCategory, String oldCategory);
    
    Map<String, Object> updateArticleCategoryIntoUnassignedByCampaignAndCatagoryName(String campaign, String deletedCategory, String unassigned);
    
    Map<String, Object> inactiveArticleByArticleIdAndCampaign(String articleId, String campaign, String state);
    
}
