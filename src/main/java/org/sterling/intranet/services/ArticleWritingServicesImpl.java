package org.sterling.intranet.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.AccountDao;
import org.sterling.intranet.interfaces.ArticleCategoryDao;
import org.sterling.intranet.interfaces.ArticleRecentlyRevisedDao;
import org.sterling.intranet.interfaces.ArticleRejectionDao;
import org.sterling.intranet.interfaces.ArticleWritingDao;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.interfaces.EmployeeDetailsDao;
import org.sterling.intranet.interfaces.PageViewDao;
import org.sterling.intranet.model.json.UpdateArticleJson;
import org.sterling.intranet.models.Account;
import org.sterling.intranet.models.ApprovedArticle;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.ArticleCategory;
import org.sterling.intranet.models.ArticleMostRecentlyAdded;
import org.sterling.intranet.models.ArticleRecentlyRevised;
import org.sterling.intranet.models.ArticleRejection;
import org.sterling.intranet.models.CategoryStatistics;
import org.sterling.intranet.models.DashboardArticleAuthor;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.PageStatistic;
import org.sterling.intranet.models.PageView;
import org.sterling.intranet.utils.ArticleAuthorComparator;
import org.sterling.intranet.utils.DateStamp;
import org.sterling.intranet.utils.PageStatisticsComparator;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("articleWritingServices")
public class ArticleWritingServicesImpl implements ArticleWritingServices
{

    @Autowired
    private ArticleWritingDao articleWritingDao; 
    
    @Autowired
    private ArticleCategoryDao articleCategoryDao;
    
    @Autowired
    private PageViewDao pageViewDao;
    
    @Autowired
    private EmployeeDetailsDao employeeDetailsDao;
    
    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private ArticleRecentlyRevisedDao articleRecentlyRevisedDao;
    
    @Autowired
    private ArticleRejectionDao articleRejectionDao;
    
