//package org.sterling.intranet.models;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Lob;
//import javax.persistence.Table;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// *
// * @author Var Arenz G. Villarino
// */
//@Entity
//@Table(name = "profilePicture")
//public class ProfilePicture 
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "pictureId", nullable = false)
//    private int pictureId;
//    
//    @Column(name = "picture", nullable = true)
//    @Lob
//    private String picture;
//
//    public void setPictureId(int pictureId)
//    {
//        this.pictureId = pictureId;
//    }
//
//    public void setPicture(String picture)
//    {
//        this.picture = picture;
//    }
//
//    public int getPictureId()
//    {
//        return pictureId;
//    }
//
//    public String getPicture() 
//    {
//        return picture;
//    }
//
//    
//    
//}
