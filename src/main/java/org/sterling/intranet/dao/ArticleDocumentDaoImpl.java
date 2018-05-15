package org.sterling.intranet.dao;

import java.io.Serializable;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.ArticleDocumentDao;
import org.sterling.intranet.models.ArticleDocument;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("articleDocumentDao")
public class ArticleDocumentDaoImpl extends AbstractDao<Integer, ArticleDocument> implements ArticleDocumentDao
{

    @Override
    public Integer selectFileIdByArticleId(String articleId)
    {
        Query query = getSession().createQuery("Select a.fileId from ArticleDocument a where a.article.articleId = :articleId");
        query.setParameter("articleId", articleId);
        
        Integer fileId = (Integer) query.uniqueResult();
        
        return fileId;
    }
    
    @Override
    public ArticleDocument selectFileIdAndDocumentNameByArticleId(String articleId)
    {
        Criteria criteria = getSession().createCriteria(ArticleDocument.class).add(Restrictions.eq("article.articleId", articleId))
                          .setProjection(Projections.projectionList()
                          .add(Projections.property("fileName"), "fileName")
                          .add(Projections.property("fileId"), "fileId"))
                          .setResultTransformer(Transformers.aliasToBean(ArticleDocument.class));
        
        ArticleDocument articleDocument = (ArticleDocument) criteria.uniqueResult();
        return articleDocument;
    }
    
    @Override
    public ArticleDocument criteriaSelectArticleDocumentByFileId(Integer fileId) 
    {
        Criteria criteria = getSession().createCriteria(ArticleDocument.class).add(Restrictions.eq("fileId", fileId))
                          .setProjection(Projections.projectionList()
                          .add(Projections.property("fileName"), "fileName")
                          .add(Projections.property("type"), "type")
                          .add(Projections.property("content"), "content"))
                          .setResultTransformer(Transformers.aliasToBean(ArticleDocument.class));
        
        ArticleDocument articleDocument = (ArticleDocument) criteria.uniqueResult();
        return articleDocument;
    }
    
    @Override
    public ArticleDocument selectArticleDocumentByFileId(Integer fileId) 
    {
        Query query = getSession().createQuery("from ArticleDocument where fileId = :fileId");
        query.setParameter("fileId", fileId);
        
        ArticleDocument articleDocument = (ArticleDocument) query.uniqueResult();
        
        return articleDocument;
    }
    
    @Override
    public ArticleDocument selectArticleDocumentByArticleId(String articleId) 
    {
        Query query = getSession().createQuery("from ArticleDocument where articleId = :articleId");
        query.setParameter("articleId", articleId);
        
        ArticleDocument articleDocument = (ArticleDocument) query.uniqueResult();
        
        return articleDocument;
    }
    
    @Override
    public void saveArticleDocument(ArticleDocument articleDocument)
    {
        save(articleDocument);
    }

    

    

    

    

    
    
}
