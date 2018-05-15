package org.sterling.intranet.model.json;

import java.util.Date;

/**
 *
 * @author Var Arenz G. Villarino
 */
public class NewsFeedObject 
{
    private String newsFeedId;
    private String newsFeedTitle;
    private String articleCategory;
    private String empName;
    private String base64;
    private String fileType;
    private Date dateCreated;
    private String newsFeedType;
    private String eventDate;
    private String eventLocation;
    private String eventDetails;
    private Long numberOfAnswer;
    private int numberOfView;

    public void setNewsFeedId(String newsFeedId)
    {
        this.newsFeedId = newsFeedId;
    }

    public void setNewsFeedTitle(String newsFeedTitle)
    {
        this.newsFeedTitle = newsFeedTitle;
    }

    public void setArticleCategory(String articleCategory) 
    {
        this.articleCategory = articleCategory;
    }

    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public void setBase64(String base64) 
    {
        this.base64 = base64;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public void setNewsFeedType(String newsFeedType) 
    {
        this.newsFeedType = newsFeedType;
    }

    public void setEventDate(String eventDate) 
    {
        this.eventDate = eventDate;
    }

    public void setEventLocation(String eventLocation) 
    {
        this.eventLocation = eventLocation;
    }
    
    public void setEventDetails(String eventDetails) 
    {
        this.eventDetails = eventDetails;
    }

    public void setNumberOfAnswer(Long numberOfAnswer)
    {
        this.numberOfAnswer = numberOfAnswer;
    }

    public void setNumberOfView(int numberOfView)
    {
        this.numberOfView = numberOfView;
    }
    
    public String getNewsFeedId() 
    {
        return newsFeedId;
    }

    public String getNewsFeedTitle()
    {
        return newsFeedTitle;
    }

    public String getArticleCategory() 
    {
        return articleCategory;
    }

    public String getEmpName() 
    {
        return empName;
    }

    public String getBase64() 
    {
        return base64;
    }

    public String getFileType()
    {
        return fileType;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public String getNewsFeedType()
    {
        return newsFeedType;
    }

    public String getEventDate()
    {
        return eventDate;
    }

    public String getEventLocation()
    {
        return eventLocation;
    }

    public String getEventDetails()
    {
        return eventDetails;
    }

    public Long getNumberOfAnswer()
    {
        return numberOfAnswer;
    }

    public int getNumberOfView() 
    {
        return numberOfView;
    }
    
}
