package org.sterling.intranet.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.UserDao;
import org.sterling.intranet.interfaces.UserServices;
import org.sterling.intranet.models.User;
/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("userServices")
public class UserServicesImpl implements UserServices
{
    
    @Autowired
    private UserDao userDao;
    
    @Transactional(readOnly = true)
    @Override
    public User SelectUserById(Integer id) 
    {
        return userDao.SelectUserById(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean findByUsername(String username) 
    {
        return userDao.findByUsername(username);
    }
    
    @Transactional(readOnly = true)
    @Override
    public User selectUserByUsername(String username)
    {
        return userDao.selectUserByUsername(username);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean findByPassword(String password)
    {
        return userDao.findByPassword(password);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveUser(User user) 
    {
        userDao.saveUser(user);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void updateUserPassword(String password, Integer userId)
    {
        User u = userDao.SelectUserById(userId);
        
        u.setPassword(password);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUser()
    {
        return userDao.getAllUser();
    }
    
    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateUsername(Integer userId, String username) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        User user = SelectUserById(userId);
        
        if(user != null)
        {
            user.setUsername(username);
            response.put("usernameUpdated", HttpStatus.OK);
        }
        
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updatePassword(Integer userId, String password) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        User user = userDao.SelectUserById(userId);
        
        if(user != null)
        {
            user.setPassword(password);
            response.put("passwordUpdated", HttpStatus.OK);
        }
        
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateRole(Integer userId, String role) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        User user = userDao.SelectUserById(userId);
        
        if(user != null)
        {
            user.setRole(role);
            response.put("roleUpdated", HttpStatus.OK);
        }
        
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public User selectOneUserByUsername(String username)
    {
        return userDao.selectOneUserByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer selectUserIdByUsername(String username)
    {
        return userDao.selectUserIdByUsername(username);
    }
    
}
