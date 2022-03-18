package com.revature.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.revature.models.Group;
import com.revature.models.Profile;
import com.revature.repositories.GroupRepo;

/**
*
* The service layer for the Group class
*
* @author John Boyle
* @batch: 211129-Enterprise
*
* @edit David Guijosa
* @batch: 220118-UTA-JAVA-GCP-EM
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
		Optional<Group> group = groupRepo.findById(id);
		return  group.isPresent() ? group.get() : null;
	}
	
	/** 
	 * @author Zak
	 * 
	 * Gets the a list of all Groups, Paginated.
	 * 
	 * @return A list of all Group objects by page
	 * 
	 */
	@Override
	public List<Group> findAllPaginated(int page) {
        Pageable pageable = PageRequest.of(page - 1, 3, Sort.by("groupName").descending());
        Page<Group> groupsPage = groupRepo.findAll(pageable);
            return groupsPage.getContent();

	}
	/**
	 * @author robot
	 * Gets the profiles associated with the group with the given id
	 * 
	 * @param id
	 * @return Set containing all profiles in a group
	 */
	public Set<Profile> showGroups(int id) {
		Optional<Group> group = groupRepo.findById(id);
		Group targetGroup = group.isPresent() ? group.get() : null;
		
		if (targetGroup != null && targetGroup.getMembers() != null && !targetGroup.getMembers().isEmpty()) {
			return targetGroup.getMembers();
		}
		return new HashSet<>();
	}
	
    /**
     * Calls ProfileRepo to get a list of matching profiles.
     * 
     * ImageUrl and Pid are ignored when searching.
     * @param Search query without spaces
     * @return List <Profile> matching search
     */ 
	public List<Group> search(String query) {
		Group sampleGroup = new Group();
		sampleGroup.setGroupId(0);
		sampleGroup.setGroupName(query);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
				 .withMatcher("groupName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				 .withIgnorePaths("groupId")
				 .withIgnorePaths("owner")
				 .withIgnorePaths("members");
				 
		 Example <Group> example = Example.of(sampleGroup, ignoringExampleMatcher);
		
		
		return groupRepo.findAll(example);
	}

	/**
     * Calls GroupRepo to update a group.
     * Currently it only updates the cover image, the image and the name
     * 
     * @param group
     * @return an updated group
     */ 
	@Override
    public Group updateGroup(Group group) {
    	
    	Group targetProfile = groupRepo.getOne(group.getGroupId());

        if (targetProfile!=null) {
            if (group.getCoverImgurl()!=null) targetProfile.setCoverImgurl(group.getCoverImgurl());
            if (group.getGroupName()!=null) targetProfile.setGroupName(group.getGroupName());
            if (group.getImgurl()!=null) targetProfile.setImgurl(group.getImgurl());
			if (group.getDescription()!=null) targetProfile.setDescription(group.getDescription());
            return groupRepo.save(targetProfile);
        } else {
            return null;
        }
    }
}
