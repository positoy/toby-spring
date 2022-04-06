package io.github.positoy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUserStatement implements StatementStrategy {
    String id;

    public DeleteUserStatement(String id) {
        this.id = id;
    }

    @Override
    public PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("delete from users where id=?");
        ps.setString(1, id);
        return ps;
    }
}
