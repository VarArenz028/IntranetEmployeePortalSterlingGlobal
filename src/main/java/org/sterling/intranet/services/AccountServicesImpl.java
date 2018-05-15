package org.sterling.intranet.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.AccountDao;
import org.sterling.intranet.interfaces.AccountServices;
import org.sterling.intranet.models.Account;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("accountServices")
public class AccountServicesImpl implements AccountServices
{

    @Autowired
    private AccountDao accountDao;
    
    @Transactional(readOnly = true)
    @Override
    public Account selectAcountByUserId(Integer userId)
    {
        return accountDao.selectAcountByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Account selectAcountByUserIdAccName(String accountName, Integer userId)
    {
        return accountDao.selectAcountByUserIdAccName(accountName, userId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean selectAcountByUserId(String accountName, Integer userId) 
    {
        return accountDao.selectAcountByUserId(accountName, userId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Account> selectAllAccount() 
    {
        return accountDao.selectAllAccount();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Account> selectAllUserAccount(Integer userId)
    {
        return accountDao.selectAllUserAccount(userId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Account> selectAllCampaignByCampaignName(String campaign) 
    {
        return accountDao.selectAllCampaignByCampaignName(campaign);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveAccount(Account account) 
    {
        accountDao.saveAccount(account);
    }
    
    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateAccountState(String account, Integer userId) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        Account requestAccount = accountDao.selectAcountByUserIdAccName(account, userId);
        
        if(requestAccount != null)
        {
            if(requestAccount.getState().equalsIgnoreCase("Active"))
            {
                requestAccount.setState("Inactive");
            }
            else if(requestAccount.getState().equalsIgnoreCase("Inactive"))
            {
                requestAccount.setState("Active");
            }
            response.put("accountStateUpdated", HttpStatus.OK);
        }
        
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Account> selectAllCampaignName(String campaign) 
    {
        return accountDao.selectAllCampaignByCampaignName(campaign);
    }
    
}
