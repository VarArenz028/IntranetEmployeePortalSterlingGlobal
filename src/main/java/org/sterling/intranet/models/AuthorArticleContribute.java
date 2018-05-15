package org.sterling.intranet.models;

/**
 *
 * @author Var Arenz Villarino
 */
public class AuthorArticleContribute 
{
    private int empDetailsId;
    private String name;
    private int numberOfArticle;

    public void setEmpDetailsId(int empDetailsId) 
    {
        this.empDetailsId = empDetailsId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setNumberOfArticle(int numberOfArticle)
    {
        this.numberOfArticle = numberOfArticle;
    }

    public int getEmpDetailsId() 
    {
        return empDetailsId;
    }

    public String getName() 
    {
        return name;
    }

    public int getNumberOfArticle()
    {
        return numberOfArticle;
    }
    
    
}
