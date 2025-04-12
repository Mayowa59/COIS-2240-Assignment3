package assignment3_source;

public class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate, String make, String model, int year) {
        super(licensePlate, make, model, year);
    }

    @Override
    public String getType() {
        return "Motorcycle";
    }
}
