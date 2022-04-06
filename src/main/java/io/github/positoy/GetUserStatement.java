package io.github.positoy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetUserStatement implements StatementStrategy {
    String id;

    public GetUserStatement(String id) {
        this.id = id;
    }

    @Override
    public PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select * from users where id=?");
        ps.setString(1, id);
        return ps;
    }
}
