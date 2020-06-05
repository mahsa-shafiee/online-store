package dao;

import model.Address;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class AddressDao {
    private Session session;

    public void insert(Address address) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(address);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception occurred while saving address...");
            throw e;
        }
    }

    public int getIdIfExist(Address address) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT id FROM address WHERE state=:state and city=:city and street=:street and postalCode=:postal_code";
            Query query = session.createQuery(hql)
                    .setParameter("state", address.getState())
                    .setParameter("city", address.getCity())
                    .setParameter("street", address.getStreet())
                    .setParameter("postal_code", address.getPostalCode());
            Object id = query.uniqueResult();
            transaction.commit();
            return (int) id;
        } catch (Exception e) {
        }
        return -1;
    }
}
