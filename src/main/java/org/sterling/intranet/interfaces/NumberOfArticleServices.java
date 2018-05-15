package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.AuthorArticleContribute;
import org.sterling.intranet.models.NumberOfArticle;

/**
 *
 * @author Var Arenz Villarino
 */
public interface NumberOfArticleServices
{
    NumberOfArticle selectNumberOfArticleByEmpId(Integer empId);
    
    List<AuthorArticleContribute> selectTopFiveContributersByCampaignName(String campaignName);
    
    List<AuthorArticleContribute> selectAllNumberOfArticleByCampaignName(String campaignName);
    
    void saveNumberOfArticle(NumberOfArticle numberOfArticle);
    
    Map<String, Object> updateNumberOfArticle(Integer empId);
    
    
}
