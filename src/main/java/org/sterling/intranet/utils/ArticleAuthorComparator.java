package org.sterling.intranet.utils;

import java.util.Comparator;
import org.sterling.intranet.models.DashboardArticleAuthor;

/**
 *
 * @author Var Arenz G. Villarino
 */
public class ArticleAuthorComparator implements Comparator<DashboardArticleAuthor>
{

    @Override
    public int compare(DashboardArticleAuthor o1, DashboardArticleAuthor o2) 
    {
        return o1.getNumberOfArticle() - o2.getNumberOfArticle();
    }
    
}
