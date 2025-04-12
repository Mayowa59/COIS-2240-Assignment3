package assignment3_source;
public class RentalRecord {
    private Customer customer;
    private Vehicle vehicle;
    private String rentYear;
    private String returnYear;

    public RentalRecord(Customer customer, Vehicle vehicle, String rentYear) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.rentYear = rentYear;
        this.returnYear = null;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isReturned() {
        return returnYear != null;
    }

    public void setReturnYear(String returnYear) {
        this.returnYear = returnYear;
    }

    public String toString() {
        return customer.getName() + " rented " + vehicle.getLicensePlate() +
               " in " + rentYear + (returnYear != null ? ", returned in " + returnYear : " (Not returned)");
    }
}
