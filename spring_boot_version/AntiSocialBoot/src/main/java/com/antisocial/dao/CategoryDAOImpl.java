package com.antisocial.dao;

import com.antisocial.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> getCategories() {
        return entityManager
                .createQuery("FROM Category ORDER BY categoryName", Category.class)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
    }

    @Override
    public Category getCategoryById(Long id) {
        List<Category> list = entityManager
                .createQuery("FROM Category WHERE id = :id", Category.class)
                .setParameter("id", id)
                .getResultList();
        if(list.isEmpty()){ return null; }
        return list.get(0);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        List<Category> list = entityManager
                .createQuery("FROM Category  WHERE categoryName = :categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getResultList();
        if(list.isEmpty()){ return null; }
        return list.get(0);
    }



    @Override
    public void saveCategory(Category category) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(category);
    }
}
