package io.github.positoy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        userDao.add(new User("hello", "name", "pw"));

        ApplicationContext xmlContext = new GenericXmlApplicationContext("userDaoConfig.xml");
        userDao = xmlContext.getBean("userDao", UserDao.class);
        userDao.add(new User("hi", "name", "pw"));
    }
}
