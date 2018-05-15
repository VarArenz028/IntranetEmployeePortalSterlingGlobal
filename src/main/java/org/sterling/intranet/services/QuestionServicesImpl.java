package org.sterling.intranet.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.QuestionDao;
import org.sterling.intranet.interfaces.QuestionServices;
import org.sterling.intranet.models.Question;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("questionServices")
public class QuestionServicesImpl implements QuestionServices
{

    @Autowired
    private QuestionDao questionDao;
    
    @Transactional(readOnly = true)
    @Override
    public List<Question> listAllQuestionFeedData() 
    {
        return questionDao.listAllQuestionFeedData();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Question> listMyQuestions(Integer userId) 
    {
        return questionDao.listMyQuestions(userId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Question> selectAllQuestionByCampaignName(String campaign)
    {
        return questionDao.selectAllQuestionByCampaignName(campaign);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Question> selectAllQuestionByEmpIdAndCampaignName(Integer empId, String campaign) 
    {
        return questionDao.selectAllQuestionByEmpIdAndCampaignName(empId, campaign);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Question selectQuestionById(String id)
    {
        return questionDao.selectQuestionById(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Question selectQuestionByIdAndCampaign(String id, String campaign) 
    {
        return questionDao.selectQuestionByIdAndCampaign(id, campaign);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveQuestion(Question question) 
    {
        questionDao.saveQuestion(question);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Question> selectAllSpecificAttrbsQuestionByCampaignName(String campaign) 
    {
        return questionDao.selectAllSpecificAttrbsQuestionByCampaignName(campaign);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Question> selectAllQuestionByEmpIdStateAndCampaignName(Integer empId, String state, String campaign) 
    {
        return questionDao.selectAllQuestionByEmpIdStateAndCampaignName(empId, state, campaign);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Question> selectAllQuestionByCampaignNameAndState(String campaign, String state) 
    {
        return questionDao.selectAllQuestionByCampaignNameAndState(campaign, state);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> inactiveQuestionByQuestionIdStateAndCampaign(String questionId, String state, String campaign) 
    {
        return questionDao.inactiveQuestionByQuestionIdStateAndCampaign(questionId, state, campaign);
    }

    
    
}
