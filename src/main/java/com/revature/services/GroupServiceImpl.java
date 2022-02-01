package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Group;
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
		return groupRepo.findById(id).get();
	}
}
