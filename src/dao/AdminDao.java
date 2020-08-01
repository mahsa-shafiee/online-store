package dao;

import model.Admin;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import util.HibernateUtil;

import java.util.List;

@Component
@Lazy
public class AdminDao {

    @Autowired(required = false)
    private Session session;
    @Autowired(required = false)
    private HibernateUtil hibernateUtil;

    public Admin[] search(String name, String password) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "from admin a where a.name=:name and a.password =:password";
            Query query = session.createQuery(hql)
                    .setParameter("name", name)
                    .setParameter("password", password);
            List<Admin> adminList = query.list();
            transaction.commit();
            return adminList.toArray(new Admin[0]);
        } catch (Exception e) {
        }
        return null;
    }

    public int getIdIfExist(Admin admin) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT id FROM admin WHERE name=:name and password=:password";
            Query query = session.createQuery(hql)
                    .setParameter("name", admin.getName())
                    .setParameter("password", admin.getPassword());
            Object id = query.uniqueResult();
            transaction.commit();
            return (int) id;
        } catch (Exception e) {
        }
        return -1;
    }
}
