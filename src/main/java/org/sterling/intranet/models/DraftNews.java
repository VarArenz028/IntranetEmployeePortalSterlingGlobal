package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 *
 * @author Var Arenz Villarino
 */
@Entity
@Table(name = "draftNews")
public class DraftNews 
{
    @Id
    @Column(name = "draftNewsId")
    private String draftNewsId = UUID.randomUUID().toString();
    
    @Column(name = "draftNewsTitle", length = 50)
    private String draftNewsTitle = "Untitled News";
    
    @Lob
    @Column(name = "draftNewsContent")
    private String draftNewsContent;
    
    @Column(name = "base64", nullable = true)
    @Lob
    private String base64;
    
    @Column(name = "fileType", nullable = true)
    private String fileType;
    
    @Column(name = "campaign")
    private String campaign;
    
    @Column(name = "dateCreated")
    private Date dateCreated;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EmployeeDetails.class)
    @JoinColumn(name = "empDetailsId")
    @JsonIgnore
    private EmployeeDetails employeeDetails;

    public void setDraftNewsId(String draftNewsId)
    {
        this.draftNewsId = draftNewsId;
    }

    public void setDraftNewsTitle(String draftNewsTitle)
    {
        this.draftNewsTitle = draftNewsTitle;
    }

    public void setDraftNewsContent(String draftNewsContent)
    {
        this.draftNewsContent = draftNewsContent;
    }

    public void setBase64(String base64
    ) {
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

    public void setEmployeeDetails(EmployeeDetails employeeDetails)
    {
        this.employeeDetails = employeeDetails;
    }

    public String getDraftNewsId()
    {
        return draftNewsId;
    }

    public String getDraftNewsTitle()
    {
        return draftNewsTitle;
    }

    public String getDraftNewsContent()
    {
        return draftNewsContent;
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

    public EmployeeDetails getEmployeeDetails() 
    {
        return employeeDetails;
    }
    
}
