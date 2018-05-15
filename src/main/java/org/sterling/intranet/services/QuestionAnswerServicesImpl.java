package org.sterling.intranet.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.QuestionAnswerDao;
import org.sterling.intranet.interfaces.QuestionAnswerServices;
import org.sterling.intranet.models.QuestionAnswer;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("questionAnswerServices")
public class QuestionAnswerServicesImpl implements QuestionAnswerServices
{

    @Autowired
    private QuestionAnswerDao questionAnswerDao;
    
    @Transactional(readOnly = true)
    @Override
    public QuestionAnswer selectQuestionAnswerByQuestionId(Integer questionAnswerId)
    {
        return questionAnswerDao.selectQuestionAnswerByQuestionId(questionAnswerId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean selectAnswersByEmpIdAndQuestionId(Integer empId, String questionId)
    {
        return questionAnswerDao.selectAnswersByEmpIdAndQuestionId(empId, questionId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<QuestionAnswer> selectAnswersByQuestionId(String id) 
    {
        return questionAnswerDao.selectAnswersByQuestionId(id);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveAnswer(QuestionAnswer questionAnswer) 
    {
        questionAnswerDao.saveAnswer(questionAnswer);
    }

    
    
}
