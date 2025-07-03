package src;

import java.util.*;

class Train {
    String number;
    String name;
    String source;
    String destination;
    int availableSeats;
    double fare;

    public Train(String number, String name, String source, String destination, int seats, double fare) {
        this.number = number;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.availableSeats = seats;
        this.fare = fare;
    }
}

class Ticket {
    String pnr;
    String passengerName;
    Train train;
    int seatsBooked;
    double totalFare;

    public Ticket(String pnr, String passengerName, Train train, int seatsBooked) {
        this.pnr = pnr;
        this.passengerName = passengerName;
        this.train = train;
        this.seatsBooked = seatsBooked;
        this.totalFare = seatsBooked * train.fare;
    }
}

public class TrainTicketBooking {
    static List<Train> trains = new ArrayList<>();
    static List<Ticket> tickets = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeTrains();
        
        while (true) {
            System.out.println("\nTrain Ticket Booking System");
            System.out.println("1. Search Trains");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Tickets");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    searchTrains();
                    break;
                case 2:
                    bookTicket();
                    break;
                case 3:
                    viewTickets();
                    break;
                case 4:
                    cancelTicket();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeTrains() {
        trains.add(new Train("12345", "Rajdhani Express", "Delhi", "Mumbai", 50, 1500.0));
        trains.add(new Train("67890", "Shatabdi Express", "Bangalore", "Chennai", 30, 800.0));
        trains.add(new Train("54321", "Duronto Express", "Kolkata", "Delhi", 40, 1200.0));
    }

    static void searchTrains() {
        System.out.print("Enter source station: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination station: ");
        String dest = scanner.nextLine();
        
        System.out.println("\nAvailable Trains:");
        boolean found = false;
        
        for (Train train : trains) {
            if (train.source.equalsIgnoreCase(source) && 
                train.destination.equalsIgnoreCase(dest) && 
                train.availableSeats > 0) {
                
                System.out.printf("%s - %s (%s to %s)\n", 
                    train.number, train.name, train.source, train.destination);
                System.out.printf("Available Seats: %d, Fare: ₹%.2f\n\n", 
                    train.availableSeats, train.fare);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No trains available for this route!");
        }
    }

    static void bookTicket() {
        System.out.print("Enter train number: ");
        String trainNum = scanner.nextLine();
        
        Train selectedTrain = null;
        for (Train train : trains) {
            if (train.number.equals(trainNum)) {
                selectedTrain = train;
                break;
            }
        }
        
        if (selectedTrain == null) {
            System.out.println("Invalid train number!");
            return;
        }
        
        if (selectedTrain.availableSeats <= 0) {
            System.out.println("No seats available on this train!");
            return;
        }
        
        System.out.print("Enter passenger name: ");
        String name = scanner.nextLine();
        System.out.print("Enter number of seats: ");
        int seats = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        if (seats <= 0) {
            System.out.println("Invalid number of seats!");
            return;
        }
        
        if (seats > selectedTrain.availableSeats) {
            System.out.println("Only " + selectedTrain.availableSeats + " seats available!");
            return;
        }
        
        String pnr = generatePNR();
        selectedTrain.availableSeats -= seats;
        tickets.add(new Ticket(pnr, name, selectedTrain, seats));
        
        System.out.println("\nTicket Booked Successfully!");
        System.out.println("PNR: " + pnr);
        System.out.println("Train: " + selectedTrain.number + " - " + selectedTrain.name);
        System.out.println("From: " + selectedTrain.source + " To: " + selectedTrain.destination);
        System.out.println("Passenger: " + name);
        System.out.println("Seats: " + seats);
        System.out.printf("Total Fare: ₹%.2f\n", seats * selectedTrain.fare);
    }

    static String generatePNR() {
        return "PNR" + System.currentTimeMillis() % 100000;
    }

    static void viewTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No tickets booked yet!");
            return;
        }
        
        System.out.println("\nYour Tickets:");
        for (Ticket ticket : tickets) {
            System.out.println("------------------------------");
            System.out.println("PNR: " + ticket.pnr);
            System.out.println("Train: " + ticket.train.number + " - " + ticket.train.name);
            System.out.println("Route: " + ticket.train.source + " to " + ticket.train.destination);
            System.out.println("Passenger: " + ticket.passengerName);
            System.out.println("Seats: " + ticket.seatsBooked);
            System.out.printf("Total Fare: ₹%.2f\n", ticket.totalFare);
            System.out.println("------------------------------");
        }
    }

    static void cancelTicket() {
        viewTickets();
        if (tickets.isEmpty()) return;
        
        System.out.print("Enter PNR to cancel: ");
        String pnr = scanner.nextLine();
        
        Ticket toCancel = null;
        for (Ticket ticket : tickets) {
            if (ticket.pnr.equals(pnr)) {
                toCancel = ticket;
                break;
            }
        }
        
        if (toCancel == null) {
            System.out.println("Invalid PNR!");
            return;
        }
        
        toCancel.train.availableSeats += toCancel.seatsBooked;
        tickets.remove(toCancel);
        System.out.println("Ticket cancelled successfully!");
        System.out.printf("Refund Amount: ₹%.2f\n", toCancel.totalFare);
    }
}
