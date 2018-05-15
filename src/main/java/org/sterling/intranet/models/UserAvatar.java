package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "userAvatar")
public class UserAvatar 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "avatarId")
    private int avatarId;
    
    @Column(name = "base64")
    @Lob
    private String base64;
    
    @Column(name = "fileType")
    private String fileType;
    
    @Column(name = "fileName")
    private String fileName;
    
    private long fileSize;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empDetailsId")
    @JsonIgnore
    private EmployeeDetails employeeDetails;

    public void setAvatarId(int avatarId) 
    {
        this.avatarId = avatarId;
    }

    public void setBase64(String base64
    ) {
        this.base64 = base64;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public void setFileSize(long fileSize)
    {
        this.fileSize = fileSize;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails) 
    {
        this.employeeDetails = employeeDetails;
    }

    public int getAvatarId() 
    {
        return avatarId;
    }
    
    public String getBase64() 
    {
        return base64;
    }

    public String getFileType() 
    {
        return fileType;
    }
    
    public String getFileName()
    {
        return fileName;
    }

    public long getFileSize()
    {
        return fileSize;
    }

    public EmployeeDetails getEmployeeDetails() 
    {
        return employeeDetails;
    }
    
}
