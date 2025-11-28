package assignment2;

public class Minibus extends Vehicle implements Rentable {

    // true if the minibus is wheelchair accessible
    private boolean isAccessible;

    // constructor
    public Minibus(String make, String model, int year, boolean isAccessible) {
        super(make, model, year);
        this.isAccessible = isAccessible;
    }

    // returns formatted info about the minibus
    @Override
    public String getInfo() {
        return super.getInfo() + " | Accessible: " + (isAccessible ? "Yes" : "No");
    }

    // sets status to rented
    @Override
    public void rentVehicle() {
        setStatus(VehicleStatus.Rented);
        System.out.println("Minibus " + getLicensePlate() + " has been rented.");
    }

    // sets status back to available
    @Override
    public void returnVehicle() {
        setStatus(VehicleStatus.Available);
        System.out.println("Minibus " + getLicensePlate() + " has been returned.");
    }
}

