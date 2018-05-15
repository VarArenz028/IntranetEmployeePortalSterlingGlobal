package org.sterling.intranet.campaign.admin.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/campaign-admin")
public class ManageArticleCategoryController
{
    
    @Autowired
    private ArticleWritingServices articleWritingServices;
    
    @Autowired
    private ArticleCategoryServices articleCategoryServices;
    
    @RequestMapping(value = "/manage-categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAllItemInMgtArticle(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            List<ArticleCategory> articleCategorys = articleCategoryServices.fetchAllCategoriesByCampaignName(campaign);
            List<ArticleCategory> moveTo = articleCategoryServices.fetchAllCategoriesByCampaignName(campaign);
            if(!articleCategorys.isEmpty())
            {
                response.put("moveToArticle", moveTo);
                
                ArticleCategory allArticle = new ArticleCategory();
                allArticle.setCategoryName("All Article");
                ArticleCategory unassigned = new ArticleCategory();
                unassigned.setCategoryName("Unassigned");
                articleCategorys.add(allArticle);
                articleCategorys.add(unassigned);
                response.put("articleCategorys", articleCategorys);
                response.put("hasCategories", HttpStatus.OK);
            }
            else
            {
                ArticleCategory unassigned = new ArticleCategory();
                unassigned.setCategoryName("Unassigned");
                articleCategorys.add(unassigned);

                response.put("articleCategorys", articleCategorys);
                response.put("hasCategories", HttpStatus.NO_CONTENT);
            }
            
            
            List<Article> articles = articleWritingServices.selectAllArticleByCampaignAndState(campaign, "Approved");
            
            if(!articles.isEmpty())
            {
                response.put("articles", articles);
                response.put("hasArticles", HttpStatus.OK);
            }
            else
            {
                response.put("hasArticles", HttpStatus.NO_CONTENT);
            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/manage-categories", params = {"articleId", "moveTo"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> updateArticleCategory(@RequestParam(name = "articleId") String[] articleId,
                      @RequestParam(name = "moveTo") String moveTo, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        List<String> listArticleId = new ArrayList<String>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            listArticleId.addAll(Arrays.asList(articleId));
            response = articleWritingServices.selectAllAndUpdateArticleByArticleIdListAndCampaign(listArticleId, moveTo, campaign);

        }

        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
