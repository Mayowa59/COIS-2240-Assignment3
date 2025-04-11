package assignment3_source;

public final class SportCar extends Car {
    private int horsepower;
    private boolean hasTurbo;

    public SportCar(String licensePlate, String make, String model, int year, int numSeats, int horsepower, boolean hasTurbo) {
        super(licensePlate, make, model, year, numSeats);
        this.horsepower = horsepower;
        this.hasTurbo = hasTurbo;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " | Horsepower: " + horsepower + " | Turbo: " + (hasTurbo ? "Yes" : "No");
    }
}
