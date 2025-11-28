package assignment2;

// interface for all vehicles that can be rented
public interface Rentable {

    // called when the vehicle is rented
    void rentVehicle();

    // called when the vehicle is returned
    void returnVehicle();
}
