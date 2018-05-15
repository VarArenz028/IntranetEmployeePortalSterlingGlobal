package org.sterling.intranet.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.CommentDao;
import org.sterling.intranet.models.Comment;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("commentDao")
public class CommentDaoImpl extends AbstractDao<Integer, Comment> implements CommentDao
{

    @Override
    public Collection<Comment> selectAllCommentByArticleId(String articleId) 
    {
        
        Criteria cr = getSession().createCriteria(Comment.class)
            .add(Restrictions.eq("article.articleId", articleId))
            .setProjection(Projections.projectionList()
            .add(Projections.property("commentId"), "commentId")
            .add(Projections.property("comment"), "comment")
            .add(Projections.property("dateCommented"), "dateCommented")
            .add(Projections.property("employeeDetails"), "employeeDetails"))
            .setResultTransformer(Transformers.aliasToBean(Comment.class));
        Collection<Comment> list = cr.list();

        
        return list;
    }

    @Override
    public Collection<Comment> selectAllCommentByQuestionAnswerId(Integer questionAnswerId) 
    {
        Criteria cr = getSession().createCriteria(Comment.class)
            .add(Restrictions.eq("questionAnswer.questionAnswerId", questionAnswerId))
            .setProjection(Projections.projectionList()
            .add(Projections.property("comment"), "comment")
            .add(Projections.property("dateCommented"), "dateCommented")
            .add(Projections.property("employeeDetails"), "employeeDetails"))
            .setResultTransformer(Transformers.aliasToBean(Comment.class));
        Collection<Comment> list = cr.list();

        
        return list;
    }
    
    @Override
    public void saveComment(Comment comment) 
    {
        save(comment);
    }

    @Override
    public Map<String, Object> inactiveCommentInArticleByCommentId(Integer commentId, String state)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        Query query = getSession().createQuery("update Comment c set c.state = :state where c.commentId = :commentId");
        query.setParameter("commentId", commentId);
        query.setParameter("state", state);
        int update = query.executeUpdate();
        
        if(update != 0)
        {
            response.put("inactiveComment", HttpStatus.OK);
        }
        else
        {
            response.put("error", HttpStatus.NOT_MODIFIED);
        }
        
        return response;
    }

    @Override
    public Collection<Comment> selectALlCommentByArticleIdAndState(String articleId, String state) 
    {
        Criteria cr = getSession().createCriteria(Comment.class)
            .add(Restrictions.eq("article.articleId", articleId))
            .add(Restrictions.and(Restrictions.eq("state", state)))
            .setProjection(Projections.projectionList()
            .add(Projections.property("commentId"), "commentId")
            .add(Projections.property("comment"), "comment")
            .add(Projections.property("dateCommented"), "dateCommented")
            .add(Projections.property("employeeDetails"), "employeeDetails"))
            .setResultTransformer(Transformers.aliasToBean(Comment.class));
        Collection<Comment> list = cr.list();

        
        return list;
    }
    
}
