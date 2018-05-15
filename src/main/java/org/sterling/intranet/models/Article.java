package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Var Arenz Villarino
 */

@Entity
@Table(name = "article")
@Indexed
public class Article implements Serializable
{
    @Id
    private String articleId = UUID.randomUUID().toString();
    
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "title", nullable = false, length = 150)
    private String title;
    
    @Column(name = "content")
    @Lob
    private String content;
    
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "category", nullable = false, length = 150)
    private String category;

    @Column(name = "localDateCreated", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime localDateCreated = LocalDateTime.now();
    
    @Column(name = "dateCreated", nullable = false)
    private Date dateCreated;
    
    @Column(name = "dateString", nullable = false)
    private String dateString;
    
    @Column(name = "type", nullable = false)
    private String type = "article";
    
    @Column(name = "campaign", nullable = false)
    private String campaign;
    
    @Column(name = "state", nullable = false, length = 10)
    private String state;
    
    @Column(name = "pushpin", nullable = false, length = 10)
    private String pushpin = "Unpin";
    
//    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
//    @JoinColumn(name = "userId")
//    private User user;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private PageView pageView;
    
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = EmployeeDetails.class)
    @JoinColumn(name = "empDetailsId")
    @JsonIgnore
    private EmployeeDetails employeeDetails;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ArticleDocument articleDocument;
    
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Comment.class)
//    @JoinTable(name = "articleComment", joinColumns = @JoinColumn(name = "articleId"), inverseJoinColumns = @JoinColumn(name = "commentId"))
    @JsonIgnore
    private Set<Comment> comments;
    
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Article.class)
    @JoinColumn(name = "approveArticleId")
    private ApprovedArticle approvedArticle;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ArticleRejection articleRejection;

    public void setArticleId(String articleId)
    {
        this.articleId = articleId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public void setLocalDateCreated(LocalDateTime localDateCreated)
    {
        this.localDateCreated = localDateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public void setDateString(String dateString) 
    {
        this.dateString = dateString;
    }

    public void setCampaign(String campaign) 
    {
        this.campaign = campaign;
    }
    
    public void setType(String type) 
    {
        this.type = type;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setPushpin(String pushpin) 
    {
        this.pushpin = pushpin;
    }
    
    public void setArticleDocument(ArticleDocument articleDocument)
    {
        this.articleDocument = articleDocument;
    }

    public void setPageView(PageView pageView)
    {
        this.pageView = pageView;
    }
    
    public void setEmployeeDetails(EmployeeDetails employeeDetails)
    {
        this.employeeDetails = employeeDetails;
    }
    
    public void setComments(Set<Comment> comments) 
    {
        this.comments = comments;
    }

    public void setApprovedArticle(ApprovedArticle approvedArticle) 
    {
        this.approvedArticle = approvedArticle;
    }
    
    public void setArticleRejection(ArticleRejection articleRejection) 
    {
        this.articleRejection = articleRejection;
    }
    
    public String getTitle() 
    {
        return title;
    }

    public String getContent() 
    {
        return content;
    }

    public PageView getPageView() 
    {
        return pageView;
    }

    public String getCategory()
    {
        return category;
    }

    public String getPushpin() 
    {
        return pushpin;
    }
    
//    public User getUser() 
//    {
//        return user;
//    }

    public EmployeeDetails getEmployeeDetails() {
        return employeeDetails;
    }
    
    public String getArticleId() 
    {
        return articleId;
    }

    public ArticleDocument getArticleDocument()
    {
        return articleDocument;
    }
    
    public LocalDateTime getLocalDateCreated() 
    {
        return localDateCreated;
    }

    public Date getDateCreated() 
    {
        return dateCreated;
    }

    public String getType() 
    {
        return type;
    }

    public String getState()
    {
        return state;
    }
    
    public String getDateString() 
    {
        return dateString;
    }

    public String getCampaign() 
    {
        return campaign;
    }

    public Set<Comment> getComments()
    {
        return comments;
    }

    public ApprovedArticle getApprovedArticle() {
        return approvedArticle;
    }
    
    public ArticleRejection getArticleRejection() 
    {
        return articleRejection;
    }
    
}
