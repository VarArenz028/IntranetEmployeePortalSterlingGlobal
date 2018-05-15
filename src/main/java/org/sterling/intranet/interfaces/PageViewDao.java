package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.PageView;

/**
 *
 * @author Var Arenz Villarino
 */
public interface PageViewDao
{
    
    PageView selectPageViewByArticleId(String articleId);
    
    PageView selectOnePageViewByArticleId(String articleId);
    
    List<PageView> selectAllArticleWhichMostViewed(String campaign);
    
    List<PageView> selectTopFiveMostView(String articleId);
    
    void saveView(PageView pageView);
}
