package assignment2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.LocalDate;

public class VehicleRentalTest {

    private RentalSystem system;

    @BeforeEach
    public void setUp() {
        system = RentalSystem.getInstance();
    }

    
    // 1. License Plate Validation Tests
    
    public void testLicensePlate() {

        Vehicle car = new Car("Honda", "Civic", 2020, 4);

        // Valid plates
        assertDoesNotThrow(() -> car.setLicensePlate("AAA100"));
        assertDoesNotThrow(() -> car.setLicensePlate("ABC567"));
        assertDoesNotThrow(() -> car.setLicensePlate("ZZZ999"));

        // Invalid plates
        assertThrows(IllegalArgumentException.class, () -> car.setLicensePlate(""));
        assertThrows(IllegalArgumentException.class, () -> car.setLicensePlate(null));
        assertThrows(IllegalArgumentException.class, () -> car.setLicensePlate("AAA1000"));
        assertThrows(IllegalArgumentException.class, () -> car.setLicensePlate("ZZZ99"));
    }

   
    // 2. Rent + Return Vehicle Tests
    
    @Test
    public void testRentAndReturnVehicle() {

        Vehicle car = new Car("Toyota", "Corolla", 2022, 5);
        car.setLicensePlate("ABC123");

        Customer c = new Customer(1, "John Doe");

        // Initial status
        assertEquals(Vehicle.VehicleStatus.Available, car.getStatus());

        // First rent should succeed
        boolean rented = system.rentVehicle(car, c, LocalDate.now(), 50.0);
        assertTrue(rented);
        assertEquals(Vehicle.VehicleStatus.Rented, car.getStatus());

        // Renting again should fail
        boolean rentedAgain = system.rentVehicle(car, c, LocalDate.now(), 50.0);
        assertFalse(rentedAgain);

        // Returning the vehicle should succeed
        boolean returned = system.returnVehicle(car, c, LocalDate.now(), 0.0);
        assertTrue(returned);
        assertEquals(Vehicle.VehicleStatus.Available, car.getStatus());

        // Returning again should fail
        boolean returnAgain = system.returnVehicle(car, c, LocalDate.now(), 0.0);
        assertFalse(returnAgain);
    }

    
    // 3. Singleton Validation Test
    
    @Test
    public void testSingletonRentalSystem() throws Exception {

        Constructor<RentalSystem> constructor =
                RentalSystem.class.getDeclaredConstructor();

        int mods = constructor.getModifiers();

        // Constructor must be PRIVATE
        assertEquals(Modifier.PRIVATE, mods);
    }
}
