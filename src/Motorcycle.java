 //Mayowa Adeyeri
package assignment2;

public class Motorcycle extends Vehicle {
    private boolean hasSidecar;

    public Motorcycle(String make, String model, int year, boolean hasSidecar) {
        super(make, model, year);
        this.hasSidecar = hasSidecar;
    }

    @Override
    public String getInfo() {
        return "Motorcycle [" + licensePlate + "] " + make + " " + model + " (" + year + "), Sidecar: " + hasSidecar + ", Status: " + status;
    }
}
//Mayowa Adeyeri
