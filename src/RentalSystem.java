 //Mayowa Adeyeri
package assignment2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();

    public static void main(String[] args) {
        RentalSystem system = new RentalSystem();
        system.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Vehicle Rental System ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Add Customer");
            System.out.println("3. Display Available Vehicles");
            System.out.println("4. Rent Vehicle");
            System.out.println("5. Return Vehicle");
            System.out.println("6. Show Rental History");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input, please enter a number:");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addVehicle(scanner);
                    break;
                case 2:
                    addCustomer(scanner);
                    break;
                case 3:
                    displayAvailableVehicles();
                    break;
                case 4:
                    rentVehicle(scanner);
                    break;
                case 5:
                    returnVehicle(scanner);
                    break;
                case 6:
                    showRentalHistory();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }

        } while (choice != 0);
    }

    private void addVehicle(Scanner scanner) {
        System.out.print("Enter vehicle type (car/motorcycle/truck/sportcar): ");
        String type = scanner.nextLine().toLowerCase();
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        Vehicle v = null;

        switch (type) {
            case "car":
                System.out.print("Enter number of seats: ");
                int seats = scanner.nextInt();
                v = new Car(make, model, year, seats);
                break;

            case "motorcycle":
                System.out.print("Has sidecar? (true/false): ");
                boolean sidecar = scanner.nextBoolean();
                v = new Motorcycle(make, model, year, sidecar);
                break;

            case "truck":
                System.out.print("Enter cargo size (in feet): ");
                double size = scanner.nextDouble();
                System.out.print("Has trailer? (true/false): ");
                boolean trailer = scanner.nextBoolean();
                v = new PickupTruck(make, model, year, size, trailer);
                break;

            case "sportcar":
                System.out.print("Enter number of seats: ");
                int sSeats = scanner.nextInt();
                System.out.print("Enter horsepower: ");
                int hp = scanner.nextInt();
                System.out.print("Has turbo? (true/false): ");
                boolean turbo = scanner.nextBoolean();
                v = new SportCar(make, model, year, sSeats, hp, turbo);
                break;

            default:
                System.out.println("Unknown vehicle type.");
                break;
        }

        if (v != null) {
            vehicles.add(v);
            System.out.println("Vehicle added: " + v.getInfo());
        }
    }

    private void addCustomer(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        Customer c = new Customer(id, name, phone);
        customers.add(c);
        System.out.println("Customer added: " + c);
    }

    private void displayAvailableVehicles() {
        System.out.println("\nAvailable Vehicles:");
        for (Vehicle v : vehicles) {
            if (v.getStatus() == Vehicle.Status.AVAILABLE) {
                System.out.println(v.getInfo());
            }
        }
    }

    private void rentVehicle(Scanner scanner) {
        System.out.print("Enter license plate to rent: ");
        String plate = scanner.nextLine();
        Vehicle v = findVehicleByPlate(plate);

        if (v == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine();
        Customer c = findCustomerById(id);

        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }

        v.setStatus(Vehicle.Status.RENTED);
        rentalHistory.addRecord(new RentalRecord(v, c, LocalDate.now(), 0.0, "RENT"));
        System.out.println("Vehicle rented to " + c.getName());
    }

    private void returnVehicle(Scanner scanner) {
        System.out.print("Enter license plate to return: ");
        String plate = scanner.nextLine();
        Vehicle v = findVehicleByPlate(plate);

        if (v == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        v.setStatus(Vehicle.Status.AVAILABLE);
        rentalHistory.addRecord(new RentalRecord(v, null, LocalDate.now(), 0.0, "RETURN"));
        System.out.println("Vehicle returned and now available.");
    }

    private void showRentalHistory() {
        System.out.println("\n--- Rental History ---");
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record);
        }
    }

    private Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }

    private Customer findCustomerById(String id) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }
}
//Mayowa Adeyeri
