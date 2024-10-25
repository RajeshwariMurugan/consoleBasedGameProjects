package src;

import java.util.Scanner;

public class TicTacToeGame_04_10_2024 {

	private static char board[][] = new char[3][3];

	public static void main(String[] args) {
	
		
//_____________________________ Rule 1____________________________________________
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = '_';
			}
		}

//______________________Players name Like X & O _________________________________
		System.out.println("********************************");
		System.out.println("Welcome To Tic-Tac-Toe Game");
		System.out.println("********************************");

		displayBoard();
		

		Scanner sc = new Scanner(System.in);
		char player = 'X';
		int count = 1;
//________________________________input time_________________________________

		while (true) {
			if(player=='X')
			System.out.println("Player 1 (X), enter your move (row and column): ");
			else
			System.out.println("Player 2 (O), enter your move (row and column): ");
			
	
			int row = sc.nextInt();
			int col = sc.nextInt();
			
			// _______________board fill or not______________
			if (count == board.length * board[0].length) {
				System.err.println("Match is draw");
				break;
			} 
			// _______________input validation 	______________
			else if (inputValidate(row, col)) {
				board[row][col] = player;
				displayBoard();
				count++;
				//______ wining process validate_______________
				if (checkValidate(player)) {
					System.err.println("Player " + player + " has  WON");
					break;
				} else {
					player = (player == 'X') ? 'O' : 'X';
				}
			} 
			//__________________ input wrong______________________
			else {
				System.err.println("Enter a valid Row and Col");
			}

		}

	}

	private static void displayBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("======================");

	}

	private static boolean inputValidate(int row, int col) {
		return row >= 0 && col >= 0 && row < 3 && col < 3 && board[row][col] == '_';
	}

	private static boolean checkValidate(char player) {
		for (int i = 0; i < board.length; i++) {
			// checking row
			if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
				return true;
		}
		// checking column
		for (int i = 0; i < board.length; i++) {
			if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
				return true;
		}
		// checking Right diagnol
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
			return true;
		}
		// checking left diagnol
		if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
			return true;
		}
		return false;
	}
}
