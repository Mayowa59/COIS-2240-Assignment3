 //Mayowa Adeyeri
package assignment2;

public class Car extends Vehicle {
    private int numSeats;

    public Car(String make, String model, int year, int numSeats) {
        super(make, model, year);
        this.numSeats = numSeats;
    }

    @Override
    public String getInfo() {
        return "Car [" + licensePlate + "] " + make + " " + model + " (" + year + "), Seats: " + numSeats + ", Status: " + status;
    }
}
//Mayowa Adeyeri
