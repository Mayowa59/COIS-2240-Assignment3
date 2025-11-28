package assignment2;

// SportCar is a type of Car with extra features (horsepower + turbo)
public final class SportCar extends Car {

    private int horsepower;   // engine power
    private boolean hasTurbo; // whether the car has a turbo system

    // constructor: creates a new SportCar with its special attributes
    public SportCar(String make, String model, int year,
                    int numSeats, int horsepower, boolean hasTurbo) {
        super(make, model, year, numSeats); // call Car constructor
        this.horsepower = horsepower;
        this.hasTurbo = hasTurbo;
    }

    // returns detailed car info including horsepower and turbo status
    @Override
    public String getInfo() {
        return super.getInfo()
               + " | Horsepower: " + horsepower
               + " | Turbo: " + (hasTurbo ? "Yes" : "No");
    }
}
