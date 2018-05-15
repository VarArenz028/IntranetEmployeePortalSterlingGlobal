package org.sterling.intranet.campaign.admin.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.PageViewServices;

/**
 *
 * @author Var Arenz Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class PageViewController
{
    
    @Autowired
    private PageViewServices pageViewServices;
    
    @RequestMapping(value = "/news-feed", params = "newsFeedArticle", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> newsFeedRequestViewCountForArticle(@RequestParam(name = "newsFeedArticle") String articleId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        response = pageViewServices.updatePageViewOfArticle(articleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
