 //Mayowa Adeyeri
package assignment2;

public abstract class Vehicle implements Rentable {
    public enum Status { AVAILABLE, RENTED }

    protected String make;
    protected String model;
    protected int year;
    protected String licensePlate;
    protected Status status;

    private static int plateCounter = 1000;

    public Vehicle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.status = Status.AVAILABLE;
        this.licensePlate = "PLT" + (plateCounter++);
    }

    public Status getStatus() { return status; }

    public void setStatus(Status s) { this.status = s; }

    public String getLicensePlate() { return licensePlate; }

    public abstract String getInfo();

    public void rent() {
        if (status == Status.AVAILABLE) {
            status = Status.RENTED;
            System.out.println(getInfo() + " is now rented.");
        } else {
            System.out.println(getInfo() + " is already rented.");
        }
    }

    public void returnVehicle() {
        if (status == Status.RENTED) {
            status = Status.AVAILABLE;
            System.out.println(getInfo() + " has been returned.");
        } else {
            System.out.println(getInfo() + " was not rented.");
        }
    }

    public boolean isAvailable() {
        return status == Status.AVAILABLE;
    }
}
//Mayowa Adeyeri
