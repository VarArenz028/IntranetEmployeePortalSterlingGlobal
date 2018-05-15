package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "comment")
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commentId", nullable = false)
    private Integer commentId; 
    
    @Column(name = "comment", nullable = true, length = 150, updatable = true)
    private String comment;
    
    @Column(name = "dateCommented")
    private Date dateCommented;
    
    @Column(name = "state", nullable = true)
    private String state;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeDetailsId")
    private EmployeeDetails employeeDetails;
    
    @ManyToOne
    @JoinColumn(name = "articleId")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "questionAnswerId")
    private QuestionAnswer questionAnswer;
    
    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public void setCommentId(Integer commentId) 
    {
        this.commentId = commentId;
    }

    public void setDateCommented(Date dateCommented)
    {
        this.dateCommented = dateCommented;
    }

    public void setState(String state) 
    {
        this.state = state;
    }
    
    public void setEmployeeDetails(EmployeeDetails employeeDetails)
    {
        this.employeeDetails = employeeDetails;
    }
    
//    public void setUser(User user) 
//    {
//        this.user = user;
//    }

    public void setArticle(Article article)
    {
        this.article = article;
    }

    public void setQuestionAnswer(QuestionAnswer questionAnswer)
    {
        this.questionAnswer = questionAnswer;
    }
    
    public String getComment()
    {
        return comment;
    }

    public Integer getCommentId() 
    {
        return commentId;
    }

    public Date getDateCommented() 
    {
        return dateCommented;
    }

    public String getState() 
    {
        return state;
    }
    
    public EmployeeDetails getEmployeeDetails() 
    {
        return employeeDetails;
    }

//    public User getUser() 
//    {
//        return user;
//    }

    public Article getArticle() 
    {
        return article;
    }

    public QuestionAnswer getQuestionAnswer() 
    {
        return questionAnswer;
    }
    
}
