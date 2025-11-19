 //Mayowa Adeyeri
package assignment2;

import java.time.LocalDate;

public class RentalRecord {
    private Vehicle vehicle;
    private Customer customer;
    private LocalDate date;
    private double cost;
    private String type; // RENT or RETURN

    public RentalRecord(Vehicle vehicle, Customer customer, LocalDate date, double cost, String type) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.date = date;
        this.cost = cost;
        this.type = type;
    }

    @Override
    public String toString() {
        String customerName = (customer != null) ? customer.getName() : "N/A";
        return "[" + type + "] " + date + " - " + vehicle.getLicensePlate() + " (" + vehicle.getClass().getSimpleName() + ") - Customer: " + customerName + " - Cost: $" + cost;
    }
}
//Mayowa Adeyeri
