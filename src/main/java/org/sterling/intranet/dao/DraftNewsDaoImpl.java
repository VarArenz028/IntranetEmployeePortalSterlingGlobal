package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.DraftNewsDao;
import org.sterling.intranet.models.DraftNews;

/**
 *
 * @author Var Arenz Villarino
 */
@Repository("draftNewsDao")
public class DraftNewsDaoImpl extends AbstractDao<Integer, DraftNews> implements DraftNewsDao
{
    
    @Override
    public DraftNews selectDraftNewsByEmpId(Integer empId)
    {
        Query query = getSession().createQuery("from DraftNews where empDetailsId = :empId");
        query.setParameter("empId", empId);
        
        DraftNews draftNews = (DraftNews) query.uniqueResult();
        
        return draftNews;
    }
    
    @Override
    public DraftNews selectDraftNewsByDraftNewsId(String draftNewsId) 
    {
        Query query = getSession().createQuery("from DraftNews  where draftNewsId = :draftNewsId");
        query.setParameter("draftNewsId", draftNewsId);
        
        DraftNews draftNews = (DraftNews) query.uniqueResult();
        
        return draftNews;
    }

    @Override
    public List<DraftNews> selectAllDraftNewsByCampaignName(String campaign, Integer empId) 
    {
        Criteria criteria = getSession().createCriteria(DraftNews.class).add(Restrictions.eq("campaign", campaign))
                          .add(Restrictions.and(Restrictions.eq("employeeDetails.empDetailsId", empId)))
                          .setProjection(Projections.projectionList()
                          .add(Projections.property("draftNewsId"), "draftNewsId")
                          .add(Projections.property("draftNewsTitle"), "draftNewsTitle")
                          .add(Projections.property("draftNewsContent"), "draftNewsContent")
                          .add(Projections.property("dateCreated"), "dateCreated")
                          .add(Projections.property("base64"), "base64")
                          .add(Projections.property("fileType"), "fileType"))
                          .setResultTransformer(Transformers.aliasToBean(DraftNews.class));
        
        List<DraftNews> list = criteria.list();
        
        return list;
    }

    @Override
    public void saveDraftNews(DraftNews draftNews)
    {
        save(draftNews);
    }

    @Override
    public void deleteDraftNews(DraftNews draftNews) 
    {
        delete(draftNews);
    }
    
}
