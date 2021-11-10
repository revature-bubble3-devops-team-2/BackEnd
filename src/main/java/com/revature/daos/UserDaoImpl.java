package com.revature.daos;

import com.revature.models.AuthorizationSession;
import com.revature.models.AuthorizationSessionJWT;
import com.revature.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static com.revature.utilites.HibernateUtil.getSessionFactory;

@Repository
public class UserDaoImpl implements UserDao {
    private static SessionFactory sf = getSessionFactory();

    @Override
    public User register(User user, String password) throws NoSuchAlgorithmException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();

        String bcrypt = bCryptPasswordEncoder.encode(password);
        user.setPasswordHash(bcrypt);

        KeyGenerator kg = KeyGenerator.getInstance("HmacSHA256");
        SecretKey tokenKey = kg.generateKey();
        String token = base64Encoder.encodeToString(tokenKey.getEncoded());

        AuthorizationSession authorizationSession = new AuthorizationSession(token, user);
        try (Session sess = sf.openSession()) {
            Transaction tx = sess.beginTransaction();
            sess.save(user);
            System.out.println("Inserted User: " + user);
            sess.save(authorizationSession);
            System.out.println("Inserted Authorization Session: " + authorizationSession);
            tx.commit();
        }
        user.setAuthorizationToken(new AuthorizationSessionJWT(token).getJwt());
        return user;
    }
}
