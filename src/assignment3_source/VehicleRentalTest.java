import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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

