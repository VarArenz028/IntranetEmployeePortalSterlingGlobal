package org.sterling.intranet.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.EmployeeDetailsDao;
import org.sterling.intranet.interfaces.NumberOfArticleDao;
import org.sterling.intranet.interfaces.NumberOfArticleServices;
import org.sterling.intranet.models.AuthorArticleContribute;
import org.sterling.intranet.models.EmployeeDetails;
import org.sterling.intranet.models.NumberOfArticle;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("numberOfArticleServices")
public class NumberOfArticleServicesImpl implements NumberOfArticleServices
{

    @Autowired
    private NumberOfArticleDao numberOfArticleDao;
    
    @Autowired
    private EmployeeDetailsDao employeeDetailsDao;
    
    @Transactional(readOnly = true)
    @Override
    public NumberOfArticle selectNumberOfArticleByEmpId(Integer empId) 
    {
        return numberOfArticleDao.selectNumberOfArticleByEmpId(empId);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveNumberOfArticle(NumberOfArticle numberOfArticle) 
    {
        numberOfArticleDao.saveNumberOfArticle(numberOfArticle);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateNumberOfArticle(Integer empId) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        NumberOfArticle numberOfArticle = numberOfArticleDao.selectNumberOfArticleByEmpId(empId);
        
        int newNumberOfArticle = numberOfArticle.getNumberOfArticle() + 1;
        
        numberOfArticle.setNumberOfArticle(newNumberOfArticle);
        
        response.put("numberOfArticleUpdated", HttpStatus.OK);
        
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorArticleContribute> selectTopFiveContributersByCampaignName(String campaignName) 
    {
        List<NumberOfArticle> topFiveNumberOfArticles = numberOfArticleDao.selectTopFiveContributersByCampaignName(campaignName);
        List<AuthorArticleContribute> authorArticleContributes = new ArrayList();
        for(NumberOfArticle numberOfArticle : topFiveNumberOfArticles)
        {
            EmployeeDetails employeeDetails = employeeDetailsDao.selectEmployeeDetalsByEmpId(numberOfArticle.getEmployeeDetails().getEmpDetailsId());
            
            AuthorArticleContribute authorArticleContribute = new AuthorArticleContribute();
            
            authorArticleContribute.setName(employeeDetails.getLastName().substring(0,1).toUpperCase() + 
                                            employeeDetails.getLastName().substring(1).toLowerCase()
                                            + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
                                            employeeDetails.getFirstName().substring(1).toLowerCase());
            
            authorArticleContribute.setEmpDetailsId(employeeDetails.getEmpDetailsId());
            
            authorArticleContribute.setNumberOfArticle(numberOfArticle.getNumberOfArticle());
            authorArticleContributes.add(authorArticleContribute);
        }
        
        return authorArticleContributes;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorArticleContribute> selectAllNumberOfArticleByCampaignName(String campaignName)
    {
        List<NumberOfArticle> allAuthorWithArticleNum = numberOfArticleDao.selectAllNumberOfArticleByCampaignName(campaignName);
        List<AuthorArticleContribute> authorArticleContributes = new ArrayList();
        
        for(NumberOfArticle numberOfArticle : allAuthorWithArticleNum)
        {
            EmployeeDetails employeeDetails = employeeDetailsDao.selectEmployeeDetalsByEmpId(numberOfArticle.getEmployeeDetails().getEmpDetailsId());
            
            AuthorArticleContribute authorArticleContribute = new AuthorArticleContribute();
            
            authorArticleContribute.setName(employeeDetails.getLastName().substring(0,1).toUpperCase() + 
                                            employeeDetails.getLastName().substring(1).toLowerCase()
                                            + " " + employeeDetails.getFirstName().substring(0,1).toUpperCase() + 
                                            employeeDetails.getFirstName().substring(1).toLowerCase());
            
            authorArticleContribute.setEmpDetailsId(employeeDetails.getEmpDetailsId());
            
            authorArticleContribute.setNumberOfArticle(numberOfArticle.getNumberOfArticle());
            authorArticleContributes.add(authorArticleContribute);
        }
        
        return authorArticleContributes;
    }
    
}
