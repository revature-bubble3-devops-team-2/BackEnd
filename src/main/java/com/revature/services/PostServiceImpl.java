package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    public PostRepo postRepo;

    /**
     * Adds a new post to the database. The date and the creator of the post cannot be null.
     *
     * If the date or creator is null, a null will be returned.
     *
     * @param post  the post to be added into the database
     * @return the post that was added, or null otherwise
     */
    @Override
    public Post addPost(Post post) {
        try {
            if (post.getDatePosted()==null || post.getCreator()==null) {
                throw new NullPointerException();
            }
            postRepo.save(post);
            return post;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns a list of all the posts within the database.
     *
     * @return a list containing all the posts
     */
    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    /**
     * Adds a new like into a post.
     *
     * If the profile already liked the post or the post does not exist, a null will be returned.
     *
     * @param profile  that liked the post
     * @param post  that has been liked
     * @return the profile that liked the post
     */
    @Override
    public Profile likePost(Profile profile, Post post) {
        Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
        if (tempPost == null) {
            return null;
        }
        boolean checkPost = tempPost.getLikes().add(profile);
        if (checkPost) {
            postRepo.save(tempPost);
            return profile;
        }
        return null;
    }

    /**
     * Deletes a like on a post.
     *
     * If the like does not exist or the post does not exist, a -1 will be returned.
     *
     * @param profile  that unliked the post
     * @param post  that has been unlike
     * @return 1 if like was deleted, -1 otherwise
     */
    @Override
    public int likeDelete(Profile profile, Post post) {
        Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
        if (tempPost == null) {
            return -1;
        }
        boolean checkPost = tempPost.getLikes().remove(profile);
        if (checkPost) {
            postRepo.save(tempPost);
            return 1;
        }
        return -1;
    }

    /**
     * Returns the total number likes on a post.
     *
     * @param post  the post to be searched for likes
     * @return total integer number of likes
     */
    @Override
    public int likeGet(Post post) {
        Set<Profile> likes = postRepo.findById(post.getPsid()).get().getLikes();
        return likes.size();
    }

    /**
     * Find out if the profile has liked the post or not.
     *
     * If the profile has not liked the post or the post does not exist, a null will be returned
     *
     * @param profile  the profile to search in the post's likes
     * @param post  the post to search for the like
     * @return a profile that has been found in the post's likes, null otherwise
     */
    @Override
    public Profile likeFindByID(Profile profile, Post post) {
        try {
            Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
            if (tempPost.getLikes().contains(profile)) {
                return profile;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}

