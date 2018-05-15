package org.sterling.intranet.exceptions;

// this is the model for generating error response
public class ErrorResponse 
{
    private int errorCode;
    private String errorMessage;

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() 
    {
        return errorCode;
    }

    public String getErrorMessage() 
    {
        return errorMessage;
    }
    
    
}
