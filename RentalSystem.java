import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

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
    }

    public static RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehicle(vehicle);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomer(customer);
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Available) {
            vehicle.setStatus(Vehicle.VehicleStatus.Rented);

            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            saveRecord(record);

            // rewrite vehicle file so latest status is stored
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

            // rewrite vehicle file so latest status is stored
            rewriteVehiclesFile();

            System.out.println("Vehicle returned by " + customer.getCustomerName());
        } else {
            System.out.println("Vehicle is not rented.");
        }
    }

    // Appends one vehicle entry to vehicles.txt
    public void saveVehicle(Vehicle vehicle) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(VEHICLE_FILE, true))) {
            writer.println(formatVehicleForFile(vehicle));
        } catch (IOException e) {
            System.out.println("Error saving vehicle: " + e.getMessage());
        }
    }

    // Appends one customer entry to customers.txt
    public void saveCustomer(Customer customer) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMER_FILE, true))) {
            writer.println(customer.getCustomerId() + "," + customer.getCustomerName());
        } catch (IOException e) {
            System.out.println("Error saving customer: " + e.getMessage());
        }
    }

    // Appends one rental record entry to rental_records.txt
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

    // Overwrites vehicles.txt with current vehicle list so statuses stay current
    private void rewriteVehiclesFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(VEHICLE_FILE, false))) {
            for (Vehicle vehicle : vehicles) {
                writer.println(formatVehicleForFile(vehicle));
            }
        } catch (IOException e) {
            System.out.println("Error updating vehicles file: " + e.getMessage());
        }
    }

    // Converts a vehicle into one line for the file
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
            extraDetails = vehicle.getInfo(); // includes accessible detail
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