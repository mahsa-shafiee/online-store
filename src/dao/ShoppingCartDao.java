package dao;

import model.Item;
import model.ShoppingCart;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDao {
    public void insert(ShoppingCart shoppingCart) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into shopping_cart(users_id,item_id)" +
                    " values (?,?);");
            preparedStatement.setInt(1, shoppingCart.getUser().getId());
            preparedStatement.setInt(2, shoppingCart.getItem().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Item> findItems(User user) {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT item_id FROM shopping_cart where users_id=?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Item> search = new ArrayList<>();
            while (resultSet.next()) {
                ItemDao itemDao = new ItemDao();
                search.add(itemDao.search(resultSet.getInt(1)).get(0));
            }
            preparedStatement.close();
            connection.close();
            return search;
        } catch (SQLException e) {
        }
        return null;
    }

    public void deleteRow(int item_id) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM shopping_cart WHERE item_id=? LIMIT 1");
            preparedStatement.setInt(1, item_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void deleteCartOfUser(int user_id) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM shopping_cart WHERE users_id=?");
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public int getIdIfExist(int user_id) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM shopping_cart WHERE users_id=?");
            preparedStatement.setInt(1, user_id);
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
