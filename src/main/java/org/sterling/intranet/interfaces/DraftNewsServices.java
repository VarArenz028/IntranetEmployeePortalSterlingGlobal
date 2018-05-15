package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.DraftNews;

/**
 *
 * @author Var Arenz Villarino
 */
public interface DraftNewsServices 
{
    
    DraftNews selectDraftNewsByEmpId(Integer empId);
    
    DraftNews selectDraftNewsByDraftNewsId(String draftNewsId);
    
    List<DraftNews> selectAllDraftNewsByCampaignName(String campaign, Integer empId);
    
    void saveDraftNews(DraftNews draftNews);
    
    Map<String, Object> updateDraftNews(String draftNewsId, String draftNewsTitle, String draftNewsContent, String draftBase64, String draftFileType);
    
    void deleteDraftNews(DraftNews draftNews);
}
