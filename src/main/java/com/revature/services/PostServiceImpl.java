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
     * likePost utilizes the repository's findById method to return a post that is to be liked by a profile. It
     * will check if the post passed through the repository is empty or not, then adds the profile that liked the
     * post to the post's set of likes.
     * @param profile that liked the post
     * @param post that has been liked
     * @return profile that liked the post
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
     * likeDelete utilizes the repository's findById method to return a post that is to be unliked by a profile. It
     * will check if the post passed through the repository is empty or not, then removes the profile that unliked the
     * post from the post's set of likes.
     * @param profile that unliked the post
     * @param post that has been unlike
     * @return 1 if post was unliked, -1 if unlike was unsuccessful
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
     * likeGet uses the repository's findById method that returns a set of likes the post has. Then it returns the
     * size of the likes set.
     * @param profile of the currently logged-in user
     * @param post that has requested its number of likes
     * @return number of likes the post has
     */
    @Override
    public int likeGet(Profile profile, Post post) {
        Set<Profile> likes = postRepo.findById(post.getPsid()).get().getLikes();
        return likes.size();
    }

    /**
     * likeFindById uses the repository's findById method that returns the post that is being searched through. Then it checks
     * if the profile that is being searched for is in the post's set of likes. If the profile is found then that profile is returned,
     * null if not
     * @param profile that is to be searched for in the post's likes
     * @param post that is to search through
     * @return profile that has been found in the post's likes
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

