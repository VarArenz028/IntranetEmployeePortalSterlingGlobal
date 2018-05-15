package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.ArticleCategoryDao;
import org.sterling.intranet.models.ArticleCategory;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("articleCategoryDao")
public class ArticleCategoryDaoImpl extends AbstractDao<Integer, ArticleCategory> implements ArticleCategoryDao
{

    @Override
    public List<ArticleCategory> listAllCategories() 
    {
        Criteria criteria = createCriteria();
        List<ArticleCategory> list = criteria.list();
        return list;
    }
    
    @Override
    public ArticleCategory getArticleCategoryById(Integer id)
    {
        Query query = getSession().createQuery("from ArticleCategory where categoryId = :categoryId");
        query.setParameter("categoryId", id);
        
        ArticleCategory articleCategory = (ArticleCategory) query.uniqueResult();
        
        return articleCategory;
    }
    @Override
    public List<ArticleCategory> fetchAllCategoriesByCampaignName(String campaignName) 
    {
        Query query = getSession().createQuery("from ArticleCategory where campaign = :campaign");
        query.setParameter("campaign", campaignName);
        
        List<ArticleCategory> list = query.list();
        
        return list;
    }
    @Override
    public boolean selectByCategoryName(String categoryName) 
    {
        
        boolean result;
        Query query = getSession().createQuery("from ArticleCategory where categoryName = :categoryName");
        query.setParameter("categoryName", categoryName);
        
        ArticleCategory articleCategory = (ArticleCategory) query.uniqueResult();
        
        if(articleCategory != null)
        {
            result = false;
            return result;
        }
        else
        {
            result = true;
            return result;
        }
        
    }
    @Override
    public void saveCategory(ArticleCategory articleCategory) 
    {
        save(articleCategory);
    }

    @Override
    public ArticleCategory selectCategoryByCategoryName(String category)
    {
        Query query = getSession().createQuery("from ArticleCategory where categoryName = :category");
        query.setParameter("category", category);
        
        ArticleCategory articleCategory = (ArticleCategory) query.uniqueResult();
        return articleCategory;
    }

    @Override
    public void deleteCategory(ArticleCategory articleCategory)
    {
        delete(articleCategory);
    }

}
