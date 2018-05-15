package org.sterling.intranet.agent.controllers;

import org.sterling.intranet.campaign.admin.controllers.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.sterling.intranet.interfaces.DraftNewsServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.NewsServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.model.json.DraftNewsJson;
import org.sterling.intranet.model.json.NewsRest;
import org.sterling.intranet.models.DraftNews;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.News;
import org.sterling.intranet.utils.DateStamp;

/**
 *
 * @author Var Arenz Villarino
 */
@RestController
@RequestMapping("/agent")
public class AgentNewsController 
{
    @Autowired
    private NewsServices newsServices;
    
    @Autowired
    private UserServices userServices;
    
    @Autowired
    private DraftNewsServices draftNewsServices;
    
    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;
    
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ResponseEntity<?> agentFetchAllNews(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            List<News> news = newsServices.selectAllNewsByCampaignName(campaign);
            
            if(!news.isEmpty())
            {
                List<NewsRest> newsRests = new ArrayList();
                for(News n : news)
                {
                    NewsRest newsRest = new NewsRest();
                    newsRest.setNewsId(n.getNewsId());
                    newsRest.setNewsTitle(n.getNewsTitle());
                    String content = Jsoup.parse(n.getNewsContent()).text();
                    newsRest.setContent(content.substring(0, 300) + "...");
                    newsRest.setDateCreated(n.getDateCreated());
                    newsRest.setBase64(n.getBase64());
                    newsRest.setFileType(n.getFileType());
                    newsRests.add(newsRest);
                }
                response.put("newsRest", newsRests);
                response.put("hasNews", HttpStatus.OK);
            }
            else if(news.isEmpty())
            {
                response.put("hasNews", HttpStatus.NO_CONTENT);
            }
            
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @RequestMapping(value = "news/company-news/{newsId}", params = "newsId", method = RequestMethod.GET)
    public ResponseEntity<?> agentSelectOneNews(@RequestParam(name = "newsId") String newsId, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            News news = newsServices.selectNewsByCampaignAndNewsId(campaign, newsId);
            if(news != null)
            {
                response.put("news", news);
            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
