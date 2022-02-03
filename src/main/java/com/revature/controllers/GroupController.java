package com.revature.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.dto.GroupDTO;
import com.revature.dto.ProfileDTO;
import com.revature.models.Group;
import com.revature.models.Profile;
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
	public ResponseEntity<GroupDTO> saveGroup(@Valid @RequestBody GroupDTO group) {
		GroupDTO groupDto = new GroupDTO(groupService.save(group.toGroup()));
		return ResponseEntity.ok(groupDto);
	}
	
	/**
	 * @author robot
	 * Get Request that returns a list of Profiles that belong to a particular group
	 * 
	 * @param id
	 * @return Set containing all the profiles that belong to the group with the given id
	 */
	@GetMapping("/{id}/members")
	public ResponseEntity<?> getMembers(@PathVariable("id") int id) {
		Group group;
		if((group = groupService.findById(id)) != null) {
			GroupDTO galaxyDTO = new GroupDTO(group);
				return ResponseEntity.ok()
	                    .body(galaxyDTO.getMembers());
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
	
    /**
     * Search all fields function in group 
     * 
     * @param query Takes in a String without space at end point /search{query}
     * @return List <Profile> matching search query
     */
	@NoAuthIn
	@GetMapping("/search/{query}")
	public ResponseEntity<List<GroupDTO>> search(@PathVariable("query") String query){
		List<Group> groups = groupService.search(query);
    	List<GroupDTO> groupDtos = new LinkedList<>();
    	groups.forEach(p -> groupDtos.add(new GroupDTO(p)));
		return new ResponseEntity<>(groupDtos, new HttpHeaders(), HttpStatus.OK);
	}
}
