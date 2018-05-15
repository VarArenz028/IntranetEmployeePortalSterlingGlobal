package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.DraftArticle;

/**
 *
 * @author Var Arenz Villarino
 */
public interface DraftArticleServices
{
    
    DraftArticle selectDraftArticleIdByDraftArticleId(String campaign, String id);
    
    DraftArticle selectDraftArticleByCampaignAndId(String campaign, String id);
    
    List<DraftArticle> selectAllDraftArticles(Integer empDetailsId, String campaign);
    
    void saveDraft(DraftArticle draftArticle);
    
    Map<String, Object> updateDraftArticleByMinute(String title, String category, String content, String id, String campaign);
    
    void deleteDraftArticle(DraftArticle draftArticle);
    
}
