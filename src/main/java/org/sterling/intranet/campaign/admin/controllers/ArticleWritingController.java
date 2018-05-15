
package org.sterling.intranet.campaign.admin.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Base64;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.sterling.intranet.exceptions.CustomNullException;
import org.sterling.intranet.exceptions.ErrorResponse;
import org.sterling.intranet.interfaces.ArticleCategoryServices;
import org.sterling.intranet.interfaces.ArticleDocumentServices;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.interfaces.DraftArticleServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.NumberOfArticleServices;
import org.sterling.intranet.interfaces.PageViewServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.model.json.CommonArticle;
import org.sterling.intranet.model.json.DraftArticleJson;
import org.sterling.intranet.models.ArticleCategory;
import org.sterling.intranet.models.ArticleDocument;
import org.sterling.intranet.models.DraftArticle;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.NumberOfArticle;
import org.sterling.intranet.models.PageView;
import org.sterling.intranet.models.User;
import org.sterling.intranet.utils.DateStamp;

/**
 *
 * @author Var Arenz Villarino
 */
@RestController
@RequestMapping("/campaign-admin")
public class ArticleWritingController 
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
    
    @RequestMapping(value = "/article", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllData(@CookieValue("currentCampaign") String cookie)
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
    
    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> getArticle(@PathVariable("articleId") String articleId)
    {
        Article article = articleWritingServices.selectArticleById(articleId);
        
        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/article/{draftArticleId}", params = "updateDraft", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDraftArticle(@RequestParam(name = "updateDraft", required = false) String draftArticleId, 
                                             @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            String campaign = cookie.replace("\"", "");
            DraftArticle draftArticle = draftArticleServices.selectDraftArticleByCampaignAndId(campaign, draftArticleId);
            if(draftArticle != null)
            {
                response.put("draftArticle", draftArticle);
            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/article/{draftArticleId}", params = "draftArticleId", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> saveArticle(@RequestParam(name = "draftArticleId", required = false) String draftArticleId,
                      @CookieValue("currentCampaign") String cookie) throws CustomNullException, UnsupportedEncodingException, ParseException, IOException
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        if(!cookie.isEmpty())
        {
            if(user != null)
            {
                EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
                if(employeeDetails != null)
                {
                    String campaign = cookie.replace("\"", "");
                    DraftArticle draftArticle = draftArticleServices.selectDraftArticleByCampaignAndId(campaign, draftArticleId);
                    
                    if(draftArticle != null)
                    {
                        Article article = new Article();
                        DateStamp dateStamp = new DateStamp();
                        article.setTitle(draftArticle.getTitle());
                        article.setCategory(draftArticle.getCategory());
                        article.setContent(draftArticle.getContent());
                        article.setLocalDateCreated(dateStamp.getLocalDateTime());
                        article.setDateCreated(dateStamp.getDate());
                        article.setDateString(dateStamp.getDateString());
                        article.setEmployeeDetails(employeeDetails);
                        article.setCampaign(campaign);
                        article.setState("Approved");
                        articleWritingServices.saveArticle(article);
                        employeeDetails.getArticles().add(article);
                        PageView pageView = new PageView();
                        pageView.setArticle(article);
                        pageView.setCampaign(campaign);
                        pageViewServices.saveView(pageView);
                        
                        NumberOfArticle hasNumOfArticle = numberOfArticleServices.selectNumberOfArticleByEmpId(employeeDetails.getEmpDetailsId());

                        if(hasNumOfArticle != null)
                        {
                            response = numberOfArticleServices.updateNumberOfArticle(employeeDetails.getEmpDetailsId());
                        }
                        else
                        {
                            NumberOfArticle numberOfArticle = new NumberOfArticle();
                            numberOfArticle.setNumberOfArticle(1);
                            numberOfArticle.setCampaign(campaign);
                            numberOfArticle.setEmployeeDetails(employeeDetails);
                            numberOfArticleServices.saveNumberOfArticle(numberOfArticle);
                            
                            employeeDetails.setNumberOfArticle(numberOfArticle);
                            
                            response.put("numberOfArticleEntityAdded", HttpStatus.OK);
                        }
                        response.put("articleIdCreated", article.getArticleId());
                        response.put("savingArticle", HttpStatus.CREATED);
                    }
                    
                }
            }
        }
        else
        {
            response.put("emptyCampaignCookie", HttpStatus.NOT_FOUND);
        }
       
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/article/{draftArticleId}", params = "articleId", method = RequestMethod.POST)
    public ResponseEntity<?> saveFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(name = "articleId") String articleId) throws IOException
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(file != null)
        {
            if(!file.isEmpty())
            {
                Article article = articleWritingServices.selectArticleById(articleId);
                ArticleDocument articleDocument = new ArticleDocument();
                
                articleDocument.setFileName(file.getOriginalFilename());
                articleDocument.setContent(file.getBytes());
                articleDocument.setType(file.getContentType());
                articleDocument.setArticle(article);
                
                article.setArticleDocument(articleDocument);
                
                articleDocumentServices.saveArticleDocument(articleDocument);
                
//                System.out.println(file.getOriginalFilename());
//                System.out.println(file.getOriginalFilename());
//                System.out.println(file.getOriginalFilename());
//                System.out.println(file.getOriginalFilename());
//                System.out.println(file.getOriginalFilename());
                response.put("fileSaved", HttpStatus.OK);

            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = {"/article/{draftArticleId}"}, method = RequestMethod.PUT)
    public ResponseEntity<?> saveAsDrafts(@CookieValue("currentCampaign") String cookie, @RequestBody DraftArticleJson draftArticleJson) throws ParseException
    {
        
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        Map<String, Object> response = new HashMap<String, Object>();
        String campaign = cookie.replace("\"", "");
        if(user != null)
        {
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
            if(employeeDetails != null)
            {
                
                DraftArticle draftArticle = draftArticleServices.selectDraftArticleIdByDraftArticleId(campaign, draftArticleJson.getDraftArticleId());
                if(draftArticle != null)
                {
                    response.put("title", draftArticleJson.getTitle());
                    response = draftArticleServices.updateDraftArticleByMinute
                    (draftArticleJson.getTitle(), draftArticleJson.getCategory(), draftArticleJson.getDraftSummernote(),
                                      draftArticleJson.getDraftArticleId(), campaign);
                }
                
            }
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/article/{draftArticleId}", params = "draftArticleId", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDraftArticle(@RequestParam(name = "draftArticleId", required = false) String draftArticleId, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        String campaign = cookie.replace("\"", "");
        DraftArticle draftArticle = draftArticleServices.selectDraftArticleIdByDraftArticleId(campaign, draftArticleId);
        if(draftArticle != null)
        {
            draftArticleServices.deleteDraftArticle(draftArticle);
            response.put("draftArticleDeleted", HttpStatus.OK);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(CustomNullException.class)
    public ResponseEntity<ErrorResponse> nullPointer(Exception e)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        
        errorResponse.setErrorCode(HttpStatus.NO_CONTENT.value());
        errorResponse.setErrorMessage(e.getMessage());
        
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
    }
}




























//    @RequestMapping(value = "/article", params = {"draftId", "title", "category", "draftSummernote"},
//                      method = RequestMethod.OPTIONS, consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<?> saveAsDrafts(@CookieValue("currentCampaign") String cookie, @RequestParam(name = "draftId") String draftId,
//                      @RequestParam(name = "title") String title,
//                      @RequestParam(name = "category") String category,
//                      @RequestParam(name = "draftSummernote", required = false) String draftSummernote) throws ParseException
//    {
//        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
//        Map<String, Object> response = new HashMap<String, Object>();
//        String campaign = cookie.replace("\"", "");
//        HttpHeaders headers = new HttpHeaders();
//        if(user != null)
//        {
//            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
//            if(employeeDetails != null)
//            {
//                DraftArticle draftArticle = draftArticleServices.selectDraftArticleIdByDraftArticleId(campaign, draftId);
//                if(draftArticle != null)
//                {
//                    response = draftArticleServices.updateDraftArticleByMinute(title, category, draftSummernote, draftId, campaign);
//                }
//                else
//                {
//                    DateStamp dateStamp = new DateStamp();
//                    DraftArticle newDraftArticle = new DraftArticle();
//                    newDraftArticle.setTitle(title);
//                    newDraftArticle.setCategory(category);
//                    newDraftArticle.setContent(draftSummernote);
//                    newDraftArticle.setCampaign(campaign);
//                    newDraftArticle.setDateCreated(dateStamp.getDate());
//                    newDraftArticle.setLocalDateCreated(dateStamp.getLocalDateTime());
//                    newDraftArticle.setDateString(dateStamp.getDateString());
//                    newDraftArticle.setEmployeeDetails(employeeDetails);
//                    draftArticleServices.saveDraft(newDraftArticle);
//
//                    employeeDetails.getDraftArticles().add(draftArticle);
//                    
//                    response.put("getDraftArticleId", newDraftArticle.getDraftArticleId());
//                    response.put("initialArticleCreated", HttpStatus.CREATED);
//                }
//                
//            }
//        }
//        
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @RequestMapping(value = "/article", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> updateDraft(@RequestBody UpdateDraftArticle updateDraftArticle)
//    {
//        Map<String, Object> response = new HashMap<String, Object>();
//        System.out.println(updateDraftArticle.getUpdatedTitle());
//        System.out.println(updateDraftArticle.getUpdatedCategory());
//        System.out.println(updateDraftArticle.getUpdatedContent());
//       
//        response.put("draftArticleUpdated", HttpStatus.OK);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @RequestMapping(value = "/article", params = {"updatedTitle", "updatedCategory", "updatedContent"}, method = RequestMethod.POST)
//    public ResponseEntity<?> updateDraft(@RequestParam(name = "updatedTitle") String updatedTitle,
//                      @RequestParam(name = "updatedCategory") String updatedCategory,
//                      @RequestParam(name = "updatedContent") String updatedContent)
//    {
//        Map<String, Object> response = new HashMap<String, Object>();
//        System.out.println(updatedTitle);
//        System.out.println(updatedCategory);
//        System.out.println(updatedContent);
//        response.put("draftArticleUpdated", HttpStatus.OK);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @RequestMapping(value = "/article", params = {"title", "category", "content"}, method = RequestMethod.POST)
//    public ResponseEntity<Article> saveArticle(@CookieValue("currentCampaign") String cookie, UriComponentsBuilder builder,
//                      @RequestParam(name = "title") String title, @RequestParam(name = "category") String category,
//                      @RequestParam(name = "content") String content,@RequestParam(value = "file", required = false) MultipartFile file) throws CustomNullException, UnsupportedEncodingException, ParseException, IOException
//    {
//        Map<String, Object> response = new HashMap<String, Object>();
//        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
//        if(!cookie.isEmpty())
//        {
//            if(user != null)
//            {
//                EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
//                if(employeeDetails != null)
//                {
//                    String campaign = cookie.replace("\"", "");
//                    DateStamp dateStamp = new DateStamp();
//                    Article article = new Article();
//                    PageView pageView = new PageView();
//                    employeeDetails.getArticles().add(article);
//                    article.setTitle(title);
//                    article.setCategory(category);
//                    article.setContent(content);
//                    article.setLocalDateCreated(dateStamp.getLocalDateTime());
//                    article.setDateCreated(dateStamp.getDate());
//                    article.setDateString(dateStamp.getDateString());
//                    article.setEmployeeDetails(employeeDetails);
//                    article.setCampaign(campaign);
//                    article.setState("Approved");
//                    articleWritingServices.saveArticle(article);
//                    
//                    pageView.setArticle(article);
//                    pageViewServices.saveView(pageView);
//                    ArticleDocument articleDocument = new ArticleDocument();
// 
//                    if(file != null)
//                    {
//                        if(!file.isEmpty())
//                        articleDocument.setFileName(file.getOriginalFilename());
//                        articleDocument.setType(file.getContentType());
//                        articleDocument.setContent(file.getBytes());
//                        articleDocument.setArticle(article);
//                        articleDocument.setEmployeeDetails(employeeDetails);
//                        articleDocumentServices.saveArticleDocument(articleDocument);
//                    }
//                    else if(file == null)
//                    {
//                        response.put("noFileUploaded", HttpStatus.OK);
//                    }
//                    article.setArticleDocument(articleDocument);
//
//                }
//            }
//        }
//        else
//        {
//            response.put("emptyCampaignCookie", HttpStatus.NOT_FOUND);
//        }
//        
//        try
//        {
//            System.out.println(file.getOriginalFilename());
//
//        }
//        catch(NullPointerException e)
//        {
//            System.out.println("nulll");
//        }
//       
////        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
////        EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
////        String campaign = cookie.replace("\"", "");
////        DateStamp dateStamp = new DateStamp();
////
////        employeeDetails.getArticles().add(article);
////        article.setLocalDateCreated(dateStamp.getLocalDateTime());
////        article.setDateCreated(dateStamp.getDate());
////        article.setDateString(dateStamp.getDateString());
////        article.setEmployeeDetails(employeeDetails);
////        article.setCampaign(campaign);
////        articleWritingServices.saveArticle(article);
//       
//        HttpHeaders headers = new HttpHeaders();
////        headers.setLocation(builder.path("/article/{articleId}").buildAndExpand(article.getArticleId()).toUri());
//        return new ResponseEntity<Article>(HttpStatus.OK);
//    }
