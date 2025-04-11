package assignment3_source;

public class Vehicle {
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private VehicleStatus status;

    public enum VehicleStatus {
        AVAILABLE,
        RENTED
    }

    public Vehicle(String make, String model, int year, String licensePlate) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.status = VehicleStatus.AVAILABLE;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public String getInfo() {
        return "Make: " + make + " | Model: " + model + " | Year: " + year;
    }
}
