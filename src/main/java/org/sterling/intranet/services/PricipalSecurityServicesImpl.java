package org.sterling.intranet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.PricipalSecurityDao;
import org.sterling.intranet.interfaces.PricipalSecurityServices;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("pricipalSecurityServices")
public class PricipalSecurityServicesImpl implements PricipalSecurityServices
{
    
    @Autowired
    private PricipalSecurityDao pricipalSecurityDao;
    
    @Transactional(readOnly = true)
    @Override
    public String getPrincipal() 
    {
        return pricipalSecurityDao.getPrincipal();
    }
    
}
