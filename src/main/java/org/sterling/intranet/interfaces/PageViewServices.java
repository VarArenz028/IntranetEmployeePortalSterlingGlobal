package org.sterling.intranet.interfaces;

import java.util.List;
import java.util.Map;
import org.sterling.intranet.models.PageStatistic;
import org.sterling.intranet.models.PageView;

/**
 *
 * @author Var Arenz Villarino
 */
public interface PageViewServices 
{
    
    PageView selectPageViewByArticleId(String articleId);
    
    List<PageStatistic> articleMostViewStatistics(String campaign);
    
    void saveView(PageView pageView);
    
    Map<String, Object> updatePageViewOfArticle(String articleId);
}
