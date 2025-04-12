package assignment3_source;

import java.util.*;

public class VehicleRentalApp {
    public static void main(String[] args) {
        RentalSystem system = RentalSystem.getInstance();
        Scanner sc = new Scanner(System.in);

        // Main menu for interaction
        while (true) {
            System.out.println("\nVehicle Rental System Menu:");
            System.out.println("1. Rent Vehicle");
            System.out.println("2. Return Vehicle");
            System.out.println("3. View All Available Vehicles");
            System.out.println("4. View Rental History");
            System.out.println("5. Add Customer");
            System.out.println("6. Add Vehicle");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    // Rent vehicle
                    System.out.print("Enter customer ID: ");
                    String rentCustomerId = sc.nextLine();
                    Customer rentCustomer = system.findCustomerById(rentCustomerId);
                    if (rentCustomer == null) {
                        System.out.println("Customer not found.");
                        break;
                    }

                    System.out.print("Enter license plate of vehicle to rent: ");
                    String rentPlate = sc.nextLine();
                    Vehicle rentVehicle = system.findVehicleByPlate(rentPlate);
                    if (rentVehicle == null) {
                        System.out.println("Vehicle not found.");
                        break;
                    }

                    if (rentVehicle.isRented()) {
                        System.out.println("Vehicle is currently not available for rent.");
                        break;
                    }

                    System.out.print("Enter rent year: ");
                    String rentYear = sc.nextLine();
                    system.rentVehicle(rentCustomer, rentVehicle, rentYear);
                    System.out.println("Vehicle rented successfully.");
                    break;

                case "2":
                    // Return vehicle
                    System.out.print("Enter customer ID: ");
                    String returnCustomerId = sc.nextLine();
                    Customer returnCustomer = system.findCustomerById(returnCustomerId);
                    if (returnCustomer == null) {
                        System.out.println("Customer not found.");
                        break;
                    }

                    System.out.print("Enter license plate of vehicle to return: ");
                    String returnPlate = sc.nextLine();
                    Vehicle returnVehicle = system.findVehicleByPlate(returnPlate);
                    if (returnVehicle == null) {
                        System.out.println("Vehicle not found.");
                        break;
                    }

                    System.out.print("Enter return year: ");
                    String returnYear = sc.nextLine();
                    system.returnVehicle(returnCustomer, returnVehicle, returnYear);
                    System.out.println("Vehicle returned successfully.");
                    break;

                case "3":
                    // View available vehicles
                    System.out.println("Available Vehicles:");
                    for (Vehicle v : system.getAvailableVehicles()) {
                        System.out.println(v.toDataString());
                    }
                    break;

                case "4":
                    // View rental history
                    System.out.println("Rental History:");
                    for (RentalRecord record : system.getRentalHistory()) {
                        System.out.println(record);
                    }
                    break;

                case "5":
                    // Add new customer
                    System.out.print("Enter customer ID: ");
                    String newCustomerId = sc.nextLine();
                    System.out.print("Enter customer name: ");
                    String newCustomerName = sc.nextLine();
                    if (system.addCustomer(new Customer(newCustomerId, newCustomerName))) {
                        System.out.println("Customer added successfully.");
                    } else {
                        System.out.println("Customer ID already exists.");
                    }
                    break;

                case "6":
                    // Add new vehicle
                    System.out.println("Choose vehicle type: 1. Car  2. SuperCar  3. Motorcycle  4. Truck");
                    String typeChoice = sc.nextLine();
                    System.out.print("Enter vehicle license plate: ");
                    String newPlate = sc.nextLine();
                    System.out.print("Enter vehicle make: ");
                    String newMake = sc.nextLine();
                    System.out.print("Enter vehicle model: ");
                    String newModel = sc.nextLine();
                    System.out.print("Enter vehicle year: ");
                    int newYear = Integer.parseInt(sc.nextLine());

                    Vehicle newVehicle = switch (typeChoice) {
                        case "1" -> new Car(newPlate, newMake, newModel, newYear);
                        case "2" -> new SuperCar(newPlate, newMake, newModel, newYear);
                        case "3" -> new Motorcycle(newPlate, newMake, newModel, newYear);
                        case "4" -> new Truck(newPlate, newMake, newModel, newYear);
                        default -> null;
                    };

                    if (newVehicle == null) {
                        System.out.println("Invalid vehicle type selected.");
                    } else if (system.addVehicle(newVehicle)) {
                        System.out.println("Vehicle added successfully.");
                    } else {
                        System.out.println("Vehicle with this license plate already exists.");
                    }
                    break;

                case "7":
                    // Exit
                    System.out.println("Exiting application. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
}
