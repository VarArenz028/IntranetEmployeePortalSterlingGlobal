package org.sterling.intranet.exceptions;

public class LoginAuthenticationException extends Exception
{
    private String message;
    public LoginAuthenticationException() {
    }

    public LoginAuthenticationException(String message) 
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
