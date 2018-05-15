package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.QuestionAnswer;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface QuestionAnswerServices 
{
    
    boolean selectAnswersByEmpIdAndQuestionId(Integer empId, String questionId);
    
    QuestionAnswer selectQuestionAnswerByQuestionId(Integer questionAnswerId);
    
    List<QuestionAnswer> selectAnswersByQuestionId(String id);
    
    void saveAnswer(QuestionAnswer questionAnswer);
}
