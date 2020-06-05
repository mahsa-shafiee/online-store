package services;

import dao.AddressDao;
import dao.UserDao;
import model.Address;
import model.User;
import util.DateUtil;
import util.OperationType;

import java.util.List;

public class UserService {

    private UserDao userDao = new UserDao();
    private AddressDao addressDao = new AddressDao();

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

}
