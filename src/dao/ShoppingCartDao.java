package dao;

import dto.*;

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
            System.out.println("Added successfully.");
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
            throw e;
        }
    }

    public void setItemsOfCart(User user) {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT item_id FROM shopping_cart where users_id=?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                ItemDao itemDao = new ItemDao();
                items.addAll(itemDao.search(resultSet.getInt(1)));
                ShoppingCart shoppingcart;
                if (user.getShoppingcart() != null)
                    shoppingcart = user.getShoppingcart();
                else
                    shoppingcart = new ShoppingCart();
                shoppingcart.setItems(items);
                user.setShoppingcart(shoppingcart);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
        }
    }

    public void deleteRow(int item_id) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM shopping_cart WHERE item_id=? LIMIT 1");
            preparedStatement.setInt(1, item_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("Deleted successfully.");
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
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
            System.out.print("SQL exception occurred : ");
            throw e;
        }
    }

    public int getIdFromDataBase(int user_id) throws Exception {
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
            System.out.print("SQL exception occurred : ");
            throw e;
        }
        return -1;
    }
}
