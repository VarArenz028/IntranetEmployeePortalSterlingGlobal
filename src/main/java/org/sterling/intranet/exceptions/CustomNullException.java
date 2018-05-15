package org.sterling.intranet.exceptions;

/**
 *
 * @author Var Arenz Villarino
 */
public class CustomNullException extends Exception
{
    String message;

    public CustomNullException()
    {
        super("No data");
    }

    public CustomNullException(String message)
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
