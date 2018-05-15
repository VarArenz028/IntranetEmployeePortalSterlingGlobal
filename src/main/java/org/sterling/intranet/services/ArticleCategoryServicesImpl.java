package org.sterling.intranet.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.ArticleCategoryDao;
import org.sterling.intranet.interfaces.ArticleCategoryServices;
import org.sterling.intranet.interfaces.ArticleWritingDao;
import org.sterling.intranet.models.ArticleCategory;
import org.sterling.intranet.models.CategoryStatistics;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("articleCategoryServices")
public class ArticleCategoryServicesImpl implements ArticleCategoryServices
{
    @Autowired
    private ArticleCategoryDao articleCategoryDao;
    
    @Autowired
    private ArticleWritingDao articleWritingDao;
    
    @Transactional(readOnly = true)
    @Override
    public List<ArticleCategory> listAllCategories()
    {
        return articleCategoryDao.listAllCategories();
    }
    
    @Transactional(readOnly = true)
    @Override
    public ArticleCategory getArticleCategoryById(Integer id) 
    {
        return articleCategoryDao.getArticleCategoryById(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<ArticleCategory> fetchAllCategoriesByCampaignName(String campaignName) 
    {
        return articleCategoryDao.fetchAllCategoriesByCampaignName(campaignName);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean validateCategoryName(String categoryName) 
    {
        return articleCategoryDao.selectByCategoryName(categoryName);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveCategory(ArticleCategory articleCategory)
    {
        articleCategoryDao.saveCategory(articleCategory);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateCategory(String newCategory, String oldCategory)
    {
        ArticleCategory article = articleCategoryDao.selectCategoryByCategoryName(oldCategory);
        if(article != null)
        {
            article.setCategoryName(newCategory);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteCategory(ArticleCategory articleCategory) 
    {
        articleCategoryDao.deleteCategory(articleCategory);
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleCategory selectCategoryByCategoryName(String category) 
    {
        return articleCategoryDao.selectCategoryByCategoryName(category);
    }


}
