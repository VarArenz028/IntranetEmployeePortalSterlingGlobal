package org.sterling.intranet.exceptions;

public class DataExistException extends Exception
{
    private String message;

    public DataExistException()
    {
        super("Data already exist");
    }

    public DataExistException(String message) 
    {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() 
    {
        return message;
    }
    
}
