package assignment3_source;

public class Car extends Vehicle {
    public Car(String licensePlate, String make, String model, int year) {
        super(licensePlate, make, model, year);
    }

    @Override
    public String getType() {
        return "Car";
    }
}
