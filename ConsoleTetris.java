package src;

import java.util.*;
import java.util.concurrent.*;

public class ConsoleTetris {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    private static char[][] grid = new char[HEIGHT][WIDTH];
    private static int[][] currentPiece;
    private static int currentX = WIDTH / 2 - 1;
    private static int currentY = 0;
    private static boolean gameOver = false;
    private static Scanner scanner = new Scanner(System.in);
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Tetris pieces (4x4 matrices)
    private static final int[][][] TETROMINOES = {
        { {0,1,0,0}, {0,1,0,0}, {0,1,0,0}, {0,1,0,0} }, // I
        { {0,1,0,0}, {0,1,0,0}, {1,1,0,0}, {0,0,0,0} },  // L
        { {0,1,0,0}, {0,1,0,0}, {0,1,1,0}, {0,0,0,0} },  // J
        { {1,1,0,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0} },  // O
        { {0,1,1,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0} },  // S
        { {1,1,0,0}, {0,1,1,0}, {0,0,0,0}, {0,0,0,0} },  // Z
        { {0,1,0,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0} }   // T
    };

    public static void main(String[] args) {
        initializeGrid();
        spawnNewPiece();

        // Game loop (auto-move down every 500ms)
        scheduler.scheduleAtFixedRate(() -> {
            if (!gameOver) {
                moveDown();
                printGrid();
                if (gameOver) {
                    System.out.println("GAME OVER!");
                    scheduler.shutdown();
                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);

        // Handle keyboard input
        while (!gameOver) {
            char input = scanner.next().toUpperCase().charAt(0);
            switch (input) {
                case 'A': moveLeft(); break;
                case 'D': moveRight(); break;
                case 'S': moveDown(); break;
                case 'W': rotate(); break;
                case 'Q': gameOver = true; break;
            }
        }
        scheduler.shutdown();
    }

    private static void initializeGrid() {
        for (int i = 0; i < HEIGHT; i++) {
            Arrays.fill(grid[i], '.');
        }
    }

    private static void spawnNewPiece() {
        Random rand = new Random();
        currentPiece = TETROMINOES[rand.nextInt(TETROMINOES.length)];
        currentX = WIDTH / 2 - 2;
        currentY = 0;

        if (checkCollision()) {
            gameOver = true;
        }
    }

    private static boolean checkCollision() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (currentPiece[y][x] != 0) {
                    int newX = currentX + x;
                    int newY = currentY + y;
                    if (newX < 0 || newX >= WIDTH || newY >= HEIGHT || (newY >= 0 && grid[newY][newX] != '.')) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void mergePiece() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (currentPiece[y][x] != 0 && currentY + y >= 0) {
                    grid[currentY + y][currentX + x] = '#';
                }
            }
        }
        clearLines();
        spawnNewPiece();
    }

    private static void clearLines() {
        for (int y = HEIGHT - 1; y >= 0; y--) {
            boolean fullLine = true;
            for (int x = 0; x < WIDTH; x++) {
                if (grid[y][x] == '.') {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                for (int ny = y; ny > 0; ny--) {
                    System.arraycopy(grid[ny - 1], 0, grid[ny], 0, WIDTH);
                }
                Arrays.fill(grid[0], '.');
                y++; // Recheck the same row after shifting
            }
        }
    }

    private static void moveLeft() {
        currentX--;
        if (checkCollision()) currentX++;
    }

    private static void moveRight() {
        currentX++;
        if (checkCollision()) currentX--;
    }

    private static void moveDown() {
        currentY++;
        if (checkCollision()) {
            currentY--;
            mergePiece();
        }
    }

    private static void rotate() {
        int[][] rotated = new int[4][4];
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                rotated[x][3 - y] = currentPiece[y][x];
            }
        }
        int[][] oldPiece = currentPiece;
        currentPiece = rotated;
        if (checkCollision()) {
            currentPiece = oldPiece;
        }
    }

    private static void printGrid() {
        System.out.print("\033[H\033[2J"); // Clear console
        System.out.flush();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                boolean isPiece = false;
                for (int py = 0; py < 4; py++) {
                    for (int px = 0; px < 4; px++) {
                        if (currentPiece[py][px] != 0 && currentY + py == y && currentX + px == x) {
                            isPiece = true;
                            break;
                        }
                    }
                    if (isPiece) break;
                }
                System.out.print(isPiece ? '#' : grid[y][x]);
            }
            System.out.println();
        }
        System.out.println("Controls: A (Left), D (Right), S (Down), W (Rotate), Q (Quit)");
    }
}