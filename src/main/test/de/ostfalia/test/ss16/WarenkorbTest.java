package de.ostfalia.test.ss16;

import de.ostfalia.test.ss16.logic.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Test class for Warenkorb
 *
 * @author Henrik Drefs
 */
public class WarenkorbTest {

    Warenkorb cart = new Warenkorb();

    public static final double DELTA = 0.0000001;

    @Test
    public void testCalcKaufwertRabatt() {
        Preiskonzept pk = new Preiskonzept();
        Map<Double, Double> rabatte = new HashMap<Double, Double>();
        // test no map set
        assertEquals(1.0, cart.calcKaufwertRabatt(pk, 0), DELTA);
        assertEquals(1.0, cart.calcKaufwertRabatt(pk, 10), DELTA);
        assertEquals(1.0, cart.calcKaufwertRabatt(pk, 30), DELTA);
        // test no rabatte
        pk.setKaufwertRabatte(rabatte);
        assertEquals(1.0, cart.calcKaufwertRabatt(pk, 0), DELTA);
        assertEquals(1.0, cart.calcKaufwertRabatt(pk, 10), DELTA);
        assertEquals(1.0, cart.calcKaufwertRabatt(pk, 30), DELTA);
        // test 1 stufe
        rabatte.put(10.0, 0.5);
        assertEquals(1.0, cart.calcKaufwertRabatt(pk, 9), DELTA);
        assertEquals(0.5, cart.calcKaufwertRabatt(pk, 10), DELTA);
        assertEquals(0.5, cart.calcKaufwertRabatt(pk, 11), DELTA);
        // test 2 stufen
        rabatte.put(30.0, 0.3);
        assertEquals(1.0, cart.calcKaufwertRabatt(pk, 9), DELTA);
        assertEquals(0.5, cart.calcKaufwertRabatt(pk, 10), DELTA);
        assertEquals(0.5, cart.calcKaufwertRabatt(pk, 11), DELTA);
        assertEquals(0.5, cart.calcKaufwertRabatt(pk, 29), DELTA);
        assertEquals(0.3, cart.calcKaufwertRabatt(pk, 30), DELTA);
        assertEquals(0.3, cart.calcKaufwertRabatt(pk, 31), DELTA);
    }

    @Test
    public void testCalcKursPreise() {
        Tickets tickets = new Tickets();
        Kurs kurs1 = new Kurs();
        kurs1.setPreis(10);
        Kurs kurs2 = new Kurs();
        kurs2.setPreis(20);
        assertEquals(0.0, cart.calcKursPreise(tickets), DELTA);
        tickets.addKurs(kurs1, 1);
        assertEquals(10.0, cart.calcKursPreise(tickets), DELTA);
        tickets.addKurs(kurs1, 2);
        assertEquals(30.0, cart.calcKursPreise(tickets), DELTA);
        tickets.removeKurs(kurs1, 1);
        assertEquals(20.0, cart.calcKursPreise(tickets), DELTA);
        tickets.addKurs(kurs2, 1);
        assertEquals(40.0, cart.calcKursPreise(tickets), DELTA);
        tickets.addKurs(kurs2, 2);
        assertEquals(80.0, cart.calcKursPreise(tickets), DELTA);
    }

    @Test
    public void testCalcZehnerRest0() {
        Tickets tickets = new Tickets();
        int art = Tickets.EINZELKARTE;
        // no tickets
        int[] act = cart.calcZehner(tickets, art, 0);
        int[] exp = {0, 0, 0};
        assertArrayEquals(exp, act);
        // 1 ticket
        tickets.addTicket(art, 1);
        exp = new int[]{0, 0, 1};
        act = cart.calcZehner(tickets, art, 0);
        assertArrayEquals(exp, act);
        // 9 tickets
        tickets.addTicket(art, 8);
        exp = new int[]{0, 0, 9};
        act = cart.calcZehner(tickets, art, 0);
        assertArrayEquals(exp, act);
        // 10 tickets
        tickets.addTicket(art, 1);
        exp = new int[]{0, 1, 0};
        act = cart.calcZehner(tickets, art, 0);
        assertArrayEquals(exp, act);
        // 11 tickets
        tickets.addTicket(art, 1);
        exp = new int[]{0, 1, 1};
        act = cart.calcZehner(tickets, art, 0);
        assertArrayEquals(exp, act);
        // 25 tickets
        tickets.addTicket(art, 14);
        exp = new int[]{0, 2, 5};
        act = cart.calcZehner(tickets, art, 0);
        assertArrayEquals(exp, act);
    }

    @Test
    public void testCalcZehnerMitRest() {
        Tickets t = new Tickets();
        int art = Tickets.EINZELKARTE;
        int rest = 5;
        // no tickets
        int[] exp = {0, 0, 0};
        int[] act = cart.calcZehner(t, art, rest);
        assertArrayEquals(exp, act);
        // 4 tickets
        t.addTicket(art, 4);
        exp = new int[]{0, 0, 4};
        act = cart.calcZehner(t, art, rest);
        assertArrayEquals(exp, act);
        // 5 tickets
        t.addTicket(art, 1);
        exp = new int[]{5, 0, 0};
        act = cart.calcZehner(t, art, rest);
        assertArrayEquals(exp, act);
        // 6 tickets
        t.addTicket(art, 1);
        exp = new int[]{5, 0, 1};
        act = cart.calcZehner(t, art, rest);
        assertArrayEquals(exp, act);
        // 14 tickets
        t.addTicket(art, 8);
        exp = new int[]{5, 0, 9};
        act = cart.calcZehner(t, art, rest);
        assertArrayEquals(exp, act);
        // 15 tickets
        t.addTicket(art, 1);
        exp = new int[]{5, 1, 0};
        act = cart.calcZehner(t, art, rest);
        assertArrayEquals(exp, act);
        // 16 tickets
        t.addTicket(art, 1);
        exp = new int[]{5, 1, 1};
        act = cart.calcZehner(t, art, rest);
        assertArrayEquals(exp, act);
    }

    @Test
    public void testCalcPreisForBadSingles() {
        Preiskonzept pk = new Preiskonzept();
        pk.setMassenRabatt(0.95);
        pk.setPreisEinzelkarte(10.0);
        pk.setSaunaAufpreis(9.0);
        pk.setFruehbadetarif(0.8);
        pk.setPreisEinzelkarteErmaessigt(7.0);
        Spassbad sb = new Spassbad("Testbad");
        sb.setPreiskonzept(pk);
        Tickets t = new Tickets();
        // einzel
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals(10.0, cart.calculatePreisForBad(sb, t), DELTA);
        // sauna
        t = new Tickets();
        t.addTicket(Tickets.SAUNA, 1);
        assertEquals(9.0, cart.calculatePreisForBad(sb, t), DELTA);
        // fruehbade
        t = new Tickets();
        t.addTicket(Tickets.FRUEHBAD, 1);
        assertEquals(0.8 * 10.0, cart.calculatePreisForBad(sb, t), DELTA);
        // ermaessigt
        t = new Tickets();
        t.addTicket(Tickets.ERMAESSIGT, 1);
        assertEquals(7.0, cart.calculatePreisForBad(sb, t), DELTA);
        // fruehbade ermaessigt
        t = new Tickets();
        t.addTicket(Tickets.FRUEHBAD_ERMAESSIGT, 1);
        assertEquals(0.8 * 7, cart.calculatePreisForBad(sb, t), DELTA);
    }

    @Test
    public void testCalcPreisForBadAlleZehner() {
        Preiskonzept pk = new Preiskonzept();
        pk.setMassenRabatt(0.95);
        pk.setPreisEinzelkarte(10.0);
        pk.setSaunaAufpreis(9.0);
        pk.setFruehbadetarif(0.8);
        pk.setPreisEinzelkarteErmaessigt(7.0);
        Spassbad sb = new Spassbad("Testbad");
        sb.setPreiskonzept(pk);
        Tickets t = new Tickets();
        // 10 einzel
        t.addTicket(Tickets.EINZELKARTE, 10);
        assertEquals(10.0 *10.0 * 0.95, cart.calculatePreisForBad(sb, t), DELTA);
        // 10 sauna
        t = new Tickets();
        t.addTicket(Tickets.SAUNA, 10);
        assertEquals(10.0 * 9.0 * 0.95, cart.calculatePreisForBad(sb, t), DELTA);
        // 10 fruehbade
        t = new Tickets();
        t.addTicket(Tickets.FRUEHBAD, 10);
        assertEquals(10.0 * (0.8 * 10.0) * 0.95, cart.calculatePreisForBad(sb, t), DELTA);
        // 10 ermaessigt
        t = new Tickets();
        t.addTicket(Tickets.ERMAESSIGT, 10);
        assertEquals(10.0 * 7.0 * 0.95, cart.calculatePreisForBad(sb, t), DELTA);
        // 10 fruehbade ermaessigt
        t = new Tickets();
        t.addTicket(Tickets.FRUEHBAD_ERMAESSIGT, 10);
        assertEquals(10.0 * (0.8 * 7) * 0.95, cart.calculatePreisForBad(sb, t), DELTA);
    }

    @Test
    public void testcalcKursPreise() {
        Warenkorb cart = new Warenkorb();
        Tickets t = new Tickets();

    }

