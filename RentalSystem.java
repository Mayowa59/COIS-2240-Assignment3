import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RentalSystem {
    private static RentalSystem instance;

    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();

    private static final String VEHICLE_FILE = "vehicles.txt";
    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String RECORD_FILE = "rental_records.txt";

    // Singleton pattern: only one RentalSystem object can exist
    private RentalSystem() {
        loadData();
    }

    public static RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }

    // Loads all saved data from text files when the program starts
    private void loadData() {
        loadVehicles();
        loadCustomers();
        loadRentalRecords();
    }

    // Loads vehicles from vehicles.txt into the vehicles list
    private void loadVehicles() {
        File file = new File(VEHICLE_FILE);

        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                String type = parts[0];
                String plate = parts[1];
                String make = parts[2];
                String model = parts[3];
                int year = Integer.parseInt(parts[4]);
                Vehicle.VehicleStatus status = Vehicle.VehicleStatus.valueOf(parts[5]);

                Vehicle vehicle = null;

                if (type.equals("Car")) {
                    int numSeats = Integer.parseInt(parts[6].split("=")[1]);
                    vehicle = new Car(make, model, year, numSeats);
                } else if (type.equals("PickupTruck")) {
                    double cargoSize = Double.parseDouble(parts[6].split("=")[1]);
                    boolean hasTrailer = Boolean.parseBoolean(parts[7].split("=")[1]);
                    vehicle = new PickupTruck(make, model, year, cargoSize, hasTrailer);
                } else if (type.equals("Minibus")) {
                    boolean accessible = false;
                    if (parts.length > 6 && parts[6].toLowerCase().contains("true")) {
                        accessible = true;
                    }
                    vehicle = new Minibus(make, model, year, accessible);
                }

                if (vehicle != null) {
                    vehicle.setLicensePlate(plate);
                    vehicle.setStatus(status);
                    vehicles.add(vehicle);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading vehicles: " + e.getMessage());
        }
    }

    // Loads customers from customers.txt into the customers list
    private void loadCustomers() {
        File file = new File(CUSTOMER_FILE);

        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                int customerId = Integer.parseInt(parts[0]);
                String customerName = parts[1];

                Customer customer = new Customer(customerId, customerName);
                customers.add(customer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    // Loads rental records from rental_records.txt into rentalHistory
    private void loadRentalRecords() {
        File file = new File(RECORD_FILE);

        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                String recordType = parts[0];
                String plate = parts[1];
                int customerId = Integer.parseInt(parts[2]);
                LocalDate recordDate = LocalDate.parse(parts[4]);
                double amount = Double.parseDouble(parts[5]);

                Vehicle vehicle = findVehicleByPlate(plate);
                Customer customer = findCustomerById(customerId);

                if (vehicle != null && customer != null) {
                    RentalRecord record = new RentalRecord(vehicle, customer, recordDate, amount, recordType);
                    rentalHistory.addRecord(record);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading rental records: " + e.getMessage());
        }
    }

    public boolean addVehicle(Vehicle vehicle) {
        if (findVehicleByPlate(vehicle.getLicensePlate()) != null) {
            System.out.println("Vehicle with license plate " + vehicle.getLicensePlate() + " already exists.");
            return false;
        }

        vehicles.add(vehicle);
        saveVehicle(vehicle);
        return true;
    }

    public boolean addCustomer(Customer customer) {
        if (findCustomerById(customer.getCustomerId()) != null) {
            System.out.println("Customer with ID " + customer.getCustomerId() + " already exists.");
            return false;
        }

        customers.add(customer);
        saveCustomer(customer);
        return true;
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Available) {
            vehicle.setStatus(Vehicle.VehicleStatus.Rented);

            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            saveRecord(record);

            rewriteVehiclesFile();

            System.out.println("Vehicle rented to " + customer.getCustomerName());
        } else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Rented) {
            vehicle.setStatus(Vehicle.VehicleStatus.Available);

            RentalRecord record = new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(record);
            saveRecord(record);

            rewriteVehiclesFile();

            System.out.println("Vehicle returned by " + customer.getCustomerName());
        } else {
            System.out.println("Vehicle is not rented.");
        }
    }

    public void saveVehicle(Vehicle vehicle) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(VEHICLE_FILE, true))) {
            writer.println(formatVehicleForFile(vehicle));
        } catch (IOException e) {
            System.out.println("Error saving vehicle: " + e.getMessage());
        }
    }

    public void saveCustomer(Customer customer) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMER_FILE, true))) {
            writer.println(customer.getCustomerId() + "," + customer.getCustomerName());
        } catch (IOException e) {
            System.out.println("Error saving customer: " + e.getMessage());
        }
    }

    public void saveRecord(RentalRecord record) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RECORD_FILE, true))) {
            writer.println(
                record.getRecordType() + "," +
                record.getVehicle().getLicensePlate() + "," +
                record.getCustomer().getCustomerId() + "," +
                record.getCustomer().getCustomerName() + "," +
                record.getRecordDate() + "," +
                record.getTotalAmount()
            );
        } catch (IOException e) {
            System.out.println("Error saving rental record: " + e.getMessage());
        }
    }

    private void rewriteVehiclesFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(VEHICLE_FILE, false))) {
            for (Vehicle vehicle : vehicles) {
                writer.println(formatVehicleForFile(vehicle));
            }
        } catch (IOException e) {
            System.out.println("Error updating vehicles file: " + e.getMessage());
        }
    }

    private String formatVehicleForFile(Vehicle vehicle) {
        String type;
        String extraDetails = "";

        if (vehicle instanceof Car) {
            type = "Car";
            extraDetails = "Seats=" + ((Car) vehicle).getNumSeats();
        } else if (vehicle instanceof PickupTruck) {
            type = "PickupTruck";
            PickupTruck truck = (PickupTruck) vehicle;
            extraDetails = "CargoSize=" + truck.getCargoSize() + ",HasTrailer=" + truck.hasTrailer();
        } else if (vehicle instanceof Minibus) {
            type = "Minibus";
            extraDetails = vehicle.getInfo();
        } else {
            type = "Vehicle";
            extraDetails = vehicle.getInfo();
        }

        return type + "," +
               vehicle.getLicensePlate() + "," +
               vehicle.getMake() + "," +
               vehicle.getModel() + "," +
               vehicle.getYear() + "," +
               vehicle.getStatus() + "," +
               extraDetails;
    }

    public void displayVehicles(Vehicle.VehicleStatus status) {
        if (status == null) {
            System.out.println("\n=== All Vehicles ===");
        } else {
            System.out.println("\n=== " + status + " Vehicles ===");
        }

        System.out.printf("|%-16s | %-12s | %-12s | %-12s | %-6s | %-18s |%n",
            " Type", "Plate", "Make", "Model", "Year", "Status");
        System.out.println("|--------------------------------------------------------------------------------------------|");

        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (status == null || vehicle.getStatus() == status) {
                found = true;
                String vehicleType;
                if (vehicle instanceof Car) {
                    vehicleType = "Car";
                } else if (vehicle instanceof Minibus) {
                    vehicleType = "Minibus";
                } else if (vehicle instanceof PickupTruck) {
                    vehicleType = "Pickup Truck";
                } else {
                    vehicleType = "Unknown";
                }

                System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-6d | %-18s |%n",
                    vehicleType, vehicle.getLicensePlate(), vehicle.getMake(),
                    vehicle.getModel(), vehicle.getYear(), vehicle.getStatus().toString());
            }
        }

        if (!found) {
            if (status == null) {
                System.out.println("  No Vehicles found.");
            } else {
                System.out.println("  No vehicles with Status: " + status);
            }
        }
        System.out.println();
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }

    public void displayRentalHistory() {
        if (rentalHistory.getRentalHistory().isEmpty()) {
            System.out.println("  No rental history found.");
        } else {
            System.out.printf("|%-10s | %-12s | %-20s | %-12s | %-12s |%n",
                " Type", "Plate", "Customer", "Date", "Amount");
            System.out.println("|-------------------------------------------------------------------------------|");

            for (RentalRecord record : rentalHistory.getRentalHistory()) {
                System.out.printf("| %-9s | %-12s | %-20s | %-12s | $%-11.2f |%n",
                    record.getRecordType(),
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerName(),
                    record.getRecordDate().toString(),
                    record.getTotalAmount()
                );
            }
            System.out.println();
        }
    }

    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }

    public Customer findCustomerById(int id) {
        for (Customer c : customers) {
            if (c.getCustomerId() == id) {
                return c;
            }
        }
        return null;
    }
}