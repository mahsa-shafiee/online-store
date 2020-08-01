package services;

import dao.AddressDao;
import dao.OperationLogDao;
import dao.UserDao;
import model.Address;
import model.OperationLog;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import util.DateUtil;
import util.OperationType;

import java.util.List;

@Component
@Lazy
public class UserService {

    @Autowired(required = false)
    private UserDao userDao;
    @Autowired(required = false)
    private AddressDao addressDao;
    @Autowired(required = false)
    private OperationLogDao operationLogDao;
    @Autowired(required = false)
    private DateUtil dateUtil;

    public User validateUser(String userName, String password) {
        List<User> users = this.userDao.search(userName, password, false);
        for (User user : users) {
            if (user != null)
                return user;
        }
        return null;
    }

    public void registerNewUser(User user, Address address) {
        addressDao.insert(address);
        address.setId(addressDao.getIdIfExist(address));
        userDao.insert(user);
        user.setId(userDao.getIdIfExist(user));
    }

    public void recordNewLog(OperationType operation, String authority) {
        OperationLog operationLog = new OperationLog(operation, authority, dateUtil.getCurrentDate(), dateUtil.getCurrentTime());
        operationLogDao.insert(operationLog);
    }
}
