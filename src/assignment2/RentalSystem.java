package assignment2;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.*;

public class RentalSystem {

    // Singleton instance (only one RentalSystem allowed)
    private static RentalSystem instance;

    // Private constructor so no one else can create an object
    private RentalSystem() {
        loadData(); // load saved data from text files
    }

    // Returns the one and only RentalSystem object
    public static RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }

    // Lists storing all vehicles, customers, and history
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();


    
    // Save a vehicle to vehicles.txt (append mode)
    
    private void saveVehicle(Vehicle vehicle) {
        try (FileWriter fw = new FileWriter("vehicles.txt", true)) {
            fw.write(
                vehicle.getLicensePlate() + "," +
                vehicle.getMake() + "," +
                vehicle.getModel() + "," +
                vehicle.getYear() + "," +
                vehicle.getStatus() + "\n"
            );
        } catch (Exception e) {
            System.out.println("Error saving vehicle: " + e.getMessage());
        }
    }

    // Save a customer to customers.txt (append mode)
    private void saveCustomer(Customer customer) {
        try (FileWriter fw = new FileWriter("customers.txt", true)) {
            fw.write(
                customer.getCustomerId() + "," +
                customer.getCustomerName() + "\n"
            );
        } catch (Exception e) {
            System.out.println("Error saving customer: " + e.getMessage());
        }
    }

    // Save a rental record to rental_records.txt (append mode)
    private void saveRecord(RentalRecord record) {
        try (FileWriter fw = new FileWriter("rental_records.txt", true)) {
            fw.write(
                record.getRecordType() + "," +
                record.getVehicle().getLicensePlate() + "," +
                record.getCustomer().getCustomerName() + "," +
                record.getRecordDate() + "," +
                record.getTotalAmount() + "\n"
            );
        } catch (Exception e) {
            System.out.println("Error saving record: " + e.getMessage());
        }
    }


    
    // Load saved data from text files when the program starts
   
    private void loadData() {

        // Load vehicles
        try (BufferedReader br = new BufferedReader(new FileReader("vehicles.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] arr = line.split(",");
                if (arr.length != 5) continue;

                String plate = arr[0];
                String make = arr[1];
                String model = arr[2];
                int year = Integer.parseInt(arr[3]);
                Vehicle.VehicleStatus status = Vehicle.VehicleStatus.valueOf(arr[4]);

                // Default load as Car (assignment simplification)
                Vehicle v = new Car(make, model, year, 4);
                v.setLicensePlate(plate);
                v.setStatus(status);

                vehicles.add(v);
            }

        } catch (Exception e) {
            System.out.println("vehicles.txt not found — starting empty.");
        }


        // Load customers
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] arr = line.split(",");
                if (arr.length != 2) continue;

                int id = Integer.parseInt(arr[0]);
                String name = arr[1];

                customers.add(new Customer(id, name));
            }

        } catch (Exception e) {
            System.out.println("customers.txt not found — starting empty.");
        }


        // Load rental records
        try (BufferedReader br = new BufferedReader(new FileReader("rental_records.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] arr = line.split(",");
                if (arr.length != 5) continue;

                String type = arr[0];
                String plate = arr[1];
                String cname = arr[2];
                LocalDate date = LocalDate.parse(arr[3]);
                double amount = Double.parseDouble(arr[4]);

                Vehicle v = findVehicleByPlate(plate);
                Customer c = findCustomerByName(cname);

                if (v != null && c != null) {
                    rentalHistory.addRecord(new RentalRecord(v, c, date, amount, type));
                }
            }

        } catch (Exception e) {
            System.out.println("rental_records.txt not found — starting empty.");
        }
    }


    // Find a customer by name (used when loading history)
    private Customer findCustomerByName(String name) {
        for (Customer c : customers)
            if (c.getCustomerName().equalsIgnoreCase(name))
                return c;
        return null;
    }


   
    // Add a vehicle (with duplicate plate check)
    
    public boolean addVehicle(Vehicle vehicle) {

        if (findVehicleByPlate(vehicle.getLicensePlate()) != null) {
            System.out.println("ERROR: Duplicate vehicle plate.");
            return false;
        }

        vehicles.add(vehicle);
        saveVehicle(vehicle);
        System.out.println("Vehicle added successfully.");
        return true;
    }

    // Add a customer (with duplicate ID check)
    public boolean addCustomer(Customer customer) {

        if (findCustomerById(customer.getCustomerId()) != null) {
            System.out.println("ERROR: Duplicate customer ID.");
            return false;
        }

        customers.add(customer);
        saveCustomer(customer);
        System.out.println("Customer added successfully.");
        return true;
    }


    
    // Rent a vehicle

    public void rentVehicle(Vehicle vehicle, Customer customer,
                            LocalDate date, double amount) {

        if (vehicle == null || customer == null) {
            System.out.println("Invalid vehicle or customer.");
            return;
        }

        if (vehicle.getStatus() == Vehicle.VehicleStatus.Available) {

            vehicle.setStatus(Vehicle.VehicleStatus.Rented);

            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            saveRecord(record);

            System.out.println("Vehicle rented to " + customer.getCustomerName());
        } else {
            System.out.println("Vehicle is not available.");
        }
    }


    
    // Return a vehicle
    
    public void returnVehicle(Vehicle vehicle, Customer customer,
                              LocalDate date, double extraFees) {

        if (vehicle == null || customer == null) {
            System.out.println("Invalid vehicle or customer.");
            return;
        }

        if (vehicle.getStatus() == Vehicle.VehicleStatus.Rented) {

            vehicle.setStatus(Vehicle.VehicleStatus.Available);

            RentalRecord record =
                new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(record);
            saveRecord(record);

            System.out.println("Vehicle returned.");
        } else {
            System.out.println("Vehicle is not rented.");
        }
    }


    // Lookup vehicle by plate
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles)
            if (v.getLicensePlate().equalsIgnoreCase(plate))
                return v;
        return null;
    }

    // Lookup customer by ID
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }


    
    // Display methods for menu output
  
    public void displayVehicles(Vehicle.VehicleStatus status) {

        if (status == null)
            System.out.println("\n=== All Vehicles ===");
        else
            System.out.println("\n=== " + status + " Vehicles ===");

        System.out.printf("| %-12s | %-10s | %-12s | %-12s | %-6s | %-12s |\n",
                "Type", "Plate", "Make", "Model", "Year", "Status");
        System.out.println("-----------------------------------------------------------------------");

        boolean found = false;

        for (Vehicle v : vehicles) {
            if (status == null || v.getStatus() == status) {
                found = true;
                System.out.printf("| %-12s | %-10s | %-12s | %-12s | %-6d | %-12s |\n",
                        v.getClass().getSimpleName(),
                        v.getLicensePlate(),
                        v.getMake(),
                        v.getModel(),
                        v.getYear(),
                        v.getStatus());
            }
        }

        if (!found)
            System.out.println("No vehicles match this criteria.");

        System.out.println();
    }

    // Display all customers
    public void displayAllCustomers() {

        System.out.println("\n=== Registered Customers ===");

        if (customers.isEmpty()) {
            System.out.println("No customers.\n");
            return;
        }

        for (Customer c : customers) {
            System.out.println("ID: " + c.getCustomerId() +
                               " | Name: " + c.getCustomerName());
        }

        System.out.println();
    }

    // Display rental history
    public void displayRentalHistory() {

        System.out.println("\n=== Rental History ===");

        if (rentalHistory.getRentalHistory().isEmpty()) {
            System.out.println("No rental history.\n");
            return;
        }

        System.out.printf("| %-6s | %-10s | %-15s | %-12s | %-10s |\n",
                "Type", "Plate", "Customer", "Date", "Amount");
        System.out.println("-------------------------------------------------------------");

        for (RentalRecord r : rentalHistory.getRentalHistory()) {

            System.out.printf("| %-6s | %-10s | %-15s | %-12s | $%-9.2f |\n",
                    r.getRecordType(),
                    r.getVehicle().getLicensePlate(),
                    r.getCustomer().getCustomerName(),
                    r.getRecordDate(),
                    r.getTotalAmount());
        }

        System.out.println();
    }
}