    @Test
    public void testCalculatePreisForBad() {
        Preiskonzept pk = new Preiskonzept();
        pk.setMassenRabatt(0.95);
        pk.setPreisEinzelkarte(10.0);
        pk.setSaunaAufpreis(9.0);
        pk.setFruehbadetarif(0.8);
        pk.setPreisEinzelkarteErmaessigt(7.0);
        Spassbad sb = new Spassbad("Testbad");
        sb.setPreiskonzept(pk);
        Tickets t = new Tickets();
        // no tickets
        assertEquals(0.0, cart.calculatePreisForBad(sb, t), DELTA);
        // 1 Einzelkarte
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals(10.0, cart.calculatePreisForBad(sb, t), DELTA);
        // 9 Einzelkarten
        t.addTicket(Tickets.EINZELKARTE, 8);
        assertEquals(90.0, cart.calculatePreisForBad(sb, t), DELTA);
        // 11 Einzelkarten
        t.addTicket(Tickets.EINZELKARTE, 2);
        assertEquals(105.0, cart.calculatePreisForBad(sb, t), DELTA);
        // clear + 1 of each
        t = new Tickets();
        t.addTicket(Tickets.EINZELKARTE, 1);
        t.addTicket(Tickets.SAUNA, 1);
        t.addTicket(Tickets.FRUEHBAD, 1);
        t.addTicket(Tickets.ERMAESSIGT, 1);
        t.addTicket(Tickets.FRUEHBAD_ERMAESSIGT, 1);
        assertEquals(10.0 + 9 + 0.8 * 10 + 7 + 0.8 * 7, cart.calculatePreisForBad(sb, t), DELTA);
        // 2 of each
        t.addTicket(Tickets.EINZELKARTE, 1);
        t.addTicket(Tickets.SAUNA, 1);
        t.addTicket(Tickets.FRUEHBAD, 1);
        t.addTicket(Tickets.ERMAESSIGT, 1);
        t.addTicket(Tickets.FRUEHBAD_ERMAESSIGT, 1);
        assertEquals(2.0 * (10.0 + 9 + 0.8 * 10 + 7 + 0.8 * 7) * 0.95, cart.calculatePreisForBad(sb, t), DELTA);
        // +1 einzelkarte
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals((10.0 * 3 + 9 * 2 + 0.8 * 2 * 10 + 7 * 2 + 0.8 * 7) * 0.95 + 0.8 * 7, cart.calculatePreisForBad(sb, t), DELTA);
    }

    /**
     * Testfall für Berechnung mit Rabatten und Optimierung
     * Nur die Summe relevant, deswegen einfach nur Einzelkarten verwendet
     */
    @Test
    public void testCalcPreisForBadMitRabatt() {
        Preiskonzept pk = new Preiskonzept();
        pk.setMassenRabatt(0.95);
        pk.setPreisEinzelkarte(10.0);
        pk.setSaunaAufpreis(9.0);
        pk.setFruehbadetarif(0.8);
        pk.setPreisEinzelkarteErmaessigt(7.0);
        Map<Double, Double> rabatte = new HashMap<Double, Double>();
        rabatte.put(100.0, 0.9);
        rabatte.put(200.0, 0.8);
        pk.setKaufwertRabatte(rabatte);
        Spassbad sb = new Spassbad("Testbad");
        sb.setPreiskonzept(pk);
        Tickets t = new Tickets();
        // no tickets
        assertEquals(0.0, cart.calculatePreisForBad(sb, t), DELTA);
        // 9 tickets - optimizing does not trigger just jet
        t.addTicket(Tickets.EINZELKARTE, 9);
        assertEquals(9.0 * 10.0, cart.calculatePreisForBad(sb, t), DELTA);
        // 10 tickets - zehner + optimizing added 1 fb-erm to get 10% off
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals((95.0 + 5.6) * 0.9, cart.calculatePreisForBad(sb,t), DELTA);
        // 11 tickets - regular reach of 10%
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals((100.0 * 0.95 + 10.0) * 0.9, cart.calculatePreisForBad(sb, t), DELTA);
        // 16 tickets
        t.addTicket(Tickets.EINZELKARTE, 5);
        assertEquals((95.0 + 60.0) * 0.9, cart.calculatePreisForBad(sb, t), DELTA);
        // 17 tickets
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals((95.0 + 70.0) * 0.9, cart.calculatePreisForBad(sb, t), DELTA);
        // 18 tickets
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals((95.0 + 80.0) * 0.9, cart.calculatePreisForBad(sb, t), DELTA);
        // 19 tickets - cheaper with 3 more fb-erm tickets
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals((100.0 * 0.95 + 9.0 * 10 + 3.0 * 5.6) * 0.8, cart.calculatePreisForBad(sb, t), DELTA);
        // 20 tickets - 2 zehner + 20% rabatt durch optimierung
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals((95.0 * 2.0 + 2.0 * 0.8 * 7) * 0.8, cart.calculatePreisForBad(sb, t), DELTA);
        // 21 tickets - regular 20% (exactly at limit)
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals((95.0 * 2.0 + 10.0) * 0.8, cart.calculatePreisForBad(sb, t), DELTA);
        // 22 tickets - regular 20% over max limit
        t.addTicket(Tickets.EINZELKARTE, 1);
        assertEquals((95.0 * 2 + 20.0) * 0.8, cart.calculatePreisForBad(sb, t), DELTA);
    }
}
