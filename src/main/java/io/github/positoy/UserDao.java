package io.github.positoy;

import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public UserDao() {
    }

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void add(User user) throws SQLException {
        jdbcContext.workWithStatementStrategyVoid(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        });
    }

    public User get(String id) throws SQLException, EmptyResultDataAccessException {
        return jdbcContext.workWithStatementStrategyUser(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("select * from users where id=?");
                ps.setString(1, id);
                return ps;
            }
        });
    }

    public void remove(String id) throws SQLException {
        jdbcContext.workWithStatementStrategyVoid(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("delete from users where id=?");
                ps.setString(1, id);
                return ps;
            }
        });
    }

    public void removeAll() throws SQLException {
        jdbcContext.workWithStatementStrategyVoid(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                return c.prepareStatement("delete from users");
            }
        });
    }

    public int getCount() throws SQLException {
        return jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                return c.prepareStatement("select count(1) from users");
            }
        });
    }
}
