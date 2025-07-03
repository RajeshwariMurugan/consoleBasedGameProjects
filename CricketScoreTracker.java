package src;

import java.util.*;

class Team {
    String name;
    int score;
    int wickets;
    double overs;

    public Team(String name) {
        this.name = name;
        this.score = 0;
        this.wickets = 0;
        this.overs = 0.0;
    }
}

public class CricketScoreTracker {
    static Team team1, team2;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Cricket Score Tracker");
        
        System.out.print("Enter Team 1 name: ");
        team1 = new Team(scanner.nextLine());
        System.out.print("Enter Team 2 name: ");
        team2 = new Team(scanner.nextLine());
        
        System.out.println("\nMatch started: " + team1.name + " vs " + team2.name);
        
        // Team 1 batting
        System.out.println("\n" + team1.name + " is batting first");
        playInnings(team1);
        
        // Team 2 batting
        System.out.println("\n" + team2.name + " needs " + (team1.score + 1) + " runs to win");
        System.out.println(team2.name + " is now batting");
        playInnings(team2);
        
        // Match result
        System.out.println("\nMatch Result:");
        System.out.println(team1.name + ": " + team1.score + "/" + team1.wickets + " in " + team1.overs + " overs");
        System.out.println(team2.name + ": " + team2.score + "/" + team2.wickets + " in " + team2.overs + " overs");
        
        if (team1.score > team2.score) {
            System.out.println(team1.name + " wins by " + (team1.score - team2.score) + " runs");
        } else if (team2.score > team1.score) {
            System.out.println(team2.name + " wins by " + (10 - team2.wickets) + " wickets");
        } else {
            System.out.println("Match tied!");
        }
    }

    static void playInnings(Team battingTeam) {
        Team bowlingTeam = (battingTeam == team1) ? team2 : team1;
        int balls = 0;
        int maxOvers = 5; // Short match for demo
        
        while (battingTeam.wickets < 10 && battingTeam.overs < maxOvers) {
            System.out.printf("\n%s: %d/%d (%.1f overs)\n", 
                battingTeam.name, battingTeam.score, battingTeam.wickets, battingTeam.overs);
            System.out.print("Enter runs scored on this ball (0-6, W for wicket): ");
            String input = scanner.next().toUpperCase();
            
            if (input.equals("W")) {
                battingTeam.wickets++;
                System.out.println("Wicket!");
            } else {
                try {
                    int runs = Integer.parseInt(input);
                    if (runs >= 0 && runs <= 6) {
                        battingTeam.score += runs;
                        System.out.println(runs + " runs scored");
                    } else {
                        System.out.println("Invalid input! Counting as 0");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Counting as 0");
                }
            }
            
            balls++;
            battingTeam.overs = balls / 6 + (balls % 6) * 0.1;
            
            // Check if target reached (for 2nd innings)
            if (bowlingTeam.score > 0 && battingTeam.score > bowlingTeam.score) {
                break;
            }
        }
    }
}