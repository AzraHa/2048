package project_2048_GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// Instanciranje objekata za logiku igre i boje
	private Board newBoard;
	private Color2048 color;

	// GUI komponente
	private JLabel[][] gridLabels;
	private JFrame frame;
	private JPanel gridPanel;
	private JLabel scoreLabel;
	private JLabel highScoreLabel;

	/**
	 * Konstruktor koji inicijalizira GUI komponente i postavlja osnovne
	 * postavke prozora.
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
}
