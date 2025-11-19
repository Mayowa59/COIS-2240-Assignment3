 //Mayowa Adeyeri

package assignment2;

public class PickupTruck extends Vehicle {
    private double cargoSize;
    private boolean hasTrailer;

    public PickupTruck(String make, String model, int year, double cargoSize, boolean hasTrailer) {
        super(make, model, year);
        this.cargoSize = cargoSize;
        this.hasTrailer = hasTrailer;
    }

    @Override
    public String getInfo() {
        return "Truck [" + licensePlate + "] " + make + " " + model + " (" + year + "), Cargo: " + cargoSize + "ft, Trailer: " + hasTrailer + ", Status: " + status;
    }
}
//Mayowa Adeyeri
