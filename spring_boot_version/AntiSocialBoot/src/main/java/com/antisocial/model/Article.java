package com.antisocial.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="article")
public class Article {

    public Article(){}

    public Article(String articleHeader, String articleBody) {
        this.articleHeader = articleHeader;
        this.articleBody = articleBody;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long ID;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name="category")
    private Category category;

    @Column(name="article_header")
    @Type(type = "text")
    private String articleHeader;

    @Column(name="article_body")
    @Type(type = "text")
    private String articleBody;

    @Column(name="article_date", insertable=false)
    @Type(type="timestamp")
    private Date articleDate;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name="user_id")
    private User userID;


    //FetchType eager
    @OneToMany(mappedBy = "articleID", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> commentList;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }



}
