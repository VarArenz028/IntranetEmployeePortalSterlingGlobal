package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.User;

public interface UserServices 
{
    
    Integer selectUserIdByUsername(String username);
    
    User SelectUserById(Integer id);
    
    boolean findByUsername(String username);
    
    User selectUserByUsername(String username);
    
    User selectOneUserByUsername(String username);
    
    boolean findByPassword(String password);
    
    void saveUser(User user);
    
    void updateUserPassword(String password, Integer userId);
    
    List<User> getAllUser();
    
    Map<String, Object> updateUsername(Integer userId, String username);
    
    Map<String, Object> updatePassword(Integer userId, String password);
    
    Map<String, Object> updateRole(Integer userId, String role);
    
}
