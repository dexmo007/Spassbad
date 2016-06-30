package de.ostfalia.test.ss16.blackbox;

import de.ostfalia.test.ss16.logic.Spassbad;
import de.ostfalia.test.ss16.logic.Tickets;
import de.ostfalia.test.ss16.logic.Ticketshop;
import de.ostfalia.test.ss16.logic.Warenkorb;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Robustheitstestklasse
 * R1: Testfall tritt auch in GUI auf
 * rest: Test auf Logik, da in GUI so nicht möglich
 *
 * @author David Winterland
 */
public class RobustheitsTest {

    private Ticketshop shop;
    private Spassbad bad;

    @Before
    public void setUp() {
        shop = new Ticketshop();
        bad = new Spassbad("Test");
    }

    @Test
    public void testR1() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.buy();
        assertTrue(shop.warenkorb.isEmpty());
    }

    @Test
    public void testR2() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb = new Warenkorb();
        assertTrue(shop.warenkorb.isEmpty());
    }

    @Test
    public void testR3() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        shop.warenkorb = new Warenkorb();
        assertTrue(shop.warenkorb.isEmpty());
    }

    @Test
    public void testR4() {
        assertTrue(shop.warenkorb.isEmpty());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, Integer.MAX_VALUE);
        assertFalse(shop.warenkorb.isEmpty());
        assertEquals(Integer.MAX_VALUE, shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE));
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);
        assertFalse(shop.warenkorb.isEmpty());
        assertEquals(Integer.MAX_VALUE, shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE));
        shop.warenkorb = new Warenkorb();
        assertTrue(shop.warenkorb.isEmpty());
    }
}
