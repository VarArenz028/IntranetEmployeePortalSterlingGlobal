package org.sterling.intranet.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.ArticleRecentlyRevisedDao;
import org.sterling.intranet.interfaces.ArticleRecentlyRevisedServices;
import org.sterling.intranet.models.ArticleRecentlyRevised;


/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("articleRecentlyRevisedServices")
public class ArticleRecentlyRevisedServicesImpl implements ArticleRecentlyRevisedServices
{

    @Autowired
    private ArticleRecentlyRevisedDao articleRecentlyRevisedDao;
    
    @Transactional(readOnly = true)
    @Override
    public List<ArticleRecentlyRevised> selectMostRecentlyRevisedByCampaignName(String campaign) 
    {
        return articleRecentlyRevisedDao.selectMostRecentlyRevisedByCampaignName(campaign);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveRecentlyRevised(ArticleRecentlyRevised articleRecentlyRevised) 
    {
        articleRecentlyRevisedDao.saveRecentlyRevised(articleRecentlyRevised);
    }

    
    
}
