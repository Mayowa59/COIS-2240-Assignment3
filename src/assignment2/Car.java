package assignment2;

public class Car extends Vehicle implements Rentable {

    // number of seats in the car
    private int numSeats;

    // constructor
    public Car(String make, String model, int year, int numSeats) {
        super(make, model, year);
        this.numSeats = numSeats;
    }

    public int getNumSeats() {
        return numSeats;
    }

    // returns vehicle info + seat count
    @Override
    public String getInfo() {
        return super.getInfo() + " | Seats: " + numSeats;
    }

    // marks the car as rented
    @Override
    public void rentVehicle() {
        setStatus(VehicleStatus.Rented);
        System.out.println("Car " + getLicensePlate() + " has been rented.");
    }

    // marks the car as returned
    @Override
    public void returnVehicle() {
        setStatus(VehicleStatus.Available);
        System.out.println("Car " + getLicensePlate() + " has been returned.");
    }
}
