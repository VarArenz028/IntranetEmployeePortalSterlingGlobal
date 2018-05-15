package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.model.json.NewsFeedObject;

/**
 *
 * @author Var Arenz Villarino
 */
public interface CampaignNewsFeedServices 
{
    List<NewsFeedObject> fetchAllCampaignNewsFeed(String campaign); 
}
