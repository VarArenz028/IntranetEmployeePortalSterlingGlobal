package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.Account;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface AccountDao 
{
    
    boolean selectAcountByUserId(String accountName, Integer userId);
    
    Account selectAcountByUserIdAccName(String accountName, Integer userId);
    
    Account selectAcountByUserId(Integer userId);
    
    List<Account> selectAllAccount();
    
    List<Account> selectAllUserAccount(Integer userId);
    
    List<Account> selectAllCampaignByCampaignName(String campaign);
    
    List<Account> selectAllCampaignName(String campaign);
    
    void saveAccount(Account account);
}
