package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.interfaces.EmployeeDetailsDao;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("employeeDetailsDao")
public class EmployeeDetailsDaoImpl extends AbstractDao<Integer, EmployeeDetails> implements EmployeeDetailsDao
{

    @Override
    public EmployeeDetails selectEmployeeDetailsByUserId(Integer userId) 
    {
        Query query = getSession().createQuery("from EmployeeDetails where userId = :userId");
        query.setParameter("userId", userId);
        
        EmployeeDetails employeeDetails = (EmployeeDetails) query.uniqueResult();
        
        return employeeDetails;
    }
    
    @Override
    public EmployeeDetails selectEmployeeDetalsByEmpId(Integer empId) 
    {
        Query query = getSession().createQuery("from EmployeeDetails where empDetailsId = :empId");
        query.setParameter("empId", empId);
        
        EmployeeDetails employeeDetails = (EmployeeDetails) query.uniqueResult();
        
        return employeeDetails;
    }
    
    @Override
    public boolean selectEmployeeDetailsById(Integer userId) 
    {
        boolean result = false;
        
        Query query = getSession().createQuery("from EmployeeDetails where userId = :userId");
        query.setParameter("userId", userId);
        
        EmployeeDetails employeeDetails = (EmployeeDetails) query.uniqueResult();
        
        if(employeeDetails.getEmail() != null)
        {
            result = true;
        }
        
        return result;
    }
    
    @Override
    public EmployeeDetails findEmployeeDetailsById(Integer userId) 
    {
        Query query = getSession().createQuery("from EmployeeDetails where userId = :userId");
        query.setParameter("userId", userId);
        
        EmployeeDetails employeeDetails = (EmployeeDetails) query.uniqueResult();
        
        return employeeDetails;
    }
    
    @Override
    public void saveUserDetails(EmployeeDetails userDetails) 
    {
        save(userDetails);
    }

    @Override
    public List<EmployeeDetails> getAllEmployeeDetails()
    {
        Criteria criteria = createCriteria();
        List<EmployeeDetails> list = criteria.list();
        return list;
    }

    @Override
    public EmployeeDetails selectOneEmployeeDetaalsByEmpId(Integer empId)
    {
        Criteria criteria = getSession().createCriteria(EmployeeDetails.class)
            .add(Restrictions.eq("empDetailsId",(int) empId))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("empDetailsId"), "empDetailsId")
                            .add(Projections.property("age"), "age")
                            .add(Projections.property("contactNumber"), "contactNumber")
                            .add(Projections.property("email"), "email")
                            .add(Projections.property("firstName"), "firstName")
                            .add(Projections.property("gender"), "gender")
                            .add(Projections.property("lastName"), "lastName")
                            .add(Projections.property("position"), "position"))
                            .setResultTransformer(Transformers.aliasToBean(EmployeeDetails.class));
        EmployeeDetails employeeDetails = (EmployeeDetails) criteria.uniqueResult();
        return employeeDetails;
    }

    @Override
    public EmployeeDetails selectNameOfEmployeeByUserId(Integer userId) 
    {
        Criteria criteria = getSession().createCriteria(EmployeeDetails.class)
            .add(Restrictions.eq("user.userId",(int) userId))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("empDetailsId"), "empDetailsId")
                            .add(Projections.property("firstName"), "firstName")
                            .add(Projections.property("lastName"), "lastName"))
                            .setResultTransformer(Transformers.aliasToBean(EmployeeDetails.class));
        EmployeeDetails employeeDetails = (EmployeeDetails) criteria.uniqueResult();
        return employeeDetails;
    }

    @Override
    public EmployeeDetails selectOneEmployeeDetailsByEmpId(Integer emDetailsId) 
    {
        Criteria criteria = getSession().createCriteria(EmployeeDetails.class)
            .add(Restrictions.eq("empDetailsId", emDetailsId))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("empDetailsId"), "empDetailsId")
                            .add(Projections.property("age"), "age")
                            .add(Projections.property("contactNumber"), "contactNumber")
                            .add(Projections.property("email"), "email")
                            .add(Projections.property("firstName"), "firstName")
                            .add(Projections.property("gender"), "gender")
                            .add(Projections.property("lastName"), "lastName")
                            .add(Projections.property("position"), "position"))
                            .setResultTransformer(Transformers.aliasToBean(EmployeeDetails.class));
        EmployeeDetails employeeDetails = (EmployeeDetails) criteria.uniqueResult();
        return employeeDetails;
    }

    @Override
    public Integer selectEmpDetailsIdByUserId(Integer userId) 
    {
        Query query = getSession().createQuery("select e.empDetailsId from EmployeeDetails e where e.user.userId = :userId");
        query.setParameter("userId", userId);
        query.setMaxResults(1);
        
        Integer empDetailsId = (Integer) query.uniqueResult();
        
        return empDetailsId;
    }
    
}
