package org.sterling.intranet.agent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Var Arenz G. Villarino
 */
@Controller
public class AgentTemplatesController
{
//    @RequestMapping(value = "userCampaigns")
//    public String agentMyCampaigns()
//    {
//        return "agentTemplates/userCampaigns";
//    }
    @RequestMapping(value = "agentNewsFeed")
    public String agentNewsFeed()
    {
        return "agentTemplates/newsFeed";
    }
    @RequestMapping(value = "agentSearch")
    public String searchTemplate()
    {
        return "agentTemplates/agentSearch";
    }
    @RequestMapping(value = "agentDashboard")
    public String agentDashboard()
    {
        return "agentTemplates/dashboard";
    }
    @RequestMapping(value = "agentShowNewsFeedArticle")
    public String agentNewsFeedArticle()
    {
        return "agentNewsFeedTemplates/showNewsFeedArticle";
    }
    @RequestMapping(value = "agentShowNewsFeedQuestion")
    public String agentNewsFeedQuestion()
    {
        return "agentNewsFeedTemplates/showNewsFeedQuestion";
    }
    @RequestMapping(value = "agent-news-feed-article")
    public String agentNewsFeedArticlesTemplate()
    {
        return "agentTemplates/agentArticleTemplates/news-feed-article";
    }
    @RequestMapping(value = "agentAllArticles")
    public String agentAllArticlesTemplate()
    {
        return "agentTemplates/agentArticleTemplates/allArticles";
    }
    @RequestMapping(value = "updateArticle")
    public String agentUpdateArticleTemplate()
    {
        return "agentTemplates/agentArticleTemplates/updateArticle";
    }
    @RequestMapping(value = "agent-new-articles")
    public String agentNewArticlesTemplate()
    {
        return "agentTemplates/agentArticleTemplates/new-articles";
    }
    @RequestMapping(value = "agent-my-articles")
    public String agentArticleWritingTemplate()
    {
        return "agentTemplates/agentArticleTemplates/my-articles";
    }
    @RequestMapping(value = "agentArticle")
    public String agentMyArticlesTemplate()
    {
        return "agentTemplates/agentArticleTemplates/article";
    }
    @RequestMapping(value = "agentShowArticle")
    public String agentShowMyArticleTemplate()
    {
        return "agentTemplates/agentArticleTemplates/showArticle";
    }
    @RequestMapping(value = "agentArticleCategories")
    public String agentArticleCategoriesTemplate()
    {
        return "agentTemplates/articleTemplates/artcleCategories";
    }
    @RequestMapping(value = "agentMyDraftArticles")
    public String agentMyDraftArticleTemplate()
    {
        return "agentTemplates/agentArticleTemplates/myDraftArticles";
    }
    @RequestMapping(value = "agentAllNews")
    public String agentAllNews()
    {
        return "agentTemplates/agentNewsTemplate/allNews";
    }
    @RequestMapping(value = "agentShowNews")
    public String agentShowOneNews()
    {
        return "agentTemplates/agentNewsTemplate/showNews";
    }
    @RequestMapping(value = "/agent-question-feed")
    public String agentQuestionFeedTemplate()
    {
        return "agentTemplates/agentQuestionsTemplates/question-feed";
    }
    @RequestMapping(value = "/agentQuestions")
    public String agentAllQuestionsTemplate()
    {
        return "agentTemplates/agentQuestionsTemplates/allQuestion";
    }
    @RequestMapping(value = "/agent-new-questions")
    public String agentNewQuestionsTemplate()
    {
        return "agentTemplates/agentQuestionsTemplates/new-questions";
    }
    @RequestMapping(value = "/agent-my-questions")
    public String agentMyQuestionsTemplate()
    {
        return "agentTemplates/agentQuestionsTemplates/my-questions";
    }
    @RequestMapping(value = "agentPeople")
    public String agentPeopleTemplate()
    {
        return "agentTemplates/people";
    }
    @RequestMapping(value = "agent-my-profile")
    public String agentProfileTemplate()
    {
        return "agentTemplates/my-profile";
    }

}
