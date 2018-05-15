package org.sterling.intranet.interfaces;

import org.sterling.intranet.models.ArticleDocument;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface ArticleDocumentDao 
{
    
    Integer selectFileIdByArticleId(String articleId);
    
    ArticleDocument selectFileIdAndDocumentNameByArticleId(String articleId);
    
    ArticleDocument criteriaSelectArticleDocumentByFileId(Integer fileId);
    
    ArticleDocument selectArticleDocumentByFileId(Integer fileId);
    
    ArticleDocument selectArticleDocumentByArticleId(String articleId);
    
    void saveArticleDocument(ArticleDocument articleDocument);
}
