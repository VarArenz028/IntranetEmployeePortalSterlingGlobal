package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "question")
@Indexed
public class Question
{
    @Id
    @Column(name = "questionId")
    private String questionId = UUID.randomUUID().toString();
    
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "title", length = 150, nullable = false)
    private String title;
    
    @Column(name = "localDateCreated", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime localDateAsked;
    
    @Column(name = "dateCreated", nullable = false)
    private Date dateCreated;
    
    @Column(name = "dateString", nullable = false)
    private String dateString;
    
    @Column(name = "type", nullable = false)
    private String type = "question";
    
    @Column(name = "campaign", nullable = false)
    private String campaign;
    
    @Column(name = "state", nullable = false)
    private String state;
    
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = EmployeeDetails.class)
    @JoinColumn(name = "employeeDetailsId")
    private EmployeeDetails employeeDetails;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private Collection<QuestionAnswer> questionAnswer = new ArrayList();

    public void setQuestionId(String questionId)
    {
        this.questionId = questionId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setLocalDateAsked(LocalDateTime localDateAsked) 
    {
        this.localDateAsked = localDateAsked;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public void setDateString(String dateString)
    {
        this.dateString = dateString;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }

    public void setCampaign(String campaign) 
    {
        this.campaign = campaign;
    }

    public void setState(String state) 
    {
        this.state = state;
    }
    
    public void setEmployeeDetails(EmployeeDetails employeeDetails) 
    {
        this.employeeDetails = employeeDetails;
    }

    public void setQuestionAnswer(Collection<QuestionAnswer> questionAnswer) 
    {
        this.questionAnswer = questionAnswer;
    }
    
//    public void setUser(User user)
//    {
//        this.user = user;
//    }
    
    public String getQuestionId() 
    {
        return questionId;
    }

    public String getTitle() 
    {
        return title;
    }

    public LocalDateTime getLocalDateAsked() 
    {
        return localDateAsked;
    }

    public Date getDateCreated() 
    {
        return dateCreated;
    }

    public String getDateString() 
    {
        return dateString;
    }
    
    public String getType() 
    {
        return type;
    }

    public String getCampaign() 
    {
        return campaign;
    }

    public String getState() 
    {
        return state;
    }
    
//    public User getUser() 
//    {
//        return user;
//    }

    public EmployeeDetails getEmployeeDetails()
    {
        return employeeDetails;
    }

    public Collection<QuestionAnswer> getQuestionAnswer()
    {
        return questionAnswer;
    }
    
}
