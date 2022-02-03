package com.revature.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.revature.models.Group;
import com.revature.models.Post;
import com.revature.repositories.GroupRepo;

/**
*
* The service layer for the Group class
*
* @author John Boyle
* @batch: 211129-Enterprise
*
*/
@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupRepo groupRepo;
	
	/** 
	 * 
	 * Saves the Group object in the database
	 * 
	 * @Params group
	 * @return The Group object that was saved
	 * 
	 */
	@Override
	public Group save(Group group) {
		return groupRepo.save(group);
	}

	/** 
	 * 
	 * Gets the Group object in the database with the corresponding id
	 * if it exists
	 * 
	 * @Params id
	 * @return The corresponding Group object or null if it doesn't exist
	 * 
	 */
	@Override
	public Group findById(int id) {
		Optional<Group> group = groupRepo.findById(id);
		return  group.isPresent() ? group.get() : null;
	}
	
	/** 
	 * @author Zak
	 * 
	 * Gets the a list of all Groups, Paginated.
	 * 
	 * @return A list of all Group objects by page
	 * 
	 */
	@Override
	public List<Group> findAllPaginated(int page) {
        Pageable pageable = PageRequest.of(page - 1, 3, Sort.by("datePosted").descending());
        Page<Group> groupsPage = groupRepo.findAll(pageable);
        if (groupsPage.hasContent()) {
            return groupsPage.getContent();
        }
        return null;
	}
}
