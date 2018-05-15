package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.ArticleCategory;
import org.sterling.intranet.models.CategoryStatistics;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface ArticleCategoryServices 
{
    List<ArticleCategory> listAllCategories();
    
    List<ArticleCategory> fetchAllCategoriesByCampaignName(String campaignName);
    
    ArticleCategory selectCategoryByCategoryName(String category);
    
    ArticleCategory getArticleCategoryById(Integer id);
    
    boolean validateCategoryName(String categoryName);
    
    void saveCategory(ArticleCategory articleCategory);
    
    void updateCategory(String newCategory, String oldCategory);
    
    void deleteCategory(ArticleCategory articleCategory);
    
}
