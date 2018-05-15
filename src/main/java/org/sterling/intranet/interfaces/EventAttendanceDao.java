package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.EventAttendance;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface EventAttendanceDao 
{
    
    EventAttendance selectEventAttendanceByEmpIdAndEventId(String eventId, Integer empId);
    
    EventAttendance selectStateInEventByEmpDetailsIdAndEventId(Integer empDetailsId, String eventId);
    
    List<EventAttendance> selectAllAttendanceByEventId(String eventId);
    
    void saveEventAttendance(EventAttendance eventAttendance);
}
