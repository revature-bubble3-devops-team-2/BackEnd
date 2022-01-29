package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Group;

@Repository
public interface GroupRepo extends JpaRepository<Group, Integer> {

}
