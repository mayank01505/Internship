package models;

/**
 * Car class - Represents a single car in our rental system
 * This class holds all the information about one car
 */
public class Car {
    // Car details
    private String plateNo;      // Car's number plate (unique ID)
    private String model;         // Car model name (e.g., "Honda City")
    private String category;      // Type of car (SUV, Sedan, Hatchback)
    private double ratePerDay;    // Rental cost per day in Rupees
    private String status;        // Current status (Available or Rented)
    
    // Constructor - Creates a new Car object with all details
    public Car(String plateNo, String model, String category, double ratePerDay, String status) {
        this.plateNo = plateNo;
        this.model = model;
        this.category = category;
        this.ratePerDay = ratePerDay;
        this.status = status;
    }
    
    // Getter methods - Used to read car information
    public String getPlateNo() {
        return plateNo;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getCategory() {
        return category;
    }
    
    public double getRatePerDay() {
        return ratePerDay;
    }
    
    public String getStatus() {
        return status;
    }
    
    // Setter for status - Used when car is rented or returned
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Display car information in a nice format
    public void displayCarInfo() {
        System.out.println("========================================");
        System.out.println("Plate Number: " + plateNo);
        System.out.println("Model: " + model);
        System.out.println("Category: " + category);
        System.out.println("Rate: ₹" + ratePerDay + " per day");
        System.out.println("Status: " + status);
        System.out.println("========================================");
    }
}
