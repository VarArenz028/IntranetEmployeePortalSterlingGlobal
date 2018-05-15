package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "user")
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private int userId;
    
    @Column(name = "username", nullable = false, length = 30, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false, length = 20, unique = true)
    private String password;
    
    @Column(name = "role", nullable = false, length = 30, unique = false)
    private String role;
    
    @Column(name = "state", nullable = false, length = 10, unique = false)
    private String state = State.ACTIVE.getState();
    
    @DateTimeFormat(pattern = "dd/MM/yyyy") 
    @Column(name = "dateRegistered", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dateRegistered = LocalDate.now();
    
    @OneToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_campaign", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "campaignId"))
    @Fetch(value = FetchMode.SUBSELECT)
//    @JsonIgnore
    private Set<Account> accounts;

    
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private EmployeeDetails employeeDetails;
    
    public void setUserId(int userId) 
    {
        this.userId = userId;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setRole(String role) 
    {
        this.role = role;
    }

    public void setState(String state) 
    {
        this.state = state;
    }

    public void setDateRegistered(LocalDate dateRegistered) 
    {
        this.dateRegistered = dateRegistered;
    }

    public void setAccounts(Set<Account> accounts) 
    {
        this.accounts = accounts;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails)
    {
        this.employeeDetails = employeeDetails;
    }

    public int getUserId() 
    {
        return userId;
    }

    public String getUsername() 
    {
        return username;
    }

    public String getPassword() 
    {
        return password;
    }

    public String getRole() 
    {
        return role;
    }

    public String getState() 
    {
        return state;
    }

    public LocalDate getDateRegistered()
    {
        return dateRegistered;
    }

    public Set<Account> getAccounts()
    {
        return accounts;
    }
    
//    public List<Article> getArticles() 
//    {
//        return articles;
//    }

//    public List<UserAvatar> getUserAvatar() 
//    {
//        return userAvatar;
//    }
    
    public EmployeeDetails getEmployeeDetails() 
    {
        return employeeDetails;
    }
    
}
