package com.revature.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.dto.GroupDTO;
import com.revature.dto.PostDTO;
import com.revature.dto.ProfileDTO;
import com.revature.models.Group;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.GroupServiceImpl;
import com.revature.services.PostServiceImpl;
import com.revature.services.ProfileServiceImpl;

/**
*
* Controller class for the Group class
* All endpoints are prefaced with /groups
*
* @author John Boyle
* @batch: 211129-Enterprise
*
* @edit David Guijosa
* @batch: 220118-UTA-JAVA-GCP-EM
*/
@RestController
@CrossOrigin
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	GroupServiceImpl groupService;
	@Autowired
	ProfileServiceImpl profileService;
	@Autowired
	PostServiceImpl postService;

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
	 * @author Zak
	 * 
	 * Get request that returns a page of the Paginated list of all groups as GroupDTO objects
	 * @return List of GroupDTOs for all groups
	 */
	@GetMapping("/page/{pageNumber}")
	@ResponseBody
	public ResponseEntity<List<GroupDTO>> findAllGroups(@PathVariable ("pageNumber") int pageNumber) {
		List<Group> groups = groupService.findAllPaginated(pageNumber);
		List<GroupDTO> groupDTOs = new LinkedList<>();
		groups.forEach(g -> groupDTOs.add(new GroupDTO(g)));
		return new ResponseEntity<>(groupDTOs, HttpStatus.OK);
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
	 * @autor Zent
     * Put mapping grabs the updated fields of group and updates the profile in
     * the database.
     * 
     * @param group
     * @return Updated group with HttpStatus.ACCEPTED otherwise if invalid returns HttpStatus.BAD_REQUEST
	 * 
     */
	@PutMapping
    public ResponseEntity<Group> updateGroup(@RequestBody GroupDTO group) {
        Group result = groupService.updateGroup(group.toGroup());
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
	
	/**
	 * @author robot
	 * Get Request that returns a list of Profiles that belong to a particular group
	 * 
	 * @param id
	 * @return Set containing all the profiles that belong to the group with the given id
	 */
	@GetMapping("/{id}/members")
	public ResponseEntity<Set<ProfileDTO>> getMembers(@PathVariable("id") int id) {
		Group group;
		if((group = groupService.findById(id)) != null) {
			GroupDTO galaxyDTO = new GroupDTO(group);
				return ResponseEntity.ok()
	                    .body(galaxyDTO.getMembers());
		}
		return new ResponseEntity<>(new GroupDTO(group).getMembers(), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * @author kphu
	 * Get Request that returns a list of Posts that belong to a particular group
	 * 
	 * @param id of Group
	 * @return List containing all the posts that belong to the group with the given id
	 */
	@GetMapping("/{id}/posts")
	public ResponseEntity<List<PostDTO>> getPosts(@PathVariable("id") int id) {
		List<Post> postList;
		if((postList = postService.getAllGroupPosts(id)) != null) {
			List<PostDTO> pDtoList = postList.stream().map(PostDTO::new).collect(Collectors.toList());
			return ResponseEntity.ok()
					.body(pDtoList);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * @author Zak
	 * 
	 * Add user to a group by UID
	 * 
	 * @param groupId
	 * @param userId
	 * @return Group object after addition
	 */
	@PostMapping("/{gid}/join/{uid}")
	public ResponseEntity<GroupDTO> userJoin(@PathVariable("gid") int groupId, @PathVariable("uid") int userId) {
		
		Group group;
		if((group = groupService.findById(groupId)) != null) {
			Profile user = profileService.getProfileByPid(userId);
			Set<Profile> members = group.getMembers();
			if (members.contains(user)) {
				return new ResponseEntity<>(new GroupDTO(group), HttpStatus.BAD_REQUEST);
			} else {
				group.getMembers().add(user);
				return ResponseEntity.ok(new GroupDTO(groupService.save(group)));
			}
		}
		return new ResponseEntity<>(new GroupDTO(group), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @author Zak
	 * 
	 * Remove user from a group by UID
	 * 
	 * @param groupId
	 * @param userId
	 * @return Group object after removal
	 */
	@PostMapping("/{gid}/leave/{uid}")
	public ResponseEntity<GroupDTO> userLeave(@PathVariable("gid") int groupId, @PathVariable("uid") int userId) {
		
		Group group;
		if((group = groupService.findById(groupId)) != null) {
			Profile user = profileService.getProfileByPid(userId);
			Set<Profile> members = group.getMembers();
			if(!members.contains(user)) {
				return new ResponseEntity<>(new GroupDTO(group), HttpStatus.BAD_REQUEST);
			}else {
				group.getMembers().remove(user);
				return ResponseEntity.ok(new GroupDTO(groupService.save(group)));
			}
		}
		return new ResponseEntity<>(new GroupDTO(group), HttpStatus.BAD_REQUEST);
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
