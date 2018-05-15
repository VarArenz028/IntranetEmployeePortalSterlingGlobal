package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.NumberOfArticle;

/**
 *
 * @author Var Arenz Villarino
 */
public interface NumberOfArticleDao 
{
    NumberOfArticle selectNumberOfArticleByEmpId(Integer empId);
    
    List<NumberOfArticle> selectTopFiveContributersByCampaignName(String campaignName);
    
    List<NumberOfArticle> selectAllNumberOfArticleByCampaignName(String campaignName);
    
    void saveNumberOfArticle(NumberOfArticle numberOfArticle);
    
}
