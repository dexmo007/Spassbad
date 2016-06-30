package de.ostfalia.test.ss16.logic;

/**
 * Klasse zur Repräsentation eines Kurses
 *
 * @author Henrik Drefs, David N. Winterland
 */
public class Kurs {

    private String name;
    private String termine;

    double preis;

    /**
     * Getter für Preis
     *
     * @return Preis
     */
    public double getPreis() {
        return preis;
    }

    /**
     * Setter für Preis
     *
     * @param preis Preis
     */
    public void setPreis(double preis) {
        this.preis = preis;
    }

    /**
     * getter kurs name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter kurs name
     *
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter termine string
     *
     * @return termin string
     */
    public String getTermine() {
        return termine;
    }

    /**
     * setter termine
     *
     * @param termine Termin string
     */
    public void setTermine(String termine) {
        this.termine = termine;
    }

    @Override
    public String toString() {
        return name + ": " + preis;
    }
}
