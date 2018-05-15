/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Var Arenz Villarino
 */
@Entity
@Table(name = "draftArticle")
public class DraftArticle implements Serializable 
{
    @Id
    private String draftArticleId = UUID.randomUUID().toString();
    
    @Column(name = "title", nullable = true, length = 150)
    private String title = "Untitled Article";
    
    @Column(name = "content", nullable = true)
    @Lob
    private String content;
    
    @Column(name = "category", nullable = true, length = 150)
    private String category = "Unassigned";
    

    @Column(name = "localDateCreated", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime localDateCreated = LocalDateTime.now();
    
    @Column(name = "dateCreated", nullable = true)
    private Date dateCreated;
    
    @Column(name = "dateString", nullable = true)
    private String dateString;
    
    @Column(name = "type", nullable = true)
    private String type = "article";
    
    @Column(name = "campaign", nullable = true)
    private String campaign;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EmployeeDetails.class)
    @JoinColumn(name = "empDetailsId")
    @JsonIgnore
    private EmployeeDetails employeeDetails;

    public void setDraftArticleId(String draftArticleId) 
    {
        this.draftArticleId = draftArticleId;
    }
    
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

    public void setLocalDateCreated(LocalDateTime localDateCreated) 
    {
        this.localDateCreated = localDateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public void setDateString(String dateString) 
    {
        this.dateString = dateString;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public void setCampaign(String campaign) 
    {
        this.campaign = campaign;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails) 
    {
        this.employeeDetails = employeeDetails;
    }

    public String getDraftArticleId()
    {
        return draftArticleId;
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

    public LocalDateTime getLocalDateCreated() 
    {
        return localDateCreated;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public String getDateString()
    {
        return dateString;
    }

    public String getType() 
    {
        return type;
    }

    public String getCampaign()
    {
        return campaign;
    }

    public EmployeeDetails getEmployeeDetails() 
    {
        return employeeDetails;
    }
    
    
}
