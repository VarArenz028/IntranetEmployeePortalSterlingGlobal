package org.sterling.intranet.services;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.ArticleDocumentDao;
import org.sterling.intranet.interfaces.ArticleDocumentServices;
import org.sterling.intranet.models.ArticleDocument;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("articleDocumentServices")
public class ArticleDocumentServicesImpl implements ArticleDocumentServices
{
    private final Path rootLocation = Paths.get("upload-dir");
    @Autowired
    private ArticleDocumentDao articleDocumentDao;

    @Transactional(readOnly = true)
    @Override
    public Integer selectFileIdByArticleId(String articleId) 
    {
        return articleDocumentDao.selectFileIdByArticleId(articleId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public ArticleDocument selectFileIdAndDocumentNameByArticleId(String articleId) 
    {
        return articleDocumentDao.selectFileIdAndDocumentNameByArticleId(articleId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public ArticleDocument criteriaSelectArticleDocumentByFileId(Integer fileId) 
    {
        return articleDocumentDao.criteriaSelectArticleDocumentByFileId(fileId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public ArticleDocument selectArticleDocumentByFileId(Integer fileId)
    {
       return articleDocumentDao.selectArticleDocumentByFileId(fileId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public ArticleDocument selectArticleDocumentByArticleId(String articleId) 
    {
        return articleDocumentDao.selectArticleDocumentByArticleId(articleId);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveArticleDocument(ArticleDocument articleDocument) 
    {
        articleDocumentDao.saveArticleDocument(articleDocument);
    }

    

    
    
}
