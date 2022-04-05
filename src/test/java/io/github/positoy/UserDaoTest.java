package io.github.positoy;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;


@Slf4j
public class UserDaoTest {
    static UserDao userDao;

    @BeforeClass
    public static void beforeAll() {
        log.info("before all - initialize userDao");
        ApplicationContext context = new GenericXmlApplicationContext("userDaoConfig.xml");
        userDao = context.getBean("userDao", UserDao.class);
    }

    @AfterClass
    public static void afterAll() {
        log.info("after all");
    }

    @Before
    public void beforeEach() throws SQLException {
        log.info("before each - clear db");
        userDao.deleteAll();

    }

    @After
    public void afterEach() throws SQLException {
        log.info("after each - clear db");
        userDao.deleteAll();
    }

    @Test
    public void add() throws SQLException {
        userDao.add(new User("test", "name", "password"));
    }

    @Test
    public void select() throws SQLException {
        userDao.add(new User("test1", "name", "password"));
        User user = userDao.get("test1");
        Assert.assertEquals("test1", user.getId());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void emptyCase() throws SQLException {
        userDao.add(new User("test", "name", "pw"));
        userDao.add(new User("test1", "name", "pw"));
        Assert.assertEquals(2, userDao.getCount());

        userDao.add(new User("test2", "name", "pw"));
        Assert.assertEquals(3, userDao.getCount());

        userDao.deleteAll();
        Assert.assertEquals(0, userDao.getCount());

        userDao.get("unknown_id");
    }
}