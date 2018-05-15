package org.sterling.intranet.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.ArticleWritingDao;
import org.sterling.intranet.interfaces.NewsFeedServices;
import org.sterling.intranet.interfaces.QuestionDao;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.NewsFeed;
import org.sterling.intranet.models.Question;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("newsFeedServices")
public class NewsFeedServicesImpl implements NewsFeedServices
{

    @Autowired
    private ArticleWritingDao articleWritingDao;
    
    @Autowired
    private QuestionDao questionDao;
    
    @Transactional(readOnly = true)
    @Override
    public List<NewsFeed> getAllNewsFeed(String campaign, String state) 
    {
        List<NewsFeed> newsFeed = new ArrayList<NewsFeed>();
        
        for(Article article : articleWritingDao.selectArticleByCampaignAndState(campaign, state))
        {
            newsFeed.add(new NewsFeed(article.getArticleId(), article.getTitle(),
                              article.getCategory(), article.getType(), article.getDateString(), article.getDateCreated()));
        }
        
        for(Question question : questionDao.selectAllQuestionByCampaignName(campaign))
        {
            newsFeed.add(new NewsFeed(question.getQuestionId(), question.getTitle(), null,
                              question.getType(), question.getDateString(), question.getDateCreated()));
        }
        
        return newsFeed;
    }
    
}
