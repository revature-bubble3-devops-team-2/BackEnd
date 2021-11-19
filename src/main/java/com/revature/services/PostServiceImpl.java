package com.revature.services;



import com.revature.models.Followers;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.FollowerRepo;
import com.revature.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepo postRepo;

    @Autowired
    FollowerRepo followerRepo;

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
    public List<Post> getAllPostsbyid(int page) {
//        if(page <= 0)
//        {
//            page = 1;
//        }
        Pageable pageable = PageRequest.of(page - 1,3, Sort.by("datePosted").descending());
        Page<Post> resultPage = postRepo.findAll(pageable);
        if(resultPage.hasContent())
        {
            return resultPage.getContent();

        }
        return null;
    }

    public List<Post> getAllPosts() {return postRepo.findAll();}

    public List<Post> getFollowerPostsByProfile(Profile profile, int page){
        if(page <= 0){
            return null;
        }
        List<Followers> followers = followerRepo.getFollowersByProfile(profile);
        List<Post> resultPosts = new ArrayList<>();
        for(Followers followers1: followers){
            List<Post> followerPosts = postRepo.findTop3ByCreator(followers1.getFollower(), Sort.by("datePosted").descending());
            resultPosts.addAll(followerPosts);
        }

        List<Post> sortedPosts = resultPosts.stream().sorted(Comparator.comparing(Post::getDatePosted).reversed()).collect(Collectors.toList());

        PagedListHolder pageable = new PagedListHolder(sortedPosts);
        pageable.setPageSize(5);
        pageable.setPage(page - 1);
        if(pageable.getPageCount() < page){
            return null;
        }
        System.out.println(pageable.getPageList());
        return pageable.getPageList();
    }


}

