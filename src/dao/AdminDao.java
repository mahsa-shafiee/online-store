package dao;

import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    public Admin[] search(String name, String password) {
        try {
            Connection connection = UserDao.getConnection();
            String query = "select * from admin a where a.name=? and a.password =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            Admin[] admins = new Admin[1];
            int index = 0;
            while (resultSet.next()) {
                if (index > 0) {
                    Admin[] tmp = new Admin[admins.length + 1];
                    for (int i = 0; i < admins.length; i++)
                        if (tmp[i] == null)
                            tmp[i] = admins[i];
                    admins = tmp;
                }
                Admin admin = new Admin();
                admin.setId(resultSet.getInt(1));
                admin.setName(resultSet.getString(2));
                admin.setPassword(resultSet.getString(3));
                admins[index] = admin;
                index++;
            }
            preparedStatement.close();
            connection.close();
            return admins;
        } catch (SQLException e) {
        }
        return null;
    }

    public int getIdIfExist(Admin admin) throws Exception {
        try {
            Connection connection = UserDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM admin WHERE name=? and password=?");
            preparedStatement.setString(1, admin.getName());
            preparedStatement.setString(2, admin.getPassword());
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
