package dao;

import model.Item;
import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public void insert(Order order) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into orders(date,users_id" +
                    ",item_id) values (?,?,?);");
            preparedStatement.setDate(1, (Date) order.getDate());
            preparedStatement.setInt(2, order.getUser().getId());
            preparedStatement.setInt(3, order.getItem().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Order> findOrdersOfUser(int users_id) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT o.date,i.name,i.description,i.price FROM " +
                    "orders o join item i on o.item_id=i.id WHERE o.users_id=?");
            preparedStatement.setInt(1, users_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setDate(resultSet.getDate(1));
                Item item = new Item();
                item.setName(resultSet.getString(2));
                item.setDescription(resultSet.getString(3));
                item.setPrice(resultSet.getInt(4));
                order.setItem(item);
                orders.add(order);
            }
            preparedStatement.close();
            connection.close();
            return orders;
        } catch (SQLException e) {
            throw e;
        }
    }
}
