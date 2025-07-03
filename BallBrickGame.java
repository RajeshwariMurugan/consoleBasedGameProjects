package src;

import java.util.*;

public class BallBrickGame {
    static final int SIZE = 10;
    static char[][] board = new char[SIZE][SIZE];
    static int ballX = SIZE / 2;
    static int ballY = SIZE - 1;
    static int ballDirection = -1; // -1 for left, 1 for right
    static int lives = 3;
    static int bricksLeft = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeGame();
        
        while (lives > 0 && bricksLeft > 0) {
            printBoard();
            System.out.println("Lives: " + lives + " | Bricks left: " + bricksLeft);
            System.out.print("Enter direction (L/R) or Q to quit: ");
            String input = scanner.next().toUpperCase();
            
            if (input.equals("Q")) {
                break;
            } else if (input.equals("L")) {
                ballDirection = -1;
            } else if (input.equals("R")) {
                ballDirection = 1;
            } else {
                System.out.println("Invalid input! Use L or R");
                continue;
            }
            
            moveBall();
        }
        
        printBoard();
        if (bricksLeft == 0) {
            System.out.println("Congratulations! You won!");
        } else {
            System.out.println("Game Over!");
        }
    }

    static void initializeGame() {
        // Fill borders
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == 0 || j == 0 || j == SIZE - 1) {
                    board[i][j] = 'W'; // Wall
                } else if (i == SIZE - 1) {
                    board[i][j] = 'G'; // Ground
                } else {
                    board[i][j] = ' '; // Empty
                }
            }
        }
        
        // Place ball
        board[ballY][ballX] = 'O';
        
        // Place some random bricks
        Random rand = new Random();
        bricksLeft = 0;
        for (int i = 1; i < SIZE / 2; i++) {
            for (int j = 1; j < SIZE - 1; j++) {
                if (rand.nextDouble() < 0.3) { // 30% chance of brick
                    board[i][j] = 'B';
                    bricksLeft++;
                }
            }
        }
    }

    static void printBoard() {
        System.out.print("\033[H\033[2J"); // Clear console
        System.out.flush();
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void moveBall() {
        // Remove ball from current position
        board[ballY][ballX] = 'G';
        
        // Move ball up diagonally
        ballY--;
        ballX += ballDirection;
        
        // Check for collisions
        if (board[ballY][ballX] == 'W') {
            // Hit wall - reverse direction
            ballDirection *= -1;
            ballX += ballDirection * 2; // Move away from wall
        } else if (board[ballY][ballX] == 'B') {
            // Hit brick
            board[ballY][ballX] = ' ';
            bricksLeft--;
            ballDirection *= -1; // Reverse direction after hit
        }
        
        // Check if ball reached top
        if (ballY == 0) {
            lives--;
            if (lives > 0) {
                // Reset ball position
                ballY = SIZE - 1;
                ballX = SIZE / 2;
                ballDirection = -1;
            }
        } else {
            // Place ball in new position
            board[ballY][ballX] = 'O';
        }
    }
}