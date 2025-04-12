import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class VehicleRentalTest {

    @Test
    public void testLicensePlateValidation() {
        // Valid plates should NOT throw an exception
        assertDoesNotThrow(() -> new Car("AAA100", "Toyota", "Camry", 2020));
        assertDoesNotThrow(() -> new Car("ABC567", "Honda", "Civic", 2021));
        assertDoesNotThrow(() -> new Car("ZZZ999", "Ford", "Focus", 2022));

        // Invalid plates should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Car("", "Toyota", "Camry", 2020));
        assertThrows(IllegalArgumentException.class, () -> new Car(null, "Toyota", "Camry", 2020));
        assertThrows(IllegalArgumentException.class, () -> new Car("AAA1000", "Toyota", "Camry", 2020));
        assertThrows(IllegalArgumentException.class, () -> new Car("ZZZ99", "Toyota", "Camry", 2020));
    }
    // Test Case 2: Rent/Return Vehicle Validation
    @Test
    public void testRentAndReturnVehicle() {
        // Create test customer and vehicle
        Customer customer = new Customer("C001", "John Doe");
        Vehicle vehicle = new Car("AAA100", "Toyota", "Camry", 2020);

        // Add customer and vehicle to the system
        system.addCustomer(customer);
        system.addVehicle(vehicle);

        // Initial availability check
        assertTrue(vehicle.isRented() == false, "Vehicle should be available initially");

        // Rent vehicle
        system.rentVehicle(customer, vehicle, "2025");
        assertTrue(vehicle.isRented(), "Vehicle should be marked as rented after being rented");

        // Try to rent the same vehicle again
        system.rentVehicle(customer, vehicle, "2025");
        assertTrue(vehicle.isRented(), "Vehicle should remain rented when attempted to rent again");

        // Return vehicle
        system.returnVehicle(customer, vehicle, "2025");
        assertFalse(vehicle.isRented(), "Vehicle should be marked as available after being returned");

        // Try to return the same vehicle again
        system.returnVehicle(customer, vehicle, "2025");
        assertFalse(vehicle.isRented(), "Vehicle should remain available when attempted to return again");
    }

    
}
