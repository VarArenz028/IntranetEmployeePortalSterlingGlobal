package org.sterling.intranet.model.json;

import java.util.Date;

/**
 *
 * @author Var Arenz Villarino

 */
public class SearchModel 
{
    private String searchedId;
    private String title;
    private String category;
    private String empName;
    private Date dateCreated;
    private String type;

    public void setSearchedId(String searchedId) 
    {
        this.searchedId = searchedId;
    }
    
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public void setEmpName(String empName) 
    {
        this.empName = empName;
    }

    public void setDateCreated(Date dateCreated) 
    {
        this.dateCreated = dateCreated;
    }
    
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getSearchedId() 
    {
        return searchedId;
    }
    
    public String getTitle() 
    {
        return title;
    }

    public String getCategory() 
    {
        return category;
    }

    public String getEmpName() 
    {
        return empName;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }
    
    public String getType()
    {
        return type;
    }
    
}
