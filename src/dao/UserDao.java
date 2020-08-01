package dao;

import model.User;
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
public class UserDao {

    @Autowired(required = false)
    private Session session;
    @Autowired(required = false)
    private HibernateUtil hibernateUtil;

    public void insert(User user) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("Successfully Registered.");
        } catch (Exception e) {
            System.out.println("Exception occurred while saving user...");
            throw e;
        }
    }

    public List<User> search(String userName, String password, boolean isReport) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "from users u where u.userName like :userName and u.password like :password";
            Query query = session.createQuery(hql);
            if (isReport) {
                query.setParameter("userName", "%" + userName + "%")
                        .setParameter("password", "%" + password + "%");
            } else {
                query.setParameter("userName", userName)
                        .setParameter("password", password);
            }
            List<User> userList = query.list();
            transaction.commit();
            return userList;
        } catch (Exception e) {
            System.out.println("Exception occurred " + e);
        }
        return null;
    }

    public int getIdIfExist(User user) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT id FROM users WHERE userName=:user_name";
            Query query = session.createQuery(hql).setParameter("user_name", user.getUserName());
            Object id = query.uniqueResult();
            transaction.commit();
            return (int) id;
        } catch (Exception e) {
        }
        return -1;
    }
}
