package src;
import java.util.*;

class Player {
    String name;
    int rating;
    int points;

    public Player(String name, int rating) {
        this.name = name;
        this.rating = rating;
        this.points = 0;
    }
}

public class ChessTournament {
    static List<Player> players = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nChess Tournament Manager");
            System.out.println("1. Add Player");
            System.out.println("2. View Players");
            System.out.println("3. Record Match Result");
            System.out.println("4. View Standings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addPlayer();
                    break;
                case 2:
                    viewPlayers();
                    break;
                case 3:
                    recordMatch();
                    break;
                case 4:
                    viewStandings();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addPlayer() {
        System.out.print("Enter player name: ");
        String name = scanner.nextLine();
        System.out.print("Enter player rating: ");
        int rating = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        players.add(new Player(name, rating));
        System.out.println("Player added successfully!");
    }

    static void viewPlayers() {
        if (players.isEmpty()) {
            System.out.println("No players in the tournament yet!");
            return;
        }
        
        System.out.println("\nTournament Players:");
        System.out.println("------------------------------------------");
        System.out.printf("%-20s %-10s %-10s\n", "Name", "Rating", "Points");
        System.out.println("------------------------------------------");
        
        for (Player player : players) {
            System.out.printf("%-20s %-10d %-10d\n", 
                player.name, player.rating, player.points);
        }
        
        System.out.println("------------------------------------------");
    }

    static void recordMatch() {
        viewPlayers();
        if (players.size() < 2) {
            System.out.println("Need at least 2 players to record a match!");
            return;
        }
        
        System.out.print("Enter winner player number: ");
        int winnerNum = scanner.nextInt() - 1;
        System.out.print("Enter loser player number: ");
        int loserNum = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (winnerNum < 0 || winnerNum >= players.size() || 
            loserNum < 0 || loserNum >= players.size() || 
            winnerNum == loserNum) {
            System.out.println("Invalid player selection!");
            return;
        }
        
        Player winner = players.get(winnerNum);
        Player loser = players.get(loserNum);
        
        // Simple point system: 1 point for win, 0 for loss
        winner.points += 1;
        
        System.out.printf("Match recorded: %s defeated %s\n", 
            winner.name, loser.name);
    }

    static void viewStandings() {
        if (players.isEmpty()) {
            System.out.println("No players in the tournament yet!");
            return;
        }
        
        // Sort players by points (descending)
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort((p1, p2) -> Integer.compare(p2.points, p1.points));
        
        System.out.println("\nTournament Standings:");
        System.out.println("------------------------------------------");
        System.out.printf("%-5s %-20s %-10s %-10s\n", "Pos", "Name", "Rating", "Points");
        System.out.println("------------------------------------------");
        
        for (int i = 0; i < sorted.size(); i++) {
            Player player = sorted.get(i);
            System.out.printf("%-5d %-20s %-10d %-10d\n", 
                i+1, player.name, player.rating, player.points);
        }
        
        System.out.println("------------------------------------------");
    }
}