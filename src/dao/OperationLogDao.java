package dao;


import model.OperationLog;
import model.User;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.DateUtil;
import util.HibernateUtil;

import java.util.List;

public class OperationLogDao {
    private Session session;
    static Logger userLogger = Logger.getLogger(OperationLogDao.class);

    public void insert(OperationLog operationLog) {
        MDC.put("authority", operationLog.getAuthority());
        try {
            userLogger.info(operationLog.getOperation());
        } finally {
            MDC.remove("user");
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
