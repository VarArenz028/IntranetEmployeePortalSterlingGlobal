package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.DraftArticleDao;
import org.sterling.intranet.models.DraftArticle;

/**
 *
 * @author Var Arenz Villarino
 */
@Repository("draftArticleDao")
public class DraftArticleDaoImpl extends AbstractDao<Integer, DraftArticle> implements DraftArticleDao
{
    
    @Override
    public DraftArticle selectDraftArticleIdByDraftArticleId(String campaign, String id) 
    {
        DraftArticle draftArticle;
        try
        {
            Criteria criteria = getSession().createCriteria(DraftArticle.class).add(Restrictions.eq("campaign", campaign))
            .add(Restrictions.eq("draftArticleId", id))
                .setProjection(Projections.projectionList()
                  .add(Projections.property("draftArticleId"), "draftArticleId")
                  .add(Projections.property("campaign"), "campaign"))
                .setResultTransformer(Transformers.aliasToBean(DraftArticle.class)).setMaxResults(1);
        
            draftArticle = (DraftArticle) criteria.uniqueResult();
        }
        catch(NullPointerException e)
        {
            draftArticle = null;
        }
        
        return draftArticle;
    }
    
    @Override
    public DraftArticle selectDraftArticleByCampaignAndId(String campaign, String id)
    {
  
        Query query = getSession().createQuery("from DraftArticle where campaign = :campaign and draftArticleId = :draftArticleId");
        query.setParameter("campaign", campaign);
        query.setParameter("draftArticleId", id);
        DraftArticle draftArticle = (DraftArticle) query.uniqueResult();
        
        return draftArticle;
    }
    
    @Override
    public List<DraftArticle> selectAllDraftArticles(Integer empDetailsId, String campaign) 
    {
//        Query query = getSession().createQuery("from DraftArticle where empDetailsId = :empDetailsId and campaign = :campaign");
//        query.setParameter("empDetailsId", empId);
//        query.setParameter("campaign", campaign);
        Criteria criteria = getSession().createCriteria(DraftArticle.class).createAlias("employeeDetails", "employeeDetails")
            .add(Restrictions.eq("campaign", campaign))
            .add(Restrictions.and(Restrictions.eq("employeeDetails.empDetailsId", (int) empDetailsId)))
                .setProjection(Projections.projectionList()
                .add(Projections.property("draftArticleId"), "draftArticleId")
                .add(Projections.property("title"), "title")
                .add(Projections.property("category"), "category"))
                    .setResultTransformer(Transformers.aliasToBean(DraftArticle.class));
        List<DraftArticle> list = criteria.list();
        
        return list;
    }
    
    @Override
    public void saveDraft(DraftArticle draftArticle)
    {
        save(draftArticle);
    }

    @Override
    public void deleteDraftArticle(DraftArticle draftArticle) 
    {
        delete(draftArticle);
    }

    

    
    
}
