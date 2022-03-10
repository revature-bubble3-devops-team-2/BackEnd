package com.revature.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.revature.utilites.SecurityUtil;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "bookmark")
@AllArgsConstructor
public class Bookmark
{
    @Id
    @Column(name = "bookmark_id", unique = true, nullable = false)
    private int bid;

    @Column(name = "post_id", unique = true, nullable = false, referenceC)


}
