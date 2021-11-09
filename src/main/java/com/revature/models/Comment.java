package com.revature.models;

import com.revature.utilites.SecurityUtil;

import java.sql.Timestamp;
import java.util.Objects;

public class Comment {
    private int cid;
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

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", writer=" + writer +
                ", post=" + post +
                ", dateCreated=" + dateCreated +
                ", previous=" + previous +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return cid == comment.cid && Objects.equals(writer, comment.writer) && Objects.equals(post, comment.post) && Objects.equals(dateCreated, comment.dateCreated) && Objects.equals(previous, comment.previous);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, writer, post, dateCreated, previous);
    }
}
