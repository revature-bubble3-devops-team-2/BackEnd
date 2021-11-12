package com.revature.repositories;

import com.revature.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Integer> {

    /**
     * queries the database for profile using email as the input
     *
     * @param email
     * @return a user profile
     */
    @Query("SELECT p FROM Profile p WHERE p.email = ?1")
    Profile findProfileByEmail(String email);
}