package io.github.positoy;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * SOLID
 * - The Single Responsibility
 * - The Open closed Principle
 * - The Liskov Substitution Principle
 * - The Interface Segregation Principle
 * - The Dependency Injection Principle
 */
public interface ConnectionMaker {
    Connection getConnection() throws ClassNotFoundException, SQLException;
}
