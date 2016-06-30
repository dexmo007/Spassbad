package de.ostfalia.test.ss16.logic;

/**
 * Spassbad Klasse
 *
 * @author Henrik Drefs, David N. Winterland
 */
public class Spassbad {

    String name;

    Preiskonzept preiskonzept;

    Tickets soldTickets;

    /**
     * Konstruktor, nutzt DEFAULT Preiskonzept
     *
     * @param name Name des Bads
     */
    public Spassbad(String name) {
        preiskonzept = Preiskonzept.DEFAULT;
        soldTickets = new Tickets();
        this.name = name;
    }

    /**
     * Getter für Preiskonzept
     *
     * @return Preiskonzept
     */
    public Preiskonzept getPreiskonzept() {
        return preiskonzept;
    }

    /**
     * Setter für Preiskonzept
     *
     * @param preiskonzept neues Preiskonzept
     */
    public void setPreiskonzept(Preiskonzept preiskonzept) {
        this.preiskonzept = preiskonzept;
    }

    /**
     * getter für Name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter für name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter für sold tickets
     *
     * @return solt tickets
     */
    public Tickets getSoldTickets() {
        return soldTickets;
    }

    @Override
    public String toString() {
        return name;
    }
}
