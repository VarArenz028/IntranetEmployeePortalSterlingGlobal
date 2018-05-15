package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Var Arenz Villarino
 */
@Entity
@Table(name = "questionAnswer")
public class QuestionAnswer 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "questionAnswerId")
    private int questionAnswerId;
    
    @Column(name = "answer")
    @Lob
    private String answer;
    
    @Column(name = "dateAnswered")
    private Date dateAnswered;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empDetailsId")
    private EmployeeDetails employeeDetails;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionId")
    @JsonIgnore
    private Question question;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Comment> comments;

    public void setQuestionAnswerId(int questionAnswerId) 
    {
        this.questionAnswerId = questionAnswerId;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails)
    {
        this.employeeDetails = employeeDetails;
    }

    public void setQuestion(Question question)
    {
        this.question = question;
    }

    public void setDateAnswered(Date dateAnswered)
    {
        this.dateAnswered = dateAnswered;
    }

    public void setComments(Set<Comment> comments) 
    {
        this.comments = comments;
    }
    
    public int getQuestionAnswerId()
    {
        return questionAnswerId;
    }

    public String getAnswer() 
    {
        return answer;
    }

    public Date getDateAnswered() 
    {
        return dateAnswered;
    }
    
    public EmployeeDetails getEmployeeDetails()
    {
        return employeeDetails;
    }

    public Question getQuestion()
    {
        return question;
    }

    public Set<Comment> getComments() 
    {
        return comments;
    }
    
    
}
