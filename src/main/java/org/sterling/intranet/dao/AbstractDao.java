package org.sterling.intranet.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractDao<PK extends Serializable, T>
{
    private final Class<T> persistentClass;
    
    @Autowired
    protected SessionFactory sessionFactory;    
    public AbstractDao()
    {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    
    protected Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }
    
    public void save(T obj)
    {
        getSession().save(obj);
    }
    
    public void delete(T obj)
    {
        getSession().delete(obj);
    }
    
    public Criteria createCriteria()
    {
        return getSession().createCriteria(persistentClass);
    }
}
