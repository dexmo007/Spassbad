package de.ostfalia.test.ss16;

import de.ostfalia.test.ss16.logic.Spassbad;
import de.ostfalia.test.ss16.logic.Tickets;
import de.ostfalia.test.ss16.logic.Ticketshop;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for Ticketshop
 */
public class TicketshopTest {

    Ticketshop shop;
    private Spassbad bad1;
    private Spassbad bad2;

    @Before
    public void init() {
        shop = new Ticketshop();
        bad1 = new Spassbad("Testbad1");
        bad2 = new Spassbad("Testbad2");
    }

    @Test
    public void testAddRemoveBad() {
        shop.addBad(bad1);
        assertEquals(bad1, shop.getBad("Testbad1"));
        shop.addBad(bad2);
        assertEquals(bad2, shop.getBad("Testbad2"));
        assertTrue(shop.getSpassbaeder().contains(bad1));
        assertTrue(shop.getSpassbaeder().contains(bad2));
        shop.removeBad(bad1);
        assertNull(shop.getBad("Testbad1"));
        assertFalse(shop.getSpassbaeder().contains(bad1));
    }

    @Test
    public void testBuy() {
        shop.addBad(bad1);
        shop.addBad(bad2);
        shop.warenkorb.addTicket(bad1, Tickets.EINZELKARTE, 10);
        shop.warenkorb.addTicket(bad1, Tickets.SAUNA, 11);
        shop.warenkorb.addTicket(bad1, Tickets.FRUEHBAD, 12);
        shop.warenkorb.addTicket(bad1, Tickets.ERMAESSIGT, 13);
        shop.warenkorb.addTicket(bad1, Tickets.FRUEHBAD_ERMAESSIGT, 14);
        shop.warenkorb.addTicket(bad2, Tickets.EINZELKARTE, 15);
        shop.warenkorb.addTicket(bad2, Tickets.SAUNA, 16);
        shop.warenkorb.addTicket(bad2, Tickets.FRUEHBAD, 17);
        shop.warenkorb.addTicket(bad2, Tickets.ERMAESSIGT, 18);
        shop.warenkorb.addTicket(bad2, Tickets.FRUEHBAD_ERMAESSIGT, 19);
        shop.buy();
        Tickets soldTickets1 = bad1.getSoldTickets();
        soldTickets1.getAnzahl(Tickets.EINZELKARTE);
//        assertEquals();

    }

}
