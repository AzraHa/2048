package project_2048_console;

import java.util.Scanner;
import project_2048_logic.Board;

public class Main {

	public static void main(String[] args) {

		Board board = new Board();

		board.addRandomTile();
		board.addRandomTile();

		Scanner scanner = new Scanner(System.in);

		while (!board.isGameOver()) {
			board.printBoard();
			System.out.print("Unesi potez: ");
			String move = scanner.next().toUpperCase();
			System.out.println(move);

			if (move.equals("L")) {
				board.moveLeft();
			}

			if (move.equals("R")) {
				board.moveRight();
			}

			if (move.equals("U")) {
				board.moveUp();
			}

			if (move.equals("D")) {
				board.moveDown();
			}
		}
		scanner.close();
	}
}