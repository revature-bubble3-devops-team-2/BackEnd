package com.revature.services;

import com.revature.models.Group;

public interface GroupService {
	
	Group save(Group group);
	Group findById(int id);
	
}
