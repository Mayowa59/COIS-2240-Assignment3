//Mayowa Adeyeri
package assignment2;

import java.util.ArrayList;
import java.util.List;

public class RentalHistory {
    private List<RentalRecord> records = new ArrayList<>();

    public void addRecord(RentalRecord record) {
        records.add(record);
    }

    public List<RentalRecord> getRentalHistory() {
        return records;
    }
}
//Mayowa Adeyeri
