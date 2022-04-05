package io.github.positoy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class UserDaoTest {
    static UserDao userDao;

    @BeforeAll
    static void beforeAll() {
        log.info("before all - initialize userDao");
        ApplicationContext context = new GenericXmlApplicationContext("userDaoConfig.xml");
        userDao = context.getBean("userDao", UserDao.class);
    }

    @AfterAll
    static void afterAll() {
        log.info("after all");
    }

    @BeforeEach
    void beforeEach() throws SQLException {
        log.info("before each - clear db");
        userDao.deleteAll();

    }

    @AfterEach
    void afterEach() throws SQLException {
        log.info("after each - clear db");
        userDao.deleteAll();
    }

    @Test
    void add() throws SQLException {
        userDao.add(new User("test", "name", "password"));
    }

    @Test
    void select() throws SQLException {
        userDao.add(new User("test1", "name", "password"));
        User user = userDao.get("test1");
        assertEquals("test1", user.getId());
    }

    @Test
    void emptyCase() throws SQLException {
        userDao.add(new User("test", "name", "pw"));
        userDao.add(new User("test1", "name", "pw"));
        assertEquals(2, userDao.getCount());

        userDao.add(new User("test2", "name", "pw"));
        assertEquals(3, userDao.getCount());

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.get("unknown_id");
        });
    }
}