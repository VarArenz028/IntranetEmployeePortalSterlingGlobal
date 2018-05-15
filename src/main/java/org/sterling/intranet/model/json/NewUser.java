package org.sterling.intranet.model.json;

import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.User;

/**
 *
 * @author Var Arenz G. Villarino
 */
public class NewUser 
{
    private User user;
    private EmployeeDetails employeeDetails;
    private String campaign;

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails)
    {
        this.employeeDetails = employeeDetails;
    }

    public void setCampaign(String campaign) 
    {
        this.campaign = campaign;
    }
    
    public User getUser() 
    {
        return user;
    }

    public EmployeeDetails getEmployeeDetails() 
    {
        return employeeDetails;
    }

    public String getCampaign() 
    {
        return campaign;
    }
    
}
