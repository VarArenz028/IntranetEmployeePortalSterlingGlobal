package org.sterling.intranet.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.QuestionAnswerDao;
import org.sterling.intranet.models.QuestionAnswer;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Repository("questionAnswerDao")
public class QuestionAnswerDaoImpl extends AbstractDao<Integer, QuestionAnswer> implements QuestionAnswerDao
{

    
    @Override
    public boolean selectAnswersByEmpIdAndQuestionId(Integer empId, String questionId) 
    {
        boolean result;
        
        Query query = getSession().createQuery("from QuestionAnswer where empDetailsId = :empDetailsId and questionId = :questionId");
        query.setParameter("empDetailsId", empId);
        query.setParameter("questionId", questionId);
        
        QuestionAnswer questionAnswer = (QuestionAnswer) query.uniqueResult();
        
        if(questionAnswer != null)
        {
            result = true;
        }
        else
        {
            result = false;
        }
        
        return result;
    }
    
    @Override
    public QuestionAnswer selectQuestionAnswerByQuestionId(Integer questionAnswerId) 
    {
        Criteria criteria = getSession().createCriteria(QuestionAnswer.class).add(Restrictions.eq("questionAnswerId", questionAnswerId))
                        .setProjection(Projections.projectionList()
                        .add(Projections.property("questionAnswerId"), "questionAnswerId")
                        .add(Projections.property("dateAnswered"), "dateAnswered"))
                        .setResultTransformer(Transformers.aliasToBean(QuestionAnswer.class));
        QuestionAnswer questionAnswer = (QuestionAnswer) criteria.uniqueResult();
        return questionAnswer;
    }
    
    @Override
    public List<QuestionAnswer> selectAnswersByQuestionId(String id) 
    {
//        Query query = getSession().createQuery("from QuestionAnswer where questionId = :questionId");
//        query.setParameter("questionId", id);
        
//        List<QuestionAnswer> questionAnswer = query.list();
        
        Criteria criteria = getSession().createCriteria(QuestionAnswer.class).add(Restrictions.eq("question.questionId", id))
                        .setProjection(Projections.projectionList()
                        .add(Projections.property("questionAnswerId"), "questionAnswerId")
                        .add(Projections.property("answer"), "answer")
                        .add(Projections.property("dateAnswered"), "dateAnswered")
                        .add(Projections.property("employeeDetails"), "employeeDetails"))
                        .setResultTransformer(Transformers.aliasToBean(QuestionAnswer.class));
        List<QuestionAnswer> questionAnswer = criteria.list();
        return questionAnswer;
    }
    
    @Override
    public void saveAnswer(QuestionAnswer questionAnswer) 
    {
        save(questionAnswer);
    }

    @Override
    public Long numberOfAnswerByQuestionId(String questionId) 
    {
        Criteria criteria = getSession().createCriteria(QuestionAnswer.class).add(Restrictions.eq("question.questionId", questionId))
                        .setProjection(Projections.rowCount());
        
        Long numOfAnswer = (Long) criteria.uniqueResult();
        
        return numOfAnswer;
    }

    

    
    
}
