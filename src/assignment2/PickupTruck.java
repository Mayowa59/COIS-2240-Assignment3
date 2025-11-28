package assignment2;

public class PickupTruck extends Vehicle implements Rentable {

    // size of the cargo area in cubic units
    private double cargoSize;

    // whether the truck has a trailer attached
    private boolean hasTrailer;

    // constructor
    public PickupTruck(String make, String model, int year, double cargoSize, boolean hasTrailer) {
        super(make, model, year);

        // cargo size must be positive
        if (cargoSize <= 0) throw new IllegalArgumentException("Cargo size must be > 0");

        this.cargoSize = cargoSize;
        this.hasTrailer = hasTrailer;
    }

    // returns cargo size
    public double getCargoSize() {
        return cargoSize;
    }

    // returns true if the truck has a trailer
    public boolean hasTrailer() {
        return hasTrailer;
    }

    // formatted info for display
    @Override
    public String getInfo() {
        return super.getInfo() +
                " | Cargo Size: " + cargoSize +
                " | Has Trailer: " + (hasTrailer ? "Yes" : "No");
    }

    // sets status to rented
    @Override
    public void rentVehicle() {
        setStatus(VehicleStatus.Rented);
        System.out.println("Pickup Truck " + getLicensePlate() + " has been rented.");
    }

    // sets status to available
    @Override
    public void returnVehicle() {
        setStatus(VehicleStatus.Available);
        System.out.println("Pickup Truck " + getLicensePlate() + " has been returned.");
    }
}
