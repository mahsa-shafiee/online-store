package services;

import dao.AddressDao;
import dao.OperationLogDao;
import dao.UserDao;
import model.Address;
import model.OperationLog;
import model.User;
import util.DateUtil;
import util.OperationType;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserService {

    private UserDao userDao = new UserDao();
    private AddressDao addressDao = new AddressDao();
    private OperationLogDao operationLogDao = new OperationLogDao();

    public User validateUser(String userName, String password) {
        System.out.println("Please wait...");
        List<User> users = this.userDao.search(userName, password, false);
        for (User user : users) {
            if (user != null)
                return user;
        }
        return null;
    }

    public void registerNewUser(User user, Address address) {
        System.out.println("Please wait...");
        addressDao.insert(address);
        address.setId(addressDao.getIdIfExist(address));
        userDao.insert(user);
        user.setId(userDao.getIdIfExist(user));
    }

    public void recordNewLog(OperationType operation, String authority) {
        OperationLog operationLog = new OperationLog(operation, authority, DateUtil.getCurrentDate(), DateUtil.getCurrentTime());
        operationLogDao.insert(operationLog);
    }
}
