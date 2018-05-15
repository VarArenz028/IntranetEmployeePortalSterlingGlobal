package org.sterling.intranet.campaign.admin.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.ApprovedArticleServices;
import org.sterling.intranet.interfaces.ArticleRejectionServices;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.NumberOfArticleServices;
import org.sterling.intranet.interfaces.PageViewServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserAvatarServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.ApprovedArticle;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.ArticleRejection;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.NumberOfArticle;
import org.sterling.intranet.models.PageView;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.UserAvatar;
import org.sterling.intranet.utils.DateStamp;

/**
 *
 * @author Var Arenz Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class PendingArticlesController 
{
    
    @Autowired
    private ArticleRejectionServices articleRejectionServices;
    
    @Autowired
    private ArticleWritingServices articleWritingServices; 
    
    @Autowired
    private ApprovedArticleServices approvedArticleServices;
    
    @Autowired
    private NumberOfArticleServices numberOfArticleServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @Autowired
    private PageViewServices pageViewServices;
    
    @Autowired
    private UserAvatarServices userAvatarServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @RequestMapping(value = "/pending-articles", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllPendingArticles(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            
            List<Article> pendingArticles = articleWritingServices.listAllPendingArticleByCampaignName(campaign, "Pending");
            if(!pendingArticles.isEmpty())
            {
                response.put("pendingArticles", pendingArticles);
                response.put("hasPendingArticles", HttpStatus.OK);
            }
            else if(pendingArticles.isEmpty())
            {
                response.put("hasPendingArticles", HttpStatus.NO_CONTENT);
            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/pending-articles/{article}", params = "article", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> selectPendingArticle(@RequestParam(name = "article") String articleId, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            Article article = articleWritingServices.selectArticleByIdCampaignAndState(articleId, campaign, "Pending");

            if(article != null)
            {
                EmployeeDetails employeeDetails = employeeDetailsServices.selectOneEmployeeDetaalsByEmpId(article.getEmployeeDetails().getEmpDetailsId());
                UserAvatar userAvatar = userAvatarServices.selectOneUserAvatarByEmpId(article.getEmployeeDetails().getEmpDetailsId());
                response.put("onePendingArticle", article);
                response.put("employeeDetails", employeeDetails);
                response.put("userAvatar", userAvatar);
            }
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/pending-articles/{articleId}", params = "approve", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> approveArticle(@RequestParam(name = "approve") String articleId, @CookieValue("currentCampaign") String cookie) throws ParseException
    {
        Map<String, Object> response = new HashMap<String, Object>();
        String campaign = cookie.replace("\"", "");

        response = articleWritingServices.approveArticle(articleId, campaign);
        if(response.get("articleApproved").equals(HttpStatus.OK))
        {
            User user = userServices.selectOneUserByUsername(pricipalSecurityServices.getPrincipal());
            
            EmployeeDetails employeeDetails = employeeDetailsServices.selectNameOfEmployeeByUserId(user.getUserId());
            
            Article article = articleWritingServices.selectArticleById(articleId);
            if(article != null)
            {
                DateStamp dateStamp = new DateStamp();
                ApprovedArticle approvedArticle = new ApprovedArticle();

                approvedArticle.setApprovedBy(employeeDetails.getLastName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getLastName().substring(1).toLowerCase()
                                                    + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getFirstName().substring(1).toLowerCase());

                approvedArticle.setApprovedDate(dateStamp.getDate());
                approvedArticle.setArticle(article);
                

                approvedArticleServices.saveApprovedArticle(approvedArticle);
            
                response.put("apprvedArticleCreated", HttpStatus.OK);
                
                NumberOfArticle hasNumOfArticle = numberOfArticleServices.selectNumberOfArticleByEmpId(article.getEmployeeDetails().getEmpDetailsId());

                if(hasNumOfArticle != null)
                {
                    response = numberOfArticleServices.updateNumberOfArticle(article.getEmployeeDetails().getEmpDetailsId());
                    PageView pageView = new PageView();
                    pageView.setArticle(article);
                    pageView.setCampaign(campaign);
                    pageViewServices.saveView(pageView);
                }
                else
                {
                    EmployeeDetails authorDetails = employeeDetailsServices.selectEmployeeDetalsByEmpId(article.getEmployeeDetails().getEmpDetailsId());
                    NumberOfArticle numberOfArticle = new NumberOfArticle();
                    numberOfArticle.setNumberOfArticle(1);
                    numberOfArticle.setCampaign(campaign);
                    numberOfArticle.setEmployeeDetails(authorDetails);
                    numberOfArticleServices.saveNumberOfArticle(numberOfArticle);

                    authorDetails.setNumberOfArticle(numberOfArticle);
                    
                    authorDetails.getArticles().add(article);
                    PageView pageView = new PageView();
                    pageView.setArticle(article);
                    pageView.setCampaign(campaign);
                    pageViewServices.saveView(pageView);

                    response.put("numberOfArticleEntityAdded", HttpStatus.OK);
                }
                response.put("articleApproved", HttpStatus.OK);
                
            }
            
            
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/pending-articles/{articleId}", params = {"articleId", "reason"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> rejectArticle(@RequestParam(name = "articleId") String articleId, @RequestParam(name = "reason") String reason) throws ParseException
    {
        
        Map<String, Object> response = new HashMap<String, Object>();
        
        response = articleWritingServices.rejectArticle(articleId);
        
        Article article = articleWritingServices.selectArticleById(articleId);
        
        if(article != null)
        {
            User user = userServices.selectOneUserByUsername(pricipalSecurityServices.getPrincipal());
            if(user != null)
            {
                EmployeeDetails employeeDetails = employeeDetailsServices.selectNameOfEmployeeByUserId(user.getUserId());
                if(employeeDetails != null)
                {
                    response = articleWritingServices.rejectArticle(articleId);
                    if(articleWritingServices.rejectArticle(articleId).get("articleRejected").equals(HttpStatus.OK))
                    {
                        DateStamp dateStamp = new DateStamp();
                        ArticleRejection articleRejection = new ArticleRejection();
                        articleRejection.setReason(reason);
                        articleRejection.setRejectedBy(employeeDetails.getLastName().substring(0,1).toUpperCase() + 
                                                employeeDetails.getLastName().substring(1).toLowerCase()
                                                + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
                                                employeeDetails.getFirstName().substring(1).toLowerCase());
                        articleRejection.setArticle(article);
                        articleRejection.setDateRejeted(dateStamp.getDate());
                        articleRejectionServices.saveArticleRejection(articleRejection);

                        article.setArticleRejection(articleRejection);

                        response.put("rejectionSaved", HttpStatus.OK);
                    }
                    else
                    {
                        // do something here
                    }
                    
                    
                }
            }
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
