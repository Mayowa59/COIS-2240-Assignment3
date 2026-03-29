import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class VehicleRentalTest {

    @Test
    public void testLicensePlate() {
        Vehicle v1 = new Car("Toyota", "Corolla", 2020, 5);
        Vehicle v2 = new Car("Honda", "Civic", 2021, 5);
        Vehicle v3 = new Car("Ford", "Focus", 2022, 5);

        assertDoesNotThrow(() -> v1.setLicensePlate("AAA100"));
        assertDoesNotThrow(() -> v2.setLicensePlate("ABC567"));
        assertDoesNotThrow(() -> v3.setLicensePlate("ZZZ999"));

        v1.setLicensePlate("AAA100");
        v2.setLicensePlate("ABC567");
        v3.setLicensePlate("ZZZ999");

        assertEquals("AAA100", v1.getLicensePlate());
        assertEquals("ABC567", v2.getLicensePlate());
        assertEquals("ZZZ999", v3.getLicensePlate());

        Vehicle v4 = new Car("BMW", "X5", 2020, 5);
        Vehicle v5 = new Car("Audi", "A4", 2021, 5);
        Vehicle v6 = new Car("Kia", "Soul", 2022, 5);
        Vehicle v7 = new Car("Mazda", "3", 2023, 5);

        assertThrows(IllegalArgumentException.class, () -> v4.setLicensePlate(""));
        assertThrows(IllegalArgumentException.class, () -> v5.setLicensePlate(null));
        assertThrows(IllegalArgumentException.class, () -> v6.setLicensePlate("AAA1000"));
        assertThrows(IllegalArgumentException.class, () -> v7.setLicensePlate("ZZZ99"));
    }

    @Test
    public void testRentAndReturnVehicle() {
        Vehicle vehicle = new Car("Toyota", "Corolla", 2020, 5);
        vehicle.setLicensePlate("ABC123");

        Customer customer = new Customer(100, "Mayowa");

        RentalSystem rentalSystem = RentalSystem.getInstance();

        assertEquals(Vehicle.VehicleStatus.Available, vehicle.getStatus());

        boolean firstRent = rentalSystem.rentVehicle(vehicle, customer, LocalDate.now(), 100.0);
        assertTrue(firstRent);
        assertEquals(Vehicle.VehicleStatus.Rented, vehicle.getStatus());

        boolean secondRent = rentalSystem.rentVehicle(vehicle, customer, LocalDate.now(), 100.0);
        assertFalse(secondRent);

        boolean firstReturn = rentalSystem.returnVehicle(vehicle, customer, LocalDate.now(), 0.0);
        assertTrue(firstReturn);
        assertEquals(Vehicle.VehicleStatus.Available, vehicle.getStatus());

        boolean secondReturn = rentalSystem.returnVehicle(vehicle, customer, LocalDate.now(), 0.0);
        assertFalse(secondReturn);
    }

    @Test
    public void testSingletonRentalSystem() throws Exception {
        Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();

        int modifiers = constructor.getModifiers();
        assertEquals(Modifier.PRIVATE, modifiers);

        RentalSystem rentalSystem = RentalSystem.getInstance();
        assertNotNull(rentalSystem);
    }
}