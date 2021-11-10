package com.revature.utilites;

import com.revature.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null) {

            Properties settings = new Properties();
            settings.put(Environment.DRIVER, System.getenv("DB_DRIVER"));
            settings.put(Environment.URL, System.getenv("DB_URL"));
            settings.put(Environment.USER, System.getenv("DB_User"));
            settings.put(Environment.PASS, System.getenv("DB_Password"));
            settings.put(Environment.DIALECT, System.getenv("DB_DIALECT"));

            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "validate");

            sessionFactory = new Configuration()
                    .setProperties(settings)
                    .addAnnotatedClass(Profile.class)
                    .addAnnotatedClass(Post.class)
                    .addAnnotatedClass(Comment.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session getSession(){
        return getSessionFactory().openSession();
    }
}
