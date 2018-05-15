package org.sterling.intranet.agent.controllers;

import org.sterling.intranet.campaign.admin.controllers.*;
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
@RequestMapping("/agent")
public class AgentAllArticlesController
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
            List<Article> articles = articleWritingServices.getAllArticleByCampaignName(campaign);
            List<ArticleCategory> categories = articleCategoryServices.fetchAllCategoriesByCampaignName(campaign);
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
}
