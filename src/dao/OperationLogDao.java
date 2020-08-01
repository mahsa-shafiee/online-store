package dao;


import model.OperationLog;
import model.User;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import util.DateUtil;
import util.HibernateUtil;

import java.util.List;

@Component
@Lazy
public class OperationLogDao {

    @Autowired(required = false)
    private Session session;
    @Autowired(required = false)
    private Logger userLogger;
    @Autowired(required = false)
    private HibernateUtil hibernateUtil;
    @Autowired(required = false)
    private DateUtil dateUtil;

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
            session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            String hql = "FROM operationlog where authority=:authority and date>=:since and date<=:end";
            Query query = session.createQuery(hql)
                    .setParameter("authority", user.getUserName())
                    .setParameter("since", dateUtil.convertStrToDate(sinceDate))
                    .setParameter("end", dateUtil.addOneMonth(sinceDate));
            List<OperationLog> operationLogs = query.list();
            transaction.commit();
            return operationLogs;
        } catch (Exception e) {
        }
        return null;
    }
}
