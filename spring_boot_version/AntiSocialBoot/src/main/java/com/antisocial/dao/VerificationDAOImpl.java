package com.antisocial.dao;

import com.antisocial.model.User;
import com.antisocial.model.VerificationToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VerificationDAOImpl implements VerificationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public VerificationToken findByToken(String token) {
                List list = entityManager
                .createQuery("FROM VerificationToken WHERE token = :token", VerificationToken.class)
                .setParameter("token", token)
                .getResultList();
                if(list == null || list.isEmpty()){ return null; }
                return (VerificationToken) list.get(0);
    }

    @Override
    public VerificationToken findByUser(User user) {
                List list = entityManager
                .createQuery("FROM VerificationToken WHERE user = :user", VerificationToken.class)
                .setParameter("user", user)
                .getResultList();
                if(list == null){ return null; }
                return (VerificationToken) list.get(0);
    }

    @Override
    public void saveVerificationToken(VerificationToken verificationToken) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(verificationToken);
        //sessionFactory.getCurrentSession().saveOrUpdate(verificationToken);
    }

    @Override
    public void deleteVerificationToken(VerificationToken verificationToken) {
        entityManager.remove(verificationToken);
    }
}
