package dao;

import dto.Admin;
import dto.Category;
import dto.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Item[] showItemsOfCategory(int category_id) {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item where category_id=?");
            preparedStatement.setInt(1, category_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Item[] items = new Item[1];
            int index = 0;
            while (resultSet.next()) {
                if (index > 0) {
                    Item[] tmp = new Item[items.length + 1];
                    for (int i = 0; i < items.length; i++)
                        if (tmp[i] == null)
                            tmp[i] = items[i];
                    items = tmp;
                }
                Item item = new Item();
                item.setId(resultSet.getInt(1));
                item.setName(resultSet.getString(2));
                item.setDescription(resultSet.getString(3));
                item.setPrice(resultSet.getInt(4));
                item.setStock(resultSet.getInt(5));
                Category category = new Category();
                category.setId(resultSet.getInt(6));
                item.setCategory(category);
                Admin admin = new Admin();
                admin.setId(resultSet.getInt(7));
                item.setAdmin(admin);
                items[index] = item;
                index++;
            }
            preparedStatement.close();
            connection.close();
            return items;
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
        }
        return null;
    }

    public List<Item> search(int id) {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT i.*,c.name FROM item i join category c" +
                    " on c.id=i.category_id where i.id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getInt(1));
                item.setName(resultSet.getString(2));
                item.setDescription(resultSet.getString(3));
                item.setPrice(resultSet.getInt(4));
                item.setStock(resultSet.getInt(5));
                Category category = new Category();
                category.setId(resultSet.getInt(6));
                category.setName(resultSet.getString(8));
                item.setCategory(category);
                Admin admin = new Admin();
                admin.setId(resultSet.getInt(7));
                item.setAdmin(admin);
                items.add(item);
            }
            preparedStatement.close();
            connection.close();
            return items;
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
        }
        return null;
    }

    public void setStock(int stock, Item item) throws SQLException {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item SET stock =? WHERE id =?;");
            preparedStatement.setInt(1, stock);
            preparedStatement.setLong(2, item.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
            throw e;
        }
    }

    public int getId(String name) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM item WHERE name=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                return id;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
            throw e;
        }
        return -1;
    }
}
