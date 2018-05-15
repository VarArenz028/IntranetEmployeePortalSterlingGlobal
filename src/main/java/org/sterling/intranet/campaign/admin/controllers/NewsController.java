package org.sterling.intranet.campaign.admin.controllers;

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
@RequestMapping("/campaign-admin")
public class NewsController 
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
    public ResponseEntity<?> fetchAllNews(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            List<News> news = newsServices.selectAllNewsByCampaignNameAndState(campaign, "Active");
            
            if(!news.isEmpty())
            {
                List<NewsRest> newsRests = new ArrayList();
                for(News n : news)
                {
                    NewsRest newsRest = new NewsRest();
                    newsRest.setNewsId(n.getNewsId());
                    newsRest.setNewsTitle(n.getNewsTitle());
                    String content = Jsoup.parse(n.getNewsContent()).text();
                    if(content.length() >= 300)
                    {
                        newsRest.setContent(content.substring(0, 300) + "...");

                    }
                    else
                    {
                        newsRest.setContent(content);
                    }
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
    
    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public ResponseEntity<?> createNews(@CookieValue("currentCampaign") String cookie) throws ParseException
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            
            org.sterling.intranet.models.User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
            
            if(user != null)
            {
                EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
                if(employeeDetails != null)
                {
                    DateStamp dateStamp = new DateStamp();
                    String campaign = cookie.replace("\"", "");
                    DraftNews draftNews = new DraftNews();
                    draftNews.setDraftNewsTitle("Untitled");
                    draftNews.setDraftNewsContent("");
                    draftNews.setDateCreated(dateStamp.getDate());
                    draftNews.setCampaign(campaign);
                    draftNews.setBase64(null);
                    draftNews.setFileType(null);
                    draftNews.setEmployeeDetails(employeeDetails);
                    
                    draftNewsServices.saveDraftNews(draftNews);
                    
                    response.put("draftNewsSaved", draftNews.getDraftNewsId());
                    response.put("successfullyCreated", HttpStatus.OK);
                    
                }
            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/news/{draftNewsId}", method = RequestMethod.PUT)
    public ResponseEntity<?> saveNewsAsDraft(@CookieValue("currentCampaign") String cookie, @RequestBody DraftNewsJson draftNewsJson)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            response = draftNewsServices.updateDraftNews(draftNewsJson.getDraftNewsId(), draftNewsJson.getDraftNewsTitle(),
                          draftNewsJson.getDraftNewsContent(), draftNewsJson.getDraftBase64(), draftNewsJson.getDraftFileType());
        }
        else if(cookie.isEmpty())
        {
            response.put("noCookie", HttpStatus.OK);
        }
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/news/{draftNewsId}", params = "draftNewsId", method = RequestMethod.GET)
    public ResponseEntity<?> fetchCurrentDraftNews(@RequestParam(name = "draftNewsId") String draftNewsId, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        DraftNews draftNews = draftNewsServices.selectDraftNewsByDraftNewsId(draftNewsId);

        if(draftNews != null)
        {
            response.put("newsCreated", draftNews);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "news/{draftNewsId}", params = "draftNewsId", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> saveNews(@RequestParam(name = "draftNewsId") String draftNewsId, @CookieValue("currentCampaign") String cookie) throws ParseException
    {
        DraftNews draftNews = draftNewsServices.selectDraftNewsByDraftNewsId(draftNewsId);
        
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            
            if(draftNews != null)
            {
                String campaign = cookie.replace("\"", "");

                DateStamp dateStamp = new DateStamp();

                News news = new News();
                news.setNewsTitle(draftNews.getDraftNewsTitle());
                news.setNewsContent(draftNews.getDraftNewsContent());
                news.setDateCreated(dateStamp.getDate());
                news.setBase64(draftNews.getBase64());
                news.setFileType(draftNews.getFileType());
                news.setCampaign(campaign);
                news.setState("Active");
                news.setEmployeeDetails(draftNews.getEmployeeDetails());
                
                newsServices.saveNews(news);
                
                response.put("newsSaved", HttpStatus.OK);
                response.put("hasSaved", HttpStatus.OK);
                
                if(response.get("newsSaved").equals(HttpStatus.OK))
                {
                    draftNewsServices.deleteDraftNews(draftNews);
                }
                
            }
        }
        else if(cookie.isEmpty())
        {
            response.put("NoCookie", HttpStatus.NOT_FOUND);
        }
            
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "news/company-news/{newsId}", params = "newsId", method = RequestMethod.GET)
    public ResponseEntity<?> selectOneNews(@RequestParam(name = "newsId") String newsId, @CookieValue("currentCampaign") String cookie)
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
    @RequestMapping(value = "/news", params = "inactiveNews", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> inactiveNews(@RequestParam(name = "inactiveNews", required = false) String inactiveNews, @CookieValue("currentCampaign") String cookie)
    {
        
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            response = newsServices.inactiveNewsByNewsIdAndCampaign(inactiveNews, "Inactive", campaign);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
