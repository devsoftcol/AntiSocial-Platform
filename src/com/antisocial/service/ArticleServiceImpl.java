package com.antisocial.service;


import com.antisocial.dao.ArticleDAO;
import com.antisocial.dto.ArticleDTO;
import com.antisocial.dto.CommentDTO;
import com.antisocial.model.Article;
import com.antisocial.model.Category;
import com.antisocial.model.Comment;
import com.antisocial.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleDAO articleDAO;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Article> getArticles() {
        return articleDAO.getArticles();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Article> getArticles(int limit) {
        return articleDAO.getArticles(limit);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Article> getArticles(int limit, int start){
        if( start > getRowCount()){
            return null;
        }
        return articleDAO.getArticles(limit, start);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public Long getRowCount(){
        return articleDAO.getRowCount();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getArticleCountByUserID(User userID) {
        return articleDAO.getArticleCountByUserID(userID);
    }

    @Override
    @Transactional(readOnly = true)
    public Article getArticleById(Long ID) {
        return articleDAO.getArticleById(ID);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getArticlesByUserID(User userID, int limit) {
        return articleDAO.getArticlesByUserID(userID, limit);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Article> getArticlesByCategory(Category category) {
        return articleDAO.getArticlesByCategory(category);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Article> getArticlesByCategory(Category category, int limit) {
        return articleDAO.getArticlesByCategory(category, limit);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Article> getArticlesByCategory(Category category, int limit, int start){
        if( start > getRowCount() ){ return null; }
        return articleDAO.getArticlesByCategory(category , limit, start);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Article> getArticlesByHeader(String wildcard) {
        return articleDAO.getArticlesByHeader(wildcard);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void saveArticle(Article article) {
        articleDAO.saveArticle(article);
    }


    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public void addComment(Comment comment, Long ID){
        Article article = articleDAO.getArticleById(ID);
        List<Comment> commentList = article.getCommentList();
        if(commentList == null){
            commentList = new ArrayList<>();
        }
        commentList.add(comment);
        comment.setArticleID(article);
    }


    private String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }


}
