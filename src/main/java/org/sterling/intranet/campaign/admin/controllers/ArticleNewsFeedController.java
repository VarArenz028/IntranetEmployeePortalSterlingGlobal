package org.sterling.intranet.campaign.admin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.models.Article;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class ArticleNewsFeedController 
{
    
    @Autowired
    private ArticleWritingServices articleWritingServices;
    
    @RequestMapping(value = "/news-feed-articles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllDataForArticleNewsFeed()
    {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("articles", articleWritingServices.getAllArticleNewsFeedData());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
