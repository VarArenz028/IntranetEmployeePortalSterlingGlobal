package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "event")
@Indexed
public class Event 
{
    @Id
    @Column(name = "eventId", nullable = false)
    private String eventId = UUID.randomUUID().toString();
    
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "eventName", nullable = false)
    private String eventName;
    
    @Column(name = "location", nullable = false)
    private String location;
    
    @Column(name = "eventDate", nullable = false)
    private String eventDate;
    
    
    
    @Column(name = "dateCreated", nullable = false)
    private Date dateCreated;
    
    @Column(name = "campaign", nullable = false)
    private String campaign;
    
    @Lob
    @Column(name = "base64", nullable = true)
    private String base64;
    
    @Column(name = "fileType", nullable = true)
    private String fileType;
    
    @Column(name = "details", nullable = false)
    private String details;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empDetailsId")
    @JsonIgnore
    private EmployeeDetails employeeDetails;
    
    @OneToMany(fetch = FetchType.EAGER)
    private Set<EventAttendance> eventAttendance;
    
    public void setEventId(String eventId)
    {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) 
    {
        this.eventName = eventName;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setEventDate(String eventDate) 
    {
        this.eventDate = eventDate;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public void setCampaign(String campaign) 
    {
        this.campaign = campaign;
    }
    
    public void setBase64(String base64) 
    {
        this.base64 = base64;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public void setDetails(String details) 
    {
        this.details = details;
    }
    
    public void setEmployeeDetails(EmployeeDetails employeeDetails)
    {
        this.employeeDetails = employeeDetails;
    }

    public void setEventAttendance(Set<EventAttendance> eventAttendance)
    {
        this.eventAttendance = eventAttendance;
    }
    
    public String getEventId() 
    {
        return eventId;
    }

    public String getEventName() 
    {
        return eventName;
    }

    public String getLocation() 
    {
        return location;
    }

    public String getEventDate() 
    {
        return eventDate;
    }

    public Date getDateCreated() 
    {
        return dateCreated;
    }

    public String getCampaign() 
    {
        return campaign;
    }
    
    public String getBase64() 
    {
        return base64;
    }

    public String getFileType()
    {
        return fileType;
    }

    public String getDetails() 
    {
        return details;
    }
    
    public EmployeeDetails getEmployeeDetails() 
    {
        return employeeDetails;
    }

    public Set<EventAttendance> getEventAttendance() 
    {
        return eventAttendance;
    }
    
}
