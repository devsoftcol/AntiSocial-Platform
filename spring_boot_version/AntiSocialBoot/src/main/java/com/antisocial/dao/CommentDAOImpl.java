package com.antisocial.dao;

import com.antisocial.model.Comment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> getComments() {
        return entityManager
                .createQuery("FROM Comment ORDER BY commentDate", Comment.class)
                .getResultList();
    }

    @Override
    public Comment getCommentById(Long ID) {
        return entityManager
                .find(Comment.class, ID);
    }

    @Override
    public List<Comment> getCommentsByUserSsoId(String commentOwnerSsoId, int limit) {
        return entityManager
                .createQuery("FROM Comment WHERE commentOwnerSsoId = :commentOwnerSsoId ORDER BY commentDate DESC",Comment.class)
                .setParameter("commentOwnerSsoId", commentOwnerSsoId)
                .setMaxResults(limit)
                .getResultList();
    }


    @Override
    public Long getCommentCountBySsoId(String commentOwnerSsoId) {
        List<Long> list = entityManager
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
        entityManager.flush();
                //.saveOrUpdate(comment);
        return comment.getID();
    }

    @Override
    public void deleteComment(Long ID) {
        entityManager
                .createQuery("DELETE FROM Comment WHERE ID = :ID")
                .setParameter("ID", ID)
                .executeUpdate();
    }
}
