package com.antisocial.dao;


import com.antisocial.model.Article;
import com.antisocial.model.Category;
import com.antisocial.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Article> getArticles() {
        return entityManager
                .createQuery("FROM Article ORDER BY articleDate DESC", Article.class)
                .getResultList();
    }

    @Override
    public List<Article> getArticles(int limit) {
        return entityManager
                .createQuery("FROM Article ORDER BY articleDate DESC", Article.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Article> getArticles(int limit, int start){
        return entityManager
                .createQuery("FROM Article ORDER BY articleDate DESC", Article.class)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Long getRowCount(){
        List<Long> list = entityManager
                .createQuery("SELECT count(*) FROM Article", Long.class)
                .getResultList();
        if (list == null){
            return null;
        }
        return list.get(0);
    }

    @Override
    public Long getArticleCountByUserID(User userID) {
        List<Long> list = entityManager
                .createQuery("SELECT count(*) FROM Article a WHERE a.userID = :userID", Long.class)
                .setParameter("userID", userID)
                .getResultList();
        if (list == null){
            return null;
        }
        return list.get(0);
    }

    @Override
    public Article getArticleById(Long ID) {
        return entityManager
                .find(Article.class, ID);
    }

    @Override
    public List<Article> getArticlesByUserID(User userID, int limit){
        return entityManager
                .createQuery("FROM Article a WHERE a.userID = :userID ORDER BY articleDate DESC",Article.class)
                .setParameter("userID", userID)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Article> getArticlesByCategory(Category category) {
        return entityManager
                .createQuery("FROM Article a WHERE a.category = :category ORDER BY a.articleDate DESC",Article.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public List<Article> getArticlesByCategory(Category category, int limit) {
        return entityManager
                .createQuery("FROM Article a WHERE a.category = :category ORDER BY a.articleDate DESC",Article.class)
                .setParameter("category", category)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Article> getArticlesByCategory(Category category, int limit, int start) {
        return entityManager
                .createQuery("FROM Article a WHERE a.category = :category ORDER BY a.articleDate DESC",Article.class)
                .setParameter("category", category)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Article> getArticlesByHeader(String wildcard) {
        return entityManager
                .createQuery("FROM Article WHERE lower(articleHeader) LIKE :header ORDER BY articleDate DESC",Article.class)
                .setParameter("header", "%"+wildcard.toLowerCase()+"%")
                .getResultList();
    }

    @Override
    public void saveArticle(Article article) {
        entityManager.flush();
                //.saveOrUpdate(article);
    }


}
