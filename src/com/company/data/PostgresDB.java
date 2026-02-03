package com.company.data;
import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {

    private static volatile PostgresDB instance;

    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    private PostgresDB(String url, String user, String password, String dbName) {
        this.url = url + "/" + dbName;
        this.user = user;
        this.password = password;
    }
    public static PostgresDB getInstance() {
        if (instance == null) {
            synchronized (PostgresDB.class){
                if (instance == null) {
                    instance= new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0000", "postgres");
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database");
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing DB connection");
        }
    }

}


