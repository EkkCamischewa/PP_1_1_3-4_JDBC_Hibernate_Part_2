package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection statConnection;
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        try {
            statConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Ошибка соединения -- Util");
        }

        return statConnection;
    }

    public static void closeConnection(){
        try {
            statConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
