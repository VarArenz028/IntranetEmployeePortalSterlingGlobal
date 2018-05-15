package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.ArticleRecentlyRevised;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface ArticleRecentlyRevisedDao 
{
    
    List<ArticleRecentlyRevised> selectMostRecentlyRevisedByCampaignName(String campaign);
    
    void saveRecentlyRevised(ArticleRecentlyRevised articleRecentlyRevised);
}
