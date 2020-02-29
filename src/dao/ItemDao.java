package dao;

import dto.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDao {
    public void insert(Item item) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into item(name,description" +
                    ",price,stock,category_id,admin_id) values (?,?,?,?,?,?);");
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setLong(3, item.getPrice());
            preparedStatement.setInt(4, item.getStock());
            preparedStatement.setInt(5, item.getCategory().getId());
            preparedStatement.setInt(6, item.getAdmin().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("Added successfully.");
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
            throw e;
        }
    }

    public void delete(String name) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM item WHERE name=?");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("Deleted successfully.");
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
            throw e;
        }
    }
    public void showAll() throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                System.out.println(name);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
            throw e;
        }
    }
}
