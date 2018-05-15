package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.EventAttendanceDao;
import org.sterling.intranet.models.EventAttendance;

/**
 *
 * @author Var Arenz Villarino
 */
@Repository("eventAttendanceDao")
public class EventAttendanceDaoImpl extends AbstractDao<Integer, EventAttendance> implements EventAttendanceDao
{

    @Override
    public EventAttendance selectEventAttendanceByEmpIdAndEventId(String eventId, Integer empId) 
    {
        Query query = getSession().createQuery("from EventAttendance where eventId = :eventId and empDetailsId = :empId");
        query.setParameter("eventId", eventId);
        query.setParameter("empId", empId);
        
        EventAttendance eventAttendance = (EventAttendance) query.uniqueResult();
        
        return eventAttendance;
    }
    
    @Override
    public List<EventAttendance> selectAllAttendanceByEventId(String eventId) 
    {
        Query query = getSession().createQuery("from EventAttendance where eventId = :eventId");
        query.setParameter("eventId", eventId);
        
        List<EventAttendance> list = query.list();
        
        return list;
    }
    
    @Override
    public void saveEventAttendance(EventAttendance eventAttendance) 
    {
        save(eventAttendance);
    }

    @Override
    public EventAttendance selectStateInEventByEmpDetailsIdAndEventId(Integer empDetailsId, String eventId)
    {
        Criteria criteria = getSession().createCriteria(EventAttendance.class)
            .add(Restrictions.eq("employeeDetails.empDetailsId", empDetailsId))
            .add(Restrictions.and(Restrictions.eq("event.eventId", eventId)))
                .setProjection(Projections.projectionList()
                .add(Projections.property("state"), "state"))
                .setResultTransformer(Transformers.aliasToBean(EventAttendance.class));
        criteria.setMaxResults(1);
        EventAttendance eventAttendance = (EventAttendance) criteria.uniqueResult();
        
        return eventAttendance;
    }

    
    
}
