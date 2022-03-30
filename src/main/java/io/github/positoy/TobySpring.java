package io.github.positoy;

import java.sql.SQLException;

public class TobySpring {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        User user = new User();
        user.setId("andy");
        user.setName("Andy");
        user.setPassword("pass");

        UserDao userDao = new UserDao();
        userDao.add(user);

        User user2 = userDao.get(user.getId());
        System.out.println(String.format("%s %s %s", user2.getId(), user2.getName(), user2.getPassword()));
    }
}
