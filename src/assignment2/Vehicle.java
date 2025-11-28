package assignment2;

public abstract class Vehicle {

    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { Available, Held, Rented, UnderMaintenance, OutOfService }

    // Constructor
    public Vehicle(String make, String model, int year) {
        this.make  = capitalize(make);
        this.model = capitalize(model);
        this.year = year;
        this.status = VehicleStatus.Available;
        this.licensePlate = null;
    }

    // Empty constructor
    public Vehicle() {
        this(null, null, 0);
    }

    // Capitalizes first letter
    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return null;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    // Validates plate format
    private boolean isValidPlate(String plate) {
        if (plate == null || plate.isEmpty()) return false;
        return plate.matches("^[A-Z]{3}[0-9]{3}$");
    }

    // Sets license plate
    public void setLicensePlate(String plate) {
        if (!isValidPlate(plate)) {
            throw new IllegalArgumentException("Invalid license plate format.");
        }
        this.licensePlate = plate.toUpperCase();
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }
    public String getMake()         { return make; }
    public String getModel()        { return model; }
    public int getYear()            { return year; }
    public VehicleStatus getStatus(){ return status; }

    // Returns formatted vehicle info
    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status + " |";
    }
}

