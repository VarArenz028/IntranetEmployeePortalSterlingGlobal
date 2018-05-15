package org.sterling.intranet.campaign.admin.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.AccountServices;
import org.sterling.intranet.interfaces.ArticleCategoryServices;
import org.sterling.intranet.interfaces.ArticleRecentlyRevisedServices;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.NumberOfArticleServices;
import org.sterling.intranet.interfaces.PageViewServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.QuestionServices;
import org.sterling.intranet.interfaces.SearchServices;
import org.sterling.intranet.interfaces.UserAvatarServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.model.json.SearchModel;
import org.sterling.intranet.models.Account;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.ArticleCategory;
import org.sterling.intranet.models.ArticleMostRecentlyAdded;
import org.sterling.intranet.models.ArticleRecentlyRevised;
import org.sterling.intranet.models.AuthorArticleContribute;
import org.sterling.intranet.models.CategoryStatistics;
import org.sterling.intranet.models.DashboardArticleAuthor;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.PageStatistic;
import org.sterling.intranet.models.Question;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.UserAvatar;
import org.sterling.intranet.utils.CategoryStatisticsUtils;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class CampaignAdminDashboardController 
{
    
    @Autowired
    private ArticleWritingServices articleWritingServices;
    
    @Autowired
    private QuestionServices questionServices;
    
    @Autowired
    private ArticleCategoryServices articleCategoryServices;
    
    @Autowired
    private PageViewServices pageViewServices;
    
    @Autowired
    private AccountServices accountServices;
    
    @Autowired
    private NumberOfArticleServices numberOfArticleServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @Autowired
    private UserAvatarServices userAvatarServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @Autowired
    private ArticleRecentlyRevisedServices articleRecentlyRevisedServices;
    
    
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ResponseEntity<?> fetchDashboardData(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            
            User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
            UserAvatar userAvatar = userAvatarServices.selectUserAvatarByEmpId(employeeDetails.getEmpDetailsId());
            
            if(userAvatar != null)
            {
                response.put("userAvatar", userAvatar);
                response.put("hasAnAvatar", HttpStatus.OK);
            }
            else
            {
                response.put("hasAnAvatar", HttpStatus.NO_CONTENT);
            }
            
            List<CategoryStatistics> categoryStatisticses = articleWritingServices.articleStatistics(campaign, "Approved");
            if(!categoryStatisticses.isEmpty())
            {
                response.put("articleStatistics", categoryStatisticses);
                response.put("hasArticleStatistocs", HttpStatus.OK);
            }
            else
            {
                  response.put("hasArticleStatistocs", HttpStatus.NO_CONTENT);  
            }
            List<AuthorArticleContribute> authorArticleContributes = numberOfArticleServices.selectTopFiveContributersByCampaignName(campaign);
            if(!authorArticleContributes.isEmpty())
            {
                response.put("contributers", authorArticleContributes);
                response.put("hasContributers", HttpStatus.OK);
            }
            else
            {
                response.put("hasContributers", HttpStatus.NO_CONTENT);
            }
           
            
            
            List<PageStatistic> mostViewList = pageViewServices.articleMostViewStatistics(campaign);
            if(!mostViewList.isEmpty())
            {
                response.put("mostView", mostViewList);
                response.put("hasMostView", HttpStatus.OK);
            }
            else if(mostViewList.isEmpty())
            {
                response.put("hasMostView", HttpStatus.NO_CONTENT);
            }
            
            List<ArticleMostRecentlyAdded> articleMostRecentlyAddeds = articleWritingServices.selectTopFiveMostRecentlyAdded(campaign);
            if(!articleMostRecentlyAddeds.isEmpty())
            {
                response.put("articleMostRecentlyAddeds", articleMostRecentlyAddeds);
                response.put("hasMostRecentlyAdded", HttpStatus.OK);
            }
            else if(articleMostRecentlyAddeds.isEmpty())
            {
                response.put("hasMostRecentlyAdded", HttpStatus.NO_CONTENT);
            }
            
            List<ArticleRecentlyRevised> articleRecentlyReviseds = articleRecentlyRevisedServices.selectMostRecentlyRevisedByCampaignName(campaign);
            if(!articleRecentlyReviseds.isEmpty())
            {
                response.put("articleRecentlyReviseds", articleRecentlyReviseds);
                response.put("hasMostRecentlyRevised", HttpStatus.OK);
            }
            else if(articleRecentlyReviseds.isEmpty())
            {
                response.put("hasMostRecentlyRevised", HttpStatus.NO_CONTENT);
            }
//            List<PageStatistic> pageStatistics = articleWritingServices.articlePageStatistics(campaign, "Approved");
//            
//            if(!pageStatistics.isEmpty())
//            {
//                response.put("mostView", pageStatistics);
//                response.put("hasMostView", HttpStatus.OK);
//            }
//            else if(pageStatistics.isEmpty())
//            {
//                response.put("hasMostView", HttpStatus.NO_CONTENT);
//            }
            
            List<Account> accountList = accountServices.selectAllCampaignByCampaignName(campaign);
            if(!accountList.isEmpty())
            {
                response.put("numberOfUser", accountList.size());
                response.put("hasUser", HttpStatus.OK);
            }
            else
            {
                response.put("numberOfUser", 0);
                response.put("hasUser", HttpStatus.NO_CONTENT);
            }
            List<Article> articleList =  articleWritingServices.getAllArticleByCampaignName(campaign);
            if(!articleList.isEmpty())
            {
                response.put("numberOfArticle", articleList.size());
                response.put("hasArticle", HttpStatus.OK);
            }
            else
            {
                response.put("numberOfArticle", 0);
                response.put("hasArticle", HttpStatus.NO_CONTENT);
            }
            List<Question> questionList = questionServices.selectAllQuestionByCampaignName(campaign);
            if(!questionList.isEmpty())
            {
                response.put("numberOfQuestion", questionList.size());
                response.put("hasQuestion", HttpStatus.OK);
            }
            else
            {   
                response.put("numberOfQuestion", 0);
                response.put("hasQuestion", HttpStatus.NO_CONTENT);
            }
            List<ArticleCategory> categoryList = articleCategoryServices.fetchAllCategoriesByCampaignName(campaign);
            if(!categoryList.isEmpty())
            {
                response.put("numberOfCategory", categoryList.size());
                response.put("hasCategory", HttpStatus.OK);
            }
            else
            {
                response.put("numberOfCategory", 0);
                response.put("hasCategory", HttpStatus.NO_CONTENT);
            }
            List<Article> numOfPendingArticle = articleWritingServices.selectAllNumOfPendingArticleByCampaignAndState(campaign, "Pending");
            if(!numOfPendingArticle.isEmpty())
            {
                response.put("numberOfPendingArticle", numOfPendingArticle.size());
                response.put("hasPendingArticle", HttpStatus.OK);
            }
            else
            {
                response.put("numberOfPendingArticle", 0);
                response.put("hasPendingArticle", HttpStatus.NO_CONTENT);
            }
            
        }
        else if(cookie.isEmpty())
        {
            response.put("noCookie", HttpStatus.NOT_FOUND);
        }
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/dashboard", params = "show", method = RequestMethod.GET)
    public ResponseEntity<?> selectAllAuthorWithNumOfArticle(@RequestParam(name = "show") String show, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            if(show.equalsIgnoreCase("show"))
            {
                List<AuthorArticleContribute> allAuthorWithNumOfArticle = numberOfArticleServices.selectAllNumberOfArticleByCampaignName(campaign);
                if(!allAuthorWithNumOfArticle.isEmpty())
                {
                    if(!allAuthorWithNumOfArticle.isEmpty())
                    {
                        response.put("allAuthorWithNumOfArticle", allAuthorWithNumOfArticle);
                        response.put("hasAuthor", HttpStatus.OK);
                    }
                    else if(allAuthorWithNumOfArticle.isEmpty())
                    {
                        response.put("hasAuthor", HttpStatus.NO_CONTENT);
                    }
                }
            }
            
        
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
