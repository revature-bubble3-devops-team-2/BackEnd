package com.revature.services;


import com.revature.models.Comment;
import com.revature.models.Post;

import java.util.List;

public interface CommentService {
    public Comment addComment(Comment comment);

    public List<Comment> getCommentByPostPsid(Integer psid);
}
