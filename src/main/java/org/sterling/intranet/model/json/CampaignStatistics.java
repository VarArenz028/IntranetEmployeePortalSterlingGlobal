package org.sterling.intranet.model.json;

/**
 *
 * @author Var Arenz Villarino
 */
public class CampaignStatistics 
{
    private String campaignName;
    private int numberOfUser;

    public void setCampaignName(String campaignName)
    {
        this.campaignName = campaignName;
    }

    public void setNumberOfUser(int numberOfUser) 
    {
        this.numberOfUser = numberOfUser;
    }

    public String getCampaignName() 
    {
        return campaignName;
    }

    public int getNumberOfUser() 
    {
        return numberOfUser;
    }
    
}
