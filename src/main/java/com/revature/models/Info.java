package com.revature.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Component
@Entity
@Data
public class Info {
    @Id
    private int infoId;
    @OneToOne
    private Profile profile;
    private String aboutMe;
    private Date birthday;
    private String pronouns;

}
