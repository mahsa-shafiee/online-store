package services;

import dao.*;
import model.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class AdminService {

    AdminDao adminDao = new AdminDao();
    CategoryDao categoryDao = new CategoryDao();
    ItemDao itemDao = new ItemDao();
    UserDao userDao = new UserDao();


    public boolean validateAdmin(String userName, String password) {
        Admin[] admins = adminDao.search(userName, password);
        for (Admin admin : admins) {
            if (admin != null) {
                return true;
            }
        }
        return false;
    }

    public void addCategory(Admin admin, String name) throws Exception {
        Category category = new Category(name, admin);
        admin.setId(adminDao.getIdIfExist(admin));
        categoryDao.insert(category);
    }

    public HashSet<String> findAllCategory() throws Exception {
        return categoryDao.findAll();
    }

    public void renameCategory(String oldName, String newName) throws Exception {
        categoryDao.rename(oldName, newName);
    }

    public void deleteCategoryByName(String name) throws Exception {
        categoryDao.delete(name);
    }

    public void addProduct(Admin admin, Item item, String categoryName) throws Exception {
        Category category = new Category(categoryDao.getIdIfExist(categoryName),
                categoryName, admin);
        item.setCategory(category);
        admin.setId(adminDao.getIdIfExist(admin));
        itemDao.insert(item);
    }

    public HashSet<String> findAllItems() throws Exception {
        return itemDao.findAll();
    }

    public void deleteItemByName(String name) throws Exception {
        itemDao.delete(name);
    }

    public List<User> findAllUsers() {
        List<User> allUsers = (userDao.search("", "", true));
        allUsers = allUsers.stream().sorted().collect(Collectors.toList());
        return allUsers;
    }


}
