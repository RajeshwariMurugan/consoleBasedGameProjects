package src;

import java.util.*;

class Cab {
    String driver;
    String vehicleNumber;
    String vehicleType;
    boolean isAvailable;

    public Cab(String driver, String vehicleNumber, String vehicleType) {
        this.driver = driver;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.isAvailable = true;
    }
}

class Booking {
    String customerName;
    String pickupLocation;
    String dropLocation;
    Cab cab;
    double fare;

    public Booking(String customerName, String pickupLocation, String dropLocation, Cab cab, double fare) {
        this.customerName = customerName;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.cab = cab;
        this.fare = fare;
    }
}

public class CabBookingSystem {
    static List<Cab> cabs = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCabs();
        
        while (true) {
            System.out.println("\nCab Booking System");
            System.out.println("1. Book a Cab");
            System.out.println("2. View Available Cabs");
            System.out.println("3. View Bookings");
            System.out.println("4. Complete Ride");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    bookCab();
                    break;
                case 2:
                    viewAvailableCabs();
                    break;
                case 3:
                    viewBookings();
                    break;
                case 4:
                    completeRide();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeCabs() {
        cabs.add(new Cab("John Doe", "KA01AB1234", "Sedan"));
        cabs.add(new Cab("Jane Smith", "KA02CD5678", "SUV"));
        cabs.add(new Cab("Mike Johnson", "KA03EF9012", "Hatchback"));
    }

    static void bookCab() {
        viewAvailableCabs();
        if (getAvailableCabs().isEmpty()) {
            System.out.println("No cabs available at the moment!");
            return;
        }
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter pickup location: ");
        String pickup = scanner.nextLine();
        System.out.print("Enter drop location: ");
        String drop = scanner.nextLine();
        System.out.print("Enter cab number to book: ");
        int cabNum = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (cabNum < 0 || cabNum >= cabs.size()) {
            System.out.println("Invalid cab number!");
            return;
        }
        
        Cab selectedCab = cabs.get(cabNum);
        if (!selectedCab.isAvailable) {
            System.out.println("Cab is not available!");
            return;
        }
        
        // Simple fare calculation based on vehicle type
        double fare;
        switch (selectedCab.vehicleType.toLowerCase()) {
            case "suv": fare = 15.0; break;
            case "sedan": fare = 12.0; break;
            default: fare = 10.0;
        }
        
        selectedCab.isAvailable = false;
        bookings.add(new Booking(name, pickup, drop, selectedCab, fare));
        System.out.println("Booking confirmed!");
        System.out.println("Driver: " + selectedCab.driver);
        System.out.println("Vehicle: " + selectedCab.vehicleNumber + " (" + selectedCab.vehicleType + ")");
        System.out.println("Fare: $" + fare);
    }

    static void viewAvailableCabs() {
        System.out.println("\nAvailable Cabs:");
        int count = 1;
        for (Cab cab : cabs) {
            if (cab.isAvailable) {
                System.out.printf("%d. %s - %s (%s)\n", 
                    count++, cab.driver, cab.vehicleNumber, cab.vehicleType);
            }
        }
        
        if (count == 1) {
            System.out.println("No cabs available at the moment!");
        }
    }

    static List<Cab> getAvailableCabs() {
        List<Cab> available = new ArrayList<>();
        for (Cab cab : cabs) {
            if (cab.isAvailable) {
                available.add(cab);
            }
        }
        return available;
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet!");
            return;
        }
        
        System.out.println("\nAll Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            String status = b.cab.isAvailable ? "Completed" : "In Progress";
            System.out.printf("%d. %s: %s to %s (%s - %s) - $%.2f - %s\n", 
                i+1, b.customerName, b.pickupLocation, b.dropLocation, 
                b.cab.driver, b.cab.vehicleNumber, b.fare, status);
        }
    }

    static void completeRide() {
        viewBookings();
        if (bookings.isEmpty()) return;
        
        System.out.print("Enter booking number to complete: ");
        int bookingNum = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (bookingNum < 0 || bookingNum >= bookings.size()) {
            System.out.println("Invalid booking number!");
            return;
        }
        
        Booking completed = bookings.get(bookingNum);
        if (completed.cab.isAvailable) {
            System.out.println("This ride is already completed!");
            return;
        }
        
        completed.cab.isAvailable = true;
        System.out.println("Ride completed successfully!");
        System.out.println("Amount to be paid: $" + completed.fare);
    }
}