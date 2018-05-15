package org.sterling.intranet.campaign.admin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.SearchServices;
import org.sterling.intranet.model.json.SearchModel;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class SearchController
{
    @Autowired
    private SearchServices searchServices;
    
    @RequestMapping(value = "/search", params = "search", method = RequestMethod.GET)
    public ResponseEntity<?> search(@RequestParam(value = "search", required = false) String search) throws Exception
    {
        Map<String, Object> response = new HashMap<String, Object>();
        List<SearchModel> searchResult = searchServices.search(search);
        
        if(!searchResult.isEmpty())
        {
            response.put("searchResult", searchResult);
            response.put("hasSearchedResult", HttpStatus.OK);
        }
        else if(searchResult.isEmpty())
        {
            response.put("hasSearchedResult", HttpStatus.NO_CONTENT);
        }
        response.put("searchedKey", search);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
