package com.upgrad.blog.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TODO 6.2: Implement the DatabaseConnection class using the Singleton Pattern (Hint. Should have the
 * private no-arg constructor).
 * TODO 6.3: The getInstance() method should create a connection object which is
 * connected with the local database and return this connection object.
 * TODO 6.4: You should handle the ClassNotFoundException and SQLException individually,
 * and not using the Exception class.
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static Connection connection;

    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String username = "AYUSH";
            String password = "AYUSH";
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }

    }

    public static void main(String[] args) throws SQLException{
        try {
            DatabaseConnection.getConnection();
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println("Not Connected");
        }
    }
}
