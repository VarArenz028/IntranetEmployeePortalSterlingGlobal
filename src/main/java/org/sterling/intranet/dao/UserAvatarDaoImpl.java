package org.sterling.intranet.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.sterling.intranet.interfaces.UserAvatarDao;
import org.sterling.intranet.models.UserAvatar;

/**
 *
 * @author KAMBO OTIS
 */
@Repository("userAvatarDao")
public class UserAvatarDaoImpl extends AbstractDao<Integer, UserAvatar> implements UserAvatarDao
{

    @Override
    public UserAvatar selectUserAvatarByAvatarId(Integer avatarId) 
    {
        Query query = getSession().createQuery("from UserAvatar where avatarId = :avatarId");
        query.setParameter("avatarId", avatarId);
        
        UserAvatar userAvatar = (UserAvatar) query.uniqueResult();
        
        return userAvatar;
    }
    
    @Override
    public UserAvatar selectUserAvatarByEmpId(Integer empId) 
    {
        Query query = getSession().createQuery("from UserAvatar where empDetailsId = :empId");
        query.setParameter("empId", empId);
        
        UserAvatar userAvatar = (UserAvatar) query.uniqueResult();
        
        return userAvatar;
        
    }
    
    @Override
    public void saveAvatar(UserAvatar userAvatar) 
    {
        save(userAvatar);
    }

    @Override
    public UserAvatar selectOneUserAvatarByEmpId(Integer empId) 
    {
        Criteria criteria = getSession().createCriteria(UserAvatar.class).createAlias("employeeDetails", "employeeDetails")
            .add(Restrictions.eq("employeeDetails.empDetailsId", (int) empId))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("base64"), "base64")
                            .add(Projections.property("fileType"), "fileType"))
                            .setResultTransformer(Transformers.aliasToBean(UserAvatar.class));
        UserAvatar userAvatar = (UserAvatar) criteria.uniqueResult();
        
        return userAvatar;
    }
    
}
