package db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {

    @Getter
    private static DBConnectionProvider instance = new DBConnectionProvider();
    private Connection connection;

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/restaurant_order_system";
    private final String JDBC_USERNAME = "root";
    private final String JDBC_PASSWORD = "root";

    private DBConnectionProvider() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if ((connection == null) || connection.isClosed()) {
                connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

