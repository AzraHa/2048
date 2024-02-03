package project_2048_logic;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		System.out.print("Board\n");

		Board b = new Board();

		b.addRandomTile();
		b.addRandomTile();

		Scanner scanner = new Scanner(System.in);
		while (!b.isGameOver()) {
			b.printBoard();
			System.out.print("Enter a move: ");
			String move = scanner.next().toUpperCase();
			System.out.println(move);

			if (move.equals("L")) {
				b.moveLeft();
			}

			if (move.equals("R")) {
				b.moveRight();
			}

			if (move.equals("U")) {
				b.moveUp();
			}

			if (move.equals("D")) {
				b.moveDown();
			}

		}
		scanner.close();

	}

}
