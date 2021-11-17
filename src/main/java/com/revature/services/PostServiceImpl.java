package com.revature.services;



import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepo postRepo;

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

    @Override
    public Post likePost(Profile profile, Post post) {
        List<Profile> likes = postRepo.findById(post.getPsid()).get().getLikes();
        if (likes.contains(profile)) {
            return null;
        } else {
            likes.add(profile);
            postRepo.updateLikes(post.getPsid(), likes);
            return post;
        }
    }

    @Override
    public int likeDelete(Profile profile, Post post) {
        List<Profile> likes = postRepo.findById(post.getPsid()).get().getLikes();
        if (!likes.contains(profile)) {
            return -1;
        } else {
            likes.remove(profile);
            postRepo.updateLikes(post.getPsid(), likes);
            return 1;
        }
    }

    @Override
    public int likeGet(Profile profile, Post post) {
        List<Profile> likes = postRepo.findById(post.getPsid()).get().getLikes();
        return likes.size();
    }

    @Override
    public Post likeFindByID(Profile profile, Post post) {
        List<Profile> likes = postRepo.findById(post.getPsid()).get().getLikes();
        if (likes.contains(profile)) {
            return post;
        } else {
            return null;
        }
    }
}

