package org.sterling.intranet.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.SearchDao;
import org.sterling.intranet.model.json.SearchModel;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.Comment;
import org.sterling.intranet.models.Event;
import org.sterling.intranet.models.News;
import org.sterling.intranet.models.Question;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("searchDao")
@SuppressWarnings("unchecked")
public class SearchDaoImpl implements SearchDao
{

    @Autowired
    private SessionFactory sessionFactory;
    
    protected Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public void index() throws Exception 
    {
        try
        {

           FullTextSession fullTextSession = Search.getFullTextSession(getSession());
           fullTextSession.createIndexer().startAndWait();
        }
        catch(Exception e)
        {
           throw e;
        }
    }

    @Override
    public List<SearchModel> search(String search) 
    {
        
        List<SearchModel> searchModels = new ArrayList();
        searchModels.clear();
        
        Analyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
        
        FullTextSession articleTextSession = Search.getFullTextSession(getSession());
        
        QueryBuilder articleQueryBuilder = articleTextSession.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
        org.apache.lucene.search.Query articleQuery = articleQueryBuilder.keyword().onFields("title", "category").matching(search).createQuery();
        org.hibernate.Query hibArticleQuery = articleTextSession.createFullTextQuery(articleQuery, Article.class);
        List<Article> articles = hibArticleQuery.list();
        
        
//        newsFeedObject.setEmpName(employeeDetails.getLastName().substring(0,1).toUpperCase() + 
//                                                    employeeDetails.getLastName().substring(1).toLowerCase()
//                                                    + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
//                                                    employeeDetails.getFirstName().substring(1).toLowerCase());
        
        for(Article article : articles)
        {
            SearchModel searchModel = new SearchModel();
            searchModel.setSearchedId(article.getArticleId());
            searchModel.setTitle(article.getTitle());
            searchModel.setCategory(article.getCategory());
            searchModel.setEmpName(article.getEmployeeDetails().getLastName().substring(0,1).toUpperCase() + 
                              article.getEmployeeDetails().getLastName().substring(1).toLowerCase()
                                                    + " " + article.getEmployeeDetails().getFirstName().substring(0,1).toUpperCase() + 
                                                    article.getEmployeeDetails().getFirstName().substring(1).toLowerCase());
            searchModel.setDateCreated(article.getDateCreated());
            searchModel.setType("article");
            searchModels.add(searchModel);
        }
        
        
        
        
        FullTextSession questionTextSession = Search.getFullTextSession(getSession());
        QueryBuilder questionQueryBuilder = questionTextSession.getSearchFactory().buildQueryBuilder().forEntity(Question.class).get();
        
        org.apache.lucene.search.Query questionQuery = questionQueryBuilder.keyword().onFields("title").matching(search).createQuery();
        
        org.hibernate.Query hibQuestionQuery = questionTextSession.createFullTextQuery(questionQuery, Question.class);
        
        List<Question> questions = hibQuestionQuery.list();
        
        for(Question question : questions)
        {
            SearchModel searchModel = new SearchModel();
            searchModel.setSearchedId(question.getQuestionId());
            searchModel.setTitle(question.getTitle());
            searchModel.setEmpName(question.getEmployeeDetails().getLastName().substring(0,1).toUpperCase() + 
                              question.getEmployeeDetails().getLastName().substring(1).toLowerCase()
                                                    + " " + question.getEmployeeDetails().getFirstName().substring(0,1).toUpperCase() + 
                                                    question.getEmployeeDetails().getFirstName().substring(1).toLowerCase());
            searchModel.setDateCreated(question.getDateCreated());
            searchModel.setType("question");
            searchModels.add(searchModel);
        }
        
        
        FullTextSession newsTextSession = Search.getFullTextSession(getSession());
        QueryBuilder newsQueryBuilder = newsTextSession.getSearchFactory().buildQueryBuilder().forEntity(News.class).get();
        
        org.apache.lucene.search.Query newsQuery = newsQueryBuilder.keyword().onFields("newsTitle").matching(search).createQuery();
        
        org.hibernate.Query hibNewsQuery = newsTextSession.createFullTextQuery(newsQuery, News.class);
        
        List<News> news = hibNewsQuery.list();
        
        for(News n : news)
        {
            SearchModel searchModel = new SearchModel();
            searchModel.setSearchedId(n.getNewsId());
            searchModel.setTitle(n.getNewsTitle());
            searchModel.setEmpName(n.getEmployeeDetails().getLastName().substring(0,1).toUpperCase() + 
                              n.getEmployeeDetails().getLastName().substring(1).toLowerCase()
                                                    + " " + n.getEmployeeDetails().getFirstName().substring(0,1).toUpperCase() + 
                                                    n.getEmployeeDetails().getFirstName().substring(1).toLowerCase());
            searchModel.setDateCreated(n.getDateCreated());
            searchModel.setType("news");
            searchModels.add(searchModel);
        }
        
        
        FullTextSession eventTextSession = Search.getFullTextSession(getSession());
        QueryBuilder eventQueryBuilder = eventTextSession.getSearchFactory().buildQueryBuilder().forEntity(Event.class).get();
        
        org.apache.lucene.search.Query eventQuery = eventQueryBuilder.keyword().onFields("eventName").matching(search).createQuery();
        
        org.hibernate.Query hibEventQuery = eventTextSession.createFullTextQuery(eventQuery, Event.class);
       
        List<Event> events = hibEventQuery.list();
        
        for(Event event : events)
        {
            SearchModel searchModel = new SearchModel();
            searchModel.setSearchedId(event.getEventId());
            searchModel.setTitle(event.getEventName());
            searchModel.setEmpName(event.getEmployeeDetails().getLastName().substring(0,1).toUpperCase() + 
                              event.getEmployeeDetails().getLastName().substring(1).toLowerCase()
                                                    + " " + event.getEmployeeDetails().getFirstName().substring(0,1).toUpperCase() + 
                                                    event.getEmployeeDetails().getFirstName().substring(1).toLowerCase());
            searchModel.setDateCreated(event.getDateCreated());
            searchModel.setType("event");
            searchModels.add(searchModel);
        }
        
        
        
        return searchModels;
    }
    
}
