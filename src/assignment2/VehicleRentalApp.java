package assignment2;
import java.time.LocalDate;
import java.util.Scanner;

public class VehicleRentalApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        RentalSystem rentalSystem = RentalSystem.getInstance();

        while (true) {

            System.out.println("\n1: Add Vehicle\n" +
                               "2: Add Customer\n" +
                               "3: Rent Vehicle\n" +
                               "4: Return Vehicle\n" +
                               "5: Display Available Vehicles\n" +
                               "6: Show Rental History\n" +
                               "0: Exit\n");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("  1: Car\n" +
                                       "  2: Minibus\n" +
                                       "  3: Pickup Truck\n" +
                                       "  4: SportCar");

                    int type = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter license plate: ");
                    String plate = scanner.nextLine().toUpperCase();

                    System.out.print("Enter make: ");
                    String make = scanner.nextLine();

                    System.out.print("Enter model: ");
                    String model = scanner.nextLine();

                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();

                    Vehicle v = null;

                    if (type == 1) {
                        System.out.print("Seats: ");
                        int seats = scanner.nextInt();
                        v = new Car(make, model, year, seats);

                    } else if (type == 2) {
                        System.out.print("Accessible? (true/false): ");
                        boolean acc = scanner.nextBoolean();
                        v = new Minibus(make, model, year, acc);

                    } else if (type == 3) {
                        System.out.print("Cargo size: ");
                        double cargo = scanner.nextDouble();
                        System.out.print("Trailer? (true/false): ");
                        boolean tr = scanner.nextBoolean();
                        v = new PickupTruck(make, model, year, cargo, tr);

                    } else if (type == 4) {
                        System.out.print("Seats: ");
                        int seats = scanner.nextInt();
                        System.out.print("Horsepower: ");
                        int hp = scanner.nextInt();
                        System.out.print("Turbo? (true/false): ");
                        boolean turbo = scanner.nextBoolean();
                        v = new SportCar(make, model, year, seats, hp, turbo);
                    }

                    if (v != null) {
                        v.setLicensePlate(plate);
                        rentalSystem.addVehicle(v);
                    }

                    break;


                case 2:
                    System.out.print("Enter customer ID: ");
                    int cid = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter name: ");
                    String cname = scanner.nextLine();

                    rentalSystem.addCustomer(new Customer(cid, cname));
                    break;


                case 3:
                    rentalSystem.displayVehicles(Vehicle.VehicleStatus.Available);

                    System.out.print("Enter license plate: ");
                    String rentPlate = scanner.nextLine().toUpperCase();

                    System.out.println("Registered Customers:");
                    rentalSystem.displayAllCustomers();

                    System.out.print("Enter customer ID: ");
                    int cidRent = scanner.nextInt();

                    System.out.print("Enter rental amount: ");
                    double rentAmount = scanner.nextDouble();

                    rentalSystem.rentVehicle(
                        rentalSystem.findVehicleByPlate(rentPlate),
                        rentalSystem.findCustomerById(cidRent),
                        LocalDate.now(),
                        rentAmount
                    );
                    break;


                case 4:
                    rentalSystem.displayVehicles(Vehicle.VehicleStatus.Rented);

                    System.out.print("Enter license plate: ");
                    String returnPlate = scanner.nextLine().toUpperCase();

                    System.out.println("Registered Customers:");
                    rentalSystem.displayAllCustomers();

                    System.out.print("Enter customer ID: ");
                    int cidReturn = scanner.nextInt();

                    System.out.print("Return fees: ");
                    double returnFees = scanner.nextDouble();

                    rentalSystem.returnVehicle(
                        rentalSystem.findVehicleByPlate(returnPlate),
                        rentalSystem.findCustomerById(cidReturn),
                        LocalDate.now(),
                        returnFees
                    );
                    break;


                case 5:
                    rentalSystem.displayVehicles(Vehicle.VehicleStatus.Available);
                    break;


                case 6:
                    rentalSystem.displayRentalHistory();
                    break;


                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
            }
        }
    }
}
