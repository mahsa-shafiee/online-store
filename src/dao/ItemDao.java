package dao;

import model.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.HashSet;
import java.util.List;

public class ItemDao {
    private Session session;

    public void insert(Item item) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
            System.out.println("Item added successfully.");
        } catch (Exception e) {
            System.out.println("Exception occurred while saving item...");
            throw e;
        }
    }

    public void delete(String name) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "DELETE FROM item WHERE name=:name";
            Query query = session.createQuery(hql).setParameter("name", name);
            int rowAffected = query.executeUpdate();
            transaction.commit();
            System.out.println("Deleted successfully.(" + rowAffected + " Rows Affected)");
        } catch (Exception e) {
            System.out.print("Exception occurred : ");
            throw e;
        }
    }

    public HashSet<String> findAll() {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT name FROM item";
            List<String> itemList = session.createQuery(hql).list();
            transaction.commit();
            return new HashSet<>(itemList);
        } catch (Exception e) {
            throw e;
        }
    }

    public Item[] showItemsOfCategory(int category_id) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "FROM item i where i.category.id=:category_id";
            Query query = session.createQuery(hql).setParameter("category_id", category_id);
            List<Item> itemList = query.list();
            transaction.commit();
            return itemList.toArray(new Item[0]);
        } catch (Exception e) {
        }
        return null;
    }

    public List<Item> search(int id) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT i,c.name FROM item i join category c on c.id=i.category where i.id=:id";
            Query query = session.createQuery(hql).setParameter("id", id);
            List<Item> itemList = query.list();
            transaction.commit();
            return itemList;
        } catch (Exception e) {
        }
        return null;
    }

    public void setStock(int stock, Item item) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "UPDATE item SET stock =:stock WHERE id =:id";
            Query query = session.createQuery(hql).setParameter("stock", stock).setParameter("id", item.getId());
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public int getIdIfExist(String name) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT id FROM item WHERE name=:name";
            Query query = session.createQuery(hql).setParameter("name", name);
            Object id = query.uniqueResult();
            transaction.commit();
            session.close();
            return (int) id;
        } catch (Exception e) {
        }
        return -1;
    }

    public int getStock(Item item) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT stock FROM item WHERE name=:name";
            Query query = session.createQuery(hql).setParameter("name", item.getName());
            Object stock = query.uniqueResult();
            transaction.commit();
            return (int) stock;
        } catch (Exception e) {
        }
        return -1;
    }
}
