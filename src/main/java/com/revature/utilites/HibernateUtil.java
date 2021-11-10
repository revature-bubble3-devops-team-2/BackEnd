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
            settings.put(Environment.DRIVER, "org.postgresql.Driver");
            settings.put(Environment.URL, System.getenv("BubbleDB_URL"));
            settings.put(Environment.USER, System.getenv("BubbleDB_User"));
            settings.put(Environment.PASS, System.getenv("BubbleDB_Password"));
            settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "validate");

            sessionFactory = new Configuration()
                    .setProperties(settings)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(AuthorizationSession.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session getSession(){
        return getSessionFactory().openSession();
    }
}
