package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.ArticleCategory;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface ArticleCategoryDao
{
    
    ArticleCategory selectCategoryByCategoryName(String category);
    
    List<ArticleCategory> listAllCategories();
    
    List<ArticleCategory> fetchAllCategoriesByCampaignName(String campaignName);
    
    ArticleCategory getArticleCategoryById(Integer id);
    
    boolean selectByCategoryName(String categoryName);
    
    void saveCategory(ArticleCategory articleCategory);
    
    void deleteCategory(ArticleCategory articleCategory);
    
}
