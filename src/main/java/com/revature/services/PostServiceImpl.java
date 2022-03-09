package com.revature.services;

import java.util.List;
import java.util.Optional;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    public PostRepo postRepo;

    /**
     * Adds a new post to the database. The date and the creator of the post cannot
     * be null.
     *
     * If the date or creator is null, a null will be returned.
     *
     * @param post the post to be added into the database
     * @return the post that was added, or null otherwise
     */
    @Override
    public Post addPost(Post post) {
        try {
            if (post.getDatePosted() == null || post.getCreator() == null) {
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
    public List<Post> getAllPostsPaginated(int page) {
        Pageable pageable = PageRequest.of(page - 1, 3, Sort.by("datePosted").descending());
        Page<Post> resultPage = postRepo.findAll(pageable);
        if (resultPage.hasContent()) {
            return resultPage.getContent();
        }
        return null;
    }

    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }
    
    public List<Post> getAllGroupPosts(int groupId) {
    	return postRepo.findAllByGroupGroupId(groupId);
    }

    /**
     * likePost utilizes the repository's findById method to return a post that is
     * to be liked by a profile. It
     * will check if the post passed through the repository is empty or not, then
     * adds the profile that liked the
     * post to the post's set of likes.
     *
     * @param profile that liked the post
     * @param post    that has been liked
     * @return profile that liked the post
     */
    @Override
    public Profile likePost(Profile profile, Post post) {
        Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
        if (tempPost == null) {
            return null;
        }
        boolean checkPost = tempPost.getLikes().add(profile.getPid());
        if (checkPost) {
            postRepo.save(tempPost);
            return profile;
        }
        return null;
    }

    /**
     * likeDelete utilizes the repository's findById method to return a post that is
     * to be unliked by a profile. It
     * will check if the post passed through the repository is empty or not, then
     * removes the profile that unliked the
     * post from the post's set of likes.
     * 
     * @param profile that unliked the post
     * @param post    that has been unlike
     * @return 1 if post was unliked, -1 if unlike was unsuccessful
     */
    @Override
    public int likeDelete(Profile profile, Post post) {
        Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
        if (tempPost == null) {
            return -1;
        }
        boolean checkPost = tempPost.getLikes().remove(profile.getPid());
        if (checkPost) {
            postRepo.save(tempPost);
            return 1;
        }
        return -1;
    }

    /**
     * likeGet uses the repository's findById method that returns a set of likes the
     * post has. Then it returns the
     * size of the likes set.
     * 
     * @param post that has requested its number of likes
     * @return number of likes the post has
     */
    @Override
    public int likeGet(Post post) {
        Optional<Post> likes = postRepo.findById(post.getPsid());
        return likes.isPresent() ? likes.get().getLikes().size() : 0;
    }

    /**
     * likeFindById uses the repository's findById method that returns the post that
     * is being searched through. Then it checks
     * if the profile that is being searched for is in the post's set of likes. If
     * the profile is found then that profile is returned,
     * null if not
     * 
     * @param profile that is to be searched for in the post's likes
     * @param post    that is to search through
     * @return profile that has been found in the post's likes
     */
    @Override
    public Profile likeFindByID(Profile profile, Post post) {
        try {
            Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
            if (tempPost != null && tempPost.getLikes().contains(profile.getPid())) {
                return profile;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
