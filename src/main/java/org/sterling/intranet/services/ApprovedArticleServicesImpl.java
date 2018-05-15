package org.sterling.intranet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.ApprovedArticleDao;
import org.sterling.intranet.interfaces.ApprovedArticleServices;
import org.sterling.intranet.models.ApprovedArticle;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("approvedArticleServices")
public class ApprovedArticleServicesImpl implements ApprovedArticleServices
{

    @Autowired
    private ApprovedArticleDao approvedArticleDao;
    
    @Transactional(readOnly = true)
    @Override
    public ApprovedArticle selectApprovedArticleByArticleId(String articleId)
    {
        return approvedArticleDao.selectApprovedArticleByArticleId(articleId);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveApprovedArticle(ApprovedArticle approvedArticle) 
    {
        approvedArticleDao.saveApprovedArticle(approvedArticle);
    }

    
    
}
