package org.sterling.intranet.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.SearchDao;
import org.sterling.intranet.interfaces.SearchServices;
import org.sterling.intranet.model.json.SearchModel;

/**
 *
 * @author Var Arenz Villarino
 */
@Service("searchServices")
public class SearchServicesImpl implements SearchServices
{

    @Autowired
    private SearchDao searchDao;
    
    @Transactional(readOnly = true)
    @Override
    public List<SearchModel> search(String search) 
    {
        return searchDao.search(search);
    }

    @Transactional
    @Override
    public void index() throws Exception 
    {
        searchDao.index();
    }
    
}
