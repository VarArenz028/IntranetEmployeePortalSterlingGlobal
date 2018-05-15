package org.sterling.intranet.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sterling.intranet.interfaces.AccountServices;
import org.sterling.intranet.models.Account;

/**
 *
 * @author Var Arenz Villarino
 */
@Component
public class CategoryStatisticsUtils 
{
    @Autowired
    private AccountServices accountServices;
    
    private Account test()
    {
        Account account = accountServices.selectAcountByUserId(18);
        return account;
    }
}
