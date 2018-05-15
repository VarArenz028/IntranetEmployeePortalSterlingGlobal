package org.sterling.intranet.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.DraftArticleDao;
import org.sterling.intranet.interfaces.DraftArticleServices;
import org.sterling.intranet.models.DraftArticle;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("draftArticleServices")
public class DraftArticleServicesImpl implements DraftArticleServices
{

    @Autowired
    private DraftArticleDao draftArticleDao;
    
    @Override
    public DraftArticle selectDraftArticleIdByDraftArticleId(String campaign, String id) 
    {
        return draftArticleDao.selectDraftArticleIdByDraftArticleId(campaign, id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public DraftArticle selectDraftArticleByCampaignAndId(String campaign, String id) 
    {
        return draftArticleDao.selectDraftArticleByCampaignAndId(campaign, id);
    }
    @Transactional(readOnly = false)
    @Override
    public void saveDraft(DraftArticle draftArticle) 
    {
        draftArticleDao.saveDraft(draftArticle);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DraftArticle> selectAllDraftArticles(Integer empDetailsId, String campaign)
    {
        return draftArticleDao.selectAllDraftArticles(empDetailsId, campaign);
    }
    
    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateDraftArticleByMinute(String title, String category, String content, String id, String campaign) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        DraftArticle draftArticle = draftArticleDao.selectDraftArticleByCampaignAndId(campaign, id);
        if(title != null)
        {
            draftArticle.setTitle(title);

        }
        else if(title == null)
        {
            draftArticle.setTitle("Untitled Article");
        }
        draftArticle.setCategory(category);
        draftArticle.setContent(content);
        response.put("draftArticleUpdated", HttpStatus.OK);
        return response;
    }
    
    @Transactional(readOnly = false)
    @Override
    public void deleteDraftArticle(DraftArticle draftArticle) 
    {
        draftArticleDao.deleteDraftArticle(draftArticle);
    }

    
    
}
