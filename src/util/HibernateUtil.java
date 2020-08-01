package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class HibernateUtil {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Exception while creating sessionFactory obj... ");
            e.printStackTrace();
        }
        return null;
    }

    public Session getSession() {
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
