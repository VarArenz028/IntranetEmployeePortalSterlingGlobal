package org.sterling.intranet.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.Comment;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface CommentServices 
{
    
    Collection<Comment> selectALlCommentByArticleIdAndState(String articleId, String state);
    
    Collection<Comment> selectAllCommentByArticleId(String articleId);
    
    Collection<Comment> selectAllCommentByQuestionAnswerId(Integer questionAnswerId);
    
    void saveComment(Comment comment);
    
    Map<String, Object> inactiveCommentInArticleByCommentId(Integer commentId, String state);
    
}
