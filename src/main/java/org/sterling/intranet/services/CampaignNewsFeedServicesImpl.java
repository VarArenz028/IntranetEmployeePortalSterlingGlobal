package org.sterling.intranet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.ArticleWritingDao;
import org.sterling.intranet.interfaces.CampaignNewsFeedServices;
import org.sterling.intranet.interfaces.EmployeeDetailsDao;
import org.sterling.intranet.interfaces.EventsDao;
import org.sterling.intranet.interfaces.NewsDao;
import org.sterling.intranet.interfaces.PageViewDao;
import org.sterling.intranet.interfaces.QuestionAnswerDao;
import org.sterling.intranet.interfaces.QuestionDao;
import org.sterling.intranet.interfaces.UserDao;
import org.sterling.intranet.model.json.NewsFeedObject;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.Event;
import org.sterling.intranet.models.News;
import org.sterling.intranet.models.PageView;
import org.sterling.intranet.models.Question;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("campaignNewsFeedServices")
public class CampaignNewsFeedServicesImpl implements CampaignNewsFeedServices
{
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private EmployeeDetailsDao employeeDetailsDao;
    
    @Autowired
    private ArticleWritingDao articleWritingDao;
    
    @Autowired
    private QuestionDao questionDao;
    
    @Autowired
    private QuestionAnswerDao questionAnswerDao;
    
    @Autowired
    private NewsDao newsDao;
    
    @Autowired
    private EventsDao eventsDao;
    
    @Autowired
    private PageViewDao pageViewDao;
    
    @Transactional(readOnly = true)
    @Override
    public List<NewsFeedObject> fetchAllCampaignNewsFeed(String campaign) 
    {
        
        List<Article> newsFeedArticles = articleWritingDao.selectAllArticleByCampaignAndState(campaign, "Approved");
        
        List<NewsFeedObject> newsFeedObjects = new ArrayList();
        
        for(Article article : newsFeedArticles)
        {
            Integer articleEmpDetailsID = articleWritingDao.selectEmpIdByArticleId(article.getArticleId());
            NewsFeedObject newsFeedObject = new NewsFeedObject();
            
            EmployeeDetails employeeDetails = employeeDetailsDao.selectOneEmployeeDetailsByEmpId(articleEmpDetailsID);
            
            PageView view = pageViewDao.selectOnePageViewByArticleId(article.getArticleId());

            
            newsFeedObject.setNewsFeedId(article.getArticleId());
            newsFeedObject.setNewsFeedTitle(article.getTitle());
            newsFeedObject.setArticleCategory(article.getCategory());
            newsFeedObject.setEmpName(employeeDetails.getLastName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getLastName().substring(1).toLowerCase()
                                                    + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getFirstName().substring(1).toLowerCase());
            newsFeedObject.setDateCreated(article.getDateCreated());
            newsFeedObject.setNumberOfView(view.getView());
            newsFeedObject.setNewsFeedType("article");
            
            newsFeedObjects.add(newsFeedObject);
        }
        
        List<Question> questions = questionDao.selectAllSpecificAttrbsQuestionByCampaignName(campaign);
        
        for(Question question : questions)
        {
            Long numOfAnswer = questionAnswerDao.numberOfAnswerByQuestionId(question.getQuestionId());
            Integer empDetailsId = questionDao.selectEmpIdByQuestionId(question.getQuestionId());
            EmployeeDetails employeeDetails = employeeDetailsDao.selectOneEmployeeDetailsByEmpId(empDetailsId);
            
            NewsFeedObject newsFeedObject = new NewsFeedObject();
            newsFeedObject.setNewsFeedId(question.getQuestionId());
            newsFeedObject.setNewsFeedTitle(question.getTitle());
            newsFeedObject.setDateCreated(question.getDateCreated());
            newsFeedObject.setNumberOfAnswer(numOfAnswer);
            newsFeedObject.setEmpName(employeeDetails.getLastName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getLastName().substring(1).toLowerCase()
                                                    + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getFirstName().substring(1).toLowerCase());
            newsFeedObject.setNewsFeedType("question");
            newsFeedObjects.add(newsFeedObject);
        }
        
        List<News> news = newsDao.selectAllSpecificAttbsByCampaignName(campaign);
        
        for(News n : news)
        {
            Integer empDetailsId = newsDao.selectEmpIdByNewsId(n.getNewsId());
            EmployeeDetails employeeDetails = employeeDetailsDao.selectOneEmployeeDetailsByEmpId(empDetailsId);
            NewsFeedObject newsFeedObject = new NewsFeedObject();
            
            newsFeedObject.setNewsFeedId(n.getNewsId());
            newsFeedObject.setNewsFeedTitle(n.getNewsTitle());
            newsFeedObject.setDateCreated(n.getDateCreated());
            newsFeedObject.setBase64(n.getBase64());
            newsFeedObject.setFileType(n.getFileType());
            newsFeedObject.setEmpName(employeeDetails.getLastName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getLastName().substring(1).toLowerCase()
                                                    + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getFirstName().substring(1).toLowerCase());
            newsFeedObject.setNewsFeedType("news");
            newsFeedObjects.add(newsFeedObject);
        }
        
        List<Event> events = eventsDao.selectSpecificAttbsByCampaignName(campaign);
        
        for(Event event : events)
        {
            Integer empDetailsId = eventsDao.selectEmpIdByEventId(event.getEventId());
            EmployeeDetails employeeDetails = employeeDetailsDao.selectOneEmployeeDetailsByEmpId(empDetailsId);
            NewsFeedObject newsFeedObject = new NewsFeedObject();
            
            newsFeedObject.setNewsFeedId(event.getEventId());
            newsFeedObject.setNewsFeedTitle(event.getEventName());
            newsFeedObject.setDateCreated(event.getDateCreated());
            newsFeedObject.setEventDate(event.getEventDate());
            newsFeedObject.setEventLocation(event.getLocation());
            newsFeedObject.setEmpName(employeeDetails.getLastName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getLastName().substring(1).toLowerCase()
                                                    + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
                                                    employeeDetails.getFirstName().substring(1).toLowerCase());
            newsFeedObject.setEventDetails(event.getDetails());
            newsFeedObject.setBase64(event.getBase64());
            newsFeedObject.setFileType(event.getFileType());
            newsFeedObject.setNewsFeedType("event");
            
            newsFeedObjects.add(newsFeedObject);
            
        }
        
        return newsFeedObjects;
        
    }
    
}
