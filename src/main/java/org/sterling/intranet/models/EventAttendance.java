package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Var Arenz Villarino
 */
@Entity
@Table(name = "eventAttendance")
public class EventAttendance 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "eventAttendanceId")
    private int eventAttendanceId;
    
    @Column(name = "state")
    private String state;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empDetailsId")
    @JsonIgnore
    private EmployeeDetails employeeDetails;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eventId")
    @JsonIgnore
    private Event event;
    
    public void setEventAttendanceId(int eventAttendanceId) 
    {
        this.eventAttendanceId = eventAttendanceId;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails) 
    {
        this.employeeDetails = employeeDetails;
    }

    public void setEvent(Event event) 
    {
        this.event = event;
    }
    
    public int getEventAttendanceId()
    {
        return eventAttendanceId;
    }

    public String getState() 
    {
        return state;
    }

    public EmployeeDetails getEmployeeDetails()
    {
        return employeeDetails;
    }

    public Event getEvent() 
    {
        return event;
    }
    
}
