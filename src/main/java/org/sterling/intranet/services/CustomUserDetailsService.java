package org.sterling.intranet.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.UserDao;
import org.sterling.intranet.models.User;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
    
    @Autowired
    private UserDao userDao;
    
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        
        User user = userDao.logUserByUsername(username.trim());
//        User user = userDao.selectUserByUsername(username.trim());
//        System.out.println("Username " + user.getUsername());
//        System.out.println("Username " + user.getUsername());
//        System.out.println("Username " + user.getUsername());
//        System.out.println("Username " + user.getUsername());
//        System.out.println("Username " + user.getUsername());
//        System.out.println("Username " + user.getUsername());
//        System.out.println("Username " + user.getUsername());
        if(user == null)
        {
            throw new UsernameNotFoundException("Username not found"); 
        }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
                     getGrantedAuthorities(user));
    }
    private List<GrantedAuthority> getGrantedAuthorities(User user)
    {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().trim()));
//        System.out.println("Role " + user.getRole());
//        System.out.println("Role " + user.getRole());
//        System.out.println("Role " + user.getRole());
//        System.out.println("Role " + user.getRole());
//        System.out.println("Role " + user.getRole());
//        System.out.println("Role " + user.getRole());
//        System.out.println("Role " + user.getRole());
        return authorities;
    }
}
//.and().formLogin().loginProcessingUrl("/authenticate").usernameParameter("username").passwordParameter("password")