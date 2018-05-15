package org.sterling.intranet.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "account")
public class Account
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "accountId")
    private int accountId;
    
    @Column(name = "accountName", length = 30)
    private String accountName;
    
    @Column(name = "state", length = 30)
    private String state = "Active";
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
    
    public void setAccountId(int accountId) 
    {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) 
    {
        this.accountName = accountName;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setUser(User user) 
    {
        this.user = user;
    }
    
    public int getAccountId()
    {
        return accountId;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public String getState()
    {
        return state;
    }

    public User getUser()
    {
        return user;
    }
    
    
}
