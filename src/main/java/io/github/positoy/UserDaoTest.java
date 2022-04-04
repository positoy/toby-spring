package io.github.positoy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        User user = new User();
        user.setId("andy7");
        user.setName("Andy");
        user.setPassword("pass");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        userDao.add(user);

        User user2 = new User();
        user2.setId("andy8");
        user2.setName("Andy");
        user2.setPassword("pass");
        userDao.add(user2);

        CountingConnectionMaker countingConnectionMaker = context.getBean("countingConnectionMaker", CountingConnectionMaker.class);
        System.out.println(String.format("countingConnectionMaker counter : %d", countingConnectionMaker.getCounter()));
    }
}
