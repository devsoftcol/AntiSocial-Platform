package com.antisocial.dao;


import com.antisocial.model.Comment;

import java.util.List;

public interface CommentDAO {

    public List<Comment> getComments();

    public Comment getCommentById(Long ID);

    public List<Comment> getCommentsByUserSsoId(String commentOwnerSsoId, int limit);

    public Long getCommentCountBySsoId(String commentOwnerSsoId);

    public Long saveComment(Comment comment);

    public void deleteComment(Long ID);




}
