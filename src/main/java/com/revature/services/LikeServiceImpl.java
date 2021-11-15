package com.revature.services;

import com.revature.models.Like;
import com.revature.models.LikeId;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{

    @Autowired
    public LikeRepo likeRepo;

    @Override
    public Like likePost(LikeId likeId) {
        Like like = new Like(likeId);
        return likeRepo.save(like);
    }
}
