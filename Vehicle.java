public abstract class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { Available, Held, Rented, UnderMaintenance, OutOfService }

    public Vehicle(String make, String model, int year) {
        // Uses the helper method to format make properly
        this.make = capitalize(make);

        // Uses the helper method to format model properly
        this.model = capitalize(model);

        this.year = year;
        this.status = VehicleStatus.Available;
        this.licensePlate = null;
    }

    // Helper method to capitalize the first letter
    // and make the rest of the letters lowercase
    private String capitalize(String input) {
        // Returns null if the input is null or empty
        if (input == null || input.isEmpty()) {
            return null;
        }

        // Formats the string so the first letter is uppercase
        // and the remaining letters are lowercase
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
 // Checks if the license plate is valid
 // A valid plate must have 3 letters followed by 3 numbers
 private boolean isValidPlate(String plate) {
     if (plate == null || plate.isEmpty()) {
         return false;
     }

     return plate.matches("[A-Za-z]{3}[0-9]{3}");
 }
    public Vehicle() {
        this(null, null, 0);
    }

    public void setLicensePlate(String plate) {
        if (!isValidPlate(plate)) {
            throw new IllegalArgumentException("Invalid license plate. It must be 3 letters followed by 3 numbers.");
        }
        this.licensePlate = plate.toUpperCase();
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }
    
    

    public String getMake() { return make; }

    public String getModel() { return model; }

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }

    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status + " |";
    }
}