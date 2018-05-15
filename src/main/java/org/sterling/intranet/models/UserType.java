package org.sterling.intranet.models;

public enum UserType 
{
    USER("Agent"),
    SA("System Admin"),
    ADMIN("Campaign Admin");

    String userProfileType;
    private UserType(String userProfileType)
    {
        this.userProfileType = userProfileType;
    }

    public String getUserProfileType() 
    {
        return userProfileType;
    }
	
	
    
}
