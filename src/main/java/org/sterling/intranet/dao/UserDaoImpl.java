package org.sterling.intranet.dao;

import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Criteria;

import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.UserDao;
import org.sterling.intranet.models.Campaign;
import org.sterling.intranet.models.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao
{
    
    @Override
    public User userLoginAuthentication(User user) 
    {
        Query query = getSession().createQuery("from User where username = :username and password = :password");
        query.setParameter("username", user.getUsername().trim());
        query.setParameter("password", user.getPassword());
        
        User userResult = (User) query.uniqueResult();
        
        return userResult;
    }
    
    @Override
    public User SelectUserById(Integer id) 
    {
        Query query = getSession().createQuery("from User where userId = :userId");
//        Query query = getSession().createQuery("Select u, c from User u JOIN FETCH u.campaigns c where u.userId = :userId");
        query.setParameter("userId", id);
        
        User user = (User) query.uniqueResult();

        return user;
    }

    @Override
    public User selectUserByUsername(String username)
    {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        User user = (User) criteria.uniqueResult();
        
        return user;
    }
    
    @Override
    public User userDetailsAuthentication(String username, String password) 
    {
        
        Query query = getSession().createQuery("select u, c from User u JOIN FETCH u.campaigns c where username = :username and password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User) query.uniqueResult();
        
        return user;
    }
    
    @Override
    public boolean findByUsername(String username) 
    {
        boolean result;
      
        Query query = getSession().createQuery("select u from User u where u.username = :username");
        query.setParameter("username", username);
        User user = (User) query.uniqueResult();
        
        if(user != null)
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
    public boolean findByPassword(String password) 
    {
        boolean result;
        
        Query query = getSession().createQuery("from User where password = :password");
        query.setParameter("password", password);
        User user = (User) query.uniqueResult();
        
        if(user != null)
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
    public User loginAuthentication(String username, String password) 
    {
        
        Query query = getSession().createQuery("from User where username = :username and password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        
        User user = (User) query.uniqueResult();
        
        return user;
    }

    @Override
    public void saveUser(User user) 
    {
        save(user);
    }
    
    @Override
    public List<User> getAllUser() 
    {
        Criteria criteria = createCriteria();
        List<User> list = criteria.list();
        return list;
    }

    @Override
    public int getNumberOfUser()
    {
        
        int size = 0;
        Query query = getSession().createQuery("select u from User u");
        List<User> list = query.list();
        size = list.size();
        return size;
        
    }

    @Override
    public int numberOfSystemAdmins() 
    {
        int size = 0;
        Query query = getSession().createQuery("select u from User u where u.role = :systemAdmin");
        query.setParameter("systemAdmin", "System Admin");
        List<User> list = query.list();
        size = list.size();
        return size;
    }

    @Override
    public int numberOfCampaignAdmins() 
    {
        int size = 0;
        Query query = getSession().createQuery("select u from User u where u.role = :campaignAdmin");
        query.setParameter("campaignAdmin", "Campaign Admin");
        List<User> list = query.list();
        size = list.size();
        return size;
    }

    @Override
    public int numberOfAgents() 
    {
        int size = 0;
        Query query = getSession().createQuery("select u from User u where u.role = :agent");
        query.setParameter("agent", "Agent");
        List<User> list = query.list();
        size = list.size();
        return size;
    }

    @Override
    public User selectOneUserByUsername(String username) 
    {
        Criteria criteria = getSession().createCriteria(User.class)
            .add(Restrictions.eq("username", username))
                .setProjection(Projections.projectionList()
                .add(Projections.property("username"), "username")
                .add(Projections.property("userId"), "userId")
                .add(Projections.property("role"), "role"))
                          .setResultTransformer(Transformers.aliasToBean(User.class));
        User user = (User) criteria.uniqueResult();
        return user;
    }

    @Override
    public Integer selectUserIdByUsername(String username) 
    {
        Query query = getSession().createQuery("select u.userId from User u where u.username = :username");
        query.setParameter("username", username);
        query.setMaxResults(1);
        
        Integer userId = (Integer) query.uniqueResult();
        
        return userId;
    }

    @Override
    public User logUserByUsername(String username) 
    {
        Criteria criteria = getSession().createCriteria(User.class).add(Restrictions.eq("username", username))
                .setProjection(Projections.projectionList()
                .add(Projections.property("username"), "username")
                .add(Projections.property("password"), "password")
                .add(Projections.property("role"), "role")
                .add(Projections.property("state"), "state"))
                .setResultTransformer(Transformers.aliasToBean(User.class));
        criteria.setMaxResults(1);
        User user = (User) criteria.uniqueResult();
        return user;
    }
    
}

