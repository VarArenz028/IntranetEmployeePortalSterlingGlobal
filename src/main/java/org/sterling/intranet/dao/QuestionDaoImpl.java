package org.sterling.intranet.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.QuestionDao;
import org.sterling.intranet.models.Question;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("questionDao")
public class QuestionDaoImpl extends AbstractDao<Integer, Question> implements QuestionDao
{

    @Override
    public List<Question> listAllQuestionFeedData() 
    {
        Criteria criteria = createCriteria()
        .setProjection(Projections.projectionList()
        .add(Projections.property("questionId"), "questionId")
        .add(Projections.property("title"), "title")
        .add(Projections.property("type"), "type")
        .add(Projections.property("dateCreated"), "dateCreated")
        .add(Projections.property("dateString"), "dateString"))
            .setResultTransformer(Transformers.aliasToBean(Question.class));
        
        List<Question> list = criteria.list();
        
        return list;
    }
    
    @Override
    public List<Question> selectAllQuestionByCampaignName(String campaign) 
    {
        Query query = getSession().createQuery("from Question where campaign = :campaign");
        query.setParameter("campaign", campaign);
        
        List<Question> list = query.list();
        
        return list;
    }
    
    @Override
    public List<Question> selectAllQuestionByCampaignNameAndState(String campaign, String state) 
    {
        Query query = getSession().createQuery("from Question where campaign = :campaign and state = :state");
        query.setParameter("campaign", campaign);
        query.setParameter("state", state);
        
        List<Question> list = query.list();
        
        return list;
    }
    
    @Override
    public List<Question> selectAllQuestionByEmpIdAndCampaignName(Integer empId, String campaign)
    {
        Query query = getSession().createQuery("from Question where employeeDetailsId = :empDetailsId and campaign = :campaign");
        query.setParameter("empDetailsId", empId);
        query.setParameter("campaign", campaign);
        
        List<Question> list = query.list();
        return list;
    }
    
    @Override
    public List<Question> selectAllQuestionByEmpIdStateAndCampaignName(Integer empId, String state, String campaign) 
    {
        Query query = getSession().createQuery("from Question where employeeDetailsId = :empDetailsId and campaign = :campaign and state = :state");
        query.setParameter("empDetailsId", empId);
        query.setParameter("campaign", campaign);
        query.setParameter("state", state);
        
        List<Question> list = query.list();
        return list;
    }
    
    @Override
    public List<Question> listMyQuestions(Integer userId)
    {
        Query query = getSession().createQuery("from Question where userId = :userId");
        query.setParameter("userId", userId);
        
        List<Question> list = query.list();
        
        return list;
    }
    
    @Override
    public Question selectQuestionById(String id) 
    {
        Criteria criteria = getSession().createCriteria(Question.class)
                          .add(Restrictions.eq("questionId", id));
        
        Question question = (Question) criteria.uniqueResult();
        
        return question;
    }
    
    @Override
    public Question selectQuestionByIdAndCampaign(String id, String campaign)
    {
        Query query = getSession().createQuery("from Question where questionId = :questionId and campaign = :campaign");
        query.setParameter("questionId", id);
        query.setParameter("campaign", campaign);
        
        Question question = (Question) query.uniqueResult();
        
        return question;
    }
    
    @Override
    public void saveQuestion(Question question)
    {
        save(question);
    }

    @Override
    public List<Question> selectAllSpecificAttrbsQuestionByCampaignName(String campaign) 
    {
        Criteria criteria = getSession().createCriteria(Question.class)
            .add(Restrictions.eq("campaign", campaign))
                .setProjection(Projections.projectionList()
                .add(Projections.property("questionId"), "questionId")
                .add(Projections.property("title"), "title")
                .add(Projections.property("dateCreated"), "dateCreated"))
                .setResultTransformer(Transformers.aliasToBean(Question.class));
        
        List<Question> list = criteria.list();
        return list;
    }

    @Override
    public Integer selectEmpIdByQuestionId(String questionId) 
    {
        Query query = getSession().createQuery("Select q.employeeDetails.empDetailsId from Question q where q.questionId = :questionId");
        query.setParameter("questionId", questionId);
        
        Integer empDetailsIs = (Integer) query.uniqueResult();
        
        return empDetailsIs;
    }

    @Override
    public Map<String, Object> inactiveQuestionByQuestionIdStateAndCampaign(String questionId, String state, String campaign) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        Query query = getSession().createQuery("update Question q set q.state = :state where q.questionId = :questionId and q.campaign = :campaign");
        query.setParameter("questionId", questionId);
        query.setParameter("state", state);
        query.setParameter("campaign", campaign);
        int update = query.executeUpdate();
        
        if(update != 0)
        {
            response.put("questionInactive", HttpStatus.OK);
        }
        else
        {
            response.put("error", HttpStatus.NOT_MODIFIED);
        }
        
        
        return response;
    }

    
    
}
