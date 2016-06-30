package de.ostfalia.test.ss16.whitebox;

import de.ostfalia.test.ss16.logic.Kurs;
import de.ostfalia.test.ss16.logic.Preiskonzept;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Test class for Preiskonzept
 *
 * @author Henrik Drefs
 */
public class PreiskonzeptTest {

    Preiskonzept pk;

    @Before
    public void initTestCase() {
        pk = new Preiskonzept();
    }

    @Test
    public void testKurse() {
        Kurs kurs1 = new Kurs();
        kurs1.setName("Kurs1");
        kurs1.setPreis(20.0);
        Kurs kurs2 = new Kurs();
        kurs2.setName("Kurs2");
        kurs2.setPreis(30.0);
        Kurs kurs3 = new Kurs();
        kurs3.setName("Kurs3");
        kurs3.setPreis(40.0);
        pk.addKurse(kurs1);
        Kurs actual1 = pk.getKurse().get(0);
        assertEquals(kurs1, actual1);
        assertEquals(kurs1.getName(), actual1.getName());
        assertEquals(kurs1.getPreis(), actual1.getPreis(), 0.0001);
        pk.addKurse(kurs2);
        pk.addKurse(kurs3);
        assertTrue(pk.getKurse().contains(kurs1));
        assertTrue(pk.getKurse().contains(kurs2));
        assertTrue(pk.getKurse().contains(kurs3));
    }

    @Test
    public void testRabatte() {
        HashMap<Double, Double> kaufwertRabatte = new HashMap<Double, Double>();
        kaufwertRabatte.put(100.0, 0.95);
        kaufwertRabatte.put(200.0, 0.9);
        pk.setKaufwertRabatte(kaufwertRabatte);
        assertEquals(0.95, pk.getKaufwertRabatte().get(100.0), 0.0001);
        assertEquals(0.9, pk.getKaufwertRabatte().get(200.0), 0.0001);
    }
}
