package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.PageViewDao;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.PageView;

/**
 *
 * @author Var Arenz Villarino
 */
@Repository("pageViewDao")
public class PageViewDaoImpl extends AbstractDao<Integer, PageView> implements PageViewDao
{
    
    @Override
    public PageView selectPageViewByArticleId(String articleId) 
    {
        
        Query query = getSession().createQuery("from PageView where articleId = :articleId");
        query.setParameter("articleId", articleId);
        
        PageView pageView = (PageView) query.uniqueResult();
        
//        Criteria criteria = getSession().createCriteria(PageView.class).add(Restrictions.eq("articleId", articleId));
        
//        PageView pageView = (PageView) criteria.uniqueResult();
        
        return pageView;
    }
    @Override
    public List<PageView> selectAllArticleWhichMostViewed(String campaign) 
    {
        Query query  = getSession().createQuery("from PageView where campaign = :campaign order by view desc");
        query.setParameter("campaign", campaign);
        query.setMaxResults(5);
        List<PageView> list = query.list();
        
        return list;
    }
    @Override
    public void saveView(PageView pageView)
    {
        save(pageView);
    }

    @Override
    public List<PageView> selectTopFiveMostView(String articleId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PageView selectOnePageViewByArticleId(String articleId) 
    {
        Criteria criteria = getSession().createCriteria(PageView.class)
            .add(Restrictions.eq("article.articleId", articleId))
                .setProjection(Projections.projectionList()
                .add(Projections.property("view"), "view"))
                .setResultTransformer(Transformers.aliasToBean(PageView.class));
        criteria.setMaxResults(1);
        PageView numOfView = (PageView) criteria.uniqueResult();
        return numOfView;
    }
    
}
