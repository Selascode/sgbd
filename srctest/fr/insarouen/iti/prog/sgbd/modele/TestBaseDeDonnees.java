package fr.insarouen.iti.prog.sgbd.modele;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import fr.insarouen.iti.prog.sgbd.exceptions.TableExistanteException;
import fr.insarouen.iti.prog.sgbd.exceptions.TableInconnueException;
import java.util.ArrayList;
import java.util.List;

public class TestBaseDeDonnees {

    private BaseDeDonnees db;
    private Table tableHeros;
    private Table tableBases;
    private Table tableGadgets;

    @Before
    public void avantTest() throws TableExistanteException {
        this.db = new BaseDeDonnees("mabase"); //base sur test_bat_creation
        // table heros
        List<Attribut> attributsHeros = new ArrayList<>();
        attributsHeros.add(new Attribut("id", Type.SERIAL));
        attributsHeros.add(new Attribut("nom", Type.VARCHAR));
        attributsHeros.add(new Attribut("alias", Type.VARCHAR));
        this.tableHeros = new Table("Heros", attributsHeros);
        //table bases
        List<Attribut> attributsBases = new ArrayList<>();
        attributsBases.add(new Attribut("id", Type.SERIAL));
        attributsBases.add(new Attribut("nom", Type.VARCHAR));
        attributsBases.add(new Attribut("ville", Type.VARCHAR));
        this.tableBases = new Table("bases", attributsBases);
        //table gadgets
        List<Attribut> attributsGadgets = new ArrayList<>();
        attributsGadgets.add(new Attribut("id", Type.SERIAL));
        attributsGadgets.add(new Attribut("nom", Type.VARCHAR));
        attributsGadgets.add(new Attribut("puissance", Type.INT));
        this.tableGadgets = new Table("gadgets", attributsGadgets);
    }

    @Test
    public void test_BD_getNom() {
        assertThat(this.db.getNom(), equalTo("mabase"));
    }

    @Test
    public void test_BD_tableExiste_faux() {
        assertThat(this.db.tableExiste("Heros"), equalTo(false));
    }

    @Test
    public void test_BD_tableExiste_vrai() throws TableExistanteException {
        this.db.creerTable(this.tableHeros);
        assertThat(this.db.tableExiste("Heros"), equalTo(true));
    }

    @Test
    public void test_BD_tableExiste_faux_apresSupression() throws TableExistanteException, TableInconnueException {
        this.db.creerTable(this.tableHeros);
        this.db.supprimerTable("Heros");
        assertThat(this.db.tableExiste("Heros"), equalTo(false));
    }

    @Test
    public void test_BD_creerTable_une() throws TableExistanteException {
        this.db.creerTable(this.tableHeros);
        assertThat(this.db.tableExiste("Heros"), equalTo(true));
    }

    @Test
    public void test_BD_creerTable_plusieurs() throws TableExistanteException {
        this.db.creerTable(this.tableHeros);
        this.db.creerTable(this.tableBases);
        this.db.creerTable(this.tableGadgets);
        assertThat(this.db.tableExiste("Heros"), equalTo(true));
        assertThat(this.db.tableExiste("bases"), equalTo(true));
        assertThat(this.db.tableExiste("gadgets"), equalTo(true));
    }

    @Test(expected = TableExistanteException.class)
    public void test_BD_creerTable_doublon() throws TableExistanteException {
        this.db.creerTable(this.tableHeros);
        this.db.creerTable(this.tableHeros);// comme dans test_duplicate.sql
    }

    @Test
    public void test_BD_supprimerTable_simple() throws TableExistanteException, TableInconnueException {
        this.db.creerTable(this.tableHeros);
        this.db.supprimerTable("Heros");
        assertThat(this.db.tableExiste("Heros"), equalTo(false));//comme dans test_drop.sql
    }

    @Test
    public void test_BD_supprimerTable_nAffectePasAutres() throws TableExistanteException, TableInconnueException {
        this.db.creerTable(this.tableHeros);
        this.db.creerTable(this.tableBases);
        this.db.supprimerTable("Heros");
        assertThat(this.db.tableExiste("bases"), equalTo(true));
    }

    @Test(expected = TableInconnueException.class)
    public void test_BD_supprimerTable_inconnue() throws TableInconnueException {
        this.db.supprimerTable("Heros");//comme dans test_drop.sql, DROP lève une erreur
    }

    @Test(expected = TableInconnueException.class)
    public void test_BD_supprimerTable_dejaSupprrimee()  throws TableExistanteException, TableInconnueException {
        this.db.creerTable(this.tableHeros);
        this.db.supprimerTable("Heros");
        this.db.supprimerTable("Heros");
    }

    @Test
    public void test_BD_getTable_simple() throws TableExistanteException, TableInconnueException {
        this.db.creerTable(this.tableHeros);
        assertThat(this.db.getTable("Heros"), equalTo(this.tableHeros));
    }

    @Test
    public void test_BD_getTable_bonneTable()throws TableExistanteException, TableInconnueException {
        this.db.creerTable(this.tableHeros);
        this.db.creerTable(this.tableBases);
        assertThat(this.db.getTable("bases"), equalTo(this.tableBases));
        assertThat(this.db.getTable("Heros"), not(equalTo(this.tableBases)));
    }

    @Test(expected = TableInconnueException.class)
    public void test_BD_getTable_inconnue() throws TableInconnueException {
        this.db.getTable("Heros");
    }

    @Test
    public void test_BD_listerTables_vide() {
        assertThat(this.db.listerTables().size(), equalTo(0));
    }

    @Test
    public void test_BD_listerTables_uneTable() throws TableExistanteException {
        this.db.creerTable(this.tableHeros);
        assertThat(this.db.listerTables().size(), equalTo(1));
        assertThat(this.db.listerTables(), hasItem("Heros"));
    }

    @Test
    public void test_BD_listerTables_plusieurs() throws TableExistanteException {
        this.db.creerTable(this.tableHeros);
        this.db.creerTable(this.tableBases);
        this.db.creerTable(this.tableGadgets);
        assertThat(this.db.listerTables().size(), equalTo(3));
        assertThat(this.db.listerTables(), hasItem("Heros"));
        assertThat(this.db.listerTables(), hasItem("bases"));
        assertThat(this.db.listerTables(), hasItem("gadgets"));
    }

    @Test
    public void test_BD_listerTables_apresSupression() throws TableExistanteException, TableInconnueException {
        this.db.creerTable(this.tableHeros);
        this.db.creerTable(this.tableBases);
        this.db.supprimerTable("Heros");
        assertThat(this.db.listerTables().size(), equalTo(1));
        assertThat(this.db.listerTables(), hasItem("bases"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_BD_listerTables_immutable() throws TableExistanteException {
        this.db.creerTable(this.tableHeros);
        this.db.listerTables().add("youssef");
    }


    @Test
    public void test_BD_prochainSerial_premierAppel() {
        assertThat(this.db.prochainSerial(), equalTo(1));
    }

    @Test// Commande inserte qui s'occupe du bon comportement des serial 
    public void test_BD_prochainSerial_incrementation() {
        this.db.prochainSerial();
        this.db.prochainSerial();
        assertThat(this.db.prochainSerial(), equalTo(3));
    }
}