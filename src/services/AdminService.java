package services;

import dao.*;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Lazy
public class AdminService {

    @Autowired(required = false)
    private AdminDao adminDao;
    @Autowired(required = false)
    private CategoryDao categoryDao;
    @Autowired(required = false)
    private ItemDao itemDao;
    @Autowired(required = false)
    private UserDao userDao;
    @Autowired(required = false)
    private OperationLogDao operationLogDao;

    public boolean validateAdmin(String userName, String password) {
        Admin[] admins = adminDao.search(userName, password);
        for (Admin admin : admins) {
            if (admin != null) {
                return true;
            }
        }
        return false;
    }

    public void addCategory(Admin admin, String name) {
        Category category = new Category(name, admin);
        admin.setId(adminDao.getIdIfExist(admin));
        categoryDao.insert(category);
    }

    public HashSet<String> findAllCategory() {
        return categoryDao.findAll();
    }

    public void renameCategory(String oldName, String newName) {
        categoryDao.rename(oldName, newName);
    }

    public void deleteCategoryByName(String name) {
        categoryDao.delete(name);
    }

    public void addProduct(Admin admin, Item item, String categoryName) {
        Category category = new Category(categoryDao.getIdIfExist(categoryName), categoryName, admin);
        item.setCategory(category);
        admin.setId(adminDao.getIdIfExist(admin));
        itemDao.insert(item);
    }

    public HashSet<String> findAllItems() {
        return itemDao.findAll();
    }

    public void deleteItemByName(String name) {
        itemDao.delete(name);
    }

    public List<User> findAllUsers() {
        List<User> allUsers = (userDao.search("", "", true));
        allUsers = allUsers.stream().sorted().collect(Collectors.toList());
        return allUsers;
    }

    public List<OperationLog> getOperationLogs(User user, String sinceDate) {
        List<OperationLog> operationLogs = operationLogDao.search(user, sinceDate);
        return operationLogs;
    }

}
