package com.revature.services;


import com.revature.models.Followers;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.FollowerRepo;

import com.revature.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Service
public class PostServiceImpl implements PostService {

    @Autowired
    public PostRepo postRepo;

    @Autowired
    public ProfileService profileService;

    @Autowired
    public FollowerRepo followerRepo;

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

    @Override
    public List<Post> getAllPostByUser(int pageNumber, int profileId) {
        return null;
    }

    public List<Post> getFollowerPostsByProfile(Profile profile, int page) {
        if (page <= 0) {
            return null;
        }
        List<Followers> followers = followerRepo.getFollowersByProfile(profile);
        List<Post> resultPosts = new ArrayList<>();
        for (Followers followers1 : followers) {
            List<Post> followerPosts = postRepo.findTop3ByCreator(followers1.getFollower(), Sort.by("datePosted").descending());
            resultPosts.addAll(followerPosts);
        }

        List<Post> sortedPosts = resultPosts.stream().sorted(Comparator.comparing(Post::getDatePosted).reversed()).collect(Collectors.toList());

        PagedListHolder pageable = new PagedListHolder(sortedPosts);
        pageable.setPageSize(5);
        pageable.setPage(page - 1);
        if (pageable.getPageCount() < page) {
            return null;
        }
        return pageable.getPageList();
    }

    /**
     * likePost utilizes the repository's findById method to return a post that is to be liked by a profile. It
     * will check if the post passed through the repository is empty or not, then adds the profile that liked the
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
        boolean checkPost = tempPost.getLikes().add(profile);
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
        boolean checkPost = tempPost.getLikes().remove(profile);
        if (checkPost) {
            postRepo.save(tempPost);
            return 1;
        }
        return -1;
    }

    /**
     * likeGet uses the repository's findById method that returns a set of likes the post has. Then it returns the
     * size of the likes set.
     * @param post that has requested its number of likes
     * @return number of likes the post has
     */
    @Override
    public int likeGet(Post post) {
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
            assert tempPost != null;
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

