package project_2048_logic;

import java.util.Random;
/**
 * 
 * Klasa Board predstavlja ploču za igru 2048. Implemetirane su metode za
 * pomicanje gore, dolje, lijevo i desno. Također implementirane metode za
 * informacijie o igri uključujući rezultat, najveću pločicu i je li igra gotova
 * ili nije. Koristit ćemo dvodimenzionalni niz za predstavljanje ploče za igru.
 * Svaki element niza će sadržavati vrijednost odgovarajuće pločice.
 * 
 * @author Azra Hadzihajdarevic
 */

public class Board {
	public Tile[][] board;
	private static final int SIZE = 4;
	public int score = 0;
	private boolean winConditionReached;

	/**
	 * Konstruktor Board postavlja plocu velicine 4x4
	 */
	public Board() {
		board = new Tile[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				board[i][j] = new Tile();
			}
		}
	}

	/**
	 * Metoda postavlja 'random' plocicu na tablu
	 */
	public void addRandomTile() {
		Random rand = new Random();

		Tile tile = new Tile();
		tile.setValue((rand.nextInt(2) + 1) * 2);

		int emptyCellCount = countEmptyCells();

		if (emptyCellCount > 0) {
			int randomIndex = rand.nextInt(emptyCellCount);
			int count = 0;

			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (board[i][j].getValue() == 0) {
						if (count == randomIndex) {
							board[i][j] = tile;
							return;
						}
						count++;
					}
				}
			}
		}
	}

	/*
	 * Metoda countEmptyCells vraca broj 'praznih' plocica, tj. one cija je
	 * vrijednost jednaka 0
	 * 
	 * @return count
	 */
	private int countEmptyCells() {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j].getValue() == 0) {
					count++;
				}
			}
		}
		return count;
	}

	/*
	 * Metoda printa plocu u konzolu
	 */
	public void printBoard() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/*
	 * Potez prema gore
	 */
	public void moveUp() {
		boolean moved = false;
		for (int j = 0; j < SIZE; j++) {
			int mergeValue = -1;
			for (int i = 1; i < SIZE; i++) {
				if (board[i][j].getValue() != 0) {
					int row = i;
					while (row > 0 && (board[row - 1][j].getValue() == 0
							|| board[row - 1][j].getValue() == board[row][j]
									.getValue())) {
						if (board[row - 1][j].getValue() == board[row][j]
								.getValue() && mergeValue != row - 1) {
							board[row - 1][j]
									.setValue(board[row - 1][j].getValue() * 2);
							score += board[row - 1][j].getValue();
							board[row][j].setValue(0);
							mergeValue = row - 1;
							moved = true;
						} else if (board[row - 1][j].getValue() == 0) {
							board[row - 1][j]
									.setValue(board[row][j].getValue());
							board[row][j].setValue(0);
							moved = true;
						}
						row--;
					}
				}
			}
		}
		if (moved) {
			addNewNumber();
			// updateScore();
			// updateGridLabels();
		}
	}

	/*
	 * Potez prema dole
	 */
	public void moveDown() {
		boolean moved = false;
		for (int j = 0; j < SIZE; j++) {
			int mergeValue = -1;
			for (int i = SIZE - 2; i >= 0; i--) {
				if (board[i][j].getValue() != 0) {
					int row = i;
					while (row < SIZE - 1 && (board[row + 1][j].getValue() == 0
							|| board[row + 1][j].getValue() == board[row][j]
									.getValue())) {
						if (board[row + 1][j].getValue() == board[row][j]
								.getValue() && mergeValue != row + 1) {
							board[row + 1][j]
									.setValue(board[row + 1][j].getValue() * 2);
							score += board[row + 1][j].getValue();
							board[row][j].setValue(0);
							mergeValue = row + 1;
							moved = true;
						} else if (board[row + 1][j].getValue() == 0) {
							board[row + 1][j]
									.setValue(board[row][j].getValue());
							board[row][j].setValue(0);
							moved = true;
						}
						row++;
					}
				}
			}
		}
		if (moved) {
			addNewNumber();
			// updateScore();
			// updateGridLabels();
		}
	}

	/*
	 * Potez prema lijevo
	 */
	public void moveLeft() {
		// int prevScore = score;
		boolean moved = false;
		for (int i = 0; i < SIZE; i++) {
			int mergeValue = -1;
			for (int j = 1; j < SIZE; j++) {
				if (board[i][j].getValue() != 0) {
					int col = j;
					while (col > 0 && (board[i][col - 1].getValue() == 0
							|| board[i][col - 1].getValue() == board[i][col]
									.getValue())) {
						if (board[i][col - 1].getValue() == board[i][col]
								.getValue() && mergeValue != col - 1) {
							board[i][col - 1]
									.setValue(board[i][col - 1].getValue() * 2);
							score += board[i][col - 1].getValue();
							board[i][col].setValue(0);
							mergeValue = col - 1;
							moved = true;
						} else if (board[i][col - 1].getValue() == 0) {
							board[i][col - 1]
									.setValue(board[i][col].getValue());
							board[i][col].setValue(0);
							moved = true;
						}
						col--;
					}
				}
			}
		}
		if (moved) {
			addNewNumber();
			// updateScore();
			// updateGridLabels();
		}
	}

	/*
	 * Potez prema desno
	 */
	public void moveRight() {
		// int prevScore = score;
		boolean moved = false;
		for (int i = 0; i < SIZE; i++) {
			int mergeValue = -1;
			for (int j = SIZE - 2; j >= 0; j--) {
				if (board[i][j].getValue() != 0) {
					int col = j;
					while (col < SIZE - 1 && (board[i][col + 1].getValue() == 0
							|| board[i][col + 1].getValue() == board[i][col]
									.getValue())) {
						if (board[i][col + 1].getValue() == board[i][col]
								.getValue() && mergeValue != col + 1) {
							board[i][col + 1]
									.setValue(board[i][col + 1].getValue() * 2);
							score += board[i][col + 1].getValue();
							board[i][col].setValue(0);
							mergeValue = col + 1;
							moved = true;
						} else if (board[i][col + 1].getValue() == 0) {
							board[i][col + 1]
									.setValue(board[i][col].getValue());
							board[i][col].setValue(0);
							moved = true;
						}
						col++;
					}
				}
			}
		}
		if (moved) {
			addNewNumber();
			// updateScore();
			// updateGridLabels();
		}
	}

	/*
	 * Metoda dodaje novi broj na praznu plocicu
	 */
	public void addNewNumber() {
		int row, col;
		Random random = new Random();

		row = random.nextInt(SIZE);
		col = random.nextInt(SIZE);

		while (board[row][col].getValue() != 0) {
			row = random.nextInt(SIZE);
			col = random.nextInt(SIZE);
		}

		int newValue = (random.nextInt(2) + 1) * 2;

		// Dodijeli novi broj (2 ili 4) praznoj pločici
		board[row][col].setValue(newValue);
	}

	/**
	 * Provjerava da li je igra završena. Igra završava kada je postignut uvjet
	 * za pobjedu (2048) ili kada nema više poteza.
	 * 
	 * @return true ako je igra završena, inače false.
	 */
	public boolean isGameOver() {
		// Provjera uslova za pobjedu
		if (winConditionReached) {
			return true; // Igra je završena s pobjedom
		}

		// Provjera praznih ćelija
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j].getValue() == 0) {
					return false; // Postoje prazne plocice, igra nije završena
				}
			}
		}

		// Provjera susjednih plocica s istom vrijednošću
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int currentValue = board[i][j].getValue();

				// Provjera susjednih plocica (gore, dolje, lijevo, desno)
				if ((i > 0 && currentValue == board[i - 1][j].getValue())
						|| (i < SIZE - 1
								&& currentValue == board[i + 1][j].getValue())
						|| (j > 0 && currentValue == board[i][j - 1].getValue())
						|| (j < SIZE - 1 && currentValue == board[i][j + 1]
								.getValue())) {
					return false; // Postoje susjedne plocice s istom
									// vrijednosti, igra nije završena
				}
			}
		}

		return true; // Igra je završena jer nema više mogućih poteza
	}

	/**
	 * Ponovno pokreće igru. Postavlja bodove na nulu, poništava uslov za
	 * pobjedu, inicijalizira novu ploču.
	 */
	public void newGame() {
		// Postavi bodove na nulu
		score = 0;

		// Poništi uslov za pobjedu
		winConditionReached = false;

		// Inicijaliziraj novu ploču
		initializeGrid();

		// Ako postoje metode za ažuriranje prikaza bodova i ploče,
		// pozovi ih
		// updateScore();
		// updateGridLabels();
	}

	/**
	 * Inicijalizira ploču na početku igre. Sve pločice se postavljaju na
	 * vrijednost 0. Dodaje se dva novi broj na nasumične pozicije.
	 */
	public void initializeGrid() {
		// Postavi sve pločice na vrijednost 0
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				board[i][j].setValue(0);
			}
		}

		// Dodaj dva nova broja na nasumične pozicije
		addNewNumber();
		addNewNumber();
	}
}
