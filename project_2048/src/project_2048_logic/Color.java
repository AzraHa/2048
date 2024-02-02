package project_2048_logic;

/**
 * Klasa Color predstavlja boju u RGB modelu boja s cjelobrojnim vrijednostima za crvenu,
 * zelenu i plavu komponentu.
 * 
 * @author Azra Hadzihajdarevic
 */
public class Color {
    private int red;
    private int green;
    private int blue;

    /**
     * Konstruktor boje s određenim vrijednostima crvene, zelene i plave komponente.
     * 
     * @param red   Crvena komponenta boje (0 do 255).
     * @param green Zelena komponenta boje (0 do 255).
     * @param blue  Plava komponenta boje (0 do 255).
     */

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Dohvata crvenu komponentu boje.
     * 
     * @return Crvena komponenta boje (0 do 255).
     */
    public int getRed() {
        return red;
    }

    /**
     * Dohvata zelenu komponentu boje.
     * 
     * @return Zelena komponenta boje (0 do 255).
     */
    public int getGreen() {
        return green;
    }

    /**
     * Dohvata plavu komponentu boje.
     * 
     * @return Plava komponenta boje (0 do 255).
     */
    public int getBlue() {
        return blue;
    }
}