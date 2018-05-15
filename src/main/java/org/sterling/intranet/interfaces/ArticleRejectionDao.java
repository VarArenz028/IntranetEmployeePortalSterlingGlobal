package org.sterling.intranet.interfaces;

import org.sterling.intranet.models.ArticleRejection;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface ArticleRejectionDao
{
    
    ArticleRejection selectArticleRejectionByArticleId(String articleId);
    
    void saveArticleRejection(ArticleRejection articleRejection);
}
