package com.revature.services;



import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepo postRepo;

    @Autowired
    public ProfileService profileService;

    /**
     * addPost will receive a post to be added and return a potential post. Within a try block, it will catch any
     * exception thrown and return null as the post. It will also check whether the post has a profile and date. If
     * not, an exception is thrown a null post is returned. The repository's save method is called to add the post and
     * returns the original past afterwards.
     *
     * @param post Post to be added into the database
     * @return Post that was added or null if an error occurs
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
     * getAllPosts calls upon the repository's findAll method to return a list of all the posts.
     *
     * @return list of all the Posts
     */
    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    /**
     * @param profile
     * @param post
     * @return
     */
    @Override
    public Profile likePost(Profile profile, Post post) {
        Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
        if (tempPost == null) {
            return null;
        }
        Boolean checkPost = tempPost.getLikes().add(profile);
        if (checkPost) {
            postRepo.save(tempPost);
            return profile;
        }
        return null;
    }

    /**
     * @param profile
     * @param post
     * @return
     */
    @Override
    public int likeDelete(Profile profile, Post post) {
        Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
        if (tempPost == null) {
            return -1;
        }
        Boolean checkPost = tempPost.getLikes().remove(profile);
        if (checkPost) {
            postRepo.save(tempPost);
            return 1;
        }
        return -1;
    }

    /**
     * @param profile
     * @param post
     * @return
     */
    @Override
    public int likeGet(Profile profile, Post post) {
        Set<Profile> likes = postRepo.findById(post.getPsid()).get().getLikes();
        return likes.size();
    }

    /**
     * @param profile
     * @param post
     * @return
     */
    @Override
    public Profile likeFindByID(Profile profile, Post post) {
        try {
           Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
           System.out.println("tempPost: "+tempPost);
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

