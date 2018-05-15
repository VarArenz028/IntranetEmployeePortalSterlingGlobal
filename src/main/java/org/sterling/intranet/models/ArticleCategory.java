
package org.sterling.intranet.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "articleCategory")
public class ArticleCategory
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryId;
    @Size(max = 30, min = 5)
    @Column(name = "categoryName", length = 50, unique = true)
    private String categoryName;
    
    @Column(name = "campaign", length = 50, nullable = false)
    private String campaign;

    public void setCategoryId(int categoryId) 
    {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public void setCampaign(String campaign) 
    {
        this.campaign = campaign;
    }
    
    public int getCategoryId()
    {
        return categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public String getCampaign()
    {
        return campaign;
    }
    
}
