 //Mayowa Adeyeri
package assignment2;

public class SportCar extends Car {
    private int horsepower;
    private boolean turbo;

    public SportCar(String make, String model, int year, int numSeats, int horsepower, boolean turbo) {
        super(make, model, year, numSeats);
        this.horsepower = horsepower;
        this.turbo = turbo;
    }

    @Override
    public String getInfo() {
        return "SportCar [" + licensePlate + "] " + make + " " + model + " (" + year + "), HP: " + horsepower + ", Turbo: " + turbo + ", Status: " + status;
    }
}
//Mayowa Adeyeri
