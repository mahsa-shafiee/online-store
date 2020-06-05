package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    static {
        try {
            buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Exception while creating sessionFactory obj... ");
            e.printStackTrace();
        }
    }

    public static void buildSessionFactory() {
        sessionFactory = new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();
    }

    public static Session getSession() {
        Session retSession = null;
        try {
            retSession = sessionFactory.getCurrentSession();
        } catch (Throwable t) {
            System.out.println("Exception while getting session... ");
            t.printStackTrace();
        }
        return retSession;
    }

}
