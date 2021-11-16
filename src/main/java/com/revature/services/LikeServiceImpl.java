package com.revature.services;

import com.revature.models.Like;
import com.revature.models.LikeId;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService{

    @Autowired
    public LikeRepo likeRepo;

    @Override
    public Like likePost(LikeId likeId) {
        Like like = new Like(likeId);
        return likeRepo.save(like);
    }

    @Override
    public int likeDelete(LikeId likeId) {
        try {
            likeRepo.deleteById(likeId);
            return 1;
        } catch(Exception e) {
            return -1;
        }
    }

    @Override
    public long likeGet(LikeId likeId) {
        Post post = likeId.getPost();
        return likeRepo.countByPost(post);
    }

    @Override
    public Like likeFindByID(LikeId likeId) {
        return likeRepo.findById(likeId).orElse(null);
    }
}
