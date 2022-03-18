package com.revature.repositories;

import com.revature.models.Post;
import com.revature.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.revature.models.Bookmark;
import java.util.List;


@Repository
public interface BookmarkRepo extends JpaRepository<Bookmark, Integer> {

    List<Bookmark> getBookmarkByProfileId(int pid);
    Bookmark createBookmark(int BookmarkId, Post post, Profile profile);
}
