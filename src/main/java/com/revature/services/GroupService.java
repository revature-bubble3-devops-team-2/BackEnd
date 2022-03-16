package com.revature.services;

import java.util.List;

import com.revature.models.Group;

public interface GroupService {
	Group save(Group group);
	Group findById(int id);
	List<Group> findAllPaginated(int pageNumber);
	Group updateGroup(Group group);
	
	
}
