package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.ArticleRecentlyRevisedDao;
import org.sterling.intranet.models.ArticleRecentlyRevised;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("articleRecentlyRevisedDao")
public class ArticleRecentlyRevisedDaoImpl extends AbstractDao<Integer, ArticleRecentlyRevised> implements ArticleRecentlyRevisedDao
{

    @Override
    public List<ArticleRecentlyRevised> selectMostRecentlyRevisedByCampaignName(String campaign) 
    {
        Criteria criteria = getSession().createCriteria(ArticleRecentlyRevised.class)
                          .add(Restrictions.eq("campaign", campaign))
                          .addOrder(Order.desc("dateRevised"))
                          .setMaxResults(5);
        List<ArticleRecentlyRevised> articleRecentlyReviseds = criteria.list();
        return articleRecentlyReviseds;
    }
    
    @Override
    public void saveRecentlyRevised(ArticleRecentlyRevised articleRecentlyRevised) 
    {
        save(articleRecentlyRevised);
    }

    
    
}
