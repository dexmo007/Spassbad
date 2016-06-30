package de.ostfalia.test.ss16.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Klasse zur Verwaltung von Tickets für ein Spassbad
 *
 * @author Henrik Drefs, David N. Winterland
 */
public class Tickets {

    public static final int EINZELKARTE = 0;
    public static final int ERMAESSIGT = 1;
    public static final int FRUEHBAD = 2;
    public static final int FRUEHBAD_ERMAESSIGT = 3;
    public static final int SAUNA = 4;


    int[] tickets = {0, 0, 0, 0, 0};

    Map<Kurs, Integer> kurse;

    /**
     * Konstruktor
     */
    public Tickets() {
        kurse = new HashMap<Kurs, Integer>();
    }

    /**
     * Getter für Anzahl Tickets einer Ticketart
     *
     * @param art Ticketart
     * @return Anzahl
     */
    public int getAnzahl(int art) {
        return tickets[art];
    }

    /**
     * Getter für Gesamtzahl aller Tickets
     *
     * @return Gesamtanzahl
     */
    public int getGesamtAnzahl() {
        int sum = 0;
        for (int anz : tickets) {
            sum += anz;
        }
        return sum;
    }

    /**
     * Fügt Tickets einer Art hinzu
     *
     * @param art    Ticketart
     * @param anzahl Anzahl
     */
    public void addTicket(int art, int anzahl) {
        if (((long) tickets[art]) + anzahl > Integer.MAX_VALUE) {
            tickets[art] = Integer.MAX_VALUE;
            return;
        }
        tickets[art] += anzahl;
        if (tickets[art] < 0) {
            tickets[art] = 0;
        }
    }

    /**
     * Fügt Kurse hinzu
     *
     * @param kurs   Kurs
     * @param anzahl Anzahl
     */
    public void addKurs(Kurs kurs, int anzahl) {
        if (kurse.containsKey(kurs)) {
            kurse.put(kurs, kurse.get(kurs) + anzahl);
        } else {
            kurse.put(kurs, anzahl);
        }
    }

    /**
     * Löscht Tickets
     *
     * @param art    Art
     * @param anzahl Anzahl
     */
    public void removeTicket(int art, int anzahl) {
        // todo assert art is correct
        tickets[art] -= anzahl;

        if (tickets[art] < 0) {
            tickets[art] = 0;
        }
    }

    /**
     * Löscht Kurse
     *
     * @param kurs   Kurs
     * @param anzahl Anzahl
     */
    public void removeKurs(Kurs kurs, int anzahl) {
        if (kurse.containsKey(kurs)) {
            if (anzahl >= kurse.get(kurs)) {
                kurse.remove(kurs);
            } else {
                kurse.put(kurs, kurse.get(kurs) - anzahl);
                if (kurse.get(kurs) < 0) {
                    kurse.put(kurs, 0);
                }
            }
        }
    }

    /**
     * getter für Anzahl eines Kurses
     *
     * @param kurs Kurs
     * @return Anzahl
     */
    public int getKursAnzahl(Kurs kurs) {
        if (kurse.containsKey(kurs)) {
            return kurse.get(kurs);
        }
        return 0;
    }

    /**
     * getter für Anzahl eines Kurses auf Basis des Names
     *
     * @param name Kursname
     * @return Anzahl
     */
    public int getKursAnzahl(String name) {
        for (Map.Entry<Kurs, Integer> entry : kurse.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    /**
     * Tickets aus Object adden
     *
     * @param t tickets obj
     */
    public void addTickets(Tickets t) {
        this.addTicket(EINZELKARTE, t.getAnzahl(EINZELKARTE));
        this.addTicket(ERMAESSIGT, t.getAnzahl(ERMAESSIGT));
        this.addTicket(FRUEHBAD, t.getAnzahl(FRUEHBAD));
        this.addTicket(FRUEHBAD_ERMAESSIGT, t.getAnzahl(FRUEHBAD_ERMAESSIGT));
        this.addTicket(SAUNA, t.getAnzahl(SAUNA));

        for (Map.Entry<Kurs, Integer> entry : t.kurse.entrySet()) {
            this.addKurs(entry.getKey(), entry.getValue());
        }
    }
}
