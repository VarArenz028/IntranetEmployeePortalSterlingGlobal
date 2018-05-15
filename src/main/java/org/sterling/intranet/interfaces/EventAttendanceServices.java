package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.EventAttendance;

/**
 *
 * @author Var Arenz Villarino
 */
public interface EventAttendanceServices
{
    
    EventAttendance selectEventAttendanceByEmpIdAndEventId(String eventId, Integer empId);
    
    EventAttendance selectStateInEventByEmpDetailsIdAndEventId(Integer empDetailsId, String eventId);
    
    void saveEventAttendance(EventAttendance eventAttendance);
    
    List<EventAttendance> selectAllAttendanceByEventId(String eventId);
    
    Map<String, Object> updateAttendance(String eventId, Integer empid);

}
