package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.Question;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface QuestionServices
{
    
    List<Question> listAllQuestionFeedData();
    
    List<Question> listMyQuestions(Integer userId);
    
    List<Question> selectAllQuestionByCampaignName(String campaign);
    
    List<Question> selectAllQuestionByCampaignNameAndState(String campaign, String state);
    
    List<Question> selectAllSpecificAttrbsQuestionByCampaignName(String campaign);
    
    List<Question> selectAllQuestionByEmpIdAndCampaignName(Integer empId, String campaign);
    
    List<Question> selectAllQuestionByEmpIdStateAndCampaignName(Integer empId, String state, String campaign);
    
    Question selectQuestionById(String id);
    
    Question selectQuestionByIdAndCampaign(String id, String campaign);
    
    void saveQuestion(Question question);
    
    Map<String, Object> inactiveQuestionByQuestionIdStateAndCampaign(String questionId, String state, String campaign);
}
