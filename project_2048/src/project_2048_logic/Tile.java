package project_2048_logic;

/**
 * 
 * Predstavlja pojedinačnu pločicu u igri. Ima cjelobrojnu vrijednost i boju -
 * obje se mijenjaju kako se kombiniraju.
 * 
 * @author Azra HAdzihajdarevic
 */
public class Tile {
	private int value;

	/**
	 * Konstruktor za osnovnu plocicu s vrijednoscu 0.
	 */
	public Tile() {
		value = 0;
	}

	/**
	 * Konstruktor za pločicu s određenom vrijednosti.
	 * 
	 * @param number
	 *            - Vrijednost koju postavljamo.
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
	 * @param value-
	 *            Vrijednost koju postavi plocici.
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

}