package dao;

import model.Address;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import util.HibernateUtil;

@Component
@Lazy
public class AddressDao {

    @Autowired(required = false)
    private Session session;
    @Autowired(required = false)
    private HibernateUtil hibernateUtil;

    public void insert(Address address) {
        try {
            session = hibernateUtil.getSession();
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
            session = hibernateUtil.getSession();
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
