package org.sterling.intranet.models;

/**
 *
 * @author Var Arenz Villarino
 */
public class CategoryStatistics 
{
    private String accountName;
    private int numberOfArticle;

    public void setAccountName(String accountName) 
    {
        this.accountName = accountName;
    }

    public void setNumberOfArticle(int numberOfArticle) 
    {
        this.numberOfArticle = numberOfArticle;
    }

    public String getAccountName() 
    {
        return accountName;
    }

    public int getNumberOfArticle() 
    {
        return numberOfArticle;
    }
    
}
