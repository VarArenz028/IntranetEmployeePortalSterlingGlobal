package org.sterling.intranet.models;

import java.util.Date;

/**
 *
 * @author Var Arenz Villarino
 */
public class ArticleMostRecentlyAdded
{
    private String articleId;
    private String articleName;
    private Date date;

    public void setArticleId(String articleId) 
    {
        this.articleId = articleId;
    }

    public void setArticleName(String articleName) 
    {
        this.articleName = articleName;
    }

    public void setDate(Date date) 
    {
        this.date = date;
    }

    public String getArticleId() 
    {
        return articleId;
    }

    public String getArticleName()
    {
        return articleName;
    }

    public Date getDate()
    {
        return date;
    }
    
}
