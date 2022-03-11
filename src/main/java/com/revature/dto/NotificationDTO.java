package com.revature.dto;

import com.revature.models.Notification;
import com.revature.models.Post;
import com.revature.models.Comment;
import com.revature.models.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class NotificationDTO {

    private int nid;
    private Timestamp nTimestamp;
    private boolean isRead;
    private Comment cid;
    private Profile fromProfileId;
    private Profile toProfileId;
    private Post postId;

    public Notification toNotification() {
        System.out.println("nTimestamp:" + nTimestamp + "TEST nid:" + nid + " isRead:" + isRead + " cid" + cid + " fromProfileId:" + fromProfileId);
        return new Notification(nid, nTimestamp, isRead, cid, fromProfileId, toProfileId, postId);
    }

    public NotificationDTO() { }

    public NotificationDTO(Timestamp nTimestamp, boolean isRead, Comment cid, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.nid = nid;
        this.nTimestamp = nTimestamp;
        this.isRead = isRead;
        this.cid = cid;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.postId = postId;
    }

    public NotificationDTO(Notification notification) {
        if (notification != null) {
            nid = notification.getNid();
            nTimestamp = notification.getNTimestamp();
            isRead = notification.isRead();
            cid = notification.getCid();
            fromProfileId = notification.getFromProfileId();
            toProfileId = notification.getToProfileId();
            postId = notification.getPid();
        } else {
            System.out.println("NULL?");
        }
    }
}
