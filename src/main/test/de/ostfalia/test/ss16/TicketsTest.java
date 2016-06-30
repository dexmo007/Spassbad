package de.ostfalia.test.ss16;

import de.ostfalia.test.ss16.logic.Kurs;
import de.ostfalia.test.ss16.logic.Tickets;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Tickets
 *
 * @author Henrik Drefs
 */
public class TicketsTest {

    Tickets tickets;

    @Before
    public void initTestCase() {
        tickets = new Tickets();
    }

    @Test
    public void testAddRemoveTicket() {
        tickets.addTicket(Tickets.EINZELKARTE, 20);
        assertEquals(20, tickets.getAnzahl(Tickets.EINZELKARTE));
        assertEquals(20, tickets.getGesamtAnzahl());
        tickets.addTicket(Tickets.SAUNA, 5);
        assertEquals(5, tickets.getAnzahl(Tickets.SAUNA));
        assertEquals(25, tickets.getGesamtAnzahl());
        tickets.addTicket(Tickets.FRUEHBAD, 10);
        assertEquals(10, tickets.getAnzahl(Tickets.FRUEHBAD));
        assertEquals(35, tickets.getGesamtAnzahl());
        tickets.addTicket(Tickets.ERMAESSIGT, 7);
        assertEquals(7, tickets.getAnzahl(Tickets.ERMAESSIGT));
        assertEquals(42, tickets.getGesamtAnzahl());
        tickets.addTicket(Tickets.FRUEHBAD_ERMAESSIGT, 12);
        assertEquals(12, tickets.getAnzahl(Tickets.FRUEHBAD_ERMAESSIGT));
        assertEquals(54, tickets.getGesamtAnzahl());
        tickets.removeTicket(Tickets.SAUNA, 4);
        assertEquals(1, tickets.getAnzahl(Tickets.SAUNA));
        assertEquals(50, tickets.getGesamtAnzahl());
        tickets.removeTicket(Tickets.EINZELKARTE, 15);
        assertEquals(5, tickets.getAnzahl(Tickets.EINZELKARTE));
        assertEquals(35, tickets.getGesamtAnzahl());
        tickets.removeTicket(Tickets.FRUEHBAD_ERMAESSIGT, 0);
        assertEquals(12, tickets.getAnzahl(Tickets.FRUEHBAD_ERMAESSIGT));
        assertEquals(35, tickets.getGesamtAnzahl());
        tickets.removeTicket(Tickets.FRUEHBAD, 10);
        assertEquals(0, tickets.getAnzahl(Tickets.FRUEHBAD));
        assertEquals(25, tickets.getGesamtAnzahl());
        // remove more than existing
        tickets.removeTicket(Tickets.ERMAESSIGT, 8);
        assertEquals(0, tickets.getAnzahl(Tickets.ERMAESSIGT));
        assertEquals(18, tickets.getGesamtAnzahl());
    }

    @Test
    public void testAddRemoveKurs() {
        Kurs kurs1 = new Kurs();
        kurs1.setName("Kurs1");
        kurs1.setPreis(10.0);
        Kurs kurs2 = new Kurs();
        kurs2.setName("Kurs2");
        kurs2.setPreis(20.0);
        Kurs kurs3 = new Kurs();
        kurs3.setName("Kurs3");
        kurs3.setPreis(15.0);
        tickets.addKurs(kurs1, 15);
        assertEquals(15, tickets.getKursAnzahl(kurs1));
        assertEquals(15, tickets.getKursAnzahl("Kurs1"));
        tickets.addKurs(kurs2, 30);
        assertEquals(30, tickets.getKursAnzahl(kurs2));
        assertEquals(30, tickets.getKursAnzahl("Kurs2"));
        tickets.removeKurs(kurs1, 14);
        assertEquals(1, tickets.getKursAnzahl(kurs1));
        assertEquals(1, tickets.getKursAnzahl("Kurs1"));
        tickets.addKurs(kurs3, 4);
        assertEquals(4, tickets.getKursAnzahl(kurs3));
        assertEquals(4, tickets.getKursAnzahl("Kurs3"));
        tickets.removeKurs(kurs2, 30);
        assertEquals(0, tickets.getKursAnzahl(kurs2));
        assertEquals(0, tickets.getKursAnzahl("Kurs2"));
        tickets.removeKurs(kurs3, 6);
        assertEquals(0, tickets.getKursAnzahl(kurs3));
        assertEquals(0, tickets.getKursAnzahl("Kurs3"));
    }

    @Test
    public void testAddTickets() {
        Tickets t = new Tickets();
        t.addTicket(Tickets.EINZELKARTE, 10);
        t.addTicket(Tickets.SAUNA, 11);
        t.addTicket(Tickets.FRUEHBAD, 12);
        t.addTicket(Tickets.ERMAESSIGT, 13);
        t.addTicket(Tickets.FRUEHBAD_ERMAESSIGT, 14);
        Kurs kurs = new Kurs();
        kurs.setName("Kurs");
        kurs.setPreis(12);
        t.addKurs(kurs, 5);
        tickets.addTickets(t);
        assertEquals(10, tickets.getAnzahl(Tickets.EINZELKARTE));
        assertEquals(11, tickets.getAnzahl(Tickets.SAUNA));
        assertEquals(12, tickets.getAnzahl(Tickets.FRUEHBAD));
        assertEquals(13, tickets.getAnzahl(Tickets.ERMAESSIGT));
        assertEquals(14, tickets.getAnzahl(Tickets.FRUEHBAD_ERMAESSIGT));
        assertEquals(60, tickets.getGesamtAnzahl());
        assertEquals(5, tickets.getKursAnzahl(kurs));
        assertEquals(5, tickets.getKursAnzahl("Kurs"));
    }

}
