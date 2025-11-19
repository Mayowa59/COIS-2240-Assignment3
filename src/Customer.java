 //Mayowa Adeyeri
package assignment2;

public class Customer {
    private String customerId;
    private String name;
    private String phoneNumber;

    public Customer(String customerId, String name, String phoneNumber) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerId() { return customerId; }

    public String getName() { return name; }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Phone: " + phoneNumber;
    }
}
//Mayowa Adeyeri
