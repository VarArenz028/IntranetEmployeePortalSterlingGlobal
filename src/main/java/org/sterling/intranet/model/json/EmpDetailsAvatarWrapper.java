package org.sterling.intranet.model.json;

import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.UserAvatar;

/**
 *
 * @author Var Arenz G. Villarino
 */
public class EmpDetailsAvatarWrapper 
{
    private User user;
    private EmployeeDetails employeeDetails;
    private UserAvatar userAvatar;

    public void setUser(User user) 
    {
        this.user = user;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails) 
    {
        this.employeeDetails = employeeDetails;
    }

    public void setUserAvatar(UserAvatar userAvatar)
    {
        this.userAvatar = userAvatar;
    }

    public User getUser() 
    {
        return user;
    }

    public EmployeeDetails getEmployeeDetails()
    {
        return employeeDetails;
    }

    public UserAvatar getUserAvatar()
    {
        return userAvatar;
    }
    
    
}
