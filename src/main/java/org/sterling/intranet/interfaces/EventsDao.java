package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.Event;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface EventsDao 
{
    
    Integer selectEmpIdByEventId(String eventId);
    
    Event selectEventByEventIdAndCapaign(String eventId, String campaign);
    
    List<Event> selectAllEventByCampaignName(String campaign);
    
    List<Event> selectSpecificAttbsByCampaignName(String campaign);
    
    void saveEvent(Event event);
}
