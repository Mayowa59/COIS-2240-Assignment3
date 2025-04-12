package assignment3_source;
import java.util.*;

public class RentalSystem {
    private static RentalSystem instance;
    private List<Vehicle> vehicles;
    private List<Customer> customers;
    private RentalHistory rentalHistory;

    private RentalSystem() {
        vehicles = new ArrayList<>();
        customers = new ArrayList<>();
        rentalHistory = new RentalHistory();
    }

    public static RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }

    public boolean addCustomer(Customer customer) {
        for (Customer c : customers) {
            if (c.getId().equals(customer.getId())) return false;
        }
        customers.add(customer);
        return true;
    }

    public boolean addVehicle(Vehicle vehicle) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equals(vehicle.getLicensePlate())) return false;
        }
        vehicles.add(vehicle);
        return true;
    }

    public Customer findCustomerById(String id) {
        for (Customer c : customers) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }

    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equals(plate)) return v;
        }
        return null;
    }

    public void rentVehicle(Customer customer, Vehicle vehicle, String year) {
        if (!vehicle.isRented()) {
            vehicle.setRented(true);
            rentalHistory.addRecord(new RentalRecord(customer, vehicle, year));
            System.out.println(customer.getName() + " successfully rented " + vehicle.getLicensePlate());
        } else {
            System.out.println(vehicle.getLicensePlate() + " is already rented.");
        }
    }

    public void returnVehicle(Customer customer, Vehicle vehicle, String year) {
        for (RentalRecord record : rentalHistory.getRecords()) {
            if (record.getVehicle().equals(vehicle) && !record.isReturned()) {
                record.setReturnYear(year);
                vehicle.setRented(false);
                System.out.println(customer.getName() + " successfully returned " + vehicle.getLicensePlate());
                break;
            }
        }
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> available = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (!v.isRented()) {
                available.add(v);
            }
        }
        return available;
    }

    public List<RentalRecord> getRentalHistory() {
        return rentalHistory.getRecords();
    }
}
