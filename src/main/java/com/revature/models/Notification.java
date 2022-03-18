package com.revature.models;

import javax.persistence.*;

import com.revature.utilites.SecurityUtil;
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

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    private Comment cid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post pid;

    @Column(name = "isRead")
    private boolean isRead;

    public Notification() {
        super();
        this.nid = SecurityUtil.getId();
    }

    public Notification(int nid, boolean isRead, Comment cid, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.nid = SecurityUtil.getId();
        this.isRead = isRead;
        this.cid = cid;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.pid = postId;
    }

    public Notification(int nid, boolean isRead, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.nid = SecurityUtil.getId();
        this.isRead = isRead;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.pid = postId;
    }

    public Notification(int nid, boolean isRead, Profile fromProfileId, Profile toProfileId) {
        this.nid = SecurityUtil.getId();
        this.isRead = isRead;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
    }

    public Notification(boolean isRead, Comment cid, Profile fromProfileId, Profile toProfileId) {
        this.isRead = isRead;
        this.cid = cid;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
    }

    public Notification(boolean isRead, Comment cid, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.nid = SecurityUtil.getId();
        this.isRead = isRead;
        this.cid = cid;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.pid = postId;
    }

    public Notification(boolean isRead, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.nid = SecurityUtil.getId();
        this.isRead = isRead;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.pid = postId;

    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "nid=" + nid +
                ", fromProfileId=" + fromProfileId +
                ", toProfileId=" + toProfileId +
                ", cid=" + cid +
                ", pid=" + pid +
                ", isRead=" + isRead +
                '}';
    }
}
