package dao;


import model.OperationLog;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.DateUtil;
import util.HibernateUtil;

import java.util.List;

public class OperationLogDao {
    private Session session;

    public void insert(OperationLog operationLog) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(operationLog);
            transaction.commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<OperationLog> search(User user, String sinceDate) {
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "FROM operationLog where authority=:authority and date>=:since and date<=:end";
            Query query = session.createQuery(hql)
                    .setParameter("authority", user.getUserName())
                    .setParameter("since", DateUtil.convertStrToDate(sinceDate))
                    .setParameter("end", DateUtil.addOneMonth(sinceDate));
            List<OperationLog> operationLogs = query.list();
            transaction.commit();
            return operationLogs;
        } catch (Exception e) {
        }
        return null;
    }
}
