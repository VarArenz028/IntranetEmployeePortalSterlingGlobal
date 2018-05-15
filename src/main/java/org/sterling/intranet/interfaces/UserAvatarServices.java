package org.sterling.intranet.interfaces;

import java.util.Map;
import org.sterling.intranet.models.UserAvatar;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface UserAvatarServices
{
    
    UserAvatar selectUserAvatarByAvatarId(Integer avatarId);
    
    UserAvatar selectUserAvatarByEmpId(Integer empId);
    
    UserAvatar selectOneUserAvatarByEmpId(Integer empId);
    
    void saveAvatar(UserAvatar userAvatar);
    
    Map<String, Object> updateAvatar(String fileType, String fileName, String base64, Integer avatarId);
    
}
