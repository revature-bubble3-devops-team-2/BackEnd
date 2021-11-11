package com.revature.services;

import com.revature.models.Post;
import com.revature.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepo postRepo;

    /**
     * @param post Post to be added into the database
     * @return Post that was added or null if an error occurs
     */
    @Override
    public Post addPost(Post post) {

        try {
            if (post.getDatePosted()==null || post.getCreator()==null) {
                System.out.println(post.getDatePosted());
                System.out.println(post.getCreator());
                throw new NullPointerException();
            }
            postRepo.save(post);
            return post;
        } catch (Exception e) {
            return null;
        }
    }

<<<<<<< HEAD
=======
    /**
     * @return list of all the Posts
     */
>>>>>>> c81adb3f5818a4cb73dd5e24f60bb63ad9d513e3
    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }
<<<<<<< HEAD

//    @Override
//    public int deletePost(Post post) {
//        return 0;
//    }
=======
>>>>>>> c81adb3f5818a4cb73dd5e24f60bb63ad9d513e3
}
