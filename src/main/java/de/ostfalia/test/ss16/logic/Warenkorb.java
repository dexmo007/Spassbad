package de.ostfalia.test.ss16.logic;

import java.util.*;

/**
 * Klasse für dem Warenkorb des Ticketshops
 *
 * @author Henrik Drefs, David N. Winterland
 */
public class Warenkorb {

    public static final int TEN = 10;
    public static final double CENTS_IN_EURO = 100.0;
    Map<Spassbad, Tickets> totalTickets = new HashMap<Spassbad, Tickets>();

    /**
     * Getter für Anzahl einer Ticketart eines Spassbads im Warenkorb
     *
     * @param sb  Spassbad
     * @param art Ticketart
     * @return Anzahl Tickets
     */
    public int getAnzahlFor(Spassbad sb, int art) {
        // todo null pointer
        if (totalTickets.containsKey(sb)) {
            return totalTickets.get(sb).getAnzahl(art);
        }
        return 0;
    }

    /**
     * ist korb leer?
     *
     * @return s.o.
     */
    public boolean isEmpty() {
        for (Tickets tickets : totalTickets.values()) {
            if (tickets.getGesamtAnzahl() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fügt dem Warenkorb ein Ticket hinzu
     *
     * @param bad    für Spassbad
     * @param art    Ticketart
     * @param anzahl Anzahl Tickets
     */
    public void addTicket(Spassbad bad, int art, int anzahl) {
        if (totalTickets.containsKey(bad)) {
            totalTickets.get(bad).addTicket(art, anzahl);
        } else {
            Tickets tickets = new Tickets();
            tickets.addTicket(art, anzahl);
            totalTickets.put(bad, tickets);
        }
    }

    /**
     * löscht ticket aus Warenkorb
     *
     * @param bad    für Bad
     * @param art    Ticketart
     * @param anzahl Anzahl
     */
    public void removeTicket(Spassbad bad, int art, int anzahl) {
        if (totalTickets.containsKey(bad)) {
            totalTickets.get(bad).removeTicket(art, anzahl);
        }
    }

    /**
     * löscht bad und alle tickets
     *
     * @param spassbad bad
     */
    public void removeBad(Spassbad spassbad) {
        totalTickets.remove(spassbad);
    }

    /**
     * fügt dem Warenkob Kurse hinzu
     *
     * @param bad    für Spassbad
     * @param kurs   Kurs
     * @param anzahl Anzahl
     */
    public void addKurs(Spassbad bad, Kurs kurs, int anzahl) {
        if (totalTickets.containsKey(bad)) {
            totalTickets.get(bad).addKurs(kurs, anzahl);
        } else {
            Tickets tickets = new Tickets();
            tickets.addKurs(kurs, anzahl);
            totalTickets.put(bad, tickets);
        }
    }

    /**
     * berechnet den Preis des gesamten Warenkorbs
     *
     * @return Gesamtpreis
     */
    public double calculatePreis() {
        double total = 0;
        for (Map.Entry<Spassbad, Tickets> entry : totalTickets.entrySet()) {
            total += calculatePreisForBad(entry.getKey(), entry.getValue());
        }
        return Math.round(total * CENTS_IN_EURO) / CENTS_IN_EURO;
    }

    /**
     * berechnet Preis für ein Spassbad
     *
     * @param spassbad Spassbad
     * @param tickets  Tickets object
     * @return Preis für Tickets des Spassbads
     */
    public double calculatePreisForBad(Spassbad spassbad, Tickets tickets) {
        Preiskonzept pk = spassbad.getPreiskonzept();
        double sum = 0.0;

        // assert einzelkarte teuerste
        int[] ez = calcZehner(tickets, Tickets.EINZELKARTE, 0);
        sum += pk.getMassenRabatt() * ez[1] * TEN * pk.getPreisEinzelkarte();
        int rest = ez[2];
        // 10er auffüllen mit sauna
        int[] sn = calcZehner(tickets, Tickets.SAUNA, rest);
        if (sn[0] > 0) {
            sum += pk.getMassenRabatt() * (rest * pk.getPreisEinzelkarte()
                    + sn[0] * pk.getSaunaAufpreis());
            rest = sn[2];
            ez[2] = 0;
        } else {
            rest += sn[2];
        }
        sum += pk.getMassenRabatt() * TEN * sn[1] * pk.getSaunaAufpreis();
        // 10er mit frühbadern
        int[] fb = calcZehner(tickets, Tickets.FRUEHBAD, rest);
        if (fb[0] > 0) {

            sum += pk.getMassenRabatt() *
                    (ez[2] * pk.getPreisEinzelkarte() + sn[2] * pk.getSaunaAufpreis()
                            + fb[0] * pk.getFruehbadetarif() * pk.getPreisEinzelkarte());

            rest = fb[2];
            ez[2] = 0;
            sn[2] = 0;
        } else {
            rest += fb[2];
        }
        sum += pk.getMassenRabatt() * TEN * fb[1] *
                pk.getFruehbadetarif() * pk.getPreisEinzelkarte();
        // 10er mit ermäßigt
        int[] em = calcZehner(tickets, Tickets.ERMAESSIGT, rest);
        if (em[0] > 0) {
            sum += pk.getMassenRabatt() *
                    (ez[2] * pk.getPreisEinzelkarte() + sn[2] * pk.getSaunaAufpreis()
                            + fb[2] * pk.getFruehbadetarif() * pk.getPreisEinzelkarte()
                            + em[0] * pk.getPreisEinzelkarteErmaessigt());

            rest = em[2];
            ez[2] = 0;
            sn[2] = 0;
            fb[2] = 0;
        } else {
            rest += em[2];
        }
        sum += pk.getMassenRabatt() * TEN * em[1] * pk.getPreisEinzelkarteErmaessigt();
        // 10er mit frühbad ermäßigt
        int[] fm = calcZehner(tickets, Tickets.FRUEHBAD_ERMAESSIGT, rest);
        if (fm[0] > 0) {

            sum += pk.getMassenRabatt() *
                    (ez[2] * pk.getPreisEinzelkarte() + sn[2] * pk.getSaunaAufpreis()
                            + fb[2] * pk.getFruehbadetarif() * pk.getPreisEinzelkarte()
                            + em[2] * pk.getPreisEinzelkarteErmaessigt()
                            + fm[0] * pk.getFruehbadetarif() * pk.getPreisEinzelkarteErmaessigt());

            rest = fm[2];
            ez[2] = 0;
            sn[2] = 0;
            fb[2] = 0;
            em[2] = 0;
        } else {
            rest += fm[2];
        }
        sum += pk.getMassenRabatt() * TEN * fm[1]
                * pk.getFruehbadetarif() * pk.getPreisEinzelkarteErmaessigt();
        // sum up all remainders
        sum += ez[2] * pk.getPreisEinzelkarte();
        sum += sn[2] * pk.getSaunaAufpreis();
        sum += fb[2] * pk.getFruehbadetarif() * pk.getPreisEinzelkarte();
        sum += em[2] * pk.getPreisEinzelkarteErmaessigt();
        sum += fm[2] * pk.getFruehbadetarif() * pk.getPreisEinzelkarteErmaessigt();
        sum += calcKursPreise(tickets);
        double kaufwertRabatt = calcKaufwertRabatt(pk, sum);
        if (pk.getKaufwertRabatte() == null) {
            return sum * kaufwertRabatt;
        }
        // check if next reaching of next rabattstufe is cheaper
        Collection<Double> rabatte = pk.getKaufwertRabatte().values();
        Double[] rabatteArray = rabatte.toArray(new Double[rabatte.size()]);
        Double[] stufen = pk.getKaufwertRabatte().keySet().toArray(new Double[rabatte.size()]);
        Arrays.sort(stufen);
        Arrays.sort(rabatteArray, Collections.<Double>reverseOrder());
        // check if highest rabattstufe already reached
        if (kaufwertRabatt != Collections.min(rabatte)) {
            // reach next rabattstufe - check
            double optSum = sum;
            double minPreis = pk.getMinPreis();
            int index = Arrays.binarySearch(rabatteArray, kaufwertRabatt);
            index = index < 0 ? 0 : index + 1;
            double nextRabattstufe = stufen[index];
            double diff = nextRabattstufe - sum;
            int num = (int) Math.ceil(diff / minPreis);
            optSum += num * minPreis;
            optSum *= calcKaufwertRabatt(pk, optSum);
            if (optSum < sum * kaufwertRabatt) {
                // really add tickets?
                return optSum;
            }
        }
        return sum * kaufwertRabatt;
    }

    /**
     * berechnet den Kaufpreisrabatt
     *
     * @param pk  Preiskonzept
     * @param sum Summe vor Rabatt
     * @return Rabattfaktor
     */
    public double calcKaufwertRabatt(Preiskonzept pk, double sum) {
        Map<Double, Double> kaufwertRabatte = pk.getKaufwertRabatte();
        if (kaufwertRabatte == null) {
            return 1.0;
        }
        Double[] levels = kaufwertRabatte.keySet().toArray(new Double[kaufwertRabatte.size()]);
        Arrays.sort(levels);
        for (int i = levels.length - 1; i >= 0; i--) {
            if (sum >= levels[i]) {
                return kaufwertRabatte.get(levels[i]);
            }
        }
        // kein rabatt
        return 1.0;
    }

    /**
     * berechnet die Parameter für Zehnertickets
     *
     * @param t    Tickets object
     * @param art  Ticketart
     * @param rest voriger Rest auf Zehnerkarte
     * @return int array mit {Auffüller der vorigen 10er, Anzahl Zehnerkarten, Restliche Tickets}
     */
    public int[] calcZehner(Tickets t, int art, int rest) {
        int[] res = new int[3];
        if (t.getAnzahl(art) >= TEN - rest) {
            res[0] = (TEN - rest) % TEN;
            res[1] = (t.getAnzahl(art) - res[0]) / TEN;
            res[2] = (t.getAnzahl(art) - res[0]) % TEN;
        } else {
            res[0] = 0;
            res[1] = 0;
            res[2] = t.getAnzahl(art);
        }
        return res;
    }

    /**
     * berechnet Gesamtpreis für die Kurse
     *
     * @param tickets Tickets object
     * @return Gesamtpreis für Kurse
     */
    public double calcKursPreise(Tickets tickets) {
        double sum = 0;
        for (Map.Entry<Kurs, Integer> entry : tickets.kurse.entrySet()) {
            sum += entry.getKey().getPreis() * entry.getValue();
        }
        return sum;
    }
}



























