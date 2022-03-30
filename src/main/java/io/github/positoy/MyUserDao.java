package io.github.positoy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyUserDao extends UserDao{
    @Override
    Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://192.168.59.103:30000/toby", "root", "test1234");
    }
}
