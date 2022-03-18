package com.revature.dto;

import com.revature.models.Notification;
import com.revature.models.Post;
import com.revature.models.Comment;
import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDTO {

    private int nid;
    private boolean isRead;
    private Comment cid;
    private Profile fromProfileId;
    private Profile toProfileId;
    private Post postId;

    public Notification toNewNotification() {
        if(cid == null) {
            return new Notification(isRead, fromProfileId, toProfileId, postId);
        }
        return new Notification(isRead, cid, fromProfileId, toProfileId, postId);
    }

    public Notification toNotification() {
        if(cid == null) {
            if(postId == null) {
                return new Notification(nid, isRead, fromProfileId, toProfileId);
            }
            return new Notification(nid, isRead, fromProfileId, toProfileId, postId);
        }
        return new Notification(nid, isRead, cid, fromProfileId, toProfileId, postId);
    }

    public NotificationDTO() { }

    public NotificationDTO(boolean isRead, Comment cid, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.isRead = isRead;
        this.cid = cid;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.postId = postId;
    }

    public NotificationDTO(int nid, boolean isRead, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.nid = nid;
        this.isRead = isRead;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.postId = postId;
    }

    public NotificationDTO(int nid, boolean isRead, Profile fromProfileId, Profile toProfileId) {
        this.nid = nid;
        this.isRead = isRead;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
    }

    public NotificationDTO(boolean isRead, Profile fromProfileId, Profile toProfileId, Post postId) {
        this.isRead = isRead;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.postId = postId;
    }

    public NotificationDTO(Notification notification) {
        if (notification != null) {
            if (notification.getNid() > 0) {
                nid = notification.getNid();
            }
            isRead = notification.isRead();
            cid = notification.getCid();
            fromProfileId = notification.getFromProfileId();
            toProfileId = notification.getToProfileId();
            postId = notification.getPid();
        }
    }
}
