package io.github.positoy;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/userDaoConfig.xml")
public class UserDaoTest {
    @Autowired
    ApplicationContext context; // 애플리케이션 컨텍스트 자신도 빈으로 등록된다.
    @Autowired
    UserDao userDao;

    @BeforeClass
    public static void beforeAll() {
        log.info("before all");
    }

    @AfterClass
    public static void afterAll() {
        log.info("after all");
    }

    @Before
    public void beforeEach() throws SQLException {
//        userDao = context.getBean("userDao", UserDao.class); // @Autowired
//        System.out.println("context: " + context);
//        System.out.println("userDao: " + userDao);
//        System.out.println("this: " + this); // context, userDao 는 모든 테스트에서 동일하지만, 테스트 클래스는 매번 다시 만들기 때문에 this 는 다르다.

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