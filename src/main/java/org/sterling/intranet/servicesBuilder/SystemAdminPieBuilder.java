package org.sterling.intranet.servicesBuilder;

public class SystemAdminPieBuilder 
{
    private String campaignname;
    private int sizeOfUser;

    public void setCampaignname(String campaignname) 
    {
        this.campaignname = campaignname;
    }

    public void setSizeOfUser(int sizeOfUser) 
    {
        this.sizeOfUser = sizeOfUser;
    }

    public String getCampaignname() 
    {
        return campaignname;
    }

    public int getSizeOfUser() 
    {
        return sizeOfUser;
    }
    
}
