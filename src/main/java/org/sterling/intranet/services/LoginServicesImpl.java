package org.sterling.intranet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.LoginServices;
import org.sterling.intranet.interfaces.UserDao;
import org.sterling.intranet.models.User;

@Service("loginServices")
public class LoginServicesImpl implements LoginServices
{
    
    @Autowired
    private UserDao userDao;
    
    @Transactional(readOnly = true)
    @Override
    public User loginAuthentication(String username, String password) 
    {
        return userDao.loginAuthentication(username, password);
    }

    @Transactional(readOnly = true)
    @Override
    public User loadUserByUsername(String username) 
    {
        return userDao.selectUserByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public User userDetailsAuthentication(String username, String password) 
    {
        return userDao.userDetailsAuthentication(username, password);
    }

    @Override
    public User userLoginAuthentication(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
