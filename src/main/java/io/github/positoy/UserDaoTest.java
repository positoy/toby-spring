package io.github.positoy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        userDao.add(new User("annotationId", "name", "pw"));
        System.out.println(String.format("annotationContext userDao : %s", userDao));

        CountingConnectionMaker countingConnectionMaker = context.getBean("countingConnectionMaker", CountingConnectionMaker.class);
        System.out.println(countingConnectionMaker.getCounter());


        ApplicationContext xmlContext = new GenericXmlApplicationContext("userDaoConfig.xml");
        userDao = xmlContext.getBean("userDao", UserDao.class);
        userDao.add(new User("xmlId", "name", "pw"));
        System.out.println(String.format("annotationContext userDao : %s", userDao));

        countingConnectionMaker = xmlContext.getBean("countingConnectionMaker", CountingConnectionMaker.class);
        System.out.println(countingConnectionMaker.getCounter());
    }
}
