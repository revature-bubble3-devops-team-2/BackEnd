package com.revature.models;

import com.revature.utilites.SecurityUtil;

import java.sql.Timestamp;


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
}