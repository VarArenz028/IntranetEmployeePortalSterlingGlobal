package org.sterling.intranet.models;

/**
 *
 * @author Var Arenz Villarino
 */
public class DashboardArticleAuthor 
{
    private String lastName;
    private String firstName;
    private int numberOfArticle;

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public void setNumberOfArticle(int numberOfArticle) 
    {
        this.numberOfArticle = numberOfArticle;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public String getFirstName() 
    {
        return firstName;
    }

    public int getNumberOfArticle() 
    {
        return numberOfArticle;
    }
    
    
}
