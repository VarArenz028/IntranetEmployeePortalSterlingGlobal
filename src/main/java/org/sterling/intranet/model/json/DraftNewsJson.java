package org.sterling.intranet.model.json;

/**
 *
 * @author Var Arenz Villarino
 */
public class DraftNewsJson 
{
    private String draftNewsId;
    private String draftNewsTitle;
    private String draftNewsContent;
    private String draftBase64;
    private String draftFileType;

    public void setDraftNewsId(String draftNewsId)
    {
        this.draftNewsId = draftNewsId;
    }

    public void setDraftNewsTitle(String draftNewsTitle) 
    {
        this.draftNewsTitle = draftNewsTitle;
    }

    public void setDraftNewsContent(String draftNewsContent)
    {
        this.draftNewsContent = draftNewsContent;
    }

    public void setDraftBase64(String draftBase64) 
    {
        this.draftBase64 = draftBase64;
    }

    public void setDraftFileType(String draftFileType) 
    {
        this.draftFileType = draftFileType;
    }

    public String getDraftNewsId() 
    {
        return draftNewsId;
    }

    public String getDraftNewsTitle() 
    {
        return draftNewsTitle;
    }

    public String getDraftNewsContent() 
    {
        return draftNewsContent;
    }

    public String getDraftBase64()
    {
        return draftBase64;
    }

    public String getDraftFileType()
    {
        return draftFileType;
    }
    
}
