package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "employeeDetails")
public class EmployeeDetails 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "empDetailsId")
    private int empDetailsId;
    
    @Column(name = "lastName", length = 30, nullable = true)
    private String lastName;
    
    @Column(name = "firstName", length = 30, nullable = true)
    private String firstName;
    
    @Column(name = "age", length = 30, nullable = true)
    private int age = 18;
    
    @Column(name = "position", nullable = true)
    private String position = "Employee";
    
    @Column(name = "gender", nullable = true)
    private String gender = "Others";
    
    @Column(name = "email", nullable = true)
    private String email = "sterlingdefault@gmail.com";
    
    @Column(name = "contactNumber", nullable = true)
    private String contactNumber = "093547123456";
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avatarId")
    private UserAvatar userAvatar;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeDetails")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private Set<Article> articles;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeDetails")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private Set<ArticleDocument> articleDocuments;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeDetails")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private Set<Question> questions;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeDetails")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private Set<Comment> comment;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeDetails")
    @JsonIgnore
    private Set<QuestionAnswer> questionAnswers;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeDetails")
    @JsonIgnore
    private Set<News> news;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeDetails")
    @JsonIgnore
    private Set<DraftArticle> draftArticles;
    
    @OneToOne(fetch = FetchType.EAGER)
    private NumberOfArticle numberOfArticle;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeDetails")
    @JsonIgnore
    private Set<Event> events;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeeDetails")
    @JsonIgnore
    private Set<EventAttendance> eventAttendances;
    
    public void setEmpDetailsId(int empDetailsId) 
    {
        this.empDetailsId = empDetailsId;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public void setAge(int age) 
    {
        this.age = age;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) 
    {
        this.contactNumber = contactNumber;
    }

    public void setUser(User user) 
    {
        this.user = user;
    }

    public void setUserAvatar(UserAvatar userAvatar) 
    {
        this.userAvatar = userAvatar;
    }

    public void setArticles(Set<Article> articles)
    {
        this.articles = articles;
    }

    public void setArticleDocuments(Set<ArticleDocument> articleDocuments) 
    {
        this.articleDocuments = articleDocuments;
    }

    public void setQuestions(Set<Question> questions)
    {
        this.questions = questions;
    }

    public void setComment(Set<Comment> comment)
    {
        this.comment = comment;
    }

    public void setQuestionAnswers(Set<QuestionAnswer> questionAnswers) 
    {
        this.questionAnswers = questionAnswers;
    }

    public void setNews(Set<News> news) 
    {
        this.news = news;
    }

    public void setDraftArticles(Set<DraftArticle> draftArticles) 
    {
        this.draftArticles = draftArticles;
    }

    public void setNumberOfArticle(NumberOfArticle numberOfArticle)
    {
        this.numberOfArticle = numberOfArticle;
    }

    public void setEvents(Set<Event> events)
    {
        this.events = events;
    }

    public void setEventAttendances(Set<EventAttendance> eventAttendances) 
    {
        this.eventAttendances = eventAttendances;
    }
    
    public int getEmpDetailsId() 
    {
        return empDetailsId;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public int getAge() 
    {
        return age;
    }

    public String getPosition() 
    {
        return position;
    }

    public String getGender()
    {
        return gender;
    }

    public String getEmail() 
    {
        return email;
    }

    public String getContactNumber() 
    {
        return contactNumber;
    }

    public User getUser()
    {
        return user;
    }

    public UserAvatar getUserAvatar() 
    {
        return userAvatar;
    }

    public Set<Article> getArticles()
    {
        return articles;
    }

    public Set<ArticleDocument> getArticleDocuments()
    {
        return articleDocuments;
    }

    public Set<Question> getQuestions() 
    {
        return questions;
    }

    public Set<Comment> getComment() 
    {
        return comment;
    }

    public Set<QuestionAnswer> getQuestionAnswers()
    {
        return questionAnswers;
    }

    public Set<News> getNews() 
    {
        return news;
    }

    public Set<DraftArticle> getDraftArticles() 
    {
        return draftArticles;
    }

    public NumberOfArticle getNumberOfArticle()
    {
        return numberOfArticle;
    }

    public Set<Event> getEvents() 
    {
        return events;
    }
    
    public Set<EventAttendance> getEventAttendances() 
    {
        return eventAttendances;
    }

    
    
}
