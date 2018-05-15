package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.model.json.SearchModel;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface SearchDao 
{
    void index() throws Exception;
    
    List<SearchModel> search(String search);
}
