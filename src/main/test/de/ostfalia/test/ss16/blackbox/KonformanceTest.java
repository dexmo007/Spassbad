package de.ostfalia.test.ss16.blackbox;

import de.ostfalia.test.ss16.logic.Spassbad;
import de.ostfalia.test.ss16.logic.Tickets;
import de.ostfalia.test.ss16.logic.Ticketshop;
import de.ostfalia.test.ss16.logic.Warenkorb;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Konformanztest-Klasse
 *
 * @author David Winterland
 */
public class KonformanceTest {

    Ticketshop shop;
    Spassbad bad;

    @Before
    public void init() {
        shop = new Ticketshop();
        bad = new Spassbad("Test");
        shop.addBad(bad);
    }

    @Test
    public void testK1() {
        assertTrue(shop.warenkorb.isEmpty());
    }

    @Test
    public void testK2() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.removeTicket(bad, Tickets.EINZELKARTE, 1);
        assertTrue(shop.warenkorb.isEmpty());
    }

    @Test
    public void testK3() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
    }

    @Test
    public void testK4() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        shop.warenkorb.removeTicket(bad, Tickets.EINZELKARTE, 1);
        assertTrue(shop.warenkorb.isEmpty());
    }

    @Test
    public void testK5() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 400);
        assertFalse(shop.warenkorb.isEmpty());
    }

    @Test
    public void testK6() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        shop.buy();
        assertTrue(shop.warenkorb.isEmpty());
    }

    @Test
    public void testK7() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, Integer.MAX_VALUE - 1);
        assertFalse(shop.warenkorb.isEmpty());
        assertEquals(Integer.MAX_VALUE, shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE));
    }

    @Test
    public void testK8() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, Integer.MAX_VALUE - 1);
        assertFalse(shop.warenkorb.isEmpty());
        assertEquals(Integer.MAX_VALUE, shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE));
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertEquals(Integer.MAX_VALUE, shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE));
    }

    @Test
    public void testK9() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, Integer.MAX_VALUE - 1);
        assertFalse(shop.warenkorb.isEmpty());
        assertEquals(Integer.MAX_VALUE, shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE));
        shop.warenkorb.removeTicket(bad, Tickets.EINZELKARTE, 1);
        assertNotEquals(Integer.MAX_VALUE, shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE));
        assertFalse(shop.warenkorb.isEmpty());
    }

    @Test
    public void testK10() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, Integer.MAX_VALUE - 10);
        assertFalse(shop.warenkorb.isEmpty());
        assertEquals(Integer.MAX_VALUE - 9, shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE));
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 20);
        assertEquals(Integer.MAX_VALUE, shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE));
    }
}
