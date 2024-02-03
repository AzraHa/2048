package project_2048_logic;

import java.util.Random;
/**
 * 
 * Klasa Board predstavlja ploču za igru 2048. Implemetirane su metode za
 * pomicanje gore, dolje, lijevo i desno. Također implementirane metode za
 * informacijie o igri uključujući rezultat, najveci rezultat i je li igra
 * gotova ili nije. Koristit ćemo dvodimenzionalni niz za predstavljanje ploče
 * za igru. Svaki element niza će sadržavati vrijednost odgovarajuće pločice.
 * 
 * @author Azra Hadzihajdarevic
 */

public class Board {
	/**
	 * 2D polje koje predstavlja ploču igre
	 */
	public Tile[][] board;

	/**
	 * Konstanta koja predstavlja veličinu ploče igre (broj redova i kolona)
	 */
	public static final int SIZE = 4;

	/**
	 * Trenutni rezultat igre.
	 */
	public int score = 0;

	/**
	 * Najviši postignuti rezultat u igri
	 */
	public int highScore = 0;

	/**
	 * Indikator koji označava pobjedu u igri
	 */
	public boolean win;

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
	 * Dodaje novu nasumičnu pločicu na tablu. Vrijednost pločice se postavlja
	 * nasumično na 2 ili 4. Pločica se postavlja na nasumično odabrano prazno
	 * polje na tabli.
	 */
	public void addRandomTile() {
		// Kreira se objekat Random za generisanje nasumičnih brojeva
		Random rand = new Random();

		// Kreira se nova pločica (Tile)
		Tile tile = new Tile();

		// Postavlja se vrijednost pločice na 2 ili 4
		tile.setValue((rand.nextInt(2) + 1) * 2);

		// Broji se koliko praznih polja ima na tabli
		int emptyCellCount = countEmptyCells();

		// Ako postoji barem jedno prazno polje na tabli
		if (emptyCellCount > 0) {
			// Generiše se nasumičan indeks unutar raspona praznih polja
			int randomIndex = rand.nextInt(emptyCellCount);

			// Brojač za praćenje trenutnog praznog polja koje se razmatra
			int count = 0;

			// Iterira se kroz svako polje na tabli
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					// Provjerava se da li je trenutno polje prazno
					if (board[i][j].getValue() == 0) {
						// Ako je ovo prazno polje koje tražimo
						if (count == randomIndex) {
							// Postavlja se nova pločica na prazno polje
							board[i][j] = tile;
							return; // Izlazi iz metode nakon postavljanja
									// pločice
						}
						count++; // Povećava se brojač praznih polja
					}
				}
			}
		}
	}

	/**
	 * Metoda countEmptyCells vraća broj praznih pločica, tj. onih čija je
	 * vrijednost jednaka 0.
	 * 
	 * @return Broj praznih pločica na tabli.
	 */
	private int countEmptyCells() {
		int count = 0;

		// Iterira kroz svako polje na tabli
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				// Provjerava da li je vrijednost pločice na trenutnom polju
				// jednaka 0
				if (board[i][j].getValue() == 0) {
					count++; // Povećava brojač praznih polja
				}
			}
		}

		return count; // Vraća ukupan broj praznih polja na tabli
	}

	/**
	 * Metoda printBoard ispisuje ploču igre u konzolu.
	 */
	public void printBoard() {
		// Iterira kroz redove ploče
		for (int i = 0; i < SIZE; i++) {
			// Iterira kroz stupce ploče
			for (int j = 0; j < SIZE; j++) {
				// Ispisuje vrijednost pločice na trenutnom polju, zatim
				// tabulator
				System.out.print(board[i][j] + "\t");
			}
			// Prelazi u novi red nakon svakog reda ploče
			System.out.println();
		}
	}

	/**
	 * Potez prema gore.
	 */
	public void moveUp() {
		boolean moved = false;
		for (int j = 0; j < SIZE; j++) {
			int mergeValue = -1;
			for (int i = 1; i < SIZE; i++) {
				if (board[i][j].getValue() != 0) {
					int row = i;
					// Pomak prema gore sve dok ima praznih polja ili dok se
					// pločice mogu spajati
					while (row > 0 && (board[row - 1][j].getValue() == 0
							|| board[row - 1][j].getValue() == board[row][j]
									.getValue())) {
						// Ako se pločice mogu spojiti
						if (board[row - 1][j].getValue() == board[row][j]
								.getValue() && mergeValue != row - 1) {
							// Spajanje pločica
							board[row - 1][j]
									.setValue(board[row - 1][j].getValue() * 2);
							score += board[row - 1][j].getValue();
							board[row][j].setValue(0);
							mergeValue = row - 1;
							moved = true;
						} else if (board[row - 1][j].getValue() == 0) {
							// Pomak pločice prema gore
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

		// Ako je potez izvršen, dodaj novi broj i ažuriraj rezultat
		if (moved) {
			addNewNumber();
			updateScore();
		}
	}

	/**
	 * Potez prema dole.
	 */
	public void moveDown() {
		boolean moved = false;
		for (int j = 0; j < SIZE; j++) {
			int mergeValue = -1;
			for (int i = SIZE - 2; i >= 0; i--) {
				if (board[i][j].getValue() != 0) {
					int row = i;
					// Pomak prema dolje sve dok ima praznih polja ili dok se
					// pločice mogu spajati
					while (row < SIZE - 1 && (board[row + 1][j].getValue() == 0
							|| board[row + 1][j].getValue() == board[row][j]
									.getValue())) {
						// Ako se pločice mogu spojiti
						if (board[row + 1][j].getValue() == board[row][j]
								.getValue() && mergeValue != row + 1) {
							// Spajanje pločica
							board[row + 1][j]
									.setValue(board[row + 1][j].getValue() * 2);
							score += board[row + 1][j].getValue();
							board[row][j].setValue(0);
							mergeValue = row + 1;
							moved = true;
						} else if (board[row + 1][j].getValue() == 0) {
							// Pomak pločice prema dolje
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
		// Ako je potez izvršen, dodaj novi broj i ažuriraj rezultat
		if (moved) {
			addNewNumber();
			updateScore();
		}
	}

	/**
	 * Potez prema lijevo.
	 */
	public void moveLeft() {
		boolean moved = false;
		for (int i = 0; i < SIZE; i++) {
			int mergeValue = -1;
			for (int j = 1; j < SIZE; j++) {
				if (board[i][j].getValue() != 0) {
					int col = j;
					// Pomak prema lijevo sve dok ima praznih polja ili dok se
					// pločice mogu spajati
					while (col > 0 && (board[i][col - 1].getValue() == 0
							|| board[i][col - 1].getValue() == board[i][col]
									.getValue())) {
						// Ako se pločice mogu spojiti
						if (board[i][col - 1].getValue() == board[i][col]
								.getValue() && mergeValue != col - 1) {
							// Spajanje pločica
							board[i][col - 1]
									.setValue(board[i][col - 1].getValue() * 2);
							score += board[i][col - 1].getValue();
							board[i][col].setValue(0);
							mergeValue = col - 1;
							moved = true;
						} else if (board[i][col - 1].getValue() == 0) {
							// Pomak pločice ulijevo
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
		// Ako je potez izvršen, dodaj novi broj i ažuriraj rezultat
		if (moved) {
			addNewNumber();
			updateScore();
		}
	}

	/**
	 * Potez prema desno.
	 */
	public void moveRight() {
		boolean moved = false;
		for (int i = 0; i < SIZE; i++) {
			int mergeValue = -1;
			for (int j = SIZE - 2; j >= 0; j--) {
				if (board[i][j].getValue() != 0) {
					int col = j;
					// Pomak prema desno sve dok ima praznih polja ili dok se
					// pločice mogu spajati
					while (col < SIZE - 1 && (board[i][col + 1].getValue() == 0
							|| board[i][col + 1].getValue() == board[i][col]
									.getValue())) {
						// Ako se pločice mogu spojiti
						if (board[i][col + 1].getValue() == board[i][col]
								.getValue() && mergeValue != col + 1) {
							// Spajanje pločica
							board[i][col + 1]
									.setValue(board[i][col + 1].getValue() * 2);
							score += board[i][col + 1].getValue();
							board[i][col].setValue(0);
							mergeValue = col + 1;
							moved = true;
						} else if (board[i][col + 1].getValue() == 0) {
							// Pomak pločice udesno
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
		// Ako je potez izvršen, dodaj novi broj i ažuriraj rezultat
		if (moved) {
			addNewNumber();
			updateScore();
		}
	}

	/**
	 * Metoda dodaje novi broj na praznu plocicu
	 **/
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
		if (win) {
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

	/**
	 * Metoda koja ažurira najviši rezultat ukoliko trenutni rezultat premašuje
	 * trenutni najviši rezultat.
	 */
	public void updateScore() {
		// Provjeri je li trenutni rezultat veći od najvišeg rezultata
		if (score > highScore) {
			// Ako je trenutni rezultat veći, postavi ga kao najviši rezultat
			highScore = score;
		}
	}

}
