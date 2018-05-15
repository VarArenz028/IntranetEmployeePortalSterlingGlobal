package org.sterling.intranet.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.ApprovedArticleDao;
import org.sterling.intranet.models.ApprovedArticle;

/**
 *
 * @author Var Arenz Villarino
 */
@Repository("approvedArticleDao")
public class ApprovedArticleDaoImpl extends AbstractDao<Integer, ApprovedArticle> implements ApprovedArticleDao
{

    @Override
    public ApprovedArticle selectApprovedArticleByArticleId(String articleId) 
    {
        Criteria criteria = getSession().createCriteria(ApprovedArticle.class).add(Restrictions.eq("article.articleId", articleId));
        
        ApprovedArticle approvedArticle = (ApprovedArticle) criteria.uniqueResult();
        
        return approvedArticle;
    }
    
    @Override
    public void saveApprovedArticle(ApprovedArticle approvedArticle) 
    {
        save(approvedArticle);
    }

    
    
}
