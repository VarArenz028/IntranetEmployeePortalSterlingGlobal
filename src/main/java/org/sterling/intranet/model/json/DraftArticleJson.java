package org.sterling.intranet.model.json;

/**
 *
 * @author Var Arenz Villarino
 */
public class DraftArticleJson 
{
    private String draftArticleId;
    private String title;
    private String category;
    private String draftSummernote;

    public void setDraftArticleId(String draftArticleId) 
    {
        this.draftArticleId = draftArticleId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public void setDraftSummernote(String draftSummernote) 
    {
        this.draftSummernote = draftSummernote;
    }

    public String getDraftArticleId() 
    {
        return draftArticleId;
    }

    public String getTitle() 
    {
        return title;
    }

    public String getCategory() 
    {
        return category;
    }

    public String getDraftSummernote() 
    {
        return draftSummernote;
    }
    
    
}
