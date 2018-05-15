package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.User;
import org.sterling.intranet.models.UserAvatar;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface EmployeeDetailsServices 
{
    
    Integer selectEmpDetailsIdByUserId(Integer userId);
    
    EmployeeDetails selectEmployeeDetailsByUserId(Integer userId);
    
    EmployeeDetails selectEmployeeDetalsByEmpId(Integer empId);
    
    EmployeeDetails selectOneEmployeeDetaalsByEmpId(Integer empId);
    
    EmployeeDetails selectNameOfEmployeeByUserId(Integer userId);
    
    boolean selectEmployeeDetailsById(Integer userId);
    
    void saveUserDetails(EmployeeDetails userDetails);
    
    List<EmployeeDetails> getAllEmployeeDetails();
    
    void updateEmployeeDetails(Integer userId, String email, String position, String gender, String contactNumber, int age, User user);
    
    Map<String, Object> updateLastName(Integer userId, String lastName);
    
    Map<String, Object> updateFirstName(Integer userId, String firstName);
    
    Map<String, Object> updatePosition(Integer userId, String position);
    
    Map<String, Object> updateEmail(Integer userId, String email);
    
    Map<String, Object> updateContactNumber(Integer userId, String contactNumber);
    
    Map<String, Object> updateGender(Integer userId, String gender);
    
    void setAvatar(UserAvatar userAvatar, Integer empDetailsId);
    
}
