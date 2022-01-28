package com.revature.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

    List<Comment> getCommentsByPostPsid(int psid);
    Page<Comment> getCommentsByPostPsid(Pageable pageable, int profileId);

    Comment getCommentByCid(int cid);
}
