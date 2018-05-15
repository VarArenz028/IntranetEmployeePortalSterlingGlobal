package org.sterling.intranet.interfaces;

import java.util.List;
import org.sterling.intranet.model.json.SearchModel;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface SearchServices
{
    List<SearchModel> search(String search);
    
    void index() throws Exception;
}
