package ru.innopolis.smoldyrev.common.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by smoldyrev on 18.03.17.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
