package dao;

import model.Category;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import util.HibernateUtil;

import java.util.HashSet;
import java.util.List;

@Component
@Lazy
public class CategoryDao {

    @Autowired(required = false)
    private Session session;
    @Autowired(required = false)
    private HibernateUtil hibernateUtil;

    public void insert(Category category) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
            System.out.println("Category added successfully.");
        } catch (Exception e) {
            System.out.println("Exception occurred while saving category...");
            throw e;
        }
    }

    public HashSet<String> findAll() {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT name FROM category";
            List<String> categoryNameList = session.createQuery(hql).list();
            transaction.commit();
            return new HashSet<>(categoryNameList);
        } catch (Exception e) {
            throw e;
        }
    }

    public void rename(String oldName, String newName) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "UPDATE category SET name=:newName WHERE name=:oldName";
            Query query = session.createQuery(hql)
                    .setParameter("newName", newName)
                    .setParameter("oldName", oldName);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete(String name) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "DELETE FROM category WHERE name=:name";
            Query query = session.createQuery(hql).setParameter("name", name);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public int getIdIfExist(String name) {
        try {
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT id FROM category WHERE name=:name";
            Query query = session.createQuery(hql).setParameter("name", name);
            Object id = query.uniqueResult();
            transaction.commit();
            return (int) id;
        } catch (Exception e) {
        }
        return -1;
    }
}
