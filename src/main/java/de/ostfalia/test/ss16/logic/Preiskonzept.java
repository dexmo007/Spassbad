package de.ostfalia.test.ss16.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasse zur Preiskonzept-Verwaltung
 *
 * @author Henrik Drefs, David N. Winterland
 */
public class Preiskonzept {

    // attributes null -> logic not extisting

    Double preisEinzelkarte;

    Double preisEinzelkarteErmaessigt;

    Double massenRabatt;

    Double fruehbadetarif;

    Double saunaAufpreis;

    Map<Double, Double> kaufwertRabatte;

    List<Kurs> kurse;

    /**
     * Standard Konstruktor
     */
    public Preiskonzept() {
        kurse = new ArrayList<Kurs>();
    }

    /**
     * getter für alle preise als Array
     *
     * @return preise Array
     */
    public double[] getPreise() {
        return new double[]{preisEinzelkarte, preisEinzelkarteErmaessigt,
                fruehbadetarif * preisEinzelkarte, saunaAufpreis,
                fruehbadetarif * preisEinzelkarteErmaessigt};
    }

    /**
     * Getter Einzelkartenpreis
     *
     * @return preis
     */
    public double getPreisEinzelkarte() {
        return preisEinzelkarte;
    }

    /**
     * setter für Einzelkartenpreis
     *
     * @param preisEinzelkarte preis
     */
    public void setPreisEinzelkarte(double preisEinzelkarte) {
        this.preisEinzelkarte = preisEinzelkarte;
    }

    /**
     * Getter getPreisEinzelkarteErmaessigt
     *
     * @return preis
     */
    public double getPreisEinzelkarteErmaessigt() {
        return preisEinzelkarteErmaessigt;
    }

    /**
     * setter für preisEinzelkarteErmaessigt
     *
     * @param preisEinzelkarteErmaessigt preisEinzelkarteErmaessigt
     */
    public void setPreisEinzelkarteErmaessigt(double preisEinzelkarteErmaessigt) {
        this.preisEinzelkarteErmaessigt = preisEinzelkarteErmaessigt;
    }

    /**
     * Getter getMassenRabatt
     *
     * @return massenrabatt
     */
    public double getMassenRabatt() {
        return massenRabatt;
    }

    /**
     * setter für massenRabatt
     *
     * @param massenRabatt massenRabatt
     */
    public void setMassenRabatt(double massenRabatt) {
        this.massenRabatt = massenRabatt;
    }

    /**
     * Getter getFruehbadetarif
     *
     * @return fb tarif
     */
    public double getFruehbadetarif() {
        return fruehbadetarif;
    }

    /**
     * setter für fruehbadetarif
     *
     * @param fruehbadetarif fruehbadetarif
     */
    public void setFruehbadetarif(double fruehbadetarif) {
        this.fruehbadetarif = fruehbadetarif;
    }

    /**
     * Getter getKaufwertRabatte
     *
     * @return rabatte-Map
     */
    public Map<Double, Double> getKaufwertRabatte() {
        return kaufwertRabatte;
    }

    /**
     * setter für kaufwertRabatte
     *
     * @param kaufwertRabatte kaufwertRabatte
     */
    public void setKaufwertRabatte(Map<Double, Double> kaufwertRabatte) {
        this.kaufwertRabatte = kaufwertRabatte;
    }

    /**
     * Getter getKurse
     *
     * @return kurse
     */
    public List<Kurs> getKurse() {
        return kurse;
    }

    /**
     * fügt Kurs hinzu
     *
     * @param kurs preis
     */
    public void addKurse(Kurs kurs) {
        kurse.add(kurs);
    }

    /**
     * Getter getSaunaAufpreis
     *
     * @return sauna preis
     */
    public double getSaunaAufpreis() {
        return saunaAufpreis;
    }

    /**
     * setter für saunaAufpreis
     *
     * @param saunaAufpreis saunaAufpreis
     */
    public void setSaunaAufpreis(double saunaAufpreis) {
        this.saunaAufpreis = saunaAufpreis;
    }

    @Override
    public String toString() {
        return "Einzelkarten: " + preisEinzelkarte + '\n'
                + "Ermäßigt: " + preisEinzelkarteErmaessigt + '\n'
                + "Frühbadetarif: " + fruehbadetarif + '\n'
                + "Saunaaufpreis: " + saunaAufpreis + '\n'
                + "Rabatte: " + kaufwertRabatte + '\n'
                + "Kurse: " + kurse;
    }

    public static final Preiskonzept DEFAULT = new Preiskonzept();

    public static final double DEFAULT_MASSENRABATT = 0.98;
    public static final double DEFAULT_FRUEHBAD_RABATT = 0.8;
    public static final double DEFAULT_FIRST_RABATTSTUFE = 100.0;
    public static final double DEFAULT_FIRST_RABATT = 0.95;
    public static final double DEFAULT_SEC_RABATTSTUFE = 300.0;
    public static final double DEFAULT_SEC_RABATT = 0.9;

    static {
        DEFAULT.preisEinzelkarte = 5.0;
        DEFAULT.preisEinzelkarteErmaessigt = 3.0;

        DEFAULT.massenRabatt = DEFAULT_MASSENRABATT;

        DEFAULT.fruehbadetarif = DEFAULT_FRUEHBAD_RABATT;

        DEFAULT.kaufwertRabatte = new HashMap<Double, Double>();
        DEFAULT.kaufwertRabatte.put(DEFAULT_FIRST_RABATTSTUFE, DEFAULT_FIRST_RABATT);
        DEFAULT.kaufwertRabatte.put(DEFAULT_SEC_RABATTSTUFE, DEFAULT_SEC_RABATT);

        DEFAULT.kurse = new ArrayList<Kurs>();

        DEFAULT.saunaAufpreis = 5.0;
    }
}
