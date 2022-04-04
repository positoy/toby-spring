package io.github.positoy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        User user = new User();
        user.setId("andy");
        user.setName("Andy");
        user.setPassword("pass");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        userDao.add(user);

        User user2 = userDao.get(user.getId());
        System.out.println(String.format("%s %s %s", user2.getId(), user2.getName(), user2.getPassword()));
    }
}
