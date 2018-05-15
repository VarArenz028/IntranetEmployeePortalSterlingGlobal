package org.sterling.intranet.model.json;

/**
 *
 * @author Var Arenz Villarino
 */
public class CommonArticle 
{
    private String title;
    private String content;
    private String category;

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

    public String getTitle() 
    {
        return title;
    }

    public String getContent()
    {
        return content;
    }

    public String getCategory()
    {
        return category;
    }
}
