package fr.insarouen.iti.prog.sgbd.modele;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTable {

    @Test
    public void testAddAttribut() {
        Table t = new Table("Hero");
        Attribut a = new Attribut("id", Type.SERIAL);
        t.addAttribut(a);

        assertTrue(t.attributExiste("id"));
        assertEquals(a, t.getAttribut("id"));
        assertEquals(0, t.indexAttribut("id"));
    }
}