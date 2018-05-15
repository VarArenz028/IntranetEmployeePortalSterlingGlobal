package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.EmployeeDetails;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface EmployeeDetailsDao 
{
    
    Integer selectEmpDetailsIdByUserId(Integer userId);
    
    EmployeeDetails selectEmployeeDetailsByUserId(Integer userId);
    
    EmployeeDetails selectEmployeeDetalsByEmpId(Integer empId);
    
    EmployeeDetails selectOneEmployeeDetaalsByEmpId(Integer empId);
    
    EmployeeDetails selectOneEmployeeDetailsByEmpId(Integer emDetailsId);
    
    EmployeeDetails selectNameOfEmployeeByUserId(Integer userId);
    
    boolean selectEmployeeDetailsById(Integer userId);
    
    EmployeeDetails findEmployeeDetailsById(Integer userId);
    
    void saveUserDetails(EmployeeDetails userDetails);
    
    List<EmployeeDetails> getAllEmployeeDetails();
    
}
