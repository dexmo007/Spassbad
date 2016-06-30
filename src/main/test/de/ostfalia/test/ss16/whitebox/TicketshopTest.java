package de.ostfalia.test.ss16.whitebox;

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
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad1, Tickets.EINZELKARTE));
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad1, Tickets.SAUNA));
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad1, Tickets.FRUEHBAD));
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad1, Tickets.ERMAESSIGT));
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad1, Tickets.FRUEHBAD_ERMAESSIGT));
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad2, Tickets.EINZELKARTE));
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad2, Tickets.SAUNA));
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad2, Tickets.FRUEHBAD));
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad2, Tickets.ERMAESSIGT));
        assertEquals(0, shop.warenkorb.getAnzahlFor(bad2, Tickets.FRUEHBAD_ERMAESSIGT));
        Tickets soldTickets1 = bad1.getSoldTickets();
        assertEquals(10, soldTickets1.getAnzahl(Tickets.EINZELKARTE));
        assertEquals(11, soldTickets1.getAnzahl(Tickets.SAUNA));
        assertEquals(12, soldTickets1.getAnzahl(Tickets.FRUEHBAD));
        assertEquals(13, soldTickets1.getAnzahl(Tickets.ERMAESSIGT));
        assertEquals(14, soldTickets1.getAnzahl(Tickets.FRUEHBAD_ERMAESSIGT));
        Tickets soldTickets2 = bad2.getSoldTickets();
        assertEquals(15, soldTickets2.getAnzahl(Tickets.EINZELKARTE));
        assertEquals(16, soldTickets2.getAnzahl(Tickets.SAUNA));
        assertEquals(17, soldTickets2.getAnzahl(Tickets.FRUEHBAD));
        assertEquals(18, soldTickets2.getAnzahl(Tickets.ERMAESSIGT));
        assertEquals(19, soldTickets2.getAnzahl(Tickets.FRUEHBAD_ERMAESSIGT));
        // buy new tickets for bad1
        shop.warenkorb.addTicket(bad1, Tickets.EINZELKARTE, 15);
        shop.warenkorb.addTicket(bad1, Tickets.SAUNA, 16);
        shop.warenkorb.addTicket(bad1, Tickets.FRUEHBAD, 17);
        shop.warenkorb.addTicket(bad1, Tickets.ERMAESSIGT, 18);
        shop.warenkorb.addTicket(bad1, Tickets.FRUEHBAD_ERMAESSIGT, 19);
        shop.buy();
        soldTickets1 = bad1.getSoldTickets();
        assertEquals(10 + 15, soldTickets1.getAnzahl(Tickets.EINZELKARTE));
        assertEquals(11 + 16, soldTickets1.getAnzahl(Tickets.SAUNA));
        assertEquals(12 + 17, soldTickets1.getAnzahl(Tickets.FRUEHBAD));
        assertEquals(13 + 18, soldTickets1.getAnzahl(Tickets.ERMAESSIGT));
        assertEquals(14 + 19, soldTickets1.getAnzahl(Tickets.FRUEHBAD_ERMAESSIGT));
    }

}
