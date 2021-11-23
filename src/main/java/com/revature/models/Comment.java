package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;


@Component
@Entity @Table(name = "comment")
@Data @AllArgsConstructor
public class Comment {
    @Id
    private int cid;

    @ManyToOne
    private Profile writer;

    @ManyToOne
    private Post post;

    @Column(name = "cbody")
    private String cBody;

    @Column(name = "date_created")
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
