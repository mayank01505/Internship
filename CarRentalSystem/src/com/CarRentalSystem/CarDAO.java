package database;

import models.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CarDAO (Data Access Object) - Handles all database operations for cars
 * This class contains methods to read, update car data from database
 */
public class CarDAO {
    
    /**
     * getAvailableCarsByCategory() - Fetch all available cars of a specific category
     * @param category - Type of car (SUV, Sedan, Hatchback)
     * @return List of available cars
     */
    public List<Car> getAvailableCarsByCategory(String category) {
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            // Connect to database
            connection = DatabaseConnection.getConnection();
            
            // SQL query to get available cars of specific category
            String sql = "SELECT * FROM cars WHERE category = ? AND status = 'Available'";
            statement = connection.prepareStatement(sql);
            statement.setString(1, category);  // Set the category parameter
            
            // Execute query
            resultSet = statement.executeQuery();
            
            // Loop through results and create Car objects
            while (resultSet.next()) {
                Car car = new Car(
                    resultSet.getString("plate_no"),
                    resultSet.getString("model"),
                    resultSet.getString("category"),
                    resultSet.getDouble("rate_per_day"),
                    resultSet.getString("status")
                );
                cars.add(car);  // Add car to list
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Error fetching cars from database!");
            e.printStackTrace();
            
        } finally {
            // Close all database resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DatabaseConnection.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return cars;
    }
    
    /**
     * rentCar() - Update car status from Available to Rented
     * @param plateNo - Plate number of the car to rent
     * @return true if successful, false otherwise
     */
    public boolean rentCar(String plateNo) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            // SQL query to update car status to Rented
            String sql = "UPDATE cars SET status = 'Rented' WHERE plate_no = ? AND status = 'Available'";
            statement = connection.prepareStatement(sql);
            statement.setString(1, plateNo);
            
            // Execute update
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Car rented successfully!");
                return true;
            } else {
                System.out.println("✗ Car not available or doesn't exist!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Error renting car!");
            e.printStackTrace();
            return false;
            
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) DatabaseConnection.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * returnCar() - Update car status from Rented back to Available
     * @param plateNo - Plate number of the car to return
     * @return true if successful, false otherwise
     */
    public boolean returnCar(String plateNo) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            // SQL query to update car status to Available
            String sql = "UPDATE cars SET status = 'Available' WHERE plate_no = ? AND status = 'Rented'";
            statement = connection.prepareStatement(sql);
            statement.setString(1, plateNo);
            
            // Execute update
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Car returned successfully!");
                return true;
            } else {
                System.out.println("✗ Car is not currently rented or doesn't exist!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Error returning car!");
            e.printStackTrace();
            return false;
            
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) DatabaseConnection.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * getCarByPlateNo() - Get a specific car's details by plate number
     * @param plateNo - Plate number to search for
     * @return Car object if found, null otherwise
     */
    public Car getCarByPlateNo(String plateNo) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Car car = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            // SQL query to get car by plate number
            String sql = "SELECT * FROM cars WHERE plate_no = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, plateNo);
            
            // Execute query
            resultSet = statement.executeQuery();
            
            // If car found, create Car object
            if (resultSet.next()) {
                car = new Car(
                    resultSet.getString("plate_no"),
                    resultSet.getString("model"),
                    resultSet.getString("category"),
                    resultSet.getDouble("rate_per_day"),
                    resultSet.getString("status")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Error fetching car details!");
            e.printStackTrace();
            
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DatabaseConnection.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return car;
    }
}
