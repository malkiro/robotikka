package com.devstack.pos.dao;

import com.devstack.pos.util.PasswordManager;
import dto.UserDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {
    //==========User management================
    public static boolean createUser(String email, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka",
                "root","1234");
        String sql="INSERT INTO user VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, PasswordManager.encryptPassword(password));
        return preparedStatement.executeUpdate()>0;
    }

    public static UserDto findUser(String email) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka",
                "root", "1234");
        String sql = "SELECT * FROM user WHERE email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);

        ResultSet set = preparedStatement.executeQuery();
        if (set.next()){ //set.next() method eka true wenawa kiyanne user knk awith
            return new UserDto(
                    set.getString(1),
                    set.getString(2)
            );
        }
            return null;
    }
    //==========User management================




    //==========Customer management================
    public static boolean createCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka",
                "root","1234");
        String sql="INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, contact);
        preparedStatement.setDouble(4, salary);
        return preparedStatement.executeUpdate()>0;
    }

    public static boolean updateCustomer(String email, String name, String contact, double salary) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka",
                "root","1234");
        String sql="UPDATE customer SET name=?, contact=?, salary=? WHERE email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, contact);
        preparedStatement.setDouble(3, salary);
        preparedStatement.setString(4, email);
        return preparedStatement.executeUpdate()>0;
    }

    public static CustomerDto findCustomer(String email) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka",
                "root","1234");
        String sql="SELECT * FROM customer WHERE email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    public static boolean deleteCustomer(String email) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka",
                "root","1234");
        String sql="DELETE FROM Customer WHERE email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        return preparedStatement.executeUpdate()>0;
    }

    public static List<CustomerDto> findAllCustomers() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka",
                "root","1234");
        String sql="SELECT * FROM customer";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<CustomerDto> dtos = new ArrayList<>();
        while (resultSet.next()){
            dtos.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return null;
    }

    public static List<CustomerDto> searchCustomers(String searchText) throws ClassNotFoundException, SQLException {
        searchText="%"+searchText+"%";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka",
                "root","1234");
        String sql="SELECT * FROM customer WHERE email LIKE ? || name LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, searchText);
        preparedStatement.setString(2, searchText);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<CustomerDto> dtos = new ArrayList<>();
        while (resultSet.next()){
            dtos.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return dtos;
    }
    //==========Customer management================
}
