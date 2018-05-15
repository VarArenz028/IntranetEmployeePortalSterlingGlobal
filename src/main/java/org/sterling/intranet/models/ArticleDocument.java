package org.sterling.intranet.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Basic;
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

/**
 *
 * @author Var Arenz G. Villarino
 */
@Entity
@Table(name = "articleDocument")
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ArticleDocument implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileId")
    private Integer fileId;	

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId")
    private Article article;
    
    @ManyToOne
    @JoinColumn(name = "empDetailsId")
    private EmployeeDetails employeeDetails;

    public void setFileId(Integer fileId) 
    {
        this.fileId = fileId;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public void setType(String type) 
    {
        this.type = type;
    }

    public void setContent(byte[] content) 
    {
        this.content = content;
    }

    public void setArticle(Article article)
    {
        this.article = article;
    }
    
    public void setEmployeeDetails(EmployeeDetails employeeDetails)
    {
        this.employeeDetails = employeeDetails;
    }
    
    public Integer getFileId() 
    {
        return fileId;
    }

    public String getFileName() 
    {
        return fileName;
    }
    
    public String getType() 
    {
        return type;
    }

    public byte[] getContent() 
    {
        return content;
    }

    public Article getArticle() 
    {
        return article;
    }

    public EmployeeDetails getEmployeeDetails() 
    {
        return employeeDetails;
    }
    
}
