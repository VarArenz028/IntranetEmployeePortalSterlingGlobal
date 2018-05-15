package org.sterling.intranet.campaign.admin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.sterling.intranet.exceptions.DataExistException;
import org.sterling.intranet.exceptions.ErrorResponse;
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
public class ArticleCategoryController 
{
    @Autowired
    private ArticleCategoryServices articleCategoryServices;
    
    @Autowired
    private ArticleWritingServices articleWritingServices;
    
    @RequestMapping(value = "/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadAllResources(@CookieValue("currentCampaign") String cookie) throws NullPointerException
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            List<ArticleCategory> list = articleCategoryServices.fetchAllCategoriesByCampaignName(campaign);
            if(!list.isEmpty())
            {
                response.put("categories", list);
                response.put("numberOfCategory", list.size());
                response.put("hasAnCategories", HttpStatus.OK);
            }
            else if(list.isEmpty())
            {
                response.put("numberOfCategory", 0);
                response.put("hasAnCategories", HttpStatus.NO_CONTENT);

            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/categories/{categoryid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleCategory> getArticleCategory(@PathVariable("categoryid") int categoryid)
    {
        ArticleCategory articleCategory = articleCategoryServices.getArticleCategoryById(categoryid);
        
        return new ResponseEntity<ArticleCategory>(articleCategory, HttpStatus.OK);
    }
    @RequestMapping(value = {"/categories/", "/categories/{categoryid}"}, params = "categoryName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateCategoryName(@RequestParam(value = "categoryName") String categoryName) throws DataExistException
    {
        if(articleCategoryServices.validateCategoryName(categoryName) == false)
        {
            throw new DataExistException("Category name already exist!");
        }
        
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
    
    @RequestMapping(value = "/categories", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleCategory> saveCategory(@RequestBody ArticleCategory articleCategory ,UriComponentsBuilder builder, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            articleCategory.setCampaign(campaign);
            articleCategoryServices.saveCategory(articleCategory);
        }
        else if(cookie.isEmpty())
        {
            response.put("emptyCampaignCookie", HttpStatus.NOT_FOUND);
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/category/{categoryId}").buildAndExpand(articleCategory.getCategoryId()).toUri());
        return new ResponseEntity<ArticleCategory>(HttpStatus.OK);
    }
    @RequestMapping(value = "/categories", params = {"newCat", "oldCat"}, method = RequestMethod.OPTIONS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCategory(@RequestParam(name = "newCat") String newCategory, @RequestParam(name = "oldCat") String oldCategory,
                      @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        String campaign = cookie.replace("\"", "");
        
        articleCategoryServices.updateCategory(newCategory, oldCategory);
        response = articleWritingServices.updateArticleCategoryByCampaignAndCatagoryName(campaign, newCategory, oldCategory);
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/categories", params = "categoryName", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> deleteCategory(@RequestParam(name = "categoryName") String categoryName, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        ArticleCategory articleCategory = articleCategoryServices.selectCategoryByCategoryName(categoryName);
        String campaign = cookie.replace("\"", "");
        
        if(!cookie.isEmpty())
        {
            if(articleCategory != null)
            {
                response = articleWritingServices.updateArticleCategoryIntoUnassignedByCampaignAndCatagoryName(campaign, categoryName, "Unassigned");

                if(response.get("isUpdated").equals(HttpStatus.OK))
                {
                    articleCategoryServices.deleteCategory(articleCategory);
                    response.put("categoryDeleted", HttpStatus.OK);
                }
            }
        }
        
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(DataExistException.class)
    public ResponseEntity<ErrorResponse> dataExist(Exception e)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        
        errorResponse.setErrorCode(HttpStatus.CONFLICT.value());
        errorResponse.setErrorMessage(e.getMessage());
        
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
    }
}
