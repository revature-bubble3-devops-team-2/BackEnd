package com.revature.services;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;

import lombok.Builder;
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
        Page<Post> resultPage = postRepo.findAllByGroupIsNull(pageable);
        if (resultPage.hasContent()) {
            return resultPage.getContent();
        }
        return null;
    }
    /**
     * Returns a list of the post that are not part of a group
     * 
     * @return List of post 
     * 
     */
    public List<Post> getAllPosts() {
        return postRepo.findAllByGroupIsNull();
    }

    /**
     * Returns a list of the post that are part of a group
     * 
     * @param groupId
     * @return List of Post that are part of a group
     */
    public List<Post> getAllGroupPosts(int groupId) {
        return postRepo.findAllByGroupGroupId(groupId);
    }

    /**
     * Looks for a Post by id in the database and then returns such Post
     * 
     * @param psid
     * @return Post
     */
    @Override
    public Post getPostByPsid(Integer psid) {
        return postRepo.getPostByPsid(psid);
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
    public Profile bookmarkPost(Profile profile, Post post) {
        Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
        if (tempPost == null) {
            return null;
        }
        boolean checkPost = tempPost.getBookmarks().add(profile.getPid());
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
    public int bookmarkDelete(Profile profile, Post post) {
        Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
        if (tempPost == null) {
            return -1;
        }
        boolean checkPost = tempPost.getBookmarks().remove(profile.getPid());
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
    public int bookmarkGet(Post post) {
        Optional<Post> bookmarks = postRepo.findById(post.getPsid());
        return bookmarks.isPresent() ? bookmarks.get().getBookmarks().size() : 0;
    }

    /**
     *bookmarkFindByID uses the repository's findById method to  returns the profile that
     * created the post. It checks  if the post passed through the repo is not null and the bookmark contains
     * the  profile then that profile is returned, else an exception is thrown
     *
     * @param profile that is to be searched for in the post's bookmark
     * @param post    that is to search through
     * @return profile that has been found in the post's bookmark
     */
    @Override
    public Profile bookmarkFindByID(Profile profile, Post post) {
        try {
            Post tempPost = postRepo.findById(post.getPsid()).orElse(null);
            if (tempPost != null && tempPost.getBookmarks().contains(profile.getPid())) {
                return profile;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * allBookMarksByCreator  uses the repo findAll method  and return an array of all bookmarked post
     * in our database.We loop through all posts and if a post contains a bookmark we store in our array
     * return the array
     */

    @Override
    public List<Post> allBookMarksByCreator(Profile profile){
        List<Post> allPosts = postRepo.findAll();
        List<Post> bookmarkedPosts = new ArrayList<>();
        for(int i = 0; i < allPosts.size(); i++){
            Post post = allPosts.get(i);
            if(post.getBookmarks().contains(profile.getPid())){
                bookmarkedPosts.add(post);
            }
        }
        return bookmarkedPosts;
    }

}