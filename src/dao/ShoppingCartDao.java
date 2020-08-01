package dao;

import model.Item;
import model.ShoppingCart;
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
public class ShoppingCartDao {

    @Autowired(required = false)
    private Session session;
    @Autowired(required = false)
    private HibernateUtil hibernateUtil;

    public void insert(ShoppingCart shoppingCart) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception occurred while saving cart...");
            throw e;
        }
    }

    public List<Item> findItems(User user) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT item FROM shopping_cart where users_id=:users_id";
            Query query = session.createQuery(hql).setParameter("users_id", user.getId());
            List<Item> items = query.list();
            transaction.commit();
            return items;
        } catch (Exception e) {
        }
        return null;
    }

    public void deleteRow(int item_id) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "select id FROM shopping_cart WHERE item.id=:item_id";
            Query query = session.createQuery(hql).setParameter("item_id", item_id).setMaxResults(1);
            Object id = query.uniqueResult();
            session.createQuery("DELETE FROM shopping_cart WHERE id=:id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteCartOfUser(int user_id) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "DELETE FROM shopping_cart WHERE users_id=:users_id";
            Query query = session.createQuery(hql).setParameter("users_id", user_id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public int getIdIfExist(int user_id) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String sql = "SELECT c.* FROM shopping_cart c WHERE c.users_id= ?";
            Query query = session.createNativeQuery(sql, ShoppingCart.class).setParameter(1, user_id);
            List<ShoppingCart> cart = query.list();
            transaction.commit();
            return cart.get(0).getId();
        } catch (Exception e) {
        }
        return -1;
    }
}
