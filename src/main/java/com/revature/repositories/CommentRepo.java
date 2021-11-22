package com.revature.repositories;

import com.revature.models.Comment;
import com.revature.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

    public List<Comment> getCommentByPostPsid(Integer psid);

    public Comment getCommentByCid(Integer cid);
}
