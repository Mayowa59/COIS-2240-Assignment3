package assignment2;

public class Customer {

    // customer ID number
    private int customerId;

    // customer name
    private String name;

    // constructor
    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    // returns customer ID
    public int getCustomerId() {
        return customerId;
    }

    // returns customer name
    public String getCustomerName() {
        return name;
    }

    // string version for displaying customer info
    @Override
    public String toString() {
        return "Customer ID: " + customerId + " | Name: " + name;
    }
}
