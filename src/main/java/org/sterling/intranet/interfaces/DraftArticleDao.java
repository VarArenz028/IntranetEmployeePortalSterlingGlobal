package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.DraftArticle;

/**
 *
 * @author Var Arenz Villarino
 */
public interface DraftArticleDao 
{
    
    DraftArticle selectDraftArticleIdByDraftArticleId(String campaign, String id);
    
    DraftArticle selectDraftArticleByCampaignAndId(String campaign, String id);
    
    List<DraftArticle> selectAllDraftArticles(Integer empDetailsId, String campaign);
    
    void saveDraft(DraftArticle draftArticle);
    
    void deleteDraftArticle(DraftArticle draftArticle);
    
}
