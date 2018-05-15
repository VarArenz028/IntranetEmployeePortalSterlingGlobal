package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.EventsDao;
import org.sterling.intranet.models.Event;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("eventsDao")
public class EventsDaoImpl extends AbstractDao<Integer, Event> implements EventsDao
{

    @Override
    public Event selectEventByEventIdAndCapaign(String eventId, String campaign)
    {
        Query query = getSession().createQuery("from Event where campaign = :campaign and eventId = :eventId");
        query.setParameter("campaign", campaign);
        query.setParameter("eventId", eventId);
        
        Event event = (Event) query.uniqueResult();
        
        return event;
    }
    
    @Override
    public List<Event> selectAllEventByCampaignName(String campaign)
    {
        Query query = getSession().createQuery("from Event where campaign = :campaign");
        query.setParameter("campaign", campaign);
        
        List<Event> list = query.list();
        
        return list;
    }
    
    @Override
    public void saveEvent(Event event) 
    {
        save(event);
    }

    @Override
    public List<Event> selectSpecificAttbsByCampaignName(String campaign) 
    {
        Criteria criteria = getSession().createCriteria(Event.class)
            .add(Restrictions.eq("campaign", campaign))
                .setProjection(Projections.projectionList()
                .add(Projections.property("eventId"), "eventId")
                .add(Projections.property("eventName"), "eventName")
                .add(Projections.property("eventDate"), "eventDate")
                .add(Projections.property("location"), "location")
                .add(Projections.property("details"), "details")
                .add(Projections.property("base64"), "base64")
                .add(Projections.property("fileType"), "fileType")
                .add(Projections.property("dateCreated"), "dateCreated"))
                .setResultTransformer(Transformers.aliasToBean(Event.class));
        
        List<Event> list = criteria.list();
        return list;
    }

    @Override
    public Integer selectEmpIdByEventId(String eventId)
    {
        Query query = getSession().createQuery("Select e.employeeDetails.empDetailsId from Event e where e.eventId = :eventId");
        query.setParameter("eventId", eventId);
        
        Integer empDetailsIs = (Integer) query.uniqueResult();
        
        return empDetailsIs;
    }

}
