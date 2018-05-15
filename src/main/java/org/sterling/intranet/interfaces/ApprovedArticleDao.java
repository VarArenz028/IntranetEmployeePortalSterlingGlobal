package org.sterling.intranet.interfaces;

import org.sterling.intranet.models.ApprovedArticle;

/**
 *
 * @author Var Arenz Villarino
 */
public interface ApprovedArticleDao 
{
    
    ApprovedArticle selectApprovedArticleByArticleId(String articleId);
    
    void saveApprovedArticle(ApprovedArticle approvedArticle);
}
