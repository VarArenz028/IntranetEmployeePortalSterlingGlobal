package org.sterling.intranet.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Var Arenz Villarino
 */
@Entity
@Table(name = "approvedArticle")
public class ApprovedArticle 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "approveArticleId", nullable = false, length = 11)
    private int approveArticleId;
    
    @Column(name = "approvedBy", nullable = false, length = 50)
    private String approvedBy;
    
    @Column(name = "approvedDate", nullable = false)
    private Date approvedDate;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "articleId")
    private Article article;
    
    public void setApproveArticleId(int approveArticleId)
    {
        this.approveArticleId = approveArticleId;
    }

    public void setApprovedBy(String approvedBy)
    {
        this.approvedBy = approvedBy;
    }

    public void setApprovedDate(Date approvedDate)
    {
        this.approvedDate = approvedDate;
    }

    public void setArticle(Article article) 
    {
        this.article = article;
    }
    
    public int getApproveArticleId()
    {
        return approveArticleId;
    }

    public String getApprovedBy() 
    {
        return approvedBy;
    }

    public Date getApprovedDate()
    {
        return approvedDate;
    }

    public Article getArticle() 
    {
        return article;
    }
    
}
