package assignment2;

import java.util.List;
import java.util.ArrayList;

public class RentalHistory {

    // list storing all rental records
    private List<RentalRecord> rentalRecords = new ArrayList<>();

    // adds a rental record to the history
    public void addRecord(RentalRecord record) {
        rentalRecords.add(record);
    }

    // returns the full list of rental records
    public List<RentalRecord> getRentalHistory() {
        return rentalRecords;
    }

    // returns all records matching a customer name
    public List<RentalRecord> getRentalRecordsByCustomer(String customerName) {
        List<RentalRecord> result = new ArrayList<>();

        for (RentalRecord record : rentalRecords) {
            // compare names using lowercase to avoid case sensitivity
            if (record.getCustomer().toString().toLowerCase().contains(customerName.toLowerCase())) {
                result.add(record);
            }
        }

        return result;
    }

    // returns all records for a specific vehicle plate
    public List<RentalRecord> getRentalRecordsByVehicle(String licensePlate) {
        List<RentalRecord> result = new ArrayList<>();

        for (RentalRecord record : rentalRecords) {
            if (record.getVehicle().getLicensePlate().equalsIgnoreCase(licensePlate)) {
                result.add(record);
            }
        }

        return result;
    }
}
