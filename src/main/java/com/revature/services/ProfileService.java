package com.revature.services;

import java.util.List;

import com.revature.models.Profile;

public interface ProfileService {

    Profile login(String username, String password);

    Profile addNewProfile(Profile profile);

    Profile getProfileByEmail(Profile profile);

    Profile getProfileByPid(Integer pid);

    Profile updateProfile(Profile profile);

    Profile removeFollowByEmail(Profile profile, String email);

    Profile addFollowerByEmail(Profile profile, String email);

    List<Profile> getAllProfilesPaginated(int page);


//	public List<Profile> getProfilesByQuery(String query);
//	public List<Profile> getAllProfiles();
//	public List<Profile> getProfileByFirstname(String firstname);

	public List<Profile> getAll();
	public List<Profile> search();
	

}
