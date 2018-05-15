package org.sterling.intranet.models;

import java.util.Date;

/**
 *
 * @author Var Arenz Villarino
 */
public class NewsFeed 
{
    private String id;
    private String title;
    private String category;
    private String type;
    private String dateString;
    private Date dateCreated;

    public NewsFeed(String id, String title, String category, String type, String dateString, Date dateCreated) 
    {
        this.id = id;
        this.title = title;
        this.category = category;
        this.type = type;
        this.dateString = dateString;
        this.dateCreated = dateCreated;
    }

    
    public void setId(String id)
    {
        this.id = id;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setDateString(String dateString)
    {
        this.dateString = dateString;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }
    
    public String getId() 
    {
        return id;
    }

    public String getTitle() 
    {
        return title;
    }

    public String getCategory()
    {
        return category;
    }

    public String getType()
    {
        return type;
    }

    public String getDateString() 
    {
        return dateString;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }
    
}
