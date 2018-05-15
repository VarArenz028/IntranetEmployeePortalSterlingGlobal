package org.sterling.intranet.interfaces;

import org.springframework.core.io.Resource;
import org.sterling.intranet.models.ArticleDocument;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface ArticleDocumentServices 
{
    
    Integer selectFileIdByArticleId(String articleId);
    
    ArticleDocument selectFileIdAndDocumentNameByArticleId(String articleId);
    
    ArticleDocument criteriaSelectArticleDocumentByFileId(Integer fileId);
    
    ArticleDocument selectArticleDocumentByFileId(Integer fileId);
    
    ArticleDocument selectArticleDocumentByArticleId(String articleId);
    
    void saveArticleDocument(ArticleDocument articleDocument);
}
