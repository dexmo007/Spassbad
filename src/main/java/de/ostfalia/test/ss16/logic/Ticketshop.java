package de.ostfalia.test.ss16.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Klasse für Ticketshop, verwaltet Warenkorb und Bäder
 *
 * @author Henrik Drefs, David N. Winterland
 */
public class Ticketshop {

    List<Spassbad> spassbaeder;

    public Warenkorb warenkorb;

    /**
     * Konstruktur
     */
    public Ticketshop() {
        spassbaeder = new ArrayList<Spassbad>();
        warenkorb = new Warenkorb();
    }

    /**
     * Getter für Spassbad auf Basis des Namens
     *
     * @param name Name
     * @return Spassbad
     */
    public Spassbad getBad(String name) {
        for (Spassbad sb : spassbaeder) {
            if (sb.name.equals(name)) {
                return sb;
            }
        }
        // not found
        return null;
    }

    /**
     * Fügt Bad hinzu
     *
     * @param spassbad Bad
     */
    public void addBad(Spassbad spassbad) {
        spassbaeder.add(spassbad);
    }

    /**
     * Löscht Bad
     *
     * @param spassbad Bad
     */
    public void removeBad(Spassbad spassbad) {
        spassbaeder.remove(spassbad);
        warenkorb.removeBad(spassbad);
    }

    /**
     * leert Warenkorb
     */
    public void buy() {
        for (Map.Entry<Spassbad, Tickets> entry : warenkorb.totalTickets.entrySet()) {
            entry.getKey().getSoldTickets().addTickets(entry.getValue());
        }
        warenkorb = new Warenkorb();
    }

    /**
     * getter für spassbäder
     *
     * @return spassbäder
     */
    public List<Spassbad> getSpassbaeder() {
        return spassbaeder;
    }
}
