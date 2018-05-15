package org.sterling.intranet.dao;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.transform.Transformers;
import org.joda.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.ArticleWritingDao;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.User;
import org.sterling.intranet.utils.DateStamp;

/**
 *
 * @author Var Arenz Villarino
 */
@Repository("articleWritingDao")
@SuppressWarnings("unchecked")
public class ArticleWritingDaoImpl extends AbstractDao<Integer, Article> implements ArticleWritingDao
{

    @Override
    public List<Article> getAllArticle() 
    {
        Criteria criteria = createCriteria();
        List<Article> list = criteria.list();
        return list;
    }
    
    @Override
    public List<Article> getAllArticleForCurrentUser(Integer empId, String campaign) 
    {

        Criteria cr = getSession().createCriteria(Article.class).createAlias("employeeDetails", "employeeDetails")
            .add(Restrictions.eq("employeeDetails.empDetailsId", (int) empId))
            .add(Restrictions.and(Restrictions.eq("campaign", campaign)))
                .setProjection(Projections.projectionList()
                .add(Projections.property("articleId"), "articleId")
                .add(Projections.property("title"), "title")
                .add(Projections.property("approvedArticle"), "approvedArticle")
                .add(Projections.property("category"), "category")
                .add(Projections.property("state"), "state")
                .add(Projections.property("dateCreated"), "dateCreated"))
            .setResultTransformer(Transformers.aliasToBean(Article.class)
            );
        List<Article> list = cr.list();
        
        return list;
    }
    @Override
    public List<Article> selectAllPinToTopArticle(String campaign, String pushpin)
    {
        
        Criteria criteria = getSession().createCriteria(Article.class).add(Restrictions.eq("campaign", campaign))
                          .add(Restrictions.and(Restrictions.eq("pushpin", pushpin)))
                          .setProjection(Projections.projectionList()
                          .add(Projections.property("articleId"), "articleId")
                          .add(Projections.property("title"), "title")
                          .add(Projections.property("category"), "category")
                          .add(Projections.property("type"), "type")
                          .add(Projections.property("dateCreated"), "dateCreated"))
                          .setResultTransformer(Transformers.aliasToBean(Article.class));
        List<Article> list = criteria.list();
        
        return list;
    }
    
