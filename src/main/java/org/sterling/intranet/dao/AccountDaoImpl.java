package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.AccountDao;
import org.sterling.intranet.models.Account;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("accountDao")
public class AccountDaoImpl extends AbstractDao<Integer, Account> implements AccountDao
{

    @Override
    public Account selectAcountByUserId(Integer userId) 
    {
        Query query = getSession().createQuery("from Account where userId = :userId");
        query.setParameter("userId", userId);
        Account account = (Account) query.uniqueResult();
        
        return account;
    }
    
    @Override
    public Account selectAcountByUserIdAccName(String accountName, Integer userId)
    {
        Query query = getSession().createQuery("from Account where accountName = :accountName and userId = :userId");
        query.setParameter("accountName", accountName);
        query.setParameter("userId", userId);
        Account account = (Account) query.uniqueResult();
        
        return account;
    }
    
    @Override
    public boolean selectAcountByUserId(String accountName, Integer userId) 
    {
        boolean result;
        Query query = getSession().createQuery("from Account where accountName = :accountName and userId = :userId");
        query.setParameter("userId", userId);
        query.setParameter("accountName", accountName);
        Account account = (Account) query.uniqueResult();
        
        if(account != null)
        {
            result = true;
        }
        else
        {
            result = false;
        }
        
        return result;
    }
    
    @Override
    public List<Account> selectAllAccount() 
    {
        Criteria criteria = createCriteria();
        List<Account> list = criteria.list();
        return list;
    }
    
    @Override
    public List<Account> selectAllUserAccount(Integer userId) 
    {
        Query query = getSession().createQuery("from Account where userId = :userId");
        query.setParameter("userId", userId);
        
        List<Account> list = query.list();
        
        return list;
    }
    
    @Override
    public List<Account> selectAllCampaignByCampaignName(String campaign)
    {
        Query query = getSession().createQuery("from Account where accountName = :campaign");
        query.setParameter("campaign", campaign);

        List<Account> list = query.list();
//        Criteria criteria = getSession().createCriteria(Account.class).add(Restrictions.eq("accountName", campaign));
//        
//        List<Account> list = criteria.list();
        
        return list;
    }
    
    @Override
    public void saveAccount(Account account) 
    {
        save(account);
    }

    @Override
    public List<Account> selectAllCampaignName(String campaign) 
    {
        Criteria criteria = getSession().createCriteria(Account.class).add(Restrictions.eq("accountName", campaign))
                          .setProjection(Projections.projectionList()
                          .add(Projections.property("accountName"), "accountName"))
                          .setResultTransformer(Transformers.aliasToBean(Account.class));
        List<Account> list = criteria.list();
        return list;
    }
    
    
}
