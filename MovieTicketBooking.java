package src;

import java.util.*;

class Movie {
    String title;
    String showTime;
    int availableSeats;
    double price;

    public Movie(String title, String showTime, int availableSeats, double price) {
        this.title = title;
        this.showTime = showTime;
        this.availableSeats = availableSeats;
        this.price = price;
    }
}

public class MovieTicketBooking {
    static List<Movie> movies = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMovies();
        
        while (true) {
            System.out.println("\nMovie Ticket Booking System");
            System.out.println("1. Show Movies");
            System.out.println("2. Book Ticket");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showMovies();
                    break;
                case 2:
                    bookTicket();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeMovies() {
        movies.add(new Movie("Avengers", "10:00 AM", 50, 12.99));
        movies.add(new Movie("Joker", "2:00 PM", 30, 10.50));
        movies.add(new Movie("Frozen 2", "6:00 PM", 40, 8.99));
    }

    static void showMovies() {
        System.out.println("\nAvailable Movies:");
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            System.out.printf("%d. %s (Time: %s, Seats: %d, Price: $%.2f)\n", 
                i+1, m.title, m.showTime, m.availableSeats, m.price);
        }
    }

    static void bookTicket() {
        showMovies();
        System.out.print("\nSelect movie number: ");
        int movieNum = scanner.nextInt() - 1;
        
        if (movieNum < 0 || movieNum >= movies.size()) {
            System.out.println("Invalid movie selection!");
            return;
        }
        
        Movie selected = movies.get(movieNum);
        System.out.print("Enter number of tickets: ");
        int tickets = scanner.nextInt();
        
        if (tickets <= 0) {
            System.out.println("Invalid ticket count!");
        } else if (tickets > selected.availableSeats) {
            System.out.println("Not enough seats available!");
        } else {
            selected.availableSeats -= tickets;
            double total = tickets * selected.price;
            System.out.printf("Booked %d tickets for %s. Total: $%.2f\n", 
                tickets, selected.title, total);
        }
    }
}
