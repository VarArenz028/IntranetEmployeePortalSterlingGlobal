package org.sterling.intranet.models;

import java.util.List;

public class UserCampaignWrapper 
{
    private List<User> users;
    private List<Campaign> campaigns;

    public void setUsers(List<User> users) 
    {
        this.users = users;
    }

    public void setCampaigns(List<Campaign> campaigns) 
    {
        this.campaigns = campaigns;
    }

    public List<User> getUsers() 
    {
        return users;
    }

    public List<Campaign> getCampaigns()
    {
        return campaigns;
    }
    
    
}
