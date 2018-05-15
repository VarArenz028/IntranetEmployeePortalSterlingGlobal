package org.sterling.intranet.model.json;

/**
 *
 * @author Var Arenz Villarino
 */
public class UpdateArticleJson 
{
    private String articleId;
    private String title;
    private String category;
    private String content;

    public void setArticleId(String articleId)
    {
        this.articleId = articleId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getArticleId()
    {
        return articleId;
    }

    public String getTitle()
    {
        return title;
    }

    public String getCategory()
    {
        return category;
    }

    public String getContent() 
    {
        return content;
    }
    
    
}
