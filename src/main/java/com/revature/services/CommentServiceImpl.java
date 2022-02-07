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
    private static final String DATE_CREATED = "dateCreated";

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
    	return commentRepo.getCommentsByPostPsid(psid) != null ? commentRepo.getCommentsByPostPsid(psid) : new ArrayList<>();
    }
    
    /**
     * This method will take in an id for a post, and a page number, and return
     * a list of comments for the corresponding page.
     * 
     * @param psid of the target post, page number
     * @return List of comments sorted by dateCreated
     */
    @Override
    public List<Comment> getCommentsByPostPsidPaginated(int psid, int page) {
    	Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(DATE_CREATED).ascending());
    	Page<Comment> resultPage = commentRepo.getCommentsByPostPsid(pageable, psid);
    	if (resultPage.hasContent()) {
    		return resultPage.getContent();
    	}
    	
    	return new ArrayList<>();	
    	
    }
    
    /**
     * This method will take in a Post psid and return a list of comments with post psid, 
     * and a previous comment is null
     * 
     * @param psid
     * @return List of all Original Comments
     */
	public List<Comment> getOriginalCommentsByPostPsid(int psid) {
		return commentRepo.getCommentsByPostPsid(psid) != null ? 
				commentRepo.getCommentsByPostPsid(psid).stream()
						   .filter(c -> c.getPrevious() == null).collect(Collectors.toList())
				: new ArrayList<>();
	}
	
    /**
     * This method will take in an id for a post, and a page number, and return
     * a list of comments for the requested post with a previous comment null,
     * for the corresponding page.
     * 
     * @param psid of the target post, page number
     * @return List of Original Comments sorted by dateCreated
     */
	public List<Comment> getOriginalCommentsByPostPsidPaginated(int psid, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(DATE_CREATED).ascending());

		Page<Comment> resultPage = commentRepo.getCommentsByPostPsid(pageable, psid);
		if (resultPage != null && resultPage.hasContent()) {
			return resultPage.getContent().stream()
					.filter(c -> c.getPrevious() == null)
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
    
    /**
     * This method will take in a Post psid, and a Comment cid
     * and returns all replies to requested comment
     * 
     * @param psid of target post, cid of target comment
     * @return List of all replies to the requested comment
     */
	@Override
	public List<Comment> getCommentsByPostPsidAndPrevious(int psid, int cid) {
		return commentRepo.getCommentsByPostPsidAndPrevious(psid, cid) != null ? 
				commentRepo.getCommentsByPostPsidAndPrevious(psid, cid) 
				: new ArrayList<>();
	}
	
    /**
     * This method will take in an id for a post, and a page number, and return
     * a list of comments for the requested post with a previous comment of cid,
     * for the corresponding page.
     * 
     * @param psid of the target post, cid of target comment, page number
     * @return List of replies for the requested Comment sorted by dateCreated
     */
	@Override
	public List<Comment> getCommentsByPostPsidAndPreviousPaginated(int psid, int cid, int page) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(DATE_CREATED).ascending());
		Page<Comment> resultPage = commentRepo.getCommentsByPostPsidAndPrevious(pageable, psid, cid);
		if (resultPage.hasContent()) {
			return resultPage.getContent();
		}
    	return new ArrayList<>();
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
	
}
