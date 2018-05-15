//package org.sterling.intranet.models;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.Lob;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
///**
// *
// * @author Var Arenz G. Villarino
// */
//@Entity
//@Table(name = "userProfileImage")
//public class UserProfileImage
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int profileImgid;
//    @Column(name = "profileImg")
//    @Lob
//    private String image;
//    
//    @OneToOne(fetch = FetchType.EAGER, targetEntity = User.class)
//    @JoinColumn(name = "userId")
//    private User user;
//
//    public void setProfileImgid(int profileImgid)
//    {
//        this.profileImgid = profileImgid;
//    }
//
//    public void setImage(String image) 
//    {
//        this.image = image;
//    }
//
//    public void setUser(User user)
//    {
//        this.user = user;
//    }
//    
//    public int getProfileImgid() 
//    {
//        return profileImgid;
//    }
//
//    public String getImage() 
//    {
//        return image;
//    }
//
//    public User getUser() 
//    {
//        return user;
//    }
//    
//}
