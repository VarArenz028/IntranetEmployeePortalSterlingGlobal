package org.sterling.intranet.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.interfaces.EmployeeDetailsDao;
import org.sterling.intranet.interfaces.EmployeeDetailsServices;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.UserAvatar;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("employeeDetailsServices")
public class EmployeeDetailsServicesImpl implements EmployeeDetailsServices
{

    @Autowired
    private EmployeeDetailsDao employeeDetailsDao;
    
    @Override
    public EmployeeDetails selectEmployeeDetailsByUserId(Integer userId) 
    {
        return employeeDetailsDao.selectEmployeeDetailsByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean selectEmployeeDetailsById(Integer userId) 
    {
        return employeeDetailsDao.selectEmployeeDetailsById(userId);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveUserDetails(EmployeeDetails userDetails) 
    {
        employeeDetailsDao.saveUserDetails(userDetails);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDetails> getAllEmployeeDetails() 
    {
        return employeeDetailsDao.getAllEmployeeDetails();
    }

    @Transactional(readOnly = false)
    @Override
    public void updateEmployeeDetails(Integer userId, String email, String position, String gender, String contactNumber, int age, User user) 
    {
        EmployeeDetails employeeDetails = employeeDetailsDao.findEmployeeDetailsById(userId);
        
        employeeDetails.setEmail(email);
        employeeDetails.setPosition(position);
        employeeDetails.setGender(gender);
        employeeDetails.setContactNumber(contactNumber);
        employeeDetails.setAge(age);
        employeeDetails.setUser(user);
        
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateLastName(Integer userId, String lastName) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        EmployeeDetails employeeDetails = employeeDetailsDao.findEmployeeDetailsById(userId);
        
        if(employeeDetails != null)
        {
            employeeDetails.setLastName(lastName);
            response.put("lastNameUpdated", HttpStatus.OK);
        }
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateFirstName(Integer userId, String firstName) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        EmployeeDetails employeeDetails = employeeDetailsDao.findEmployeeDetailsById(userId);
        
        if(employeeDetails != null)
        {
            employeeDetails.setFirstName(firstName);
            response.put("firstNameUpdated", HttpStatus.OK);
        }
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updatePosition(Integer userId, String position) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        EmployeeDetails employeeDetails = employeeDetailsDao.selectEmployeeDetailsByUserId(userId);
        
        if(employeeDetails != null)
        {
            employeeDetails.setPosition(position);
            response.put("positionUpdated", HttpStatus.OK);
        }
        else
        {
            response.put("positionNotUpdated", HttpStatus.NOT_IMPLEMENTED);
        }
        
        
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateEmail(Integer userId, String email) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        EmployeeDetails employeeDetails = employeeDetailsDao.selectEmployeeDetailsByUserId(userId);
        
        if(employeeDetails != null)
        {
            employeeDetails.setEmail(email);
            response.put("emailUpdated", HttpStatus.OK);
        }
        else
        {
            response.put("emailNotUpdated", HttpStatus.NOT_IMPLEMENTED);
        }
        
        
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateContactNumber(Integer userId, String contactNumber)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        EmployeeDetails employeeDetails = employeeDetailsDao.selectEmployeeDetailsByUserId(userId);
        
        if(employeeDetails != null)
        {
            employeeDetails.setContactNumber(contactNumber);
            response.put("contactNumberUpdated", HttpStatus.OK);
        }
        else
        {
            response.put("contactNumberNotUpdated", HttpStatus.NOT_IMPLEMENTED);
        }
        
        
        return response;
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateGender(Integer userId, String gender) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        EmployeeDetails employeeDetails = employeeDetailsDao.selectEmployeeDetailsByUserId(userId);
        
        if(employeeDetails != null)
        {
            employeeDetails.setGender(gender);
            response.put("genderUpdated", HttpStatus.OK);
        }
        else
        {
            response.put("genderNotUpdated", HttpStatus.NOT_IMPLEMENTED);
        }
        
        
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDetails selectEmployeeDetalsByEmpId(Integer empId) 
    {
        return employeeDetailsDao.selectEmployeeDetalsByEmpId(empId);
    }

    @Transactional(readOnly = false)
    @Override
    public void setAvatar(UserAvatar userAvatar, Integer empDetailsId) 
    {
        EmployeeDetails employeeDetails = employeeDetailsDao.selectEmployeeDetalsByEmpId(empDetailsId);
        employeeDetails.setUserAvatar(userAvatar);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDetails selectOneEmployeeDetaalsByEmpId(Integer empId) 
    {
        return employeeDetailsDao.selectOneEmployeeDetaalsByEmpId(empId);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDetails selectNameOfEmployeeByUserId(Integer userId) 
    {
        return employeeDetailsDao.selectNameOfEmployeeByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Integer selectEmpDetailsIdByUserId(Integer userId)
    {
        return employeeDetailsDao.selectEmpDetailsIdByUserId(userId);
    }
    
}
