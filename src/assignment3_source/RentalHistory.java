package assignment3_source;

import java.util.*;

public class RentalHistory {
    private List<RentalRecord> records;

    public RentalHistory() {
        records = new ArrayList<>();
    }

    public void addRecord(RentalRecord record) {
        records.add(record);
    }

    public List<RentalRecord> getRecords() {
        return records;
    }
}
