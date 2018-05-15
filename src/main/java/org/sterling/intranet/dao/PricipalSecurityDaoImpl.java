package org.sterling.intranet.dao;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.PricipalSecurityDao;

/**
 *
 * @author KAMBO OTIS
 */
@Repository("pricipalSecurityDao")
public class PricipalSecurityDaoImpl implements PricipalSecurityDao
{

    @Override
    public String getPrincipal() 
    {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal instanceof UserDetails) 
        {
            userName = ((UserDetails)principal).getUsername();
        } 
        else 
        {
            userName = principal.toString();
        }
        return userName;
    }
    
}
