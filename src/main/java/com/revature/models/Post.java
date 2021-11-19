package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.revature.utilites.SecurityUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Component
@Entity @Table(name = "post")
@Getter @Setter @AllArgsConstructor
public class Post {

    @Id
    @Column(name = "post_id", unique = true, nullable = false)
    private int psid;

    @Autowired
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile creator;

    @Column
    private String body;

    @Column(name = "image_url")
    private String imgURL;

    @Column(name = "date_posted", nullable = false)
    private Timestamp datePosted;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    @JsonIgnore
    private Set<Profile> likes = new LinkedHashSet<>();

    public Post() {
        super();
        psid = SecurityUtil.getId();
    }

    public Post(int psid, Profile creator, String body, String imgURL, Timestamp dateposted) {
        this.psid = psid;
        this.creator = creator;
        this.body = body;
        this.imgURL = imgURL;
        this.datePosted = dateposted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return psid == post.psid && creator.equals(post.creator) && Objects.equals(body, post.body) && Objects.equals(imgURL, post.imgURL) && datePosted.equals(post.datePosted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(psid, creator, body, imgURL, datePosted);
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
}

