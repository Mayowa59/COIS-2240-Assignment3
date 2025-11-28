package assignment2;

import java.time.LocalDate;

public class RentalRecord {

    // vehicle involved in the rental
    private Vehicle vehicle;

    // customer involved in the rental
    private Customer customer;

    // date of the rental or return
    private LocalDate recordDate;

    // rental amount or return fee
    private double totalAmount;

    // "RENT" or "RETURN"
    private String recordType;

    // constructor: creates a rental record entry
    public RentalRecord(Vehicle vehicle, Customer customer, LocalDate recordDate,
                        double totalAmount, String recordType) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.recordDate = recordDate;
        this.totalAmount = totalAmount;
        this.recordType = recordType;
    }

    // returns the customer for this record
    public Customer getCustomer() {
        return customer;
    }

    // returns the vehicle for this record
    public Vehicle getVehicle() {
        return vehicle;
    }

    // returns the date of this record
    public LocalDate getRecordDate() {
        return recordDate;
    }

    // returns the cost of the rental or return
    public double getTotalAmount() {
        return totalAmount;
    }

    // returns whether this record is a "RENT" or "RETURN"
    public String getRecordType() {
        return recordType;
    }

    // converts the record to a readable string
    @Override
    public String toString() {
        return recordType + " | Plate: " + vehicle.getLicensePlate()
                + " | Customer: " + customer.getCustomerName()
                + " | Date: " + recordDate
                + " | Amount: $" + totalAmount;
    }
}
