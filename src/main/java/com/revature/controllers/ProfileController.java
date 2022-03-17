package com.revature.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RestController;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.dto.ProfileDTO;
import com.revature.models.Profile;
import com.revature.services.ProfileService;
import com.revature.utilites.SecurityUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {

    private static final String TOKEN_NAME = "Authorization";

    @Autowired
    private ProfileService profileService;

    /**
     * processes login attempt via http request from client
     *
     * @param username
     * @param password
     * @return secure token as json
     *
     * @author marouanekhabbaz
     * refactor the code prevent it from crushing when it have an image 49 - 73	
     *
     */
    @PostMapping("/login")
    @NoAuthIn
    public ResponseEntity<ProfileDTO> login(String username, String password) {
        Profile profile = profileService.login(username, password);

        // log.info(profile);
        if(profile != null) {
            HttpHeaders headers = new HttpHeaders();
            // log.info(profile.getImgurl());   //this will make you wait for a long time
            // log.info(profile);

            Profile pro = new Profile();

            pro.setPid(profile.getPid());
            pro.setUsername(profile.getUsername());
            pro.setPasskey(profile.getPasskey());
            pro.setFirstName(profile.getFirstName());
            pro.setLastName(profile.getLastName());
            pro.setEmail(profile.getEmail());
            pro.setVerification(profile.isVerification());

            headers.set(TOKEN_NAME, SecurityUtil.generateToken(new ProfileDTO(pro)));
            return new ResponseEntity<>(new ProfileDTO(profile), headers, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post request that gets client profile registration info and then checks to see if information is not
     *      a duplicate in the database. If info is not a duplicate, it sets Authorization headers
     *      and calls profile service to add the request body profile to the database.
     * @param profile
     * @return a response with the new profile and status created
     */
    @NoAuthIn
    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> addNewProfile(@Valid @RequestBody ProfileDTO profile) {
        Profile newProfile = profile.toProfile();
        Profile returnedUser = profileService.getProfileByEmail(newProfile);
        if (returnedUser == null) {

            HttpHeaders responseHeaders = new HttpHeaders();
            String token = SecurityUtil.generateToken(new ProfileDTO(newProfile));
            responseHeaders.set(TOKEN_NAME, token);
            Profile tempProfile = profileService.addNewProfile(newProfile);
            ProfileDTO profileDto = new ProfileDTO(tempProfile);
            if (tempProfile == null || profileDto.isIncomplete()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(profileDto, responseHeaders, HttpStatus.CREATED);


        } else {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }
    }

    /**
     * Get Mapping that grabs the profile by the path variable id. It then returns the profile if it is valid.
     *
     * @param id
     * @return Profile object with HttpStatusAccepted or HttpStatusBackRequest
     */
    @GetMapping("{id}")
    public ResponseEntity<ProfileDTO> getProfileByPid(@PathVariable("id")int id) {
        Profile profile = profileService.getProfileByPid(id);
        if (profile!=null) {
            return new ResponseEntity<>(new ProfileDTO(profile), HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Put mapping grabs the updated fields of profile and updates the profile in
     * the database.
     * If no token is sent in the token it fails the Auth and doesn't update the
     * profile.	
     *
     * @param profile
     * @return Updated profile with HttpStatus.ACCEPTED otherwise if invalid returns HttpStatus.BAD_REQUEST
     */
    @PutMapping
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profile) {
        Profile result = profileService.updateProfile(profile.toProfile());
        if (result != null) {

            Profile pro = new Profile();

            pro.setPid(result.getPid());
            pro.setUsername(result.getUsername());
            pro.setPasskey(result.getPasskey());
            pro.setFirstName(result.getFirstName());
            pro.setLastName(result.getLastName());
            pro.setEmail(result.getEmail());
            pro.setImgurl(result.getImgurl());
            pro.setCoverImgurl(result.getCoverImgurl());

            return new ResponseEntity<>(new ProfileDTO(pro), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Adds profile to list of profiles being followed by user
     * @param email email of profile to follow
     * @param req http request including the user's authorization token in the "Authroization" header
     * @return
     */
    @PostMapping("/follow")
    public ResponseEntity<String> newFollower(String email, int id , HttpServletRequest req) {
        Profile profile = profileService.getProfileByPid(id);

        Profile newProfile = profileService.addFollowerByEmail(profile, email);
        if (newProfile != null) {
            HttpHeaders headers = new HttpHeaders();
            Profile pro = new Profile();

            pro.setPid(newProfile.getPid());
            pro.setUsername(newProfile.getUsername());
            pro.setPasskey(newProfile.getPasskey());
            pro.setFirstName(newProfile.getFirstName());
            pro.setLastName(newProfile.getLastName());
            pro.setEmail(newProfile.getEmail());
            headers.set(TOKEN_NAME, SecurityUtil.generateToken(new ProfileDTO(pro)));
            return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Removed profile from list of profiles being followed by the user
     * @param email email of the profile to be unfollowed
     * @param req http request including the user's authorization token in the "Authorization" header
     * @return OK response with new authorization token, bad request response if unsuccessful
     */
    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollow(String email, HttpServletRequest req) {
        Profile follower = (Profile) req.getAttribute("profile");
        follower = profileService.getProfileByEmail(follower);
        if (follower != null) {
            follower = profileService.removeFollowByEmail(follower, email);
            if (follower != null) {
                Profile pro = new Profile();

                pro.setPid(follower.getPid());
                pro.setUsername(follower.getUsername());
                pro.setPasskey(follower.getPasskey());
                pro.setFirstName(follower.getFirstName());
                pro.setLastName(follower.getLastName());
                pro.setEmail(follower.getEmail());

                log.info("Profile successfully unfollowed");
                HttpHeaders headers = new HttpHeaders();
                String newToken = SecurityUtil.generateToken(new ProfileDTO(pro));
                String body = "{\"Authorization\":\"" +
                        newToken
                        + "\"}";
                return new ResponseEntity<>(body, headers, HttpStatus.ACCEPTED);
            }

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    /**
     * Retrieved a page of profiles
     *
     * @param pageNumber pageNumber to be retrieved
     * @return page of profiles for page number requested
     */
    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<List<ProfileDTO>> getAllPostsbyPage(@PathVariable("pageNumber") int pageNumber) {
        List<Profile> profiles = profileService.getAllProfilesPaginated(pageNumber);
        List<ProfileDTO> profileDtos = new LinkedList<>();
        profiles.forEach(p -> profileDtos.add(new ProfileDTO(p)));
        return new ResponseEntity<>(profileDtos, HttpStatus.OK);
    }

    /**
     * Search all fields function in profile 
     *
     * @param query Takes in a String without space at end point /search{query}
     * @return List <Profile> matching search query
     */
    @GetMapping("/search/{query}")
    public ResponseEntity<List<ProfileDTO>> search(@PathVariable("query") String query){
        log.info("/search hit");
        List<Profile> profiles = profileService.search(query);
        List<ProfileDTO> profileDtos = new LinkedList<>();
        profiles.forEach(p -> profileDtos.add(new ProfileDTO(p)));
        return new ResponseEntity<>(profileDtos, new HttpHeaders(), HttpStatus.OK);
    }

    @NoAuthIn
    @GetMapping("/followers/{id}")
    public ResponseEntity<List<ProfileDTO>> getFollowers(@PathVariable("id") int id){


        List<Profile> profiles = profileService.getFollowers(id);


        List<ProfileDTO> profileDtos = new LinkedList<>();
        profiles.forEach(p -> profileDtos.add(new ProfileDTO(p)));

        return new ResponseEntity<>(profileDtos, new HttpHeaders(), HttpStatus.OK);
    }

}