package org.sterling.intranet.interfaces;

import org.sterling.intranet.models.ApprovedArticle;

/**
 *
 * @author Var Arenz Villarino
 */
public interface ApprovedArticleServices 
{
    
    ApprovedArticle selectApprovedArticleByArticleId(String articleId);
    
    void saveApprovedArticle(ApprovedArticle approvedArticle);
}
