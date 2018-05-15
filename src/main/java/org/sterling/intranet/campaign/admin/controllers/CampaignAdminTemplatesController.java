package org.sterling.intranet.campaign.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Var Arenz G. Villarino
 */
@Controller
public class CampaignAdminTemplatesController
{
    @RequestMapping(value = "userCampaigns")
    public String myCampaigns()
    {
        return "campaignAdminTemplates/userCampaigns";
    }
    @RequestMapping(value = "search")
    public String searchTemplate()
    {
        return "campaignAdminTemplates/search";
    }
    @RequestMapping(value = "newsFeed")
    public String newsFeed()
    {
        return "campaignAdminTemplates/newsFeed";
    }
    @RequestMapping(value = "dashboard")
    public String dashboard()
    {
        return "campaignAdminTemplates/dashboard";
    }
    @RequestMapping(value = "showNewsFeedArticle")
    public String newsFeedArticle()
    {
        return "campaignAdminNewsFeedTemplates/showNewsFeedArticle";
    }
    @RequestMapping(value = "showNewsFeedQuestion")
    public String newsFeedQuestion()
    {
        return "campaignAdminNewsFeedTemplates/showNewsFeedQuestion";
    }
    @RequestMapping(value = "news-feed-article")
    public String newsFeedArticlesTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/news-feed-article";
    }
    @RequestMapping(value = "allArticles")
    public String allArticlesTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/allArticles";
    }
    @RequestMapping(value = "manageCategories")
    public String manageCategories()
    {
        return "campaignAdminTemplates/articleTemplates/manageCategories";
    }
    @RequestMapping(value = "new-articles")
    public String newArticlesTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/new-articles";
    }
    @RequestMapping(value = "my-articles")
    public String articleWritingTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/my-articles";
    }
    @RequestMapping(value = "article")
    public String myArticlesTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/article";
    }
    @RequestMapping(value = "showArticle")
    public String showMyArticleTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/showArticle";
    }
    @RequestMapping(value = "articleCategories")
    public String articleCategoriesTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/artcleCategories";
    }
    @RequestMapping(value = "myDraftArticles")
    public String myDraftArticleTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/myDraftArticles";
    }
    @RequestMapping(value = "viewPendingArticle")
    public String viewPendingArticleTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/viewPendingArticle";
    }
    @RequestMapping(value = "pendingArticles")
    public String pendingArticleTemplate()
    {
        return "campaignAdminTemplates/articleTemplates/pendingArticles";
    }
    @RequestMapping(value = "allNews")
    public String allNewsTemplate()
    {
        return "campaignAdminTemplates/newsTemplates/allNews";
    }
    @RequestMapping(value = "createNews")
    public String createNewsTemplate()
    {
        return "campaignAdminTemplates/newsTemplates/createNews";
    }
    @RequestMapping(value = "showNews")
    public String showNewsTemplate()
    {
        return "campaignAdminTemplates/newsTemplates/showNews";
    }
    @RequestMapping(value = "draftNews")
    public String draftNewsTemplate()
    {
        return "campaignAdminTemplates/newsTemplates/draftNews";
    }
    @RequestMapping(value = "events")
    public String eventsTemplate()
    {
        return "campaignAdminTemplates/events";
    }
    @RequestMapping(value = "/question-feed")
    public String questionFeedTemplate()
    {
        return "campaignAdminTemplates/questionsTemplates/question-feed";
    }
    @RequestMapping(value = "/questions")
    public String allQuestionsTemplate()
    {
        return "campaignAdminTemplates/questionsTemplates/allQuestion";
    }
    @RequestMapping(value = "/new-questions")
    public String newQuestionsTemplate()
    {
        return "campaignAdminTemplates/questionsTemplates/new-questions";
    }
    @RequestMapping(value = "/my-questions")
    public String myQuestionsTemplate()
    {
        return "campaignAdminTemplates/questionsTemplates/my-questions";
    }
    @RequestMapping(value = "people")
    public String peopleTemplate()
    {
        return "campaignAdminTemplates/people";
    }
    @RequestMapping(value = "my-profile")
    public String profileTemplate()
    {
        return "campaignAdminTemplates/my-profile";
    }

}
