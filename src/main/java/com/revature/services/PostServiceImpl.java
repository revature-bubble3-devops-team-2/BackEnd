package com.revature.services;

import com.revature.repositories.PostRepo;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepo postData;


//    @Override
//    public List<Post> getAllPost() {
//        return  postData.findAll();
//    }


}