package dao;

import dto.Address;
import dto.User;

import java.sql.*;

public class UserDao {
    protected static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/online-store", "root", null);
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public void insert(User user) throws Exception {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users(user_name,password" +
                    ",first_name,last_name,moblie_number,email_address,home_address_id)" +
                    " values (?,?,?,?,?,?,?);");
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getMobileNumber());
            preparedStatement.setString(6, user.getEmailAddress());
            preparedStatement.setInt(7, user.getHomeAddress().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("Successfully Registered.");
        } catch (SQLException e) {
            System.out.print("SQL exception occurred : ");
            throw e;
        }
    }

    public User[] search(String userName, String password) {
        try {
            Connection connection = getConnection();
            String query = "select * from users u join address a on a.id=u.home_address_id where u.user_name=? and u.password =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            User[] users = new User[1];
            int index = 0;
            while (resultSet.next()) {
                if (index > 0) {
                    User[] tmp = new User[users.length + 1];
                    for (int i = 0; i < users.length; i++)
                        if (tmp[i] == null)
                            tmp[i] = users[i];
                    users = tmp;
                }
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setFirstName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setMobileNumber(resultSet.getString(6));
                user.setEmailAddress(resultSet.getString(7));
                Address address = new Address();
                user.setHomeAddress(address);
                address.setId(resultSet.getInt(8));
                address.setState(resultSet.getString(10));
                address.setCity(resultSet.getString(11));
                address.setStreet(resultSet.getString(12));
                address.setPostalCode(resultSet.getLong(13));
                users[index] = user;
                index++;
            }
            preparedStatement.close();
            connection.close();
            return users;

        } catch (SQLException e) {
            System.out.println("SQL exception occurred " + e);
        }
        return null;
    }

    public int getIdFromDataBase(User user) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users WHERE user_name=?");
            preparedStatement.setString(1, user.getUserName());
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
