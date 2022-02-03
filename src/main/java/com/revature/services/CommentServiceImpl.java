package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.revature.models.Comment;
import com.revature.repositories.CommentRepo;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepo commentRepo;

    /**
     * This method will receive a comment to be added from the user and return a comment. 
     * Within a try block, it will catch any exception and return null. It also makes sure the comment has a profile and date, otherwise
     * it will throw an exception.
     *
     * @param comment Comment to be added to the database
     * @throws NullPointerException if null pointer error occurs
     * @return Comment that was added or null if an exception is thrown.
     */
    @Override
    public Comment addComment(Comment comment) {
        try {
            if (comment.getDateCreated()==null || comment.getWriter()==null) {
                throw new NullPointerException();
            }
           commentRepo.save(comment);
            return comment;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Comment> getCommentsByPostPsidPaginated(int psid, int page) {
    	Pageable pageable = PageRequest.of(page - 1, 5, Sort.by("dateCreated").descending());
    	Page<Comment> resultPage = commentRepo.getCommentsByPostPsid(pageable, psid);
    	if (resultPage.hasContent()) {
    		return resultPage.getContent();
    	}
    	
    	return new ArrayList<>();	
    	
    }
    /**
     * This method will take in an id for a Post and return all comments 
     * related to the post
     *
     * @param psid of the target Post
     * @return list of comments organized by PostId
     *
     */
    @Override
    public List<Comment> getCommentsByPostPsid(int psid) {
        return commentRepo.getCommentsByPostPsid(psid);
    }

    /**
     * This method will take in an id for a Comment and return the comment
     *
     * @param cid of the target Comment
     * @return the target Comment
     */
    @Override
    public Comment getCommentByCid(int cid){
        return commentRepo.getCommentByCid(cid);
    }

	public List<Comment> getCommentsByPostPsidAndPreviousIsNull(int psid) {
		return commentRepo.getCommentsByPostPsid(psid).stream()
				.filter(c -> c.getPrevious() == null)
				.collect(Collectors.toList());
	}

	public List<Comment> getCommentsByPostPsidAndPreviousPaginated(int psid, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by("datePosted").descending());
		Page<Comment> resultPage = commentRepo.getCommentsByPostPsid(pageable, psid);
		if (resultPage.hasContent()) {
			return resultPage.getContent().stream()
					.filter(c -> c.getPrevious() == null)
					.collect(Collectors.toList());
		}
    	return new ArrayList<>();	
	}

	@Override
	public List<Comment> getCommentsByPostPsidAndPrevious(int psid, int cid) {
		return commentRepo.getCommentsByPostPsidAndPrevious(psid, cid);
	}

	@Override
	public List<Comment> getCommentsByPostPsidAndPreviousPaginated(int psid, int cid, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by("datePosted").descending());
		Page<Comment> resultPage = commentRepo.getCommentsByPostPsidAndPrevious(pageable, psid, cid);
		if (resultPage.hasContent()) {
			return resultPage.getContent();
		}
    	return new ArrayList<>();
	}

	
	
	
	
	

}
