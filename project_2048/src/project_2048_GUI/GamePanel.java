package project_2048_GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import project_2048_logic.Board;
import project_2048_logic.Color2048;

/**
 * GamePanel klasa koja predstavlja GUI za igru 2048.
 * 
 * @author Azra Hadzihajdarevic
 */
public class GamePanel extends JPanel {
	/**
	 * Identifikacijski broj za verziju serijalizacije. Ovo se koristi kako bi
	 * se osiguralo da se serijalizirani podaci mogu deserializirati ispravno
	 * čak i kada su se unijele određene promjene u klasu.
	 */
	private static final long serialVersionUID = 1L;

	// Instanciranje objekata za logiku igre i boje

	/**
	 * Objekt koji predstavlja logiku igre 2048
	 */
	private Board newBoard;

	/**
	 * Objekt koji predstavlja boje u igri 2048
	 */
	private Color2048 color;

	// GUI komponente

	/**
	 * Dvodimenzionalno polje JLabel objekata koje predstavlja prikaz ploče igre
	 */
	private JLabel[][] gridLabels;

	/**
	 * Glavni prozor (JFrame) igre
	 */
	private JFrame frame;

	/**
	 * Panel (JPanel) koji sadrži prikaz ploče igre
	 */
	private JPanel gridPanel;

	/**
	 * JLabel komponenta koja prikazuje trenutni rezultat igre
	 */
	private JLabel scoreLabel;

	/**
	 * JLabel komponenta koja prikazuje najveci postignuti rezultat igre
	 */
	private JLabel highScoreLabel;

	/**
	 * SAVE_FILE_NAME predstavlja ime fajla u kojeg cuvamo podatke o igri .ser
	 * se koristi za datoteke koje su serializirane u Javi serializacijom stanje
	 * objekta pretvaramo u niz bajtova
	 */
	private static final String SAVE_FILE_NAME = "spremljeno/2048_save.ser";

