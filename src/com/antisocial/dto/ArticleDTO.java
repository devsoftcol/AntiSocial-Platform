package com.antisocial.dto;



import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class ArticleDTO {

    public ArticleDTO(){}

    public ArticleDTO(Long ID,String articleOwner, String category, String articleHeader, String articleBody, String articleDate, String userSsoId, List<CommentDTO> commentDTOS, String requestSsoId) {
        this.ID = ID;
        this.articleOwner = articleOwner;
        this.category = category;
        this.articleHeader = articleHeader;
        this.articleBody = articleBody;
        this.articleDate = articleDate;
        this.userSsoId = userSsoId;
        this.commentDTOS = commentDTOS;
        this.requestSsoId = requestSsoId;
    }

    private Long ID;

    private String articleOwner;

    @NotBlank(message = "Category cannot be empty.")
    private String category;

    @NotBlank(message = "Article header cannot be empty.")
    @Size(min = 3, max = 240 , message = "Article header min 3 and max 240 characters.")
    private String articleHeader;

    @NotBlank(message = "Article body cannot be empty.")
    @Size(min = 3, max = 2000 , message = "Article body min 3 and max 2000 characters.")
    private String articleBody;

    private String articleDate;

    private String userSsoId;

    private Integer userID;

    private String requestSsoId;

    private List<CommentDTO> commentDTOS;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getArticleOwner() {
        return articleOwner;
    }

    public void setArticleOwner(String articleOwner) {
        this.articleOwner = articleOwner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArticleHeader() {
        return articleHeader;
    }

    public void setArticleHeader(String articleHeader) {
        this.articleHeader = articleHeader;
    }

    public String getArticleBody() {
        return articleBody;
    }

    public void setArticleBody(String articleBody) {
        this.articleBody = articleBody;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public String getUserSsoId() {
        return userSsoId;
    }

    public void setUserSsoId(String userSsoId) {
        this.userSsoId = userSsoId;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getRequestSsoId() {
        return requestSsoId;
    }

    public void setRequestSsoId(String requestSsoId) {
        this.requestSsoId = requestSsoId;
    }

    public List<CommentDTO> getCommentDTOS() {
        return commentDTOS;
    }

    public void setCommentDTOS(List<CommentDTO> commentDTOS) {
        this.commentDTOS = commentDTOS;
    }



}
