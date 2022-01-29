package com.revature.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.GroupDTO;
import com.revature.models.Group;
import com.revature.services.GroupServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	GroupServiceImpl groupService;

	@PostMapping("/save")
	public ResponseEntity<GroupDTO> saveGroup(@Valid @RequestBody Group group) {
		return ResponseEntity.ok(new GroupDTO(groupService.save(group)));
	}
	
}
