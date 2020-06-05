package dao;

import model.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class OrderDao {
    private Session session;

    public void insert(Order order) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception occurred while finalizing order...");
            throw e;
        }
    }

    public List<Order> findOrdersOfUser(int users_id) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "FROM orders o WHERE o.user.id=:users_id";
            Query query = session.createQuery(hql).setParameter("users_id", users_id);
            List<Order> orders = query.list();
            transaction.commit();
            return orders;
        } catch (Exception e) {
            throw e;
        }
    }
}