    @Override
    public List<Article> listArticleByCampaignNameState(String campaign, String state) 
    {
        Criteria criteria = getSession().createCriteria(Article.class)
            .add(Restrictions.eq("campaign", campaign))
            .add(Restrictions.and(Restrictions.eq("state", state)))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("title"), "title")
                            .add(Projections.property("category"), "category")
                            .add(Projections.property("campaign"), "campaign"))
                            .setResultTransformer(Transformers.aliasToBean(Article.class));
        List<Article> list = criteria.list();
        return list;
    }
    
    @Override
    public List<Article> listArticleByCampaignNameStateAndEmpId(String campaign, String state, Integer empId) 
    {
        Criteria criteria = getSession().createCriteria(Article.class).createAlias("employeeDetails", "employeeDetails")
            .add(Restrictions.eq("campaign", campaign))
            .add(Restrictions.and(Restrictions.eq("state", state))
            .add(Restrictions.and(Restrictions.eq("employeeDetails.empDetailsId", (int) empId))))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("title"), "title")
                            .add(Projections.property("category"), "category")
                            .add(Projections.property("campaign"), "campaign"))
                            .setResultTransformer(Transformers.aliasToBean(Article.class));
        List<Article> list = criteria.list();
        return list;
    }
    
    @Override
    public List<Article> articleStatistics(String campaignName, String state, String category) 
    {
        Criteria criteria = getSession().createCriteria(Article.class)
            .add(Restrictions.eq("campaign", campaignName))
            .add(Restrictions.and(Restrictions.eq("category", category)))
            .add(Restrictions.and(Restrictions.eq("state", state)))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("title"), "title")
                            .add(Projections.property("category"), "category")
                            .add(Projections.property("campaign"), "campaign"))
                            .setResultTransformer(Transformers.aliasToBean(Article.class));
        List<Article> list = criteria.list();
        return list;
    }
    
    @Override
    public List<Article> getAllArticleByCampaignName(String campaign) 
    {
        Criteria criteria = getSession().createCriteria(Article.class).add(Restrictions.eq("campaign", campaign))
             .setProjection(Projections.projectionList()
             .add(Projections.property("articleId"), "articleId")
             .add(Projections.property("category"), "category")
             .add(Projections.property("title"), "title")
             .add(Projections.property("dateCreated"), "dateCreated"))
            .setResultTransformer(Transformers.aliasToBean(Article.class));
        
        List<Article> list = criteria.list();
        
        return list;
    }
    
    @Override
    public Article selectArticleById(String articleId)
    {
        
        Query query = getSession().createQuery("from Article where articleId = :articleId");
        query.setParameter("articleId", articleId);
        
        Article article = (Article) query.uniqueResult();
        
        return article;
    }
    
    @Override
    public Article selectArticleByIdCampaignAndState(String articleId, String campaign, String state)
    {
        Query query = getSession().createQuery("from Article where articleId  = :articleId and campaign = :campaign and state = :state");
        query.setParameter("articleId", articleId);
        query.setParameter("campaign", campaign);
        query.setParameter("state", state);
        
        Article article = (Article) query.uniqueResult();
        
        return article;
    }
    @Override
    public Article findArticleById(String articleId)
    {
        
        Query query = getSession().createQuery("from Article where articleId = :articleId");
        query.setParameter("articleId", articleId);
        
        Article article = (Article) query.uniqueResult();
        
        return article;
    }
    @Override
    public void saveArticle(Article article)
    {
        save(article);
    }

    //note: edit to get today and yesterday
    // not finished logical and code
    @Override
    public List<Article> getArticleRecentlyAdded()
    {
        DateStamp dateStamp = new DateStamp();
        String date;
        
        Query query = getSession().createQuery("from Article where dateString like :dateString");
        try 
        {
            Integer year = dateStamp.getLocalDateTime().getYear();
            Integer month = dateStamp.getLocalDateTime().getMonthOfYear();
            Integer day = dateStamp.getLocalDateTime().getDayOfMonth();
            date = "%" + year.toString() + "-" + month.toString() + "-" + day.toString() + "%";
            System.out.println(date);
            
            query.setParameter("dateString", date);
        } 
        catch (ParseException ex)
        {
            Logger.getLogger(ArticleWritingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Article> list = query.list();
        return list;
    }

    @Override
    public List<Article> getAllArticleNewsFeedData() 
    {
        Criteria cr = getSession().createCriteria(Article.class)
            .setProjection(Projections.projectionList()
              .add(Projections.property("articleId"), "articleId")
              .add(Projections.property("title"), "title")
            .add(Projections.property("category"), "category")
            .add(Projections.property("type"), "type")
            .add(Projections.property("dateCreated"), "dateCreated")
            .add(Projections.property("dateString"), "dateString"))
            .setResultTransformer(Transformers.aliasToBean(Article.class));
        List<Article> list = cr.list();
        return list;
    }
    
    @Override
    public List<Article> selectArticleByCampaignAndState(String campaign, String state) 
    {
        Query query = getSession().createQuery("from Article where campaign = :campaign and state = :state");
        query.setParameter("campaign", campaign);
        query.setParameter("state", state);
        
        List<Article> list = query.list();
        
        return list;
    }

    @Override
    public Article selectTitleByArticleId(String articleId) 
    {
        Criteria criteria = getSession().createCriteria(Article.class).add(Restrictions.eq("articleId", articleId))
                          .setProjection(Projections.projectionList()
                          .add(Projections.property("title"), "title")
                          .add(Projections.alias(Projections.property("employeeDetails"), "employeeDetails"))
                          .add(Projections.property("employeeDetails"), "employeeDetails"))
                          .setResultTransformer(Transformers.aliasToBean(Article.class));
        Article article = (Article) criteria.uniqueResult();
        
        return article;
    }

    @Override
    public List<Article> selectTopFiveMostRecentlyAdded(String campaignName)
    {
        Criteria criteria = getSession().createCriteria(Article.class).add(Restrictions.eq("campaign", campaignName))
                          .addOrder(Order.desc("dateCreated"))
                          .setProjection(Projections.projectionList()
                          .add(Projections.property("articleId"), "articleId")
                          .add(Projections.property("title"), "title")
                          .add(Projections.property("dateCreated"), "dateCreated"))
                          .setResultTransformer(Transformers.aliasToBean(Article.class));
        criteria.setMaxResults(5);
        
        List<Article> list = criteria.list();
        
        return list;
    }

    @Override
    public List<Article> listAllPendingArticleByCampaignName(String campaign, String state) 
    {
        Criteria criteria = getSession().createCriteria(Article.class)
            .add(Restrictions.eq("campaign", campaign))
            .add(Restrictions.and(Restrictions.eq("state", state)))
                .setProjection(Projections.projectionList()
                .add(Projections.property("articleId"), "articleId")
                .add(Projections.property("title"), "title")
                .add(Projections.property("category"), "category")
                .add(Projections.property("dateCreated"), "dateCreated"))
                .setResultTransformer(Transformers.aliasToBean(Article.class));
        
        List<Article> list = criteria.list();
        return list;
    }

    @Override
    public Article selectPendingArticle(String articleId, String state, String campaign)
    {
        Criteria criteria = getSession().createCriteria(Article.class)
            .add(Restrictions.eq("campaign", campaign))
            .add(Restrictions.and(Restrictions.eq("state", state))
            .add(Restrictions.and(Restrictions.eq("articleId", articleId))))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("title"), "title")
                            .add(Projections.property("category"), "category")
                            .add(Projections.property("campaign"), "campaign"))
                            .setResultTransformer(Transformers.aliasToBean(Article.class));
        Article article = (Article) criteria.uniqueResult();
        return article;
    }

    @Override
    public Article selectOnlyStateByArticleId(String articleId) 
    {
        Criteria criteria = getSession().createCriteria(Article.class)
            .add(Restrictions.eq("articleId", articleId))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("state"), "state"))
                            .setResultTransformer(Transformers.aliasToBean(Article.class));
        Article article = (Article) criteria.uniqueResult();
        return article;
    }

    @Override
    public Article selectOneArticleByArticleId(String articleId) 
    {
        Criteria criteria = getSession().createCriteria(Article.class).add(Restrictions.eq("articleId", articleId));
        
        Article article = (Article) criteria.uniqueResult();
        
        return article;
    }

    @Override
    public Article selectToBeUpdateByArticleId(String articleId) 
    {
        Criteria criteria = getSession().createCriteria(Article.class)
            .add(Restrictions.eq("articleId", articleId))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("title"), "title")
                            .add(Projections.property("category"), "category")
                            .add(Projections.property("content"), "content"))
                            .setResultTransformer(Transformers.aliasToBean(Article.class));
        Article article = (Article) criteria.uniqueResult();
        return article;
    }

    @Override
    public List<Article> selectAllNumOfPendingArticleByCampaignAndState(String campaign, String state) 
    {
        Criteria criteria = getSession().createCriteria(Article.class)
            .add(Restrictions.eq("campaign", campaign))
            .add(Restrictions.and(Restrictions.eq("state", state)))
                .setProjection(Projections.projectionList()
                .add(Projections.property("articleId"), "articleId"))
                .setResultTransformer(Transformers.aliasToBean(Article.class));
        
        List<Article> list = criteria.list();
        return list;
    }

    @Override
    public Integer selectEmpIdByArticleId(String articleId) 
    {
        Query query = getSession().createQuery("Select a.employeeDetails.empDetailsId from Article a where a.articleId = :articleId");
        query.setParameter("articleId", articleId);
        
        Integer empDetailsIs = (Integer) query.uniqueResult();
        
        return empDetailsIs;
    }

    @Override
    public List<Article> selectAllArticleByCampaignAndState(String campaign, String state) 
    {
        Criteria criteria = getSession().createCriteria(Article.class)
            .add(Restrictions.eq("campaign", campaign))
            .add(Restrictions.and(Restrictions.eq("state", state)))
                .setProjection(Projections.projectionList()
                .add(Projections.property("articleId"), "articleId")
                .add(Projections.property("title"), "title")
                .add(Projections.property("category"), "category")
                .add(Projections.property("dateCreated"), "dateCreated"))
                .setResultTransformer(Transformers.aliasToBean(Article.class));
        
        List<Article> list = criteria.list();
        return list;
    }
    
    @Override
    public List<Article> selectAllArticleByCampaignStateAndCategory(String campaign, String state, String category)
    {
        Criteria criteria = getSession().createCriteria(Article.class)
            .add(Restrictions.eq("campaign", campaign))
            .add(Restrictions.and(Restrictions.eq("category", category)))
            .add(Restrictions.and(Restrictions.eq("state", state)))
                .setProjection(Projections.projectionList()
                .add(Projections.property("articleId"), "articleId")
                .add(Projections.property("title"), "title")
                .add(Projections.property("category"), "category")
                .add(Projections.property("dateCreated"), "dateCreated"))
                .setResultTransformer(Transformers.aliasToBean(Article.class));
        
        List<Article> list = criteria.list();
        return list;
    }

    @Override
    public List<Article> articleSearch(String articleSearch)
    {
        FullTextSession fullTextSession = Search.getFullTextSession(getSession());
        QueryBuilder articleQueryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
        
        org.apache.lucene.search.Query articleQuery = articleQueryBuilder.keyword().onFields("title").matching(articleSearch).createQuery();
        
        org.hibernate.Query hibArticleQuery = fullTextSession.createFullTextQuery(articleQuery, Article.class);
        
        List<Article> articles = hibArticleQuery.list();
        
        return articles;
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
    public Map<String, Object> updateArticleCategoryByCampaignAndCatagoryName(String campaign, String newCategory, String oldCategory) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        Query query = getSession().createQuery("update Article a set a.category = :newCategory where a.category = :oldCategory and a.campaign = :campaign");
        query.setParameter("newCategory", newCategory);
        query.setParameter("oldCategory", oldCategory);
        query.setParameter("campaign", campaign);
        int update = query.executeUpdate();
        if(update != 0)
        {

            response.put("isUpdated", HttpStatus.OK);
        }
        else
        {
            
            response.put("isUpdated", HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
        
        return response;
    }

    @Override
    public Map<String, Object> updateArticleCategoryIntoUnassignedByCampaignAndCatagoryName(String campaign, String deletedCategory, String unassigned) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        Query query = getSession().createQuery("update Article a set a.category = :unassigned where a.category = :deletedCategory and a.campaign = :campaign");
        query.setParameter("campaign", campaign);
        query.setParameter("deletedCategory", deletedCategory);
        query.setParameter("unassigned", unassigned);
        int update = query.executeUpdate();
        if(update != 0)
        {

            response.put("isUpdated", HttpStatus.OK);
        }
        else
        {
            
            response.put("isUpdated", HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
        
        return response;
    }

    @Override
    public Map<String, Object> selectAllAndUpdateArticleByArticleIdListAndCampaign(List<String> articleId, String category, String campaign)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        Query query = getSession().createQuery("update Article a set a.category = :category where a.articleId in(:articleId) and a.campaign = :campaign");
        query.setParameterList("articleId", articleId);
        query.setParameter("category", category);
        query.setParameter("campaign", campaign);
        
        int update = query.executeUpdate();
        if(update != 0)
        {

            response.put("isUpdated", HttpStatus.OK);
        }
        else
        {
            
            response.put("isUpdated", HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
        
        return response;
    }

    @Override
    public Map<String, Object> inactiveArticleByArticleIdAndCampaign(String articleId, String campaign, String state)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        Query query = getSession().createQuery("update Article a set a.state = :state where a.articleId = :articleId and a.campaign = :campaign");
        query.setParameter("articleId", articleId);
        query.setParameter("state", state);
        query.setParameter("campaign", campaign);
        
        int update = query.executeUpdate();
        if(update != 0)
        {

            response.put("isUpdated", HttpStatus.OK);
        }
        else
        {
            
            response.put("isUpdated", HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
        
        return response;
    }

    

}
