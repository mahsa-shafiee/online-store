package dao;

import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class CategoryDao {
    public void insert(Category category) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into category(name,admin_id) values (?,?);");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getAdmin().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public HashSet<String> findAll() throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM category");
            ResultSet resultSet = preparedStatement.executeQuery();
            HashSet<String> categoryNames = new HashSet<>();
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                categoryNames.add(name);
            }
            preparedStatement.close();
            connection.close();
            return categoryNames;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void rename(String oldName, String newName) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE category SET name=? WHERE name=?;");
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, oldName);
            int rowAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void delete(String name) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM category WHERE name=?");
            preparedStatement.setString(1, name);
            int rowAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public int getIdIfExist(String name) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM category WHERE name=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                return id;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
        return -1;
    }
}
