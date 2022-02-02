package com.revature.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.GroupDTO;
import com.revature.models.Group;
import com.revature.services.GroupServiceImpl;

/**
*
* Controller class for the Group class
* All endpoints are prefaced with /groups
*
* @author John Boyle
* @batch: 211129-Enterprise
*
*/
@RestController
@CrossOrigin
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	GroupServiceImpl groupService;

	/** 
	 * 
	 * Get request that returns a GroupDTO object with the given id
	 * 
	 * @Params id
	 * @return GroupDto with the corresponding id
	 * 
	 */
	@GetMapping("/{id}")
	public ResponseEntity<GroupDTO> findGroup(@PathVariable("id") int id) {
		GroupDTO groupDto = new GroupDTO(groupService.findById(id));
		return ResponseEntity.ok(groupDto);
	}
	
	/** 
	 * 
	 * Post request that sends a Group object to the service layer to be saved
	 * and returns the updated GroupDTO object
	 * 
	 * @Params group
	 * @return GroupDto for the Group object that was saved
	 * 
	 */
	@PostMapping("/save")
	public ResponseEntity<GroupDTO> saveGroup(@Valid @RequestBody Group group) {
		GroupDTO groupDto = new GroupDTO(groupService.save(group));
		return ResponseEntity.ok(groupDto);
	}
	
}
