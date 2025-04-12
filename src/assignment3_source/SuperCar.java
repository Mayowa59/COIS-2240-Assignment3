package assignment3_source;

public class SuperCar extends Vehicle {
    public SuperCar(String licensePlate, String make, String model, int year) {
        super(licensePlate, make, model, year);
    }

    @Override
    public String getType() {
        return "SuperCar";
    }
}
