package com.antisocial.dao;

import com.antisocial.model.Article;
import com.antisocial.model.Comment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Comment> getComments() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Comment ORDER BY commentDate", Comment.class)
                .getResultList();
    }

    @Override
    public Comment getCommentById(Long ID) {
        return sessionFactory.getCurrentSession()
                .get(Comment.class, ID);
    }

    @Override
    public List<Comment> getCommentsByUserSsoId(String commentOwnerSsoId, int limit) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Comment WHERE commentOwnerSsoId = :commentOwnerSsoId ORDER BY commentDate DESC",Comment.class)
                .setParameter("commentOwnerSsoId", commentOwnerSsoId)
                .setMaxResults(limit)
                .getResultList();
    }


    @Override
    public Long getCommentCountBySsoId(String commentOwnerSsoId) {
        List<Long> list = sessionFactory.getCurrentSession()
                .createQuery("SELECT count(*) FROM Comment where commentOwnerSsoId = :commentOwnerSsoId", Long.class)
                .setParameter("commentOwnerSsoId", commentOwnerSsoId)
                .getResultList();
        if (list == null){
            return null;
        }
        return list.get(0);
    }

    @Override
    public Long saveComment(Comment comment) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(comment);
        return comment.getID();
    }

    @Override
    public void deleteComment(Long ID) {
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Comment WHERE ID = :ID")
                .setParameter("ID", ID)
                .executeUpdate();
    }
}
