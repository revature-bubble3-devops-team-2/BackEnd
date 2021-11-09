package com.revature.models;

import com.revature.utilites.SecurityUtil;

import java.sql.Timestamp;
import java.util.Objects;

public class Post {
    private int psid;
    private Profile creator;
    private String body;
    private String imgURL;
    private Timestamp datePosted;

    public Post() {
        psid = SecurityUtil.getId();
    }

    public Post(int psid, Profile creator, String body, String imgURL, Timestamp datePosted) {
        this.psid = psid;
        this.creator = creator;
        this.body = body;
        this.imgURL = imgURL;
        this.datePosted = datePosted;
    }

    @Override
    public String toString() {
        return "Post{" +
                "psid=" + psid +
                ", creator=" + creator +
                ", body='" + body + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", datePosted=" + datePosted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return psid == post.psid && Objects.equals(creator, post.creator) && Objects.equals(body, post.body) && Objects.equals(imgURL, post.imgURL) && Objects.equals(datePosted, post.datePosted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(psid, creator, body, imgURL, datePosted);
    }
}
