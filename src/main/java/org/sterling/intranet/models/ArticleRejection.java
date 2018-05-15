package org.sterling.intranet.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "articleRejection")
public class ArticleRejection 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rejectionId")
    private int rejectionId;
    
    @Column(name = "reason", nullable = false)
    private String reason;
    
    @Column(name = "rejectedBy", nullable = false)
    private String rejectedBy;
    
    @Column(name = "dateRejeted", nullable = false)
    private Date dateRejeted;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empDetailsId")
    private Article article;

    public void setRejectionId(int rejectionId) 
    {
        this.rejectionId = rejectionId;
    }

    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public void setRejectedBy(String rejectedBy) 
    {
        this.rejectedBy = rejectedBy;
    }

    public void setDateRejeted(Date dateRejeted) 
    {
        this.dateRejeted = dateRejeted;
    }
    
    public void setArticle(Article article)
    {
        this.article = article;
    }

    public int getRejectionId() 
    {
        return rejectionId;
    }

    public String getReason()
    {
        return reason;
    }

    public String getRejectedBy() 
    {
        return rejectedBy;
    }

    public Date getDateRejeted()
    {
        return dateRejeted;
    }
    
    public Article getArticle() 
    {
        return article;
    }
    
    
}
