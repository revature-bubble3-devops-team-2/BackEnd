package com.revature.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component
@Entity
@Data
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int infoId;
    @OneToOne
    private Profile profile;
    private String aboutMe;
    private Date birthday;
    private String pronouns;

}