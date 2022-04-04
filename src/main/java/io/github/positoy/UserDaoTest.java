package io.github.positoy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.Assert;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException {

        User user = new User("test", "id", "pw");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        userDao.add(user);
        Assert.isTrue(userDao.get("test").getId().equals(user.getId()), "db insertion failed");
        userDao.delete("test");


        ApplicationContext xmlContext = new GenericXmlApplicationContext("userDaoConfig.xml");
        userDao = xmlContext.getBean("userDao", UserDao.class);
        userDao.add(user);
        Assert.isTrue(userDao.get("test").getId().equals(user.getId()), "db insertion failed");
        userDao.delete("test");
    }
}
