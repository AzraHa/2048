package project_2048_console;

import java.util.Scanner;
import project_2048_logic.Board;

/**
 * Glavna klasa za konzolnu verziju igre 2048.
 * 
 * @author Azra Hadzihajdarevic
 */
public class Main {

	public static void main(String[] args) {

		// Inicijalizacija igre sa pločom
		Board board = new Board();

		// Dodavanje dva nasumična bloka na početku igre
		board.addRandomTile();
		board.addRandomTile();

		// Inicijalizacija skenera za unos korisnika
		Scanner scanner = new Scanner(System.in);

		// Petlja za unos poteza dok igra nije završena
		while (!board.isGameOver()) {
			// Ispis trenutnog stanja ploče
			board.printBoard();
			System.out.print("Unesi potez: ");
			String move = scanner.next().toUpperCase(); // Unos poteza korisnika
														// (pretvara u velika
														// slova)

			// Obrada poteza korisnika
			if (move.equals("L")) {
				board.moveLeft();
			} else if (move.equals("R")) {
				board.moveRight();
			} else if (move.equals("U")) {
				board.moveUp();
			} else if (move.equals("D")) {
				board.moveDown();
			}

			// Ako se unese nepoznat potez, ništa se ne događa

			// Provjera završetka igre nakon svakog poteza

		}

		// Zatvaranje skenera nakon završetka igre
		scanner.close();
	}
}
