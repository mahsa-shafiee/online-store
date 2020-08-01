package dao;

import model.Order;
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
public class OrderDao {

    @Autowired(required = false)
    private Session session;
    @Autowired(required = false)
    private HibernateUtil hibernateUtil;

    public void insert(Order order) {
        try {
            session = hibernateUtil.getSession();
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
            session = hibernateUtil.getSession();
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
