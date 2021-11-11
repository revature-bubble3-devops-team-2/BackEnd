package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
@Entity
@Data @AllArgsConstructor
public class Profile {

    @Id
    private int pid;

    //@NotNull
    @Column(unique = true)
    @Size(min=3, max = 50)
    private String username;

   // @NotNull
    @Size(min=3, max = 50)
    private String passkey;

    //@NotNull
    @Column(name = "firstname")
    @Size(min=3, max = 50)
    private String firstName;

    //@NotNull
    @Column(name = "lastname")
    @Size(min=3, max = 50)
    private String lastName;

    //@NotNull
    @Column(unique = true)
    @Size(min=3, max = 50)
    private String email;

    public Profile() {
        pid = SecurityUtil.getId();
    }

}
