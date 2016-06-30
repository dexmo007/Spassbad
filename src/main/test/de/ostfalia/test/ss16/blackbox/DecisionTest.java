package de.ostfalia.test.ss16.blackbox;

import de.ostfalia.test.ss16.logic.*;
import org.junit.Before;
import org.junit.Test;
import sun.security.x509.DeltaCRLIndicatorExtension;

import java.util.HashMap;

import static de.ostfalia.test.ss16.whitebox.WarenkorbTest.DELTA;
import static org.junit.Assert.*;

/**
 * Testfälle aus Entscheidungstabelle
 * ÄK Kommentare sind Referenzen auf BlackBox-Testspezifikation
 *
 * @author David Winterland
 */
public class DecisionTest {

    private Ticketshop shop;
    private Spassbad bad;

    @Before
    public void setUp() {
        shop = new Ticketshop();
        bad = new Spassbad("Test");
        shop.addBad(bad);
    }

    @Test
    public void test1() {
        // ÄK 0.1
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 0);
        assertTrue(shop.warenkorb.isEmpty());
        // ÄK 0.2
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
    }

    @Test
    public void test2() {
        //ÄK 1.1 Gültige
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        assertEquals(5.0, shop.warenkorb.calculatePreis(), DELTA);
        //ÄK 1.2 Ungültige
        shop.warenkorb = new Warenkorb();
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 0);
        assertTrue(shop.warenkorb.isEmpty());
        //ÄK1.3 Ungültige
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 10);
        assertNotEquals(50.0, shop.warenkorb.calculatePreis(), DELTA);
    }


    @Test
    public void test4() {
        Preiskonzept preiskonzept = new Preiskonzept();
        preiskonzept.setPreisEinzelkarte(8.0);
        HashMap<Double, Double> massenRabat = new HashMap<Double, Double>();
        massenRabat.put(70.0, 0.87);
        preiskonzept.setKaufwertRabatte(massenRabat);
        preiskonzept.setMassenRabatt(0.97);
        bad.setPreiskonzept(preiskonzept);

        //ÄK 6.1
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 8);
        assertEquals(((8.0 + 1.0) * 8) * 0.87, shop.warenkorb.calculatePreis(), DELTA);

        //ÄK 6.2
        shop.warenkorb = new Warenkorb();
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 7);
        assertNotEquals(((7.0 + 1.0) * 8) * 0.87, shop.warenkorb.calculatePreis(), DELTA);
    }

    @Test
    public void test5() {
        Preiskonzept preiskonzept = new Preiskonzept();
        preiskonzept.setPreisEinzelkarte(10);
        HashMap<Double, Double> massenRabat = new HashMap<Double, Double>();
        massenRabat.put(80.0, 0.94);
        preiskonzept.setKaufwertRabatte(massenRabat);
        preiskonzept.setMassenRabatt(0.97);
        bad.setPreiskonzept(preiskonzept);

        //ÄK 5.1
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 8);
        assertEquals(80.0 * 0.94, shop.warenkorb.calculatePreis(), DELTA);

        //ÄK 5.2
        shop.warenkorb = new Warenkorb();
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 7);
        assertNotEquals(70.0 * 0.94, shop.warenkorb.calculatePreis(), DELTA);

        //ÄK 5.3
        shop.warenkorb = new Warenkorb();
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 12);
        assertNotEquals(120.0 * 0.94, shop.warenkorb.calculatePreis(), DELTA);
    }

    @Test
    public void test6() {
        //ÄK 2.1
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 10);
        assertEquals((10.0 * 5.0) * 0.98, shop.warenkorb.calculatePreis(), DELTA);

        //ÄK 2.2
        shop.warenkorb = new Warenkorb();
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 9);
        assertNotEquals((9.0 * 5.0) * 0.98, shop.warenkorb.calculatePreis(), DELTA);
    }


    @Test
    public void test8() {
        Preiskonzept preiskonzept = new Preiskonzept();
        preiskonzept.setPreisEinzelkarte(5.0);
        HashMap<Double, Double> massenRabat = new HashMap<Double, Double>();
        massenRabat.put(95.0, 0.94);
        preiskonzept.setKaufwertRabatte(massenRabat);
        preiskonzept.setMassenRabatt(0.998);
        bad.setPreiskonzept(preiskonzept);

        //ÄK 4.1
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 19);
        double exact = (10.0 * 5 * 0.998 + 9.0 * 5 + 1.0 * 5) * 0.94;
        assertEquals(((double) Math.round(exact * 100)) / 100.0, shop.warenkorb.calculatePreis(), DELTA);

        //ÄK 4.2
        shop.warenkorb=new Warenkorb();
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 18);
        exact = (10.0 * 5 * 0.998 + 8.0 * 5 + 1.0 * 5) * 0.94;
        assertNotEquals(((double) Math.round(exact * 100)) / 100.0, shop.warenkorb.calculatePreis(), DELTA);
    }

    @Test
    public void test9() {
        Preiskonzept preiskonzept = new Preiskonzept();
        preiskonzept.setPreisEinzelkarte(5.0);
        HashMap<Double, Double> massenRabat = new HashMap<Double, Double>();
        massenRabat.put(100.0, 0.94);
        preiskonzept.setKaufwertRabatte(massenRabat);
        preiskonzept.setMassenRabatt(0.998);
        bad.setPreiskonzept(preiskonzept);

        //ÄK 3.1
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 25);
        double exact = (20.0 * 5 * 0.998 + 5.0 * 5) * 0.94;
        assertEquals(((double) Math.round(exact * 100)) / 100.0, shop.warenkorb.calculatePreis(), DELTA);

        //ÄK 3.2
        preiskonzept.setPreisEinzelkarte(3.0);
        shop.warenkorb=new Warenkorb();
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 30);
        exact = (30.0 * 3 * 0.998) * 0.94;
        assertNotEquals(((double) Math.round(exact * 100)) / 100.0, shop.warenkorb.calculatePreis(), DELTA);

    }

    @Test
    public void testGrenzwerte(){
        //ÄK 7.1
        shop.warenkorb.addTicket(bad,Tickets.EINZELKARTE,Integer.MAX_VALUE);
        int anzahl10er = Integer.MAX_VALUE/10;
        int rest = Integer.MAX_VALUE%10;
        assertEquals(((double)anzahl10er*10*5*0.98+(double)rest*5)*0.9, shop.warenkorb.calculatePreis(),DELTA);

        //ÄK 7.2
        shop.warenkorb.addTicket(bad,Tickets.EINZELKARTE,2);
        assertEquals(((double)anzahl10er*10*5*0.98+(double)rest*5)*0.9, shop.warenkorb.calculatePreis(),DELTA);
        //ÄK 7.3
        shop.warenkorb = new Warenkorb();
        shop.warenkorb.addTicket(bad,Tickets.EINZELKARTE,1);
        assertEquals(1.0*5, shop.warenkorb.calculatePreis(),DELTA);

        //ÄK 7.4
        shop.warenkorb = new Warenkorb();
        shop.warenkorb.addTicket(bad,Tickets.EINZELKARTE,0);
        assertEquals(0.0, shop.warenkorb.calculatePreis(),DELTA);
    }


}
