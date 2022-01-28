package com.revature.dto;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.revature.models.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProfileDTO {

    private @NonNull Integer pid;

    private @NonNull String username;

    private @NonNull String passkey;

    private @NonNull String firstName;

    private @NonNull String lastName;

    private @NonNull String email;

    private List<ProfileDTO> following = new LinkedList<>();
    
    private Set<GroupDTO> groups;


    public ProfileDTO(Profile profile) {
    	pid = profile.getPid();
    	username = profile.getUsername();
    	passkey = profile.getPasskey();
    	firstName = profile.getFirstName();
    	lastName = profile.getLastName();
    	email = profile.getEmail();
    	following = new LinkedList<>();
    	profile.getFollowing().forEach(f -> following.add(new ProfileDTO(profile.getPid(), profile.getUsername(), profile.getPasskey(), profile.getFirstName(), profile.getLastName(), profile.getEmail())));
    	groups = new HashSet<>();
    	profile.getGroups().forEach(g -> groups.add(new GroupDTO(g.getGroupId(), g.getGroupName())));
    }

}
