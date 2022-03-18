package com.revature.models;

import java.sql.Timestamp;

import javax.persistence.*;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "bookmark")
@Data

public class Bookmark {
    @Id
    @Column(name = "bookmark_id", unique = true, nullable = false)
    private int bookmarkId;

    @OneToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;



    public Bookmark() {
    }


    public Bookmark(int bookmark_id, Post post, Profile profile) {
        this.bookmarkId = bookmarkId;
        this.post = post;
        this.profile = profile;
    }

    public int getBookmarkId() { return bookmarkId; }

    public void setBookmarkId(int bookmarkId) { this.bookmarkId = bookmarkId; }

    public Post getPost() {return post; }

    public void setPost(Post post) { this.post = post;}

    public Profile getProfile() {return profile; }

    public void setProfile(Profile profile) {this.profile = profile; }


    @Override
    public String toString() {
        return "Bookmark{" +
                "bookmarkId=" + bookmarkId +
                ", post=" + post +
                ", profile=" + profile +
                '}';
    }


}




