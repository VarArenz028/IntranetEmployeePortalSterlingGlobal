package org.sterling.intranet.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.ArticleWritingDao;
import org.sterling.intranet.interfaces.PageViewDao;
import org.sterling.intranet.interfaces.PageViewServices;
import org.sterling.intranet.models.Article;
import org.sterling.intranet.models.PageStatistic;
import org.sterling.intranet.models.PageView;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("pageViewServices")
public class PageViewServicesImpl implements PageViewServices
{

    @Autowired
    private PageViewDao pageViewDao;
    
    @Autowired
    private ArticleWritingDao articleWritingDao;
    
    @Transactional(readOnly = false)
    @Override
    public void saveView(PageView pageView) 
    {
        pageViewDao.saveView(pageView);
    }
    
    @Transactional(readOnly = true)
    @Override
    public PageView selectPageViewByArticleId(String articleId)
    {
        return pageViewDao.selectPageViewByArticleId(articleId);
    }
    @Override
    public List<PageStatistic> articleMostViewStatistics(String campaign) 
    {
        List<PageView> pageViews = pageViewDao.selectAllArticleWhichMostViewed(campaign);
        List<PageStatistic> pageStatistics = new ArrayList();
        for(PageView pageView : pageViews)
        {
            Article article = articleWritingDao.selectTitleByArticleId(pageView.getArticle().getArticleId());
            PageStatistic pageStatistic = new PageStatistic();
            pageStatistic.setArticleId(pageView.getArticle().getArticleId());
            pageStatistic.setArticleName(article.getTitle());
            pageStatistic.setArticleStatistics(pageView.getView());
            
            pageStatistics.add(pageStatistic);
        }
        
        return pageStatistics;
    }
    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updatePageViewOfArticle(String articleId) 
    {
        Map<String, Object> response = new HashMap<String, Object>();
        PageView pageView = pageViewDao.selectPageViewByArticleId(articleId);
        long newViewCount = pageView.getView() + 1;
        
        pageView.setView((int) newViewCount);
        
        response.put("viewCounted", HttpStatus.OK);
        
        return response;
    }

    
    
}
