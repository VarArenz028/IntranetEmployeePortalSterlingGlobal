package org.sterling.intranet.services;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterling.intranet.interfaces.UserAvatarDao;
import org.sterling.intranet.interfaces.UserAvatarServices;
import org.sterling.intranet.models.UserAvatar;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Service("userAvatarServices")
public class UserAvatarServicesImpl implements UserAvatarServices
{

    @Autowired
    private UserAvatarDao userAvatarDao;
    
    @Transactional(readOnly = true)
    @Override
    public UserAvatar selectUserAvatarByAvatarId(Integer avatarId) 
    {
        return userAvatarDao.selectUserAvatarByAvatarId(avatarId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public UserAvatar selectUserAvatarByEmpId(Integer empId)
    {
        return userAvatarDao.selectUserAvatarByEmpId(empId);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void saveAvatar(UserAvatar userAvatar)
    {
        userAvatarDao.saveAvatar(userAvatar);
    }

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> updateAvatar(String fileType, String fileName, String base64, Integer avatarId)
    {
        Map<String, Object> response = new HashMap<String, Object>();
        
        UserAvatar userAvatar = userAvatarDao.selectUserAvatarByAvatarId(avatarId);
        
        userAvatar.setFileName(fileName);
        userAvatar.setFileType(fileType);
        userAvatar.setBase64(base64);
        response.put("userAvatarUpdated", HttpStatus.OK);
                      
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public UserAvatar selectOneUserAvatarByEmpId(Integer empId) 
    {
        return userAvatarDao.selectOneUserAvatarByEmpId(empId);
    }
    
}
