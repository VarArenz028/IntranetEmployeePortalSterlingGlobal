package org.sterling.intranet.models;

public class UserRegistration 
{
    private String username;
    private String password;
    private String role;
    private String state;
    private Integer campaignsId;

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

    public void setCampaignsId(Integer campaignsId)
    {
        this.campaignsId = campaignsId;
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

    public Integer getCampaignsId() 
    {
        return campaignsId;
    }
    
}