    @Transactional(readOnly = true)
    @Override
    public List<Article> getAllArticle() 
    {
        return articleWritingDao.getAllArticle();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Article> getAllArticleForCurrentUser(Integer userId, String campaign) 
    {
        return articleWritingDao.getAllArticleForCurrentUser(userId, campaign);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Article selectArticleById(String articleId) 
    {
        return articleWritingDao.selectArticleById(articleId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Article selectArticleByIdCampaignAndState(String articleId, String campaign, String state) 
    {
        return articleWritingDao.selectArticleByIdCampaignAndState(articleId, campaign, state);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Article> getAllArticleByCampaignName(String campaign) 
    {
        return articleWritingDao.getAllArticleByCampaignName(campaign);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveArticle(Article article) 
    {
        articleWritingDao.saveArticle(article);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> getArticleRecentlyAdded()
    {
        return articleWritingDao.getArticleRecentlyAdded();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> getAllArticleNewsFeedData()
    {
        return articleWritingDao.getAllArticleNewsFeedData();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> selectArticleByCampaignAndState(String campaign, String state)
    {
        return articleWritingDao.selectArticleByCampaignAndState(campaign, state);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updatePushPin(String articleId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        Article article = articleWritingDao.findArticleById(articleId);
        if(article.getPushpin().equalsIgnoreCase("Unpin"))
        {
            article.setPushpin("Pin");
            response.put("pushPin", "pin");
        }
        else if(article.getPushpin().equalsIgnoreCase("Pin"))
        {
            article.setPushpin("Unpin");
            response.put("pushPin", "unpin");
        }
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> selectAllPinToTopArticle(String campaign, String pushpin)
    {
        return articleWritingDao.selectAllPinToTopArticle(campaign, pushpin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryStatistics> articleStatistics(String campaignName, String state) 
    {
        List<CategoryStatistics> categoryStatisticses = new ArrayList();
        List<ArticleCategory> articleCategory = articleCategoryDao.fetchAllCategoriesByCampaignName(campaignName);
        
        for(ArticleCategory articleCat : articleCategory)
        {
//            String campName = campaignName;
//            String articleState = state;
            
            List<Article> articleStats = articleWritingDao.articleStatistics(campaignName, state, articleCat.getCategoryName().trim());
            
            CategoryStatistics categoryStatistics = new CategoryStatistics();
            
            categoryStatistics.setAccountName(articleCat.getCategoryName());
            
            if(!articleStats.isEmpty())
            {
                categoryStatistics.setNumberOfArticle(articleStats.size());
            }
            else
            {
                categoryStatistics.setNumberOfArticle(0);
            }
            categoryStatisticses.add(categoryStatistics);
        }
        
        return categoryStatisticses;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DashboardArticleAuthor> articleContributers(String campaignName, String state) 
    {
        List<Account> accounts = accountDao.selectAllCampaignByCampaignName(campaignName);
        List<DashboardArticleAuthor> dashboardArticleAuthors = new ArrayList();
        
        for(Account account : accounts)
        {
            EmployeeDetails employeeDetails = employeeDetailsDao.selectEmployeeDetailsByUserId(account.getUser().getUserId());
            
            List<Article> articles = articleWritingDao.listArticleByCampaignNameStateAndEmpId(campaignName, state, employeeDetails.getEmpDetailsId());
            
            DashboardArticleAuthor dashboardArticleAuthor = new DashboardArticleAuthor();
            
            dashboardArticleAuthor.setLastName(employeeDetails.getLastName());
            dashboardArticleAuthor.setFirstName(employeeDetails.getFirstName());
            dashboardArticleAuthor.setNumberOfArticle(articles.size());
            dashboardArticleAuthors.add(dashboardArticleAuthor);
        }
        Collections.sort(dashboardArticleAuthors, new ArticleAuthorComparator());
        Collections.reverse(dashboardArticleAuthors);
        
        return dashboardArticleAuthors;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PageStatistic> articlePageStatistics(String campaignName, String state) 
    {
        List<Article> articles = articleWritingDao.selectArticleByCampaignAndState(campaignName, state);
        
        List<PageStatistic> pageStatistics = new ArrayList();
        
        for(Article article : articles)
        {
            PageView pageView = pageViewDao.selectPageViewByArticleId(article.getArticleId());
            
            PageStatistic pageStatistic = new PageStatistic();
            pageStatistic.setArticleId(pageView.getArticle().getArticleId());
            pageStatistic.setArticleName(pageView.getArticle().getTitle());
            pageStatistic.setArticleStatistics((int) pageView.getView());
            
            pageStatistics.add(pageStatistic);
            
            Collections.sort(pageStatistics, new PageStatisticsComparator());
            Collections.reverse(pageStatistics);
            
        }
        
        return pageStatistics;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<ArticleMostRecentlyAdded> selectTopFiveMostRecentlyAdded(String campaignName) 
    {
        List<Article> articles = articleWritingDao.selectTopFiveMostRecentlyAdded(campaignName);
        
        List<ArticleMostRecentlyAdded> articleMostRecentlyAddeds = new ArrayList();
        
        for(Article article : articles)
        {
            ArticleMostRecentlyAdded articleMostRecentlyAdded = new ArticleMostRecentlyAdded();
            articleMostRecentlyAdded.setArticleId(article.getArticleId());
            articleMostRecentlyAdded.setArticleName(article.getTitle());
            articleMostRecentlyAdded.setDate(article.getDateCreated());
            
            articleMostRecentlyAddeds.add(articleMostRecentlyAdded);
            
        }
        return articleMostRecentlyAddeds;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> listAllPendingArticleByCampaignName(String campaign, String state)
    {
        return articleWritingDao.listAllPendingArticleByCampaignName(campaign, state);
    }

    @Transactional(readOnly = true)
    @Override
    public Article selectPendingArticle(String articleId, String state, String campaign) 
    {
        return articleWritingDao.selectPendingArticle(articleId, state, campaign);
    }

    @Transactional(readOnly = true)
    @Override
    public Article selectTitleByArticleId(String articleId) 
    {
        return articleWritingDao.selectTitleByArticleId(articleId);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> approveArticle(String articleId, String campaign)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        Article article = articleWritingDao.findArticleById(articleId);
        

        ArticleRejection articleRejection = articleRejectionDao.selectArticleRejectionByArticleId(articleId);
        
        if(articleRejection != null)
        {
            try 
            {
                DateStamp dateStamp = new DateStamp();
                ArticleRecentlyRevised articleRecentlyRevised = new ArticleRecentlyRevised();
                articleRecentlyRevised.setArticleId(article.getArticleId());
                articleRecentlyRevised.setTitle(article.getTitle());
                articleRecentlyRevised.setDateRevised(dateStamp.getDate());
                articleRecentlyRevised.setCampaign(campaign);
                articleRecentlyRevisedDao.saveRecentlyRevised(articleRecentlyRevised);
            }
            catch (ParseException ex) 
            {
                Logger.getLogger(ArticleWritingServicesImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        article.setState("Approved");
        response.put("articleApproved", HttpStatus.OK);
        
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> rejectArticle(String articleId) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        Article article = articleWritingDao.findArticleById(articleId);
        article.setState("Rejected");
        response.put("articleRejected", HttpStatus.OK);
        
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public Article selectOneArticleByArticleId(String articleId) 
    {
        return articleWritingDao.selectOneArticleByArticleId(articleId);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> setApprovedArticle(String articleId, ApprovedArticle approvedArticle)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        Article article = articleWritingDao.selectOneArticleByArticleId(articleId);
        
        article.setApprovedArticle(approvedArticle);
        response.put("apprvedCreated", HttpStatus.OK);
        
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateArticle(String title, String category, String content, String articleId) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        Article article = articleWritingDao.findArticleById(articleId);
        
        article.setTitle(title);
        article.setCategory(category);
        article.setContent(content);
        
        response.put("articleUpdated", HttpStatus.OK);
        
        
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateState(String articleId) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        Article article = articleWritingDao.selectArticleById(articleId);
        
        article.setState("Pending");
        response.put("articleUpdated", HttpStatus.OK);
        
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> selectAllNumOfPendingArticleByCampaignAndState(String campaign, String state) 
    {
        return articleWritingDao.selectAllNumOfPendingArticleByCampaignAndState(campaign, state);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer selectEmpIdByArticleId(String articleId) 
    {
        return articleWritingDao.selectEmpIdByArticleId(articleId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> selectAllArticleByCampaignAndState(String campaign, String state) 
    {
        return articleWritingDao.selectAllArticleByCampaignAndState(campaign, state);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> articleSearch(String articleSearch)
    {
        return articleWritingDao.articleSearch(articleSearch);
    }

    @Transactional
    @Override
    public void index() throws Exception
    {
        articleWritingDao.index();
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateArticleCategoryByCampaignAndCatagoryName(String campaign, String newCategory, String oldCategory)
    {
        return articleWritingDao.updateArticleCategoryByCampaignAndCatagoryName(campaign, newCategory, oldCategory);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateArticleCategoryIntoUnassignedByCampaignAndCatagoryName(String campaign, String deletedCategory, String unassigned)
    {
        return articleWritingDao.updateArticleCategoryIntoUnassignedByCampaignAndCatagoryName(campaign, deletedCategory, unassigned);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> selectAllAndUpdateArticleByArticleIdListAndCampaign(List<String> articleId, String category, String campaign) 
    {
        return articleWritingDao.selectAllAndUpdateArticleByArticleIdListAndCampaign(articleId, category, campaign);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> inactiveArticleByArticleIdAndCampaign(String articleId, String campaign, String state) 
    {
        return articleWritingDao.inactiveArticleByArticleIdAndCampaign(articleId, campaign, state);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> selectAllArticleByCampaignStateAndCategory(String campaign, String state, String category)
    {
        return articleWritingDao.selectAllArticleByCampaignStateAndCategory(campaign, state, category);
    }

}
