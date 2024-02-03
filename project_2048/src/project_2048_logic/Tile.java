package project_2048_logic;

import java.awt.Color;

/**
 * 
 * Predstavlja pojedinačnu pločicu u igri. Ima cjelobrojnu vrijednost i boju -
 * obje se mijenjaju kako se kombiniraju.
 * 
 * @author Azra HAdzihajdarevic
 */
public class Tile {
    private int value;
	private Color color;

	/**
	 * Konstruktor za osnovnu plocicu s vrijednoscu 0.
	 */
	public Tile() {
		value = 0;
	}

	/**
	 * Konstruktor za pločicu s određenom vrijednosti.
	 * 
	 * @param number - Vrijednost koju postavljamo.
	 */
	public Tile(int number) {
		value = number;
	}

	/**
	 * 
	 * Dohvata vrijednost plocice
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Postavlja vrijednost pločice - koristi se prilikom sabiranja dviju
	 * plocica.
	 * 
	 * @param value-  Vrijednost koju postavi plocici.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Predstavlja pločicu kao niz znakova (String) - koristi se u GUI
	 */
	public String toString() {
		return "" + value;
	}

	/**
	 * Postavlja boju pločice na osnovu njezine vrijednosti
	 */
	public void setColor() {
		if (value == 2) {
			color = new Color(238, 228, 218);
		} else if (value == 4) {
			color = new Color(237, 224, 200);
		} else if (value == 8) {
			color = new Color(242, 177, 121);
		} else if (value == 16) {
			color = new Color(245, 149, 99);
		} else if (value == 32) {
			color = new Color(246, 124, 95);
		} else if (value == 64) {
			color = new Color(246, 94, 59);
		} else if (value == 128) {
			color = new Color(237, 207, 114);
		} else if (value == 256) {
			color = new Color(237, 204, 97);
		} else if (value == 512) {
			color = new Color(237, 200, 80);
		} else if (value == 1024) {
			color = new Color(237, 197, 63);
		} else {
			color = new Color(237, 194, 46);
		}
	}

	/**
	 * Dohvata boju na osnovu postavljene boje iz metode setColor()
	 * 
	 * @return color
	 */
	public Color getColor() {
		this.setColor();
		return color;
	}

}