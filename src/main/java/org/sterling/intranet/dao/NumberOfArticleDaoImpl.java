package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.NumberOfArticleDao;
import org.sterling.intranet.models.NumberOfArticle;

/**
 *
 * @author Var Arenz Villarino
 */
@Repository("numberOfArticleDao")
public class NumberOfArticleDaoImpl extends AbstractDao<Integer, NumberOfArticle> implements NumberOfArticleDao
{

    @Override
    public NumberOfArticle selectNumberOfArticleByEmpId(Integer empId) 
    {
        Query query = getSession().createQuery("from NumberOfArticle where empDetailsId = :empId");
        query.setParameter("empId", empId);
        
        NumberOfArticle numberOfArticle = (NumberOfArticle) query.uniqueResult();
        
        return numberOfArticle;
    }
    @Override
    public List<NumberOfArticle> selectTopFiveContributersByCampaignName(String campaignName)
    {
        
        Query query = getSession().createQuery("from NumberOfArticle where campaign = :campaignName order by numberOfArticle desc");
        query.setParameter("campaignName", campaignName);
        query.setMaxResults(5);
        
        List<NumberOfArticle> list = query.list();
        
        return list;
    }
    
    @Override
    public List<NumberOfArticle> selectAllNumberOfArticleByCampaignName(String campaignName) 
    {
        Query query = getSession().createQuery("from NumberOfArticle where campaign = :campaignName");
        query.setParameter("campaignName", campaignName);
        
        List<NumberOfArticle> list = query.list();
        
        return list;
    }
    
    @Override
    public void saveNumberOfArticle(NumberOfArticle numberOfArticle) 
    {
        save(numberOfArticle);
    }
    
}
