package com.revature.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class NotificationDTO {

    private int nid;
    private Timestamp date;
    private boolean read;
    private int cid;
    private int fromProfileId;
    private int toProfileId;
    private int postId;


}
