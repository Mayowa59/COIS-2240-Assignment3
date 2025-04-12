package assignment3_source;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class VehicleRentalTest {
    private RentalSystem system;

    @BeforeEach
    public void setUp() {
        // Initialize the RentalSystem instance for shared use in test cases.
        system = RentalSystem.getInstance();
    }

    // Test Case 1: Vehicle License Plate Validation
    @Test
    public void testLicensePlateValidation() {
        // Test valid plates
        assertDoesNotThrow(() -> new Car("AAA100", "Toyota", "Camry", 2020));  // valid plate
        assertDoesNotThrow(() -> new Car("ABC567", "Honda", "Civic", 2021));  // valid plate
        assertDoesNotThrow(() -> new Car("ZZZ999", "Ford", "Focus", 2022));   // valid plate

        // Test invalid plates
        assertThrows(IllegalArgumentException.class, () -> new Car("", "Toyota", "Camry", 2020));        // empty plate
        assertThrows(IllegalArgumentException.class, () -> new Car(null, "Toyota", "Camry", 2020));      // null plate
        assertThrows(IllegalArgumentException.class, () -> new Car("AAA1000", "Toyota", "Camry", 2020)); // plate with more than 6 characters
        assertThrows(IllegalArgumentException.class, () -> new Car("ZZZ99", "Toyota", "Camry", 2020));   // plate with less than 6 characters
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

    // Test Case 3: Singleton Validation
    @Test
    public void testSingletonRentalSystem() throws Exception {
        // Access the constructor of RentalSystem class
        Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();
        // Make the constructor accessible
        constructor.setAccessible(true);

        // Assert that the constructor is private (Singleton pattern enforcement)
        int modifiers = constructor.getModifiers();
        assertEquals(Modifier.PRIVATE, modifiers, "The RentalSystem constructor should be private");

        // Try to instantiate a new object via reflection (should throw an exception)
        assertThrows(IllegalAccessException.class, () -> constructor.newInstance());

        // Get the instance of RentalSystem through the Singleton method
        RentalSystem singletonInstance = RentalSystem.getInstance();
        assertNotNull(singletonInstance, "The RentalSystem instance should not be null");
    }
}