	/**
	 * Konstruktor koji inicijalizira GUI komponente i postavlja osnovne
	 * postavke ploce
	 */
	public GamePanel() {
		// Inicijalizacija objekta za generiranje slučajnih brojeva
		new Random();

		// Inicijalizacija ploče i boje
		newBoard = new Board();
		color = new Color2048();

		// Inicijalizacija GUI komponenti
		frame = new JFrame("2048");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Pozivamo metodu saveGame kada se prozor zatvori
				saveGame();
				frame.dispose();
			}
		});

		frame.setSize(400, 450);
		frame.setLayout(new BorderLayout());

		gridPanel = new JPanel(new GridLayout(Board.SIZE, Board.SIZE));
		gridLabels = new JLabel[Board.SIZE][Board.SIZE];

		// Postavljanje ploče sa JLabel komponentama
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				gridLabels[i][j] = new JLabel("", JLabel.CENTER);
				gridLabels[i][j].setFont(new Font("Arial", Font.BOLD, 24));
				gridLabels[i][j].setOpaque(true);
				gridLabels[i][j].setBackground(color.getColor(0));
				gridPanel.add(gridLabels[i][j]);
			}
		}

		// Dodavanje komponenti na prozor
		frame.add(gridPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

		JPanel infoPanel = new JPanel(new GridLayout(2, 2));
		scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
		highScoreLabel = new JLabel("High Score: 0", JLabel.CENTER);
		infoPanel.add(scoreLabel);
		infoPanel.add(highScoreLabel);
		frame.add(infoPanel, BorderLayout.NORTH);

		// Postavljanje KeyListener-a za hvatanje događaja sa tastature
		frame.addKeyListener(new KeyAdapter() {
			// Obrada događaja sa tastature
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_UP) {
					newBoard.moveUp();
				} else if (keyCode == KeyEvent.VK_DOWN) {
					newBoard.moveDown();
				} else if (keyCode == KeyEvent.VK_LEFT) {
					newBoard.moveLeft();
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					newBoard.moveRight();
				}

				// Ažuriranje prikaza
				updateGridLabels();
				updateScore();

				// Provjera završetka igre
				if (newBoard.isGameOver()) {
					showGameOverMessage();
				}
			}
		});

		// Postavljanje svojstava prozora i inicijalizacija ploče
		frame.setFocusable(true);
		frame.requestFocus();
		frame.setVisible(true);

		newBoard.initializeGrid();
		updateGridLabels();

		// Pozivamo metodu loadGame da provjerimo da li postoji vec spasena
		// igirca
		loadGame();

	}

	/**
	 * Metoda updateGridLabels azurira stanje na ploci
	 */
	public void updateGridLabels() {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				if (newBoard.board[i][j].getValue() == 0) {
					gridLabels[i][j].setText("");
					gridLabels[i][j].setBackground(color.getColor(1));
				} else if (newBoard.board[i][j].getValue() == 2048) {
					// Postignut je cilj - 2048
					newBoard.win = true;
					gridLabels[i][j]
							.setText(String.valueOf(newBoard.board[i][j]));
					gridLabels[i][j].setBackground(
							color.getColor(newBoard.board[i][j].getValue()));
				} else {
					// Postavljanje teksta i boje za svaku pločicu
					gridLabels[i][j]
							.setText(String.valueOf(newBoard.board[i][j]));
					gridLabels[i][j].setBackground(
							color.getColor(newBoard.board[i][j].getValue()));
				}
				// Dodavanje linija između pločica
				gridLabels[i][j].setBorder(
						BorderFactory.createLineBorder(color.getColor(0)));
			}
		}
	}

	/**
	 * Metoda showGameOverMessage prikazuje poruku kada je igra završena.
	 */
	public void showGameOverMessage() {
		String message;
		if (newBoard.win) {
			message = "Congratulations! You reached the 2048 tile!\nDo you want to continue playing?";
		} else {
			message = "Game over! Do you want to play again?";
		}
		int choice = JOptionPane.showConfirmDialog(frame, message, "Game Over",
				JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			restartGame();
		} else {
			System.exit(0);
		}
	}

	/**
	 * Metoda restartGame ponovo pokreće igru
	 */
	public void restartGame() {
		// Postavljanje početnih vrijednosti za novu igru
		newBoard.score = 0;
		newBoard.win = false;
		updateScore();
		newBoard.initializeGrid();
		updateGridLabels();
	}

	/**
	 * Metoda updateScore azurira prikaz rezultata.
	 */
	public void updateScore() {
		// Ažuriranje rezultata
		scoreLabel.setText("Score: " + newBoard.score);

		// Postavljanje najboljeg rezultata ako je trenutni rezultat veći
		if (newBoard.score >= newBoard.highScore) {
			newBoard.highScore = newBoard.score;
			highScoreLabel.setText("High Score: " + newBoard.highScore);
		}
	}

	/**
	 * Metoda za spremanje stanja igre
	 */
	public void saveGame() {
		try {

			FileOutputStream fos = new FileOutputStream(SAVE_FILE_NAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(newBoard);
			oos.writeObject(color);

			oos.close();
			fos.close();

		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Metoda koja učitava podatke o igri iz datoteke.
	 */
	public void loadGame() {
		try {
			// Otvaranje ulaznog toka za čitanje podataka iz datoteke
			FileInputStream fis = new FileInputStream(SAVE_FILE_NAME);
			ObjectInputStream ois = new ObjectInputStream(fis);

			// Čitanje objekata Board i Color2048 iz datoteke
			newBoard = (Board) ois.readObject();
			color = (Color2048) ois.readObject();

			// Zatvaranje ulaznog toka
			ois.close();

			// Učitavanje stanja ploče i rezultata iz objekta Board
			newBoard.loadGrid(newBoard);
			newBoard.loadScore(newBoard.score, newBoard.highScore);

			// Ažuriranje prikaza rezultata
			newBoard.updateScore();
			scoreLabel.setText("Score: " + newBoard.score);
			highScoreLabel.setText("High Score: " + newBoard.highScore);

			// Ažuriranje prikaza ploče
			updateGridLabels();
		} catch (IOException i) {
			// Greška prilikom čitanja datoteke
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			// Greška prilikom pronalaska klase tokom deserializacije
			c.printStackTrace();
			return;
		}
	}

}
