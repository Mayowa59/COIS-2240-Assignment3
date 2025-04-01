import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    
    private static RentalSystem instance;

    private RentalSystem() {
        loadData(); // Load saved data when the system starts
    }

    public static RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }

    // Method to load all data when the program starts
    private void loadData() {
        loadVehicles();
        loadCustomers();
        loadRentalRecords();
    }

    // Load vehicles from vehicles.txt
    private void loadVehicles() {
        File file = new File("vehicles.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String licensePlate = parts[0].trim();
                String make = parts[1].trim();
                String model = parts[2].trim();
                int year = Integer.parseInt(parts[3].trim());
                String status = parts[4].trim();

                Vehicle vehicle;
                if (licensePlate.startsWith("C")) { // Example logic: cars start with "C"
                    vehicle = new Car(licensePlate, make, model, year);
                } else {
                    vehicle = new Motorcycle(licensePlate, make, model, year);
                }

                // Set the status of the vehicle
                vehicle.setStatus(status.equalsIgnoreCase("RENTED") 
                                  ? Vehicle.VehicleStatus.RENTED 
                                  : Vehicle.VehicleStatus.AVAILABLE);

                vehicles.add(vehicle);
            }
        } catch (IOException e) {
            System.out.println("Error loading vehicles: " + e.getMessage());
        }
    }

    // Load customers from customers.txt
    private void loadCustomers() {
        File file = new File("customers.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                int customerId = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();

                customers.add(new Customer(customerId, name));
            }
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    // Load rental records from rental_records.txt
    private void loadRentalRecords() {
        File file = new File("rental_records.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String licensePlate = parts[0].trim();
                int customerId = Integer.parseInt(parts[1].trim());
                LocalDate date = LocalDate.parse(parts[2].trim());
                double amount = Double.parseDouble(parts[3].trim());
                String type = parts[4].trim();

                Vehicle vehicle = findVehicleByPlate(licensePlate);
                Customer customer = findCustomerById(customerId);

                if (vehicle != null && customer != null) {
                    RentalRecord record = new RentalRecord(vehicle, customer, date, amount, type);
                    rentalHistory.addRecord(record);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading rental records: " + e.getMessage());
        }
    }

    // Save vehicle details to file
    public void saveVehicle(Vehicle vehicle) {
        try (FileWriter writer = new FileWriter("vehicles.txt", true)) {
            writer.write(vehicle.getLicensePlate() + "," +
                    vehicle.getMake() + "," +
                    vehicle.getModel() + "," +
                    vehicle.getYear() + "," +
                    vehicle.getStatus() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to vehicles.txt: " + e.getMessage());
        }
    }

    // Save customer details to file
    public void saveCustomer(Customer customer) {
        try (FileWriter writer = new FileWriter("customers.txt", true)) {
            writer.write(customer.getCustomerId() + "," +
                    customer.getCustomerName() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to customers.txt: " + e.getMessage());
        }
    }

    // Save rental record details to file
    public void saveRecord(RentalRecord record) {
        try (FileWriter writer = new FileWriter("rental_records.txt", true)) {
            writer.write(record.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to rental_records.txt: " + e.getMessage());
        }
    }

    public boolean addVehicle(Vehicle vehicle) {
        if (findVehicleByPlate(vehicle.getLicensePlate()) != null) {
            System.out.println("Error: A vehicle with the license plate " + vehicle.getLicensePlate() + " already exists.");
            return false; // Duplicate found
        }
        vehicles.add(vehicle);
        saveVehicle(vehicle);
        return true; // Successfully added
    }

    public boolean addCustomer(Customer customer) {
        if (findCustomerById(customer.getCustomerId()) != null) {
            System.out.println("Error: A customer with ID " + customer.getCustomerId() + " already exists.");
            return false; // Duplicate found
        }
        customers.add(customer);
        saveCustomer(customer);
        return true; // Successfully added
    }

    // Rent a vehicle
    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            saveRecord(record);
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        } else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    // Return a vehicle
    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            RentalRecord record = new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(record);
            saveRecord(record);
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        } else {
            System.out.println("Vehicle is not rented.");
        }
    }

    // Display available vehicles
    public void displayVehicles(boolean onlyAvailable) {
        System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
        System.out.println("---------------------------------------------------------------------------------");

        for (Vehicle v : vehicles) {
            if (!onlyAvailable || v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
                System.out.println("|     " + (v instanceof Car ? "Car          " : "Motorcycle   ") + "|\t" +
                        v.getLicensePlate() + "\t|\t" + v.getMake() + "\t|\t" +
                        v.getModel() + "\t|\t" + v.getYear() + "\t|\t");
            }
        }
        System.out.println();
    }

    // Display all customers
    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }

    // Display rental history
    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record.toString());
        }
    }

    // Find a vehicle by its license plate
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }

    // Find a customer by ID
    public Customer findCustomerById(int id) {
        for (Customer c : customers) {
            if (c.getCustomerId() == id) {
                return c;
            }
        }
        return null;
    }
}
