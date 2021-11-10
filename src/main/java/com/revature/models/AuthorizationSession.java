package com.revature.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name = "authorization_session", schema = "public")
public class AuthorizationSession {
    @Id
    @Column(name = "token")
    String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    public AuthorizationSession() {
        super();
    }

    public AuthorizationSession(String token, User user) {
        super();
        this.token = token;
        this.user = user;
    }
}
