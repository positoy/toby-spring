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
        insert(new AddUserStatement(user));
    }

    public User get(String id) throws SQLException, EmptyResultDataAccessException {
        return select(new GetUserStatement(id));
    }

    public void remove(String id) throws SQLException {
        delete(new DeleteUserStatement(id));
    }

    public void removeAll() throws SQLException {
        delete(new DeleteAllStatement());
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
