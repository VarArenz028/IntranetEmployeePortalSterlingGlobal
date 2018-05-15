package org.sterling.intranet.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.sterling.intranet.model.json.UpdateArticleJson;
import org.sterling.intranet.models.ApprovedArticle;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.ArticleMostRecentlyAdded;
import org.sterling.intranet.models.CategoryStatistics;
import org.sterling.intranet.models.DashboardArticleAuthor;
import org.sterling.intranet.models.PageStatistic;

/**
 *
 * @author Var Arenz Villarino
 */
public interface ArticleWritingServices
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
    
    Article selectTitleByArticleId(String articleId); 
    
    Article selectOneArticleByArticleId(String articleId);
    
    List<Article> selectArticleByCampaignAndState(String campaign, String state);
    
    List<Article> selectAllArticleByCampaignStateAndCategory(String campaign, String state, String category);
    
    List<Article> selectAllPinToTopArticle(String campaign, String pushpin);
    
    List<Article> selectAllNumOfPendingArticleByCampaignAndState(String campaign, String state);
    
    Article selectArticleByIdCampaignAndState(String articleId, String campaign, String state);
    
    void saveArticle(Article article);
    
    List<Article> getArticleRecentlyAdded();
    
    List<DashboardArticleAuthor> articleContributers(String campaignName, String state);
    
    List<PageStatistic> articlePageStatistics(String campaignName, String state);
    
    List<CategoryStatistics> articleStatistics(String campaignName, String state);
    
    List<ArticleMostRecentlyAdded> selectTopFiveMostRecentlyAdded(String campaignName);
    
    List<Article> listAllPendingArticleByCampaignName(String campaign, String state);
    
    Map<String, Object> updatePushPin(String articleId);
    
    Map<String, Object> approveArticle(String articleId, String campaign);
    
    Map<String, Object> rejectArticle(String articleId);
    
    Map<String, Object> setApprovedArticle(String articleId, ApprovedArticle approvedArticle);
    
    Map<String, Object> updateArticle(String title, String category, String content, String articleId);
    
    Map<String, Object> updateState(String articleId);
    
    List<Article> articleSearch(String articleSearch);
    
    void index() throws Exception;
    
    Map<String, Object> updateArticleCategoryByCampaignAndCatagoryName(String campaign, String newCategory, String oldCategory);
    
    Map<String, Object> updateArticleCategoryIntoUnassignedByCampaignAndCatagoryName(String campaign, String deletedCategory, String unassigned);
    
    Map<String, Object> inactiveArticleByArticleIdAndCampaign(String articleId, String campaign, String state);
    
}
