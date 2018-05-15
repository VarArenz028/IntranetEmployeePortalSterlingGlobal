package org.sterling.intranet.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "articleRecentlyRevised")
public class ArticleRecentlyRevised 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recentlyRevisedId", length = 11)
    private int recentlyRevisedId;
    
    @Column(name = "articleId", length = 50)
    private String articleId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "dateRevised")
    private Date dateRevised; 
    
    @Column(name = "campaign")
    private String campaign;

    public void setRecentlyRevisedId(int recentlyRevisedId) 
    {
        this.recentlyRevisedId = recentlyRevisedId;
    }

    public void setArticleId(String articleId)
    {
        this.articleId = articleId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDateRevised(Date dateRevised)
    {
        this.dateRevised = dateRevised;
    }

    public void setCampaign(String campaign) 
    {
        this.campaign = campaign;
    }
    
    public int getRecentlyRevisedId()
    {
        return recentlyRevisedId;
    }

    public String getArticleId() 
    {
        return articleId;
    }

    public String getTitle() 
    {
        return title;
    }

    public Date getDateRevised() 
    {
        return dateRevised;
    }

    public String getCampaign()
    {
        return campaign;
    }
    
}
