package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.DraftNews;

/**
 *
 * @author Var Arenz Villarino
 */
public interface DraftNewsDao 
{
    
    DraftNews selectDraftNewsByEmpId(Integer empId);
    
    DraftNews selectDraftNewsByDraftNewsId(String draftNewsId);
    
    List<DraftNews> selectAllDraftNewsByCampaignName(String campaign, Integer empId);
    
    void saveDraftNews(DraftNews draftNews);
    
    void deleteDraftNews(DraftNews draftNews);
}
