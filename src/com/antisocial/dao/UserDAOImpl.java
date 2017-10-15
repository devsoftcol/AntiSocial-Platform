package com.antisocial.dao;


import com.antisocial.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List getUsers(boolean lazy) {
        Query query;
        if(lazy){
            query = sessionFactory.getCurrentSession().createQuery("FROM User ORDER BY name", User.class);
        }else{
            query = sessionFactory.getCurrentSession().createQuery("FROM User u left join fetch u.articleList ORDER BY u.name", User.class);
        }
        return  query.getResultList();
    }

    @Override
    public User getUserById(Long ID) {
        return sessionFactory.getCurrentSession().get(User.class, ID);
    }

    @Override
    public User getUserBySsoId(String ssoId, boolean lazy) {
        Query query;
        if(lazy){
            query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE ssoId = :ssoId", User.class);
        }else{
            query = sessionFactory.getCurrentSession().createQuery("FROM User u left join fetch u.articleList WHERE u.ssoId = :ssoId", User.class);
        }

        List list = query
                .setParameter("ssoId", ssoId)
                .getResultList();

        if (list.isEmpty()) {
            return null;
        }
        return (User) list.get(0);
    }


    @Override
    public User getUserByEmail(String email, boolean lazy) {
        Query query;
        if(lazy){
            query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE email = :email", User.class);
        }else{
            query = sessionFactory.getCurrentSession().createQuery("FROM User u left join fetch u.articleList WHERE u.email = :email", User.class);
        }
        List list = query
                .setParameter("email", email)
                .getResultList();

        if (list.isEmpty()) {
            return null;
        }
        return (User) list.get(0);
    }

    @Override
    public List getUserByName(String name, boolean lazy) {
        Query query;
        if(lazy){
            query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE name = :name", User.class);
        }else{
            query = sessionFactory.getCurrentSession().createQuery("FROM User u left join fetch u.articleList WHERE u.name = :name", User.class);
        }
        return  query
                .setParameter("name", name)
                .getResultList();
    }


    @Override
    public List<User> getUserByWildcard(String wildcard) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE lower(name) LIKE :wildcard ORDER BY name", User.class)
                .setParameter("wildcard", "%" + wildcard.toLowerCase() + "%")
                .getResultList();

    }

    @Override
    public void saveUser(User user){
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }


    @Override
    public void deleteUser(Long ID) {
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM User WHERE ID = :ID")
                .setParameter("ID", ID)
                .executeUpdate();
    }


}
