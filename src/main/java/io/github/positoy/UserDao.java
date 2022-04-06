package io.github.positoy;

import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

public class UserDao extends SqlOperation {

    public UserDao() {
    }

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        insert(new StatementStrategy() {
                   @Override
                   public PreparedStatement makeStatement(Connection c) throws SQLException {
                       PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
                       ps.setString(1, user.getId());
                       ps.setString(1, user.getName());
                       ps.setString(1, user.getPassword());
                       return ps;
                   }
               });
    }

    public User get(String id) throws SQLException, EmptyResultDataAccessException {
        return select(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("select * from users where id=?");
                ps.setString(1, id);
                return ps;
            }
        });
    }

    public void remove(String id) throws SQLException {
        delete(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("delete from users where id=?");
                ps.setString(1, id);
                return ps;
            }
        });
    }

    public void removeAll() throws SQLException {
        delete(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                return c.prepareStatement("delete from users");
            }
        });
    }

    public int getCount() throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("select count(1) from users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("count(1)");
        ps.close();
        c.close();
        return count;
    }
}
