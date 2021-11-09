package com.revature.repositories;

import com.revature.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> getCommentByPost(int postId);
    List<Comment> getCommentsByProfile(int profileId);
}
