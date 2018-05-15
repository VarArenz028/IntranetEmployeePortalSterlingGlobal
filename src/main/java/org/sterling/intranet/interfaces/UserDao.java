package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.User;

public interface UserDao 
{
    
    Integer selectUserIdByUsername(String username);
    
    User logUserByUsername(String username);
    
    User userLoginAuthentication(User user);
    
    User SelectUserById(Integer id);
    
    User selectUserByUsername(String username);
    
    User selectOneUserByUsername(String username);
    
    User userDetailsAuthentication(String username, String password);
    
    boolean findByUsername(String username);
    
    boolean findByPassword(String password);
    
    User loginAuthentication(String username, String password);
    
    void saveUser(User user);
    
    List<User> getAllUser();
    
    int getNumberOfUser();
    
    int numberOfSystemAdmins();
    
    int numberOfCampaignAdmins();
    
    int numberOfAgents();
    
}
