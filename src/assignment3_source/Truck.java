package assignment3_source;

public class Truck extends Vehicle {
    public Truck(String licensePlate, String make, String model, int year) {
        super(licensePlate, make, model, year);
    }

    @Override
    public String getType() {
        return "Truck";
    }
}
