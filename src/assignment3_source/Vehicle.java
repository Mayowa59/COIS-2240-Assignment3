package assignment3_source;
public abstract class Vehicle implements Rentable {
    protected String licensePlate;
    protected String make;
    protected String model;
    protected int year;
    protected boolean isRented;

    public Vehicle(String licensePlate, String make, String model, int year) {
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.isRented = false;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        this.isRented = rented;
    }

    public String toDataString() {
        return getType() + "," + licensePlate + "," + make + "," + model + "," + year;
    }

    public String toString() {
        return make + " " + model + " (" + year + ") - " + licensePlate + " [" + (isRented ? "RENTED" : "AVAILABLE") + "]";
    }
}
