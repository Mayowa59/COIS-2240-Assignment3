package assignment3_source;
import java.util.Scanner;
import java.time.LocalDate;

public class VehicleRentalApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RentalSystem rentalSystem = RentalSystem.getInstance();

        while (true) {
            System.out.println("\n1: Add Vehicle\n2: Add Customer\n3: Rent Vehicle\n4: Return Vehicle\n5: Display Available Vehicles\n6: Show Rental History\n7: Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("  1: Car\n  2: Motorcycle\n  3: Truck");
                    int vehicleChoice = scanner.nextInt();
                    scanner.nextLine();
                    Vehicle vehicle = null;
                    switch (vehicleChoice) {
                        case 1:
                            System.out.print("Enter License Plate: ");
                            String plate = scanner.nextLine();
                            System.out.print("Enter Make: ");
                            String make = scanner.nextLine();
                            System.out.print("Enter Model: ");
                            String model = scanner.nextLine();
                            System.out.print("Enter Year: ");
                            int year = scanner.nextInt();
                            System.out.print("Enter Number of Seats: ");
                            int seats = scanner.nextInt();
                            scanner.nextLine();
                            vehicle = new Car(plate, make, model, year, seats);
                            break;
                        case 2:
                            System.out.print("Enter License Plate: ");
                            plate = scanner.nextLine();
                            System.out.print("Enter Make: ");
                            make = scanner.nextLine();
                            System.out.print("Enter Model: ");
                            model = scanner.nextLine();
                            System.out.print("Enter Year: ");
                            year = scanner.nextInt();
                            System.out.print("Does it have a sidecar? (true/false): ");
                            boolean hasSidecar = scanner.nextBoolean();
                            vehicle = new Motorcycle(make, model, plate, year, hasSidecar);
                            break;
                        // Other vehicle types can be added here
                    }
                    if (vehicle != null) {
                        rentalSystem.addVehicle(vehicle);
                    }
                    break;
                case 2:
                    System.out.print("Enter Customer ID: ");
                    int customerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    Customer customer = new Customer(customerId, customerName);
                    rentalSystem.addCustomer(customer);
                    break;
                case 3:
                    System.out.print("Enter customer ID: ");
                    int cidRent = scanner.nextInt();
                    scanner.nextLine();
                    Customer customerToRent = rentalSystem.findCustomerById(cidRent);
                    if (customerToRent == null) {
                        System.out.println("Customer not found.");
                        break;
                    }
                    System.out.print("Enter Vehicle License Plate: ");
                    String plateRent = scanner.nextLine();
                    Vehicle vehicleToRent = rentalSystem.findVehicleByPlate(plateRent);
                    if (vehicleToRent == null) {
                        System.out.println("Vehicle not found.");
                        break;
                    }
                    System.out.print("Enter rental amount: ");
                    double rentalAmount = scanner.nextDouble();
                    LocalDate rentDate = LocalDate.now();
                    rentalSystem.rentVehicle(vehicleToRent, customerToRent, rentDate, rentalAmount);
                    break;
                case 4:
                    System.out.print("Enter customer ID: ");
                    cidRent = scanner.nextInt();
                    scanner.nextLine();
                    customerToRent = rentalSystem.findCustomerById(cidRent);
                    if (customerToRent == null) {
                        System.out.println("Customer not found.");
                        break;
                    }
                    System.out.print("Enter Vehicle License Plate: ");
                    plateRent = scanner.nextLine();
                    vehicleToRent = rentalSystem.findVehicleByPlate(plateRent);
                    if (vehicleToRent == null) {
                        System.out.println("Vehicle not found.");
                        break;
                    }
                    System.out.print("Enter extra fees: ");
                    double extraFees = scanner.nextDouble();
                    LocalDate returnDate = LocalDate.now();
                    rentalSystem.returnVehicle(vehicleToRent, customerToRent, returnDate, extraFees);
                    break;
                case 5:
                    // Show available vehicles code here
                    break;
                case 6:
                    // Display rental history code here
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
