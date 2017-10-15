package com.antisocial.dao;

import com.antisocial.model.Category;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> getCategories() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Category ORDER BY categoryName", Category.class)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
    }

    @Override
    public Category getCategoryById(Long id) {
        List<Category> list = sessionFactory.getCurrentSession()
                .createQuery("FROM Category WHERE id = :id", Category.class)
                .setParameter("id", id)
                .getResultList();
        if(list.isEmpty()){ return null; }
        return list.get(0);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        List<Category> list = sessionFactory.getCurrentSession()
                .createQuery("FROM Category  WHERE categoryName = :categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getResultList();
        if(list.isEmpty()){ return null; }
        return list.get(0);
    }



    @Override
    public void saveCategory(Category category) {
        sessionFactory.getCurrentSession().saveOrUpdate(category);
    }
}
