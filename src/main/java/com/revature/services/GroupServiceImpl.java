package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Group;
import com.revature.repositories.GroupRepo;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupRepo groupRepo;
	
	@Override
	public Group save(Group group) {
		return groupRepo.save(group);
	}

	@Override
	public Group findById(int id) {
		return groupRepo.findById(id).get();
	}
}
