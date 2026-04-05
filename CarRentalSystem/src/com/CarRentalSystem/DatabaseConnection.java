package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection class - Handles connection to MySQL database
 * This class helps us connect to the database where car information is stored
 */
public class DatabaseConnection {
    
    // Database connection details - CHANGE THESE according to your setup
    private static final String URL = "jdbc:mysql://localhost:3306/car_rental_db";
    private static final String USERNAME = "root";  // Change to your MySQL username
    private static final String PASSWORD = "";      // Change to your MySQL password
    
    /**
     * getConnection() - Creates and returns a connection to the database
     * @return Connection object to interact with database
     */
    public static Connection getConnection() {
        Connection connection = null;
        
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create connection to database
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            System.out.println("✓ Database connected successfully!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("✗ Error: MySQL JDBC Driver not found!");
            System.out.println("Make sure you have added mysql-connector-java to your project.");
            e.printStackTrace();
            
        } catch (SQLException e) {
            System.out.println("✗ Error: Could not connect to database!");
            System.out.println("Check your database URL, username, and password.");
            e.printStackTrace();
        }
        
        return connection;
    }
    
    /**
     * closeConnection() - Safely closes the database connection
     * @param connection - The connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✓ Database connection closed.");
            } catch (SQLException e) {
                System.out.println("✗ Error closing connection.");
                e.printStackTrace();
            }
        }
    }
}
