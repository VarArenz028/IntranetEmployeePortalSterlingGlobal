package org.sterling.intranet.interfaces;

import org.sterling.intranet.models.UserAvatar;

/**
 *
 * @author Var Arenz G. Villarino
 */
public interface UserAvatarDao
{
    
    UserAvatar selectUserAvatarByAvatarId(Integer avatarId);
    
    UserAvatar selectUserAvatarByEmpId(Integer empId);
    
    UserAvatar selectOneUserAvatarByEmpId(Integer empId);
    
    void saveAvatar(UserAvatar userAvatar);
}
