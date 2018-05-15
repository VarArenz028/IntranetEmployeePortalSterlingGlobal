package org.sterling.intranet.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.ArticleRejectionDao;
import org.sterling.intranet.models.ArticleRejection;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("articleRejectionDao")
public class ArticleRejectionDaoImpl extends AbstractDao<Integer, ArticleRejection> implements ArticleRejectionDao
{

    @Override
    public ArticleRejection selectArticleRejectionByArticleId(String articleId) 
    {
        Criteria criteria = getSession().createCriteria(ArticleRejection.class).add(Restrictions.eq("article.articleId", articleId))
                          .setProjection(Projections.projectionList()
                          .add(Projections.property("reason"), "reason")
                          .add(Projections.property("rejectedBy"), "rejectedBy")
                          .add(Projections.property("rejectionId"), "rejectionId"))
                          .setResultTransformer(Transformers.aliasToBean(ArticleRejection.class));
        ArticleRejection articleRejection = (ArticleRejection) criteria.uniqueResult();
        return articleRejection;
    }
    
    @Override
    public void saveArticleRejection(ArticleRejection articleRejection) 
    {
        save(articleRejection);
    }

    
    
}
