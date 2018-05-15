package org.sterling.intranet.campaign.admin.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.ArticleCategoryServices;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.ArticleCategory;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class AllArticlesController
{

    @Autowired
    private ArticleWritingServices articleWritingServices;
    
    @Autowired
    private ArticleCategoryServices articleCategoryServices;

    @RequestMapping(value = "/all-article", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllArticle(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            List<Article> articles = articleWritingServices.selectAllArticleByCampaignAndState(campaign, "Approved");
            List<ArticleCategory> categories = articleCategoryServices.fetchAllCategoriesByCampaignName(campaign);
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setCategoryName("All Article");
            categories.add(articleCategory);
            if(!articles.isEmpty())
            {
                response.put("articles", articles);
                response.put("categories", categories);
            }
            else
            {
                response.put("emptyArticle", HttpStatus.NO_CONTENT);
            }

        }
        else if(cookie.isEmpty())
        {
            response.put("emptyCookie", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/all-article", params = "article", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOneArticle(@RequestParam(name = "article") String articleId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        try
        {
            Article article = articleWritingServices.selectArticleById(articleId);
            if(article != null)
            {
                response.put("article", article);
            }
        }
        catch(NullPointerException e)
        {
            response.put("articleNotFount", HttpStatus.NOT_FOUND);
        }
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @ResponseBody
    @RequestMapping(value = "/all-article", params = "article", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> pinToTopArticle(@RequestParam(name = "article", required = false) String articleId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        try
        {
            Article article = articleWritingServices.selectArticleById(articleId);
            if(article != null)
            {
                response = articleWritingServices.updatePushPin(articleId);
            }
        }
        catch(NullPointerException e)
        {
            response.put("articleNotFount", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/all-article", params = "inactiveArticle", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> inactiveArticle(@RequestParam(name = "inactiveArticle", required = false) String inactiveArticle,
                      @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            response = articleWritingServices.inactiveArticleByArticleIdAndCampaign(inactiveArticle, campaign, "Inactive");
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/all-article", params = "articleCategory", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> selectedArticleCategory(@RequestParam(name = "articleCategory", required = false) String articleCategory,
                      @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            
            if(articleCategory.equalsIgnoreCase("All Article"))
            {
                List<Article> articles = articleWritingServices.selectAllArticleByCampaignAndState(campaign, "Approved");
                if(!articles.isEmpty())
                {
                    response.put("articles", articles);
                }
            }
            else
            {
                List<Article> articles = articleWritingServices.selectAllArticleByCampaignStateAndCategory(campaign, "Approved", articleCategory);
                if(!articles.isEmpty())
                {
                    response.put("articles", articles);
                }
            }
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
