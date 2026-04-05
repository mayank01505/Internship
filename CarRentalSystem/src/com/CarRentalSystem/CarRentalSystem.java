package main;

import models.Car;
import database.CarDAO;
import java.util.List;
import java.util.Scanner;

/**
 * CarRentalSystem - Main application class
 * This is where the program starts and handles user interactions
 */
public class CarRentalSystem {
    
    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);
    
    // CarDAO object to perform database operations
    private static CarDAO carDAO = new CarDAO();
    
    /**
     * main() - Starting point of the program
     */
    public static void main(String[] args) {
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO CAR RENTAL SYSTEM         ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        boolean running = true;
        
        // Main program loop
        while (running) {
            displayMainMenu();
            
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    filterAndViewInventory();
                    break;
                    
                case 2:
                    rentCar();
                    break;
                    
                case 3:
                    returnCarAndBill();
                    break;
                    
                case 4:
                    System.out.println("\n✓ Thank you for using Car Rental System!");
                    System.out.println("  Goodbye! 👋");
                    running = false;
                    break;
                    
                default:
                    System.out.println("\n✗ Invalid choice! Please enter 1-4.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * displayMainMenu() - Shows the main menu options
     */
    private static void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("           MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. View Available Cars (Filter by Category)");
        System.out.println("2. Rent a Car");
        System.out.println("3. Return a Car & Get Bill");
        System.out.println("4. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice (1-4): ");
    }
    
    /**
     * getUserChoice() - Gets and validates user's menu choice
     * @return User's choice as integer
     */
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;  // Return invalid choice if not a number
        }
    }
    
    /**
     * filterAndViewInventory() - Shows available cars filtered by category
     */
    private static void filterAndViewInventory() {
        System.out.println("\n========================================");
        System.out.println("     FILTER CARS BY CATEGORY");
        System.out.println("========================================");
        System.out.println("1. SUV");
        System.out.println("2. Sedan");
        System.out.println("3. Hatchback");
        System.out.println("========================================");
        System.out.print("Enter category choice (1-3): ");
        
        int categoryChoice = getUserChoice();
        String category = "";
        
        // Convert user choice to category name
        switch (categoryChoice) {
            case 1:
                category = "SUV";
                break;
            case 2:
                category = "Sedan";
                break;
            case 3:
                category = "Hatchback";
                break;
            default:
                System.out.println("✗ Invalid category choice!");
                return;
        }
        
        // Fetch available cars from database
        System.out.println("\nFetching available " + category + " cars...\n");
        List<Car> cars = carDAO.getAvailableCarsByCategory(category);
        
        // Display results
        if (cars.isEmpty()) {
            System.out.println("✗ Sorry! No " + category + " cars available right now.");
        } else {
            System.out.println("✓ Found " + cars.size() + " available " + category + " car(s):\n");
            for (Car car : cars) {
                car.displayCarInfo();
            }
        }
    }
    
    /**
     * rentCar() - Handles the car rental process
     */
    private static void rentCar() {
        System.out.println("\n========================================");
        System.out.println("          RENT A CAR");
        System.out.println("========================================");
        System.out.print("Enter plate number of car to rent: ");
        String plateNo = scanner.nextLine().trim();
        
        // Check if car exists and is available
        Car car = carDAO.getCarByPlateNo(plateNo);
        
        if (car == null) {
            System.out.println("✗ Car with plate number '" + plateNo + "' not found!");
            return;
        }
        
        if (!car.getStatus().equals("Available")) {
            System.out.println("✗ Sorry! This car is already rented.");
            return;
        }
        
        // Show car details
        System.out.println("\nCar Details:");
        car.displayCarInfo();
        
        // Confirm rental
        System.out.print("\nConfirm rental? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes") || confirm.equals("y")) {
            // Update car status to Rented
            boolean success = carDAO.rentCar(plateNo);
            
            if (success) {
                System.out.println("\n✓ SUCCESS! Car rented successfully.");
                System.out.println("  Please remember the plate number: " + plateNo);
                System.out.println("  Daily rate: ₹" + car.getRatePerDay());
            }
        } else {
            System.out.println("✗ Rental cancelled.");
        }
    }
    
    /**
     * returnCarAndBill() - Handles car return and calculates rental bill
     */
    private static void returnCarAndBill() {
        System.out.println("\n========================================");
        System.out.println("      RETURN CAR & GET BILL");
        System.out.println("========================================");
        System.out.print("Enter plate number of car to return: ");
        String plateNo = scanner.nextLine().trim();
        
        // Fetch car details
        Car car = carDAO.getCarByPlateNo(plateNo);
        
        if (car == null) {
            System.out.println("✗ Car with plate number '" + plateNo + "' not found!");
            return;
        }
        
        if (!car.getStatus().equals("Rented")) {
            System.out.println("✗ This car is not currently rented!");
            return;
        }
        
        // Ask for number of days
        System.out.print("How many days was the car rented? ");
        int days = 0;
        
        try {
            days = Integer.parseInt(scanner.nextLine());
            
            if (days <= 0) {
                System.out.println("✗ Invalid number of days!");
                return;
            }
            
        } catch (NumberFormatException e) {
            System.out.println("✗ Please enter a valid number!");
            return;
        }
        
        // Calculate total bill
        double ratePerDay = car.getRatePerDay();
        double totalBill = days * ratePerDay;
        
        // Display bill
        System.out.println("\n========================================");
        System.out.println("          RENTAL BILL");
        System.out.println("========================================");
        System.out.println("Car Model: " + car.getModel());
        System.out.println("Plate Number: " + plateNo);
        System.out.println("Category: " + car.getCategory());
        System.out.println("----------------------------------------");
        System.out.println("Rate per Day: ₹" + ratePerDay);
        System.out.println("Number of Days: " + days);
        System.out.println("----------------------------------------");
        System.out.println("TOTAL BILL: ₹" + totalBill);
        System.out.println("========================================");
        
        // Update car status back to Available
        boolean success = carDAO.returnCar(plateNo);
        
        if (success) {
            System.out.println("✓ Car returned successfully!");
            System.out.println("  Thank you for your business! 🚗");
        }
    }
}
