package org.sterling.intranet.models;

/**
 *
 * @author Var Arenz Villarino
 */
public class PageStatistic 
{
    private String articleId;
    private String articleName;
    private int articleStatistics;

    public void setArticleId(String articleId) 
    {
        this.articleId = articleId;
    }

    public void setArticleName(String articleName)
    {
        this.articleName = articleName;
    }

    public void setArticleStatistics(int articleStatistics)
    {
        this.articleStatistics = articleStatistics;
    }

    public String getArticleId() 
    {
        return articleId;
    }

    public String getArticleName() 
    {
        return articleName;
    }

    public int getArticleStatistics()
    {
        return articleStatistics;
    }
    
}
