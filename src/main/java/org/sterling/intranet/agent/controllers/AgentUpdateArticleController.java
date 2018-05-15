package org.sterling.intranet.agent.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.ArticleCategoryServices;
import org.sterling.intranet.interfaces.ArticleDocumentServices;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.interfaces.DraftArticleServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.NumberOfArticleServices;
import org.sterling.intranet.interfaces.PageViewServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.model.json.UpdateArticleJson;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.ArticleCategory;

/**
 *
 * @author Var Arenz Villarino
 */
@RestController
@RequestMapping("/agent")
public class AgentUpdateArticleController 
{
    @Autowired
    private ArticleWritingServices articleWritingServices;
    
    @Autowired
    private ArticleDocumentServices articleDocumentServices;
    
    @Autowired
    private ArticleCategoryServices articleCategoryServices;
    
    @Autowired
    private PageViewServices pageViewServices;
    
    @Autowired
    private DraftArticleServices draftArticleServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private NumberOfArticleServices numberOfArticleServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @RequestMapping(value = "/update-article", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchCategories(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> responseData = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            List<ArticleCategory> articleCategorys = articleCategoryServices.fetchAllCategoriesByCampaignName(campaign);
            responseData.put("categories", articleCategorys);
        }
        
        System.out.println(pricipalSecurityServices.getPrincipal() + " article writing");
        
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/update-article/{articleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> getArticle(@PathVariable("articleId") String articleId)
    {
        Article article = articleWritingServices.selectArticleById(articleId);
        
        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }
    @RequestMapping(value = {"/update-article/{articleId}"}, method = RequestMethod.PUT)
    public ResponseEntity<?> updateArticle(@RequestBody UpdateArticleJson updateArticleJson)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        response = articleWritingServices.updateArticle(updateArticleJson.getTitle(), updateArticleJson.getCategory(),
                          updateArticleJson.getContent(), updateArticleJson.getArticleId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = {"/update-article/{article}"}, params = "article", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> saveUpdatedArticle(@RequestParam(name = "article") String articleId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        response = articleWritingServices.updateState(articleId);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
