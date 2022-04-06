package io.github.positoy;

import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    DataSource dataSource;

    public UserDao() {
    }

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        ps.executeUpdate();
        ps.close();

        c.close();
    }

    public User get(String id) throws SQLException, EmptyResultDataAccessException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select * from users where id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null ) {
                    if (rs.next()) {
                        user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                    }
                    rs.close();
                }
            } catch (SQLException e) {
                throw e;
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }

        if (user == null)
            throw new EmptyResultDataAccessException(1);

        return user;
    }

    public void delete(String id) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        int deleted = 0;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("delete from users where id=?");
            ps.setString(1, id);
            deleted = ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {

                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {

                }
            }
        }
        System.out.println("deleted " + deleted);
    }

    public void deleteAll() throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("delete from users");
        int deleted = ps.executeUpdate();
        ps.close();
        c.close();
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
