package org.sterling.intranet.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Var Arenz G. Villarino
 */
public class DateStamp 
{
    private LocalDateTime localDateTime;
    private Date dateNow;
    private String dateString;

    public LocalDateTime getLocalDateTime() throws ParseException 
    {
        
        Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aaa");  
        String stringDate = formatter.format(ts);
        Date dateStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa").parse(stringDate); 
        
        DateTime dateTime = new DateTime(dateStamp);
        
        LocalDateTime localDateTime = dateTime.toLocalDateTime();
        
        this.localDateTime = localDateTime;
        
        return localDateTime;
    }

    public Date getDate() throws ParseException
    {
        
        Date date = new Date();  
        Timestamp ts = new Timestamp(date.getTime());  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");  
        String stringDate = formatter.format(ts);
        Date dateStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa").parse(stringDate); 
        this.dateNow = dateStamp;
        return dateNow;
    }

    public String getDateString() 
    {
        Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");  
        String stringDate = formatter.format(ts);
        this.dateString = stringDate;
        return dateString;
    }
    
    
}
