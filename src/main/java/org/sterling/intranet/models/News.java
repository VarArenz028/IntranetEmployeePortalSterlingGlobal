package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 *
 * @author Var Arenz Villarino
 */
@Entity
@Table(name = "news")
@Indexed
public class News implements Serializable
{
    @Id
    @Column(name = "newsId")
    private String newsId = UUID.randomUUID().toString();
    
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "newsTitle", length = 50)
    private String newsTitle;
    
    @Lob
    @Column(name = "newsContent")
    private String newsContent;
    
    @Column(name = "base64")
    @Lob
    private String base64;
    
    @Column(name = "fileType")
    private String fileType;
    
    @Column(name = "campaign")
    private String campaign;
    
    @Column(name = "dateCreated")
    private Date dateCreated;
    
    @Column(name = "state")
    private String state;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EmployeeDetails.class)
    @JoinColumn(name = "empDetailsId")
    @JsonIgnore
    private EmployeeDetails employeeDetails;
    

    public void setNewsId(String newsId) 
    {
        this.newsId = newsId;
    }

    public void setNewsTitle(String newsTitle) 
    {
        this.newsTitle = newsTitle;
    }

    public void setNewsContent(String newsContent)
    {
        this.newsContent = newsContent;
    }

    public void setBase64(String base64) 
    {
        this.base64 = base64;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public void setCampaign(String campaign) 
    {
        this.campaign = campaign;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public void setState(String state)
    {
        this.state = state;
    }
    
    public void setEmployeeDetails(EmployeeDetails employeeDetails) 
    {
        this.employeeDetails = employeeDetails;
    }
    
    public String getNewsId() 
    {
        return newsId;
    }

    public String getNewsTitle() 
    {
        return newsTitle;
    }

    public String getNewsContent() 
    {
        return newsContent;
    }

    public String getBase64() 
    {
        return base64;
    }

    public String getFileType() 
    {
        return fileType;
    }

    public String getCampaign()
    {
        return campaign;
    }

    public Date getDateCreated() 
    {
        return dateCreated;
    }

    public String getState() 
    {
        return state;
    }
    
    public EmployeeDetails getEmployeeDetails()
    {
        return employeeDetails;
    }
    
}
