package io.github.positoy;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    int counter = 0;
    ConnectionMaker connectionMaker;

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        counter++;
        return connectionMaker.getConnection();
    }

    public int getCounter() {
        return counter;
    }
}
