package com.revature.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Group;

/**
*
* The class is a JPA Repository for the Group class
*
* @author John Boyle
* @batch: 211129-Enterprise
*
*/
@Repository
public interface GroupRepo extends JpaRepository<Group, Integer> {
	
	Page<Group> findAll(Pageable pageable);
	
}
