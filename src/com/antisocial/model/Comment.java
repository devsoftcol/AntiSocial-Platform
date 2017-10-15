package com.antisocial.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="comment")
public class Comment {

    public Comment(){}

    public Comment(String commentOwner, String commentOwnerSsoId, String commentBody) {
        this.commentOwner = commentOwner;
        this.commentOwnerSsoId = commentOwnerSsoId;
        this.commentBody = commentBody;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long ID;

    @Column(name="comment_owner")
    private String commentOwner;

    @Column(name="comment_owner_ssoid")
    private String commentOwnerSsoId;

    @Column(name="comment_body")
    @Type(type = "text")
    private String commentBody;

    @Column(name="comment_date", insertable=false)
    @Type(type="timestamp")
    private Date commentDate;

    //ManyToOne fetch type is EAGER by default.
    //yorum silmeye gelince eageri lazy yapmayi dene cunku yorumu silerken yorum cekicez databaseden nabalim articleleri
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name="article_id")
    private Article articleID;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(String commentOwner) {
        this.commentOwner = commentOwner;
    }

    public String getCommentOwnerSsoId() {
        return commentOwnerSsoId;
    }

    public void setCommentOwnerSsoId(String commentOwnerID) {
        this.commentOwnerSsoId = commentOwnerID;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Article getArticleID() {
        return articleID;
    }

    public void setArticleID(Article articleID) {
        this.articleID = articleID;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "ID=" + ID +
                ", commentOwner='" + commentOwner + '\'' +
                ", commentOwnerID=" + commentOwnerSsoId +
                ", commentBody='" + commentBody + '\'' +
                ", commentDate=" + commentDate +
                ", articleID=" + articleID +
                '}';
    }
}
