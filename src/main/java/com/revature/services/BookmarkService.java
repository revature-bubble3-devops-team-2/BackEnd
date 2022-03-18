package com.revature.services;
import com.revature.repositories.BookmarkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.revature.models.*;

import javax.validation.constraints.Past;
import java.util.List;

@Service
@Transactional
public class BookmarkService {
    BookmarkRepo bkr;

    public BookmarkService(){

    }

    @Autowired
    public BookmarkService(BookmarkRepo bkr){this.bkr = bkr;}

    //create bookmark
    public Bookmark createBookmark(int BookmarkId,Post post, Profile profile){
        Bookmark bk = new Bookmark(BookmarkId,post,profile);
        return bkr.save(bk);
    }

    //get bookmark
    public List<Bookmark> getAllBookmarkByProfileId(int pid){
        return bkr.getBookmarkByProfileId(pid);
    }


}
