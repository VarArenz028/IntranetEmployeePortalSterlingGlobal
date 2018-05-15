package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author KAMBO OTIS
 */

@Entity
@Table(name = "campaign")
public class Campaign implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignId;
    
    @Column(name = "campaignName", length = 20, nullable = false, unique = true)
    private String campaignName;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy") 
    @Column(name = "dateCreated", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dateCreated;

    public void setCampaignId(int campaignId) 
    {
        this.campaignId = campaignId;
    }

    public void setCampaignName(String campaignName) 
    {
        this.campaignName = campaignName;
    }

    public void setDateCreated(LocalDate dateCreated) 
    {
        this.dateCreated = dateCreated;
    }

    public int getCampaignId()
    {
        return campaignId;
    }

    public String getCampaignName()
    {
        return campaignName;
    }
    public LocalDate getDateCreated() 
    {
        return dateCreated;
    }

}
