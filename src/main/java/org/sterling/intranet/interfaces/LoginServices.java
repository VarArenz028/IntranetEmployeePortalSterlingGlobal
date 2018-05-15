package org.sterling.intranet.interfaces;

import org.sterling.intranet.models.User;

public interface LoginServices
{
    
    User userLoginAuthentication(User user);
    
    User loginAuthentication(String username, String password);
    
    User loadUserByUsername(String username);
    
    User userDetailsAuthentication(String username, String password);
    
}
