package org.sterling.intranet.campaign.admin.controllers;

import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.interfaces.DraftArticleServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.model.json.CommonArticle;
import org.sterling.intranet.model.json.DraftArticleJson;
import org.sterling.intranet.models.DraftArticle;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.User;
import org.sterling.intranet.utils.DateStamp;

/**
 *
 * @author Var Arenz Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class MyArticlesController
{
    
    @Autowired
    private ArticleWritingServices articleWritingServices;
    
    @Autowired
    private DraftArticleServices draftArticleServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    
    @RequestMapping(value = "/my-articles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllData(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        try
        {
            
            User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
            if(user != null || !cookie.isEmpty())
            {
                EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
                if(employeeDetails != null)
                {
                    String campaign = cookie.replace("\"", "");
                    List<Article> articles = articleWritingServices.getAllArticleForCurrentUser(employeeDetails.getEmpDetailsId(), campaign);
                    response.put("articles", articles);
                    response.put("numberOfArticles", articles.size());
                    response.put("articleRecentlyAdded", articleWritingServices.getArticleRecentlyAdded());
                }
                else if(employeeDetails == null)
                {
                    response.put("noCurrentUser", HttpStatus.NOT_FOUND);
                }

            }
            else if(user == null)
            {
                response.put("noCurrentUser", HttpStatus.NOT_FOUND);
            }
            
        }
        catch(NullPointerException exception)
        {
            response.put("noCurrentUser", HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/my-articles/{articleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> selectArticle(@PathVariable("articleId") String articleId)
    {
        CommonArticle commonArticle = new CommonArticle();
        Article article = articleWritingServices.selectArticleById(articleId);
        
//        String encodeArticle = Base64.getEncoder().encodeToString(article.getContent());
//        
//        commonArticle.setTitle(article.getTitle());
//        commonArticle.setContent(encodeArticle);
//        commonArticle.setCategory(article.getCategory());
//        if(article == null)
//        {
//            // do something
//        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("article", article);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
@RequestMapping(value = "/my-articles", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> saveAsDrafts(@CookieValue("currentCampaign") String cookie, @RequestBody DraftArticleJson draftArticleJson) throws ParseException
    {
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        Map<String, Object> response = new HashMap<String, Object>();
        String campaign = cookie.replace("\"", "");
        if(user != null)
        {
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
            if(employeeDetails != null)
            {
                
                DateStamp dateStamp = new DateStamp();
                DraftArticle newDraftArticle = new DraftArticle();
                newDraftArticle.setCampaign(campaign);
                newDraftArticle.setDateCreated(dateStamp.getDate());
                newDraftArticle.setLocalDateCreated(dateStamp.getLocalDateTime());
                newDraftArticle.setDateString(dateStamp.getDateString());
                newDraftArticle.setEmployeeDetails(employeeDetails);
                draftArticleServices.saveDraft(newDraftArticle);

                employeeDetails.getDraftArticles().add(newDraftArticle);

                response.put("getDraftArticleId", newDraftArticle.getDraftArticleId());
                response.put("initialArticleCreated", HttpStatus.CREATED);
                
            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
