package org.sterling.intranet.models;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "pageView")
public class PageView 
{
    @Id
    @Column(name = "pageViewId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pageViewId;
    
    @Column(name = "view", nullable = false)
    private int view = 0;
    
    @Column(name = "campaign", nullable = false)
    private String campaign;
    
    @OneToOne
    @JoinColumn(name = "articleId")
    private Article article;

    public void setPageViewId(long pageViewId) 
    {
        this.pageViewId = pageViewId;
    }

    public void setView(int view) 
    {
        this.view = view;
    }

    public void setCampaign(String campaign) 
    {
        this.campaign = campaign;
    }
    
    public void setArticle(Article article)
    {
        this.article = article;
    }

    public long getPageViewId() 
    {
        return pageViewId;
    }

    public int getView()
    {
        return view;
    }

    public String getCampaign() 
    {
        return campaign;
    }
    
    public Article getArticle()
    {
        return article;
    }
    
    
}
