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

    /**
     * likePost receives a likeId that will be turned into a Like and then posted on the database. It returns the Like
     * that was created when successfully saved by the repo or null otherwise.
     *
     * @param likeId likeId that is being posted for the Like
     * @return Like of the Like posted, null otherwise.
     */
    @Override
    public Like likePost(LikeId likeId) {
        Like like = new Like(likeId);
        return likeRepo.save(like);
    }

    /**
     * likeDelete receives a likeId that is inputted into deleteById() from the LikeRepo to be deleted. If the delete
     * method was successful, a 1 will be returned. If the delete method was not successful, the error will be caught
     * and a -1 is returned.
     *
     * @param likeId likeId that is linked to the Like that will be deleted
     * @return 1 if Like has been deleted,
     *          -1 otherwise
     */
    @Override
    public int likeDelete(LikeId likeId) {
        try {
            likeRepo.deleteById(likeId);
            return 1;
        } catch(Exception e) {
            return -1;
        }
    }

    /**
     * likeGet receives a likeId where the Post will be pulled from and put into the countByPost method in the LikeRepo.
     * Then it returns the number of likes in a long format.
     *
     * @param likeId likeId that has the Post within that it will have its Likes counted
     * @return long of the number of counts on the Post
     */
    @Override
    public long likeGet(LikeId likeId) {
        Post post = likeId.getPost();
        return likeRepo.countByPost(post);
    }

    /**
     * likeFindByID receives a likeId and puts it within the LikeRepo method findById. The method will return the
     * Like when it is found or return null if it is not found.
     *
     * @param likeId likeId of the Like to be searched for
     * @return Like when it is found, null if otherwise
     */
    @Override
    public Like likeFindByID(LikeId likeId) {
        return likeRepo.findById(likeId).orElse(null);
    }
}
