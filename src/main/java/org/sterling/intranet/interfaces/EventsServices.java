package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.Event;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface EventsServices
{
    
    Event selectEventByEventIdAndCapaign(String eventId, String campaign);
    
    List<Event> selectAllEventByCampaignName(String campaign);
    
    void saveEvent(Event event);
    
}
