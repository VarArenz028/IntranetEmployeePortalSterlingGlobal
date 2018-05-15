package org.sterling.intranet.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.DraftNewsDao;
import org.sterling.intranet.interfaces.DraftNewsServices;
import org.sterling.intranet.models.DraftNews;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("draftNewsServices")
public class DraftNewsServicesImpl implements DraftNewsServices
{
    
    @Autowired
    private DraftNewsDao draftNewsDao;

    @Transactional(readOnly = true)
    @Override
    public DraftNews selectDraftNewsByEmpId(Integer empId) 
    {
        return draftNewsDao.selectDraftNewsByEmpId(empId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public DraftNews selectDraftNewsByDraftNewsId(String draftNewsId) 
    {
        return draftNewsDao.selectDraftNewsByDraftNewsId(draftNewsId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DraftNews> selectAllDraftNewsByCampaignName(String campaign, Integer empId)
    {
        return draftNewsDao.selectAllDraftNewsByCampaignName(campaign, empId);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveDraftNews(DraftNews draftNews)
    {
        draftNewsDao.saveDraftNews(draftNews);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateDraftNews(String draftNewsId, String draftNewsTitle, String draftNewsContent, String draftBase64, String draftFileType) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        DraftNews draftNews = draftNewsDao.selectDraftNewsByDraftNewsId(draftNewsId);
        
        draftNews.setDraftNewsTitle(draftNewsTitle);
        draftNews.setDraftNewsContent(draftNewsContent);
        draftNews.setBase64(draftBase64);
        draftNews.setFileType(draftFileType);
        response.put("DraftNewsUpdated", HttpStatus.OK);
        
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteDraftNews(DraftNews draftNews)
    {
        draftNewsDao.deleteDraftNews(draftNews);
    }
    
}
