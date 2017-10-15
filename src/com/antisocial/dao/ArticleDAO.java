package com.antisocial.dao;


import com.antisocial.model.Article;
import com.antisocial.model.Category;
import com.antisocial.model.User;

import java.util.List;

public interface ArticleDAO {

    public List<Article> getArticles();

    public List<Article> getArticles(int limit);

    public List<Article> getArticles(int limit, int start);

    public Long getRowCount();

    public Long getArticleCountByUserID(User userID);

    public Article getArticleById(Long ID);

    public List<Article> getArticlesByUserID(User userID, int limit);

    public List<Article> getArticlesByCategory(Category category);

    public List<Article> getArticlesByCategory(Category category, int limit);

    public List<Article> getArticlesByCategory(Category category, int limit, int start);

    public List<Article> getArticlesByHeader(String wildcard);

    public void saveArticle(Article article);




}
