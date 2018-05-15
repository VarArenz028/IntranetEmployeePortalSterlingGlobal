package org.sterling.intranet.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.CommentDao;
import org.sterling.intranet.interfaces.CommentServices;
import org.sterling.intranet.models.Comment;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("commentServices")
public class CommentServicesImpl implements CommentServices
{
    
    @Autowired
    private CommentDao commentDao;
    

    @Transactional(readOnly = true)
    @Override
    public Collection<Comment> selectAllCommentByArticleId(String articleId) 
    {
        return commentDao.selectAllCommentByArticleId(articleId);
    }
    @Override
    public Collection<Comment> selectAllCommentByQuestionAnswerId(Integer questionAnswerId)
    {
        return commentDao.selectAllCommentByQuestionAnswerId(questionAnswerId);
    }
    @Transactional(readOnly = false)
    @Override
    public void saveComment(Comment comment) 
    {
        commentDao.saveComment(comment);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> inactiveCommentInArticleByCommentId(Integer commentId, String state)
    {
        return commentDao.inactiveCommentInArticleByCommentId(commentId, state);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Comment> selectALlCommentByArticleIdAndState(String articleId, String state)
    {
        return commentDao.selectALlCommentByArticleIdAndState(articleId, state);
    }

    
}
