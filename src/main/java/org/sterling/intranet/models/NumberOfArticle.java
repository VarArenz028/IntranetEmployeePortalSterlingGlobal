package org.sterling.intranet.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Var Arenz Villarino
 */
@Entity
@Table(name = "numberOfArticle")
public class NumberOfArticle
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numberOfArticleId", length = 11)
    private int numberOfArticleId;
    
    @Column(name = "numberOfArticle")
    private int numberOfArticle;
    
    @Column(name = "campaign", length = 30, nullable = false)
    private String campaign;
    
    @OneToOne
    @JoinColumn(name = "empDetailsId")
    private EmployeeDetails employeeDetails;

    public void setNumberOfArticleId(int numberOfArticleId) 
    {
        this.numberOfArticleId = numberOfArticleId;
    }

    public void setNumberOfArticle(int numberOfArticle)
    {
        this.numberOfArticle = numberOfArticle;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails)
    {
        this.employeeDetails = employeeDetails;
    }

    public void setCampaign(String campaign)
    {
        this.campaign = campaign;
    }
    
    public int getNumberOfArticleId() 
    {
        return numberOfArticleId;
    }

    public int getNumberOfArticle() 
    {
        return numberOfArticle;
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
