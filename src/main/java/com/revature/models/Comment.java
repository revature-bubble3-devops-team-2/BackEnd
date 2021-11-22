package com.revature.models;

import com.revature.utilites.SecurityUtil;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;


@Component
public class Comment {
    private final int cid;
    private Profile writer;
    private Post post;
    private Timestamp dateCreated;
    private Comment previous;

    public Comment() {
        cid = SecurityUtil.getId();
    }

    public Comment(int cid, Profile writer, Post post, Timestamp dateCreated, Comment previous) {
        this.cid = cid;
        this.writer = writer;
        this.post = post;
        this.dateCreated = dateCreated;
        this.previous = previous;
    }
}
