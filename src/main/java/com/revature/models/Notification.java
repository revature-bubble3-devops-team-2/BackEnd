package com.revature.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
public class Notification {

    @Id
    @Column(name = "nid", unique = true, nullable = false)
    private int nid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_profile_id", referencedColumnName = "profile_id")
    private Profile fromProfileId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_profile_id", referencedColumnName = "profile_id")
    private Profile toProfileId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    private Comment cid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post pid;

    @Column(name = "isRead")
    private boolean isRead;

    public Notification() { }

    public Notification(int nid, boolean isRead, Comment cid, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.nid = nid;
        this.isRead = isRead;
        this.cid = cid;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.pid = postId;
    }

    public Notification(int nid, boolean isRead, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.nid = nid;
        this.isRead = isRead;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.pid = postId;
    }

    public Notification(int nid, boolean isRead, Comment cid, Profile fromProfileId, Profile toProfileId) {
        this.nid = nid;
        this.isRead = isRead;
        this.cid = cid;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
    }
}
