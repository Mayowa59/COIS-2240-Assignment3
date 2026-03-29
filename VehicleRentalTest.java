
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VehicleRentalTest {

    @Test
    public void testLicensePlate() {
        Vehicle v1 = new Car("Toyota", "Corolla", 2020, 5);
        Vehicle v2 = new Car("Honda", "Civic", 2021, 5);
        Vehicle v3 = new Car("Ford", "Focus", 2022, 5);

        // valid plates
        assertDoesNotThrow(() -> v1.setLicensePlate("AAA100"));
        assertDoesNotThrow(() -> v2.setLicensePlate("ABC567"));
        assertDoesNotThrow(() -> v3.setLicensePlate("ZZZ999"));

        v1.setLicensePlate("AAA100");
        v2.setLicensePlate("ABC567");
        v3.setLicensePlate("ZZZ999");

        assertEquals("AAA100", v1.getLicensePlate());
        assertEquals("ABC567", v2.getLicensePlate());
        assertEquals("ZZZ999", v3.getLicensePlate());

        // invalid plates
        Vehicle v4 = new Car("BMW", "X5", 2020, 5);
        Vehicle v5 = new Car("Audi", "A4", 2021, 5);
        Vehicle v6 = new Car("Kia", "Soul", 2022, 5);
        Vehicle v7 = new Car("Mazda", "3", 2023, 5);

        assertThrows(IllegalArgumentException.class, () -> v4.setLicensePlate(""));
        assertThrows(IllegalArgumentException.class, () -> v5.setLicensePlate(null));
        assertThrows(IllegalArgumentException.class, () -> v6.setLicensePlate("AAA1000"));
        assertThrows(IllegalArgumentException.class, () -> v7.setLicensePlate("ZZZ99"));
    }
}