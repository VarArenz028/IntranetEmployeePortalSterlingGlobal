package org.sterling.intranet.agent.controllers;

import org.sterling.intranet.campaign.admin.controllers.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.sterling.intranet.interfaces.ArticleDocumentServices;
import org.sterling.intranet.interfaces.ArticleWritingServices;
import org.sterling.intranet.interfaces.CampaignNewsFeedServices;
import org.sterling.intranet.interfaces.CommentServices;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.interfaces.NewsFeedServices;
import org.sterling.intranet.interfaces.PricipalSecurityServices;
import org.sterling.intranet.interfaces.QuestionAnswerServices;
import org.sterling.intranet.interfaces.QuestionServices;
import org.sterling.intranet.interfaces.UserAvatarServices;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.model.json.NewsFeedObject;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.ArticleDocument;
import org.sterling.intranet.models.Comment;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.NewsFeed;
import org.sterling.intranet.models.Question;
import org.sterling.intranet.models.QuestionAnswer;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.UserAvatar;
import org.sterling.intranet.utils.DateStamp;

/**
 *
 * @author Var Arenz G. Villarino
 */
@RestController
@RequestMapping("/agent")
public class AgentNewsFeedController
{

    @Autowired
    private CampaignNewsFeedServices campaignNewsFeedServices;
    
    @Autowired
    private PricipalSecurityServices pricipalSecurityServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private NewsFeedServices newsFeedServices;
    
    @Autowired
    private UserAvatarServices userAvatarServices;
    
    @Autowired
    private ArticleWritingServices articleWritingServices;

    @Autowired
    private ArticleDocumentServices articleDocumentServices;
    
    @Autowired
    private QuestionServices questionServices;

    @Autowired
    private QuestionAnswerServices questionAnswerServices;

    @Autowired
    private CommentServices commentServices;

    @Autowired
    private EmployeeDetailsServices employeeDetailsServices;


    @RequestMapping(value = "/news-feed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newsFeed(@CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        if(!cookie.isEmpty())
        {
            
            String campaign = cookie.replace("\"", "");
            List<NewsFeedObject> newsFeedObjects = campaignNewsFeedServices.fetchAllCampaignNewsFeed(campaign);
            if(!newsFeedObjects.isEmpty())
            {
                response.put("newsFeedObjects", newsFeedObjects);
                response.put("hasNewsFeed", HttpStatus.OK);

            }
            
//            User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
//            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
//            UserAvatar userAvatar = userAvatarServices.selectUserAvatarByEmpId(employeeDetails.getEmpDetailsId());
//            
//            if(userAvatar != null)
//            {
//                response.put("userAvatar", userAvatar);
//            }
//            
//            
//            String campaign = cookie.replace("\"", "");
//            List<NewsFeed> list = newsFeedServices.getAllNewsFeed(campaign, "Approved");
//            if(!list.isEmpty())
//            {
//                response.put("newsFeed", list);
//                response.put("hasNewsFeed", HttpStatus.OK);
//            }
//            else
//            {
//                response.put("hasNewsFeed", HttpStatus.NO_CONTENT);
//            }
//            
//            List<Article> pinToTopArticles = articleWritingServices.selectAllPinToTopArticle(campaign, "Pin");
//            if(!pinToTopArticles.isEmpty())
//            {
//                response.put("pinToTopArticles", pinToTopArticles);
//                response.put("hasPinToTopArticles", HttpStatus.OK);
//            }
//            else if(pinToTopArticles.isEmpty())
//            {
//                response.put("hasPinToTopArticles", HttpStatus.NO_CONTENT);
//            }
            
        }
        else if (cookie.isEmpty())
        {
            response.put("emptyCookie", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/news-feed/article/{articleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> selectArticleFromNewsFeed(@PathVariable("articleId") String articleId, @CookieValue("currentCampaign") String cookie)
    {

        Map<String, Object> response = new HashMap<String, Object>();
        String campaign = cookie.replace("\"", "").trim();

        String state = "Approved";
        Article article = articleWritingServices.selectArticleByIdCampaignAndState(articleId, campaign, state);
        
        if(article == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            response.put("article", article);
            
            Integer articleEmpDetailsID = articleWritingServices.selectEmpIdByArticleId(article.getArticleId());
            UserAvatar authorAvatar = userAvatarServices.selectUserAvatarByEmpId(articleEmpDetailsID);
            if(authorAvatar != null)
            {
                response.put("authorAvatar", authorAvatar);
            }
            else
            {
                response.put("noAvatar", HttpStatus.NO_CONTENT);
            }
            
            EmployeeDetails authorDetails = employeeDetailsServices.selectNameOfEmployeeByUserId(article.getEmployeeDetails().getEmpDetailsId());
            if(authorDetails != null)
            {
                String authorName = authorDetails.getLastName().substring(0,1).toUpperCase() + 
                                                    authorDetails.getLastName().substring(1).toLowerCase()
                                                    + " " + authorDetails.getFirstName().substring(0,1).toUpperCase() + 
                                                    authorDetails.getFirstName().substring(1).toLowerCase();
                response.put("authorName", authorName);
            }
            
            User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
            
            UserAvatar currentUserAvatar = userAvatarServices.selectUserAvatarByEmpId(employeeDetails.getEmpDetailsId());
            
            if(currentUserAvatar != null)
            {
                response.put("currentUserAvatar", currentUserAvatar);
            }
            else
            {
                response.put("noAvatar", HttpStatus.NO_CONTENT);
            }
            
            ArticleDocument articleDocument = articleDocumentServices.selectFileIdAndDocumentNameByArticleId(articleId);
            
            if(articleDocument != null)
            {
                response.put("articleDocument", articleDocument);
                response.put("hasFileUploaded", HttpStatus.OK);
            }
            else
            {
                response.put("hasFileUploaded", HttpStatus.NO_CONTENT);
            }
            
            Collection<Comment> comments = commentServices.selectAllCommentByArticleId(article.getArticleId());
            if(!comments.isEmpty())
            {
                response.put("comments", comments);
                response.put("hasComments", HttpStatus.OK);
            }
            else
            {
                response.put("hasComments", HttpStatus.NO_CONTENT);
            }
        }


        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping(value = "/news-feed/article/{articleId}", params = {"comment", "articleId"}, method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> saveCommentInArticle(@RequestParam(value = "comment", required = false) String comment,
        @RequestParam(value = "articleId") String articleId) throws ParseException
    {
        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
        Comment c = new Comment();
        Article article = articleWritingServices.selectArticleById(articleId);
        DateStamp dateStamp = new DateStamp();

        if(user == null)
        {
            // do something
        }
        else
        {
            c.setComment(comment);
            c.setDateCommented(dateStamp.getDate());
            c.setEmployeeDetails(employeeDetails);
            c.setArticle(article);
            article.getComments().add(c);
            commentServices.saveComment(c);


            response.put("commentSave", HttpStatus.OK);
        }

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/news-feed/download/{fileId}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable(name = "fileId") Integer fileId, HttpServletResponse response) throws IOException
    {
        
        ArticleDocument articleDocument = articleDocumentServices.criteriaSelectArticleDocumentByFileId(fileId);

        response.setContentType(articleDocument.getType());
        response.setContentLength(articleDocument.getContent().length);
        response.setHeader("Content-Disposition","attachment; filename=\"" + articleDocument.getFileName()+"\"");
        FileCopyUtils.copy(articleDocument.getContent(), response.getOutputStream());
        
    }

    @RequestMapping(value = "/news-feed/question/{questionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> selectQuestionFromNewsFeed(@PathVariable("questionId") String questionId, @CookieValue("currentCampaign") String cookie)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        String campaign = cookie.replace("\"", "").trim();

        Question question = questionServices.selectQuestionByIdAndCampaign(questionId, campaign);
        List<QuestionAnswer> questionAnswer = questionAnswerServices.selectAnswersByQuestionId(questionId);

        if(question == null)
        {
            // do something
        }
        else if(question != null)
        {
            response.put("question", question);

            if(questionAnswer.isEmpty())
            {
                response.put("noAnswer", HttpStatus.NO_CONTENT);
            }
            else if(!questionAnswer.isEmpty())
            {
                response.put("answers", questionAnswer);
                response.put("numberOfAnswers", questionAnswer.size());
            }

        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/news-feed/question/{questionId}/", params = "questionId", method = RequestMethod.GET)
    public ResponseEntity<?> checkIfUserAnswered(@RequestParam(value = "questionId", required = false) String questionId)
    {
        Map<String, Object> response = new HashMap<String, Object>();

        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        if(user != null)
        {
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
            if(employeeDetails != null)
            {
                boolean result = questionAnswerServices.selectAnswersByEmpIdAndQuestionId(employeeDetails.getEmpDetailsId(), questionId);
                
                if(result == false)
                {
                    response.put("notYetAnswered", HttpStatus.OK);
                }
                else if(result == true)
                {
                    response.put("alreadyAnswered", HttpStatus.CONFLICT);
                }
            }
            else if(employeeDetails == null)
            {
                // do something
            }
        }
        else if(user == null)
        {
            // do something
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/news-feed/question/{questionId}", params = {"answer", "questionId"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAnswerQuestion(@RequestParam(value = "answer") String answer,
                      @RequestParam(value = "questionId") String questionId) throws ParseException
    {

        Map<String, Object> response = new HashMap<String, Object>();
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        DateStamp dateStamp = new DateStamp();
        if(user == null)
        {
            // do something
        }
        else
        {
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());

            if(employeeDetails == null)
            {
                // do something
            }
            else //(employeeDetails != null)
            {
                Question question = questionServices.selectQuestionById(questionId);

                if(question == null)
                {
                    // do someyhing
                }
                else //(question != null)
                {

                    QuestionAnswer questionAnswer = new QuestionAnswer();

                    questionAnswer.setAnswer(answer);
                    questionAnswer.setEmployeeDetails(employeeDetails);
                    questionAnswer.setQuestion(question);
                    questionAnswer.setDateAnswered(dateStamp.getDate());
                    questionAnswerServices.saveAnswer(questionAnswer);
                    question.getQuestionAnswer().add(questionAnswer);
                    employeeDetails.getQuestionAnswers().add(questionAnswer);
                    response.put("successfullyAnswered", HttpStatus.OK);
                }

            }

        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/news-feed/question/{questionId}/", params = "questionAnswerId", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllCommentInOneQuestionAnswer(@RequestParam(name = "questionAnswerId") Integer questionAnswerId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        Collection<Comment> comments = commentServices.selectAllCommentByQuestionAnswerId(questionAnswerId);
        
        if(!comments.isEmpty())
        {
            response.put("comments", comments);
            response.put("hasComments", HttpStatus.OK);
        }
        else if(comments.isEmpty())
        {
            response.put("hasComments", HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/news-feed/question/{questionId}", params = {"comment", "questionAnswerId"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<?> commentOnAnswer(@RequestParam(name = "comment") String comment,
                      @RequestParam(name = "questionAnswerId") Integer questionAnswerId) throws ParseException
    {
        
        Map<String, Object> response = new HashMap<String, Object>();
        
        User user = userServices.selectUserByUsername(pricipalSecurityServices.getPrincipal());
        
        if(user != null)
        {
            EmployeeDetails employeeDetails = employeeDetailsServices.selectEmployeeDetailsByUserId(user.getUserId());
            if(employeeDetails != null)
            {
                
                QuestionAnswer questionAnswer = questionAnswerServices.selectQuestionAnswerByQuestionId(questionAnswerId);
        
                if(questionAnswer != null)
                {
                    DateStamp dateStamp = new DateStamp();
                    Comment com = new Comment();
                    com.setComment(comment);
                    com.setDateCommented(dateStamp.getDate());
                    com.setQuestionAnswer(questionAnswer);
                    com.setEmployeeDetails(employeeDetails);
                    commentServices.saveComment(com);
                    
                    employeeDetails.getComment().add(com);
                    
                    response.put("commentSaved", HttpStatus.OK);
                    
                    Collection<Comment> comments = commentServices.selectAllCommentByQuestionAnswerId(questionAnswerId);
        
                    if(!comments.isEmpty())
                    {
                        response.put("comments", comments);
                        response.put("hasComments", HttpStatus.OK);
                    }
                    else if(comments.isEmpty())
                    {
                        response.put("hasComments", HttpStatus.NO_CONTENT);
                    }
                    
                }
                
            }
        }
        
        
        
        
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}






































