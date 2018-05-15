package org.sterling.intranet.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.EventsDao;
import org.sterling.intranet.interfaces.EventsServices;
import org.sterling.intranet.models.Event;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("eventsServices")
public class EventsServicesImpl implements EventsServices
{
    
    
    @Autowired
    private EventsDao eventsDao;

    @Transactional(readOnly = true)
    @Override
    public Event selectEventByEventIdAndCapaign(String eventId, String campaign) 
    {
        return eventsDao.selectEventByEventIdAndCapaign(eventId, campaign);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Event> selectAllEventByCampaignName(String campaign) 
    {
        return eventsDao.selectAllEventByCampaignName(campaign);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveEvent(Event event) 
    {
        eventsDao.saveEvent(event);
    }

}
