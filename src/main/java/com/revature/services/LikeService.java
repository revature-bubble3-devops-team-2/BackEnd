package com.revature.services;

import com.revature.models.Like;
import com.revature.models.LikeId;
import com.revature.models.Post;
import com.revature.models.Profile;

public interface LikeService {
    public Like likePost(LikeId likeId);
    public int likeDelete(LikeId likeId);
    public long likeGet(LikeId likeId);
    public Like likeFindByID(LikeId likeId);
}
