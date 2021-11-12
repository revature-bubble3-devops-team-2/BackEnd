package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Timestamp;


@Component
@Entity
@Data
public class Comment {
    @Id
    private int cid;

    @ManyToOne
    private Profile writer;

    @ManyToOne
    private Post post;
    private Timestamp dateCreated;

    @OneToOne
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
