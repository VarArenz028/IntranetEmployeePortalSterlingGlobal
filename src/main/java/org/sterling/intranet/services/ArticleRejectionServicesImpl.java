package org.sterling.intranet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.ArticleRejectionDao;
import org.sterling.intranet.interfaces.ArticleRejectionServices;
import org.sterling.intranet.models.ArticleRejection;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("articleRejectionServices")
public class ArticleRejectionServicesImpl implements ArticleRejectionServices
{
 
    @Autowired
    private ArticleRejectionDao articleRejectionDaoction;

    @Transactional(readOnly = false)
    @Override
    public void saveArticleRejection(ArticleRejection articleRejection) 
    {
        articleRejectionDaoction.saveArticleRejection(articleRejection);
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleRejection selectArticleRejectionByArticleId(String articleId)
    {
        return articleRejectionDaoction.selectArticleRejectionByArticleId(articleId);
    }
    
}
