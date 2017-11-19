package com.antisocial.dto;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDTO {

    public CommentDTO(){}

    public CommentDTO(String commentOwner, String commentOwnerSsoId , Long ID, String commentBody, String commentDate, Long articleID, Boolean errorAccess) {
        this.commentOwner = commentOwner;
        this.commentOwnerSsoId = commentOwnerSsoId;
        this.ID = ID;
        this.commentBody = commentBody;
        this.commentDate = commentDate;
        this.articleID = articleID;
        this.errorAccess = errorAccess;
    }

    private String commentOwner;

    private String commentOwnerSsoId;

    private Long ID;

    @NotBlank
    @Size(min = 1, max = 750)
    private String commentBody;

    private String commentDate;

    @NotNull
    @Min(1)
    private Long articleID;

    private Boolean errorAccess;

    public String getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(String commentOwner) {
        this.commentOwner = commentOwner;
    }

    public String getCommentOwnerSsoId() {
        return commentOwnerSsoId;
    }

    public void setCommentOwnerSsoId(String commentOwnerSsoId) {
        this.commentOwnerSsoId = commentOwnerSsoId;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public Long getArticleID() {
        return articleID;
    }

    public void setArticleID(Long articleID){ this.articleID = articleID; }

    public Boolean getErrorAccess() {
        return errorAccess;
    }

    public void setErrorAccess(Boolean errorAccess) {
        this.errorAccess = errorAccess;
    }
}

