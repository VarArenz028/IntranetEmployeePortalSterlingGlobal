package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.models.News;
import org.sterling.intranet.models.NewsFeed;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface NewsFeedServices 
{
    List<NewsFeed> getAllNewsFeed(String campaign, String state);
        
}
