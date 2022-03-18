package com.revature.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.revature.models.Bookmark;

import java.awt.print.Book;

@Repository
public interface BookmarkDao extends JpaRepository<Bookmark, Integer> {
    List<Bookmark> getAllBookmarkByProfileId(int pid);
}
