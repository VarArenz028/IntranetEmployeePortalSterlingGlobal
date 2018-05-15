package org.sterling.intranet.servicesBuilder;

import org.joda.time.LocalDate;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.UserRegistration;

/**
 *
 * @author Var Arenz G. Villarino
 */

public class UserRegistrationBuilder
{
    private UserRegistration userRegistration = new UserRegistration();
    private User user = new User();

    public UserRegistrationBuilder(UserRegistration userRegistration) 
    {
        this.userRegistration = userRegistration;
    }
    public void setUserInformation()
    {
        getUser().setUsername(getUserRegistration().getUsername());
        getUser().setPassword(getUserRegistration().getPassword());
        getUser().setRole(getUserRegistration().getRole());
    }

    public UserRegistration getUserRegistration() 
    {
        return userRegistration;
    }

    public User getUser()
    {
        return user;
    }
    
    
}
