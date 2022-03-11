package com.revature.models;

import java.sql.Timestamp;
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

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "Lid", referencedColumnName = "post_id")
//    private String like;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post pid;

    @Column(name = "timestamp")
    private Timestamp nTimestamp;

    @Column(name = "read")
    private boolean read;



}
