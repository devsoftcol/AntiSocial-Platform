package com.antisocial.service;


import com.antisocial.dao.CommentDAO;
import com.antisocial.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentDAO commentDAO;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<Comment> getComments() {
        return commentDAO.getComments();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public Comment getCommentById(Long ID) {
        return commentDAO.getCommentById(ID);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUserSsoId(String commentOwnerSsoId, int limit) {
        return commentDAO.getCommentsByUserSsoId(commentOwnerSsoId, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCommentCountBySsoId(String commentOwnerSsoId) {
        return commentDAO.getCommentCountBySsoId(commentOwnerSsoId);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Long saveComment(Comment comment) {
        return commentDAO.saveComment(comment);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void deleteComment(Long ID) {
        commentDAO.deleteComment(ID);
    }
}
