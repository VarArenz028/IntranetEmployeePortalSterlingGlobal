package org.sterling.intranet.utils;

import java.util.Comparator;
import org.sterling.intranet.models.PageStatistic;

/**
 *
 * @author Var Arenz Villarino
 */
public class PageStatisticsComparator implements Comparator<PageStatistic>
{

    @Override
    public int compare(PageStatistic o1, PageStatistic o2) 
    {
        return o1.getArticleStatistics() - o2.getArticleStatistics();
    }
    
}
