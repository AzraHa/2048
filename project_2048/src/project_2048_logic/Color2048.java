package project_2048_logic;

import java.awt.Color;
import java.io.Serializable;

/**
 * Klasa Color2048 ima jednu metodu za dobijanje boja koje predstavljaju
 * različite vrijednosti plocica u igri 2048.
 * 
 * Metoda getColor uzima cjelobrojnu vrijednost kao ulaz i vraća
 * odgovarajuću boju na osnovu navedenih slucajeva. Ako unesena vrijednost
 * odgovara jednom od navedenih slučajeva, vraća se odgovarajuća boja; inače se
 * vraća bijela (Color(255, 255, 255)).
 * 
 * @author Azra Hadžihajdarević
 */
public class Color2048 implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Vraća boju na osnovu navedene vrijednosti za pločicu u igri 2048.
	 * 
	 * @param value
	 *            Vrijednost pločice u igri.
	 * @return Odgovarajuća boja za datu vrijednost pločice.
	 */
	public Color getColor(int value) {
		switch (value) {
			case 0 :
				return new Color(0, 0, 0); // Crna boja
			case 1 :
				return new Color(192,192,192); // Svijetlo siva boja
			case 2 :
				return new Color(238, 228, 218); // Svijetla boja za 2
			case 4 :
				return new Color(237, 224, 200); // Svijetla boja za 4
			case 8 :
				return new Color(242, 177, 121); // Narandžasta boja za 8
			case 16 :
				return new Color(245, 149, 99); // Narandžasta boja za 16
			case 32 :
				return new Color(246, 124, 95); // Narandžasta boja za 32
			case 64 :
				return new Color(246, 94, 59); // Narandžasta boja za 64
			case 128 :
				return new Color(237, 207, 114); // Žuta boja za 128
			case 256 :
				return new Color(237, 204, 97); // Žuta boja za 256
			case 512 :
				return new Color(237, 200, 80); // Žuta boja za 512
			case 1024 :
				return new Color(237, 197, 63); // Žuta boja za 1024
			case 2048 :
				return new Color(237, 194, 46); // Žuta boja za 2048
			default :
				return new Color(255, 255, 255); // Bijela boja za ostale
													// vrijednosti
		}
	}
}
