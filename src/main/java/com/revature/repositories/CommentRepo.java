package com.revature.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {

    List<Comment> getCommentsByPostPsid(int psid);
    Page<Comment> getCommentsByPostPsid(Pageable pageable, int psid);
    
    List<Comment> getCommentsByPostPsidAndPrevious(int psid, int cid);
    Page<Comment> getCommentsByPostPsidAndPrevious(Pageable pageable, int psid, int cid);
    
    Comment getCommentByCid(int cid);
    
}
