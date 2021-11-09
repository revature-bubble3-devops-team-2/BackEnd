package com.revature.services;

import com.revature.models.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> getCommentByPost(int postId);
    public List<Comment> getCommentByProfile(int profileId);
    public Comment createComment(Comment newComment);
    public Comment updateComment(Comment updateComment,int id);
    public Comment deleteComment(int id);

}
