package io.github.positoy;

import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlOperation {
    DataSource dataSource;

    public SqlOperation() {
    }

    public SqlOperation(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(StatementStrategy ss) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = ss.makeStatement(c);
            ps.executeUpdate();
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
    }

    public User select(StatementStrategy ss) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            c = dataSource.getConnection();
            ps = ss.makeStatement(c);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
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

    public void delete(StatementStrategy ss) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        int deleted = 0;
        try {
            c = dataSource.getConnection();
            ps = ss.makeStatement(c);
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

    int workWithStatementStrategy(StatementStrategy ss) throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = ss.makeStatement(c);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("count(1)");
        ps.close();
        c.close();
        return count;
    }
}
