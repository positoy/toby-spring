package io.github.positoy;

import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        class AddUserStatement implements StatementStrategy {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        }
        insert(new AddUserStatement());
    }

    public User get(String id) throws SQLException, EmptyResultDataAccessException {
        class GetUserStatement implements StatementStrategy {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("select * from users where id=?");
                ps.setString(1, id);
                return ps;
            }
        }
        return select(new GetUserStatement());
    }

    public void remove(String id) throws SQLException {
        class DeleteUserStatement implements StatementStrategy {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("delete from users where id=?");
                ps.setString(1, id);
                return ps;
            }
        }
        delete(new DeleteUserStatement());
    }

    public void removeAll() throws SQLException {
        class DeleteAllStatement implements StatementStrategy {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                return c.prepareStatement("delete from users");
            }
        }
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
