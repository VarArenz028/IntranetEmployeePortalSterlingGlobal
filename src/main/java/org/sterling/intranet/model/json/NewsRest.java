package org.sterling.intranet.model.json;

import java.util.Date;

/**
 *
 * @author Var Arenz Villarino
 */
public class NewsRest 
{
    private String newsId;
    private String newsTitle;
    private String content;
    private String base64;
    private String fileType;
    private Date dateCreated;

    public void setNewsId(String newsId) 
    {
        this.newsId = newsId;
    }

    public void setNewsTitle(String newsTitle) 
    {
        this.newsTitle = newsTitle;
    }

    public void setContent(String content) 
    {
        this.content = content;
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

    public String getNewsId() 
    {
        return newsId;
    }

    public String getNewsTitle() 
    {
        return newsTitle;
    }

    public String getContent() 
    {
        return content;
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
    
}
