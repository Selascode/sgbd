package fr.insarouen.iti.prog.sgbd.modele;
 
import org.junit.Before;
import org.junit.Test;
 
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
 
import fr.insarouen.iti.prog.sgbd.exceptions.AttributInconnuException;
import fr.insarouen.iti.prog.sgbd.exceptions.TupleInconnuException;
 
import java.util.ArrayList;
import java.util.List;
 
public class TestTable {
    private Table tableHeros;
    private Table tableVide;
    private Attribut attributId;
    private Attribut attributNom;
    private Attribut attributPuissance;
    private Tuple tuple1;
    private Tuple tuple2;
    private Tuple tuple3;


    @Before
    public void avantTest(){
        this.attributId = new Attribut("id", Type.SERIAL); 
        this.attributNom = new Attribut("nom", Type.VARCHAR); 
        this.attributPuissance = new Attribut("puissance",Type.INT);

        List<Attribut> attributs = new ArrayList<>();
        attributs.add(this.attributId);
        attributs.add(this.attributNom);
        attributs.add(this.attributPuissance);
        this.tableHeros = new Table("heros_de_l_insa", attributs); 
        
        this.tableVide = new Table("vide", new ArrayList<>()); 

        // Tuples
        List<Valeur> valeurs1 = new ArrayList<>();
        valeurs1.add(new ValeurInt(1));
        valeurs1.add(new ValeurVarchar("Batman"));
        valeurs1.add(new ValeurInt(100));
        this.tuple1 = new Tuple(valeurs1);

        List<Valeur> valeurs2 = new ArrayList<>();
        valeurs2.add(new ValeurInt(2));
        valeurs2.add(new ValeurVarchar("Robin"));
        valeurs2.add(new ValeurInt(60));
        this.tuple2 = new Tuple(valeurs2);

        List<Valeur> valeurs3 = new ArrayList<>();
        valeurs3.add(new ValeurInt(3));
        valeurs3.add(new ValeurVarchar("Batgirl"));
        valeurs3.add(new ValeurInt(80));
        this.tuple3 = new Tuple(valeurs3);
    }
    //getters
    @Test
    public void test_Table_getNom(){
        assertThat(this.tableHeros.getNom(), equalTo("heros_de_l_insa"));
    }

    @Test
    public void test_Table_getNom_vide(){
        assertThat(this.tableVide.getNom(), equalTo("vide"));
    }

    @Test
    public void test_Table_getAttributs_taille() {
        assertThat(this.tableHeros.getAttributs().size(), equalTo(3));
    }

    @Test
    public void test_Table_getAttributs(){
        List<Attribut> attributs = this.tableHeros.getAttributs();
        assertThat(attributs.get(0), equalTo(this.attributId));
        assertThat(attributs.get(1), equalTo(this.attributNom));
        assertThat(attributs.get(2), equalTo(this.attributPuissance));
    }

    @Test
    public void test_Table_getAttributs_vide(){
        assertThat(this.tableVide.getAttributs().size(), equalTo(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_Table_getAttributs_immutable() {
        this.tableHeros.getAttributs().add(new Attribut("test", Type.INT));
    }

        @Test
    public void test_Table_getAttribut_id() throws AttributInconnuException {
        assertThat(this.tableHeros.getAttribut("id"), equalTo(this.attributId));
    }

    @Test
    public void test_Table_getAttribut_nom() throws AttributInconnuException {
        assertThat(this.tableHeros.getAttribut("nom"), equalTo(this.attributNom));
    }
 
    @Test
    public void test_Table_getAttribut_puissance() throws AttributInconnuException {
        assertThat(this.tableHeros.getAttribut("puissance"), equalTo(this.attributPuissance));
    }
 
    @Test(expected = AttributInconnuException.class)
    public void test_Table_getAttribut_inconnu() throws AttributInconnuException {
        this.tableHeros.getAttribut("test");
    }

    @Test
    public void test_Table_getTuples_tableVide() {
        assertThat(this.tableHeros.getTuples().size(), equalTo(0));
    }
 
    @Test(expected = UnsupportedOperationException.class)
    public void test_Table_getTuples_immutable() {
        this.tableHeros.getTuples().add(this.tuple1);
    }
    // insererTuples
    @Test
    public void test_Table_insererTuple_taille(){
        this.tableHeros.insererTuple(this.tuple1);
        assertThat(this.tableHeros.getTuples().size(),equalTo(1)); 
    }

    @Test
    public void test_Table_insererTuple_contenu(){
        this.tableHeros.insererTuple(this.tuple1);
        assertThat(this.tableHeros.getTuples().get(0), equalTo(this.tuple1)); 
    }

    @Test
    public void test_Table_insererTuples(){
        this.tableHeros.insererTuple(this.tuple1);
        this.tableHeros.insererTuple(this.tuple2);
        this.tableHeros.insererTuple(this.tuple3);
        assertThat(this.tableHeros.getTuples().size(), equalTo(3)); 
    }

    @Test
    public void test_Table_insererTuple_ordre() {
        this.tableHeros.insererTuple(this.tuple1);
        this.tableHeros.insererTuple(this.tuple2);
        assertThat(this.tableHeros.getTuples().get(0), equalTo(this.tuple1));
        assertThat(this.tableHeros.getTuples().get(1), equalTo(this.tuple2));
    }
    //suppression

    @Test
    public void test_Table_supprimerTuples_un() throws TupleInconnuException {
        this.tableHeros.insererTuple(this.tuple1);
        this.tableHeros.insererTuple(this.tuple2);
        this.tableHeros.insererTuple(this.tuple3);
 
        List<Tuple> aSupprimer = new ArrayList<>();
        aSupprimer.add(this.tuple2);
 
        int nbSupprimes = this.tableHeros.supprimerTuples(aSupprimer);
 
        assertThat(nbSupprimes, equalTo(1));
        assertThat(this.tableHeros.getTuples().size(), equalTo(2));
        assertThat(this.tableHeros.getTuples().contains(this.tuple2), equalTo(false));
    }

    @Test
    public void test_Table_supprimerTuples_plusieurs() throws TupleInconnuException {
        this.tableHeros.insererTuple(this.tuple1);
        this.tableHeros.insererTuple(this.tuple2);
        this.tableHeros.insererTuple(this.tuple3);
 
        List<Tuple> aSupprimer = new ArrayList<>();
        aSupprimer.add(this.tuple2);
        aSupprimer.add(this.tuple3);
 
        int nbSupprimes = this.tableHeros.supprimerTuples(aSupprimer);
 
        assertThat(nbSupprimes, equalTo(2));
        assertThat(this.tableHeros.getTuples().size(), equalTo(1));
        assertThat(this.tableHeros.getTuples().contains(this.tuple2), equalTo(false));
        assertThat(this.tableHeros.getTuples().contains(this.tuple3), equalTo(false));
    }

    @Test
    public void test_Table_supprimerTuples_tous() throws TupleInconnuException {
        this.tableHeros.insererTuple(this.tuple1);
        this.tableHeros.insererTuple(this.tuple2);
        this.tableHeros.insererTuple(this.tuple3);
 
        List<Tuple> aSupprimer = new ArrayList<>();
        aSupprimer.add(this.tuple1);
        aSupprimer.add(this.tuple2);
        aSupprimer.add(this.tuple3);
 
        int nbSupprimes = this.tableHeros.supprimerTuples(aSupprimer);
 
        assertThat(nbSupprimes, equalTo(3));
        assertThat(this.tableHeros.getTuples().size(), equalTo(0));
    }

    @Test(expected = TupleInconnuException.class)
    public void test_Table_supprimerTuples_Inexistant() throws TupleInconnuException {
        this.tableHeros.insererTuple(this.tuple1);
        List<Tuple> aSupprimer = new ArrayList<>();
        aSupprimer.add(this.tuple2);
        this.tableHeros.supprimerTuples(aSupprimer);
    }
    
    // ============ TESTS PROJECTION ============
    // projection sur une colonne
    @Test
    public void test_Table_projection_uneColonne() throws AttributInconnuException {
        this.tableHeros.insererTuple(this.tuple1);
        this.tableHeros.insererTuple(this.tuple2);

        List<String> colonnes = new ArrayList<>();
        colonnes.add("nom");

        Table result = this.tableHeros.projection(colonnes);

        assertThat(result.getAttributs().size(), equalTo(1));
        assertThat(result.getAttributs().get(0), equalTo(this.attributNom));
}
    // projection sur plusieurs colonnes
    @Test
    public void test_Table_projection_plusieursColonnes() throws AttributInconnuException {
        this.tableHeros.insererTuple(this.tuple1);

        List<String> colonnes = new ArrayList<>();
        colonnes.add("nom");
        colonnes.add("puissance");

        Table result = this.tableHeros.projection(colonnes);

        assertThat(result.getAttributs().size(), equalTo(2));
        assertThat(result.getAttributs().get(0), equalTo(this.attributNom));
        assertThat(result.getAttributs().get(1), equalTo(this.attributPuissance));
    }


    @Test
    public void test_Table_projection_valeursTuples() throws AttributInconnuException {
        this.tableHeros.insererTuple(this.tuple1); // [1, "Batman", 100]
        this.tableHeros.insererTuple(this.tuple2); // [2, "Robin", 60]

        List<String> colonnes = new ArrayList<>();
        colonnes.add("nom");
        colonnes.add("puissance");

        Table result = this.tableHeros.projection(colonnes);

        // Vérification du nombre de tuples
        assertThat(result.getTuples().size(), equalTo(2));

        // Construction des tuples attendus
        List<Valeur> valeursAttendues1 = new ArrayList<>();
        valeursAttendues1.add(new ValeurVarchar("Batman"));
        valeursAttendues1.add(new ValeurInt(100));
        Tuple tupleAttendu1 = new Tuple(valeursAttendues1);

        List<Valeur> valeursAttendues2 = new ArrayList<>();
        valeursAttendues2.add(new ValeurVarchar("Robin"));
        valeursAttendues2.add(new ValeurInt(60));
        Tuple tupleAttendu2 = new Tuple(valeursAttendues2);

        assertThat(result.getTuples().get(0), equalTo(tupleAttendu1));
        assertThat(result.getTuples().get(1), equalTo(tupleAttendu2));
    }

    // Vérification de l'ordre des colonnes demandé
    @Test
    public void test_Table_projection_ordreColonnes() throws AttributInconnuException {
        this.tableHeros.insererTuple(this.tuple1); // [1, "Batman", 100]

        List<String> colonnes = new ArrayList<>();
        colonnes.add("puissance"); // inversé par rapport à la table originale
        colonnes.add("nom");

        Table result = this.tableHeros.projection(colonnes);

        assertThat(result.getAttributs().get(0), equalTo(this.attributPuissance));
        assertThat(result.getAttributs().get(1), equalTo(this.attributNom));
    }

    // Projection sur toutes les colonnes
    @Test
    public void test_Table_projection_toutesLesColonnes() throws AttributInconnuException {
        this.tableHeros.insererTuple(this.tuple1);

        List<String> colonnes = new ArrayList<>();
        colonnes.add("id");
        colonnes.add("nom");
        colonnes.add("puissance");

        Table result = this.tableHeros.projection(colonnes);

        assertThat(result.getAttributs().size(), equalTo(3));
        assertThat(result.getTuples().size(), equalTo(1));
    }

    @Test
    public void test_Table_projection_sanstuples() throws AttributInconnuException {
        List<String> colonnes = new ArrayList<>();
        colonnes.add("nom");

        Table result = this.tableHeros.projection(colonnes);

        assertThat(result.getAttributs().size(), equalTo(1));
        assertThat(result.getTuples().size(), equalTo(0));
    }

    // Colonne inconnue — exception attendue
    @Test(expected = AttributInconnuException.class)
    public void test_Table_projection_colonneInconnue() throws AttributInconnuException {
        List<String> colonnes = new ArrayList<>();
        colonnes.add("inconnu");

        this.tableHeros.projection(colonnes);
    }

    // Mélange colonne valide + invalide — exception attendue
    @Test(expected = AttributInconnuException.class)
    public void test_Table_projection_melange_valide_invalide() throws AttributInconnuException {
        List<String> colonnes = new ArrayList<>();
        colonnes.add("nom");
        colonnes.add("colonne_inexistante");

        this.tableHeros.projection(colonnes);
    }

        // ============ TESTS PRODUIT CARTESIEN ============

    // Vérification du nom de la nouvelle table
    @Test
    public void test_Table_produitCartesien_nom() {
        Table result = this.tableHeros.produitCartesien(this.tableVide);
        assertThat(result.getNom(), equalTo("heros_de_l_insa_vide"));
    }

    // Vérification de la fusion des attributs
    @Test
    public void test_Table_produitCartesien_attributs() {
        Attribut attributVille = new Attribut("ville", Type.VARCHAR);
        List<Attribut> attributsVille = new ArrayList<>();
        attributsVille.add(attributVille);
        Table tableVilles = new Table("villes", attributsVille);

        Table result = this.tableHeros.produitCartesien(tableVilles);

        assertThat(result.getAttributs().size(), equalTo(4)); // id, nom, puissance, ville
        assertThat(result.getAttributs().get(0), equalTo(this.attributId));
        assertThat(result.getAttributs().get(1), equalTo(this.attributNom));
        assertThat(result.getAttributs().get(2), equalTo(this.attributPuissance));
        assertThat(result.getAttributs().get(3), equalTo(attributVille));
    }

    // Vérification du nombre de tuples (n × m)
    @Test
    public void test_Table_produitCartesien_nbTuples() {
        Attribut attributVille = new Attribut("ville", Type.VARCHAR);
        List<Attribut> attributsVille = new ArrayList<>();
        attributsVille.add(attributVille);
        Table tableVilles = new Table("villes", attributsVille);

        List<Valeur> valeursVille1 = new ArrayList<>();
        valeursVille1.add(new ValeurVarchar("Paris"));
        tableVilles.insererTuple(new Tuple(valeursVille1));

        List<Valeur> valeursVille2 = new ArrayList<>();
        valeursVille2.add(new ValeurVarchar("Londres"));
        tableVilles.insererTuple(new Tuple(valeursVille2));

        this.tableHeros.insererTuple(this.tuple1); // Batman
        this.tableHeros.insererTuple(this.tuple2); // Robin

        Table result = this.tableHeros.produitCartesien(tableVilles);

        assertThat(result.getTuples().size(), equalTo(4)); // 2 × 2
    }

    // Vérification de l'ordre et du contenu des tuples
    @Test
    public void test_Table_produitCartesien_contenuTuples() {
        Attribut attributVille = new Attribut("ville", Type.VARCHAR);
        List<Attribut> attributsVille = new ArrayList<>();
        attributsVille.add(attributVille);
        Table tableVilles = new Table("villes", attributsVille);

        List<Valeur> valeursVille1 = new ArrayList<>();
        valeursVille1.add(new ValeurVarchar("Paris"));
        tableVilles.insererTuple(new Tuple(valeursVille1));

        this.tableHeros.insererTuple(this.tuple1); // [1, Batman, 100]
        this.tableHeros.insererTuple(this.tuple2); // [2, Robin, 60]

        Table result = this.tableHeros.produitCartesien(tableVilles);

        // 2 tuples × 1 tuple = 2 tuples
        assertThat(result.getTuples().size(), equalTo(2));

        // Vérification du premier tuple fusionné [1, Batman, 100, Paris]
        assertThat(result.getTuples().get(0).getValeur(0).toString(), equalTo("1"));
        assertThat(result.getTuples().get(0).getValeur(1).toString(), equalTo("Batman"));
        assertThat(result.getTuples().get(0).getValeur(2).toString(), equalTo("100"));
        assertThat(result.getTuples().get(0).getValeur(3).toString(), equalTo("Paris"));

        // Vérification du second tuple fusionné [2, Robin, 60, Paris]
        assertThat(result.getTuples().get(1).getValeur(0).toString(), equalTo("2"));
        assertThat(result.getTuples().get(1).getValeur(1).toString(), equalTo("Robin"));
        assertThat(result.getTuples().get(1).getValeur(2).toString(), equalTo("60"));
        assertThat(result.getTuples().get(1).getValeur(3).toString(), equalTo("Paris"));
    }

    // Table vide à gauche → résultat vide
    @Test
    public void test_Table_produitCartesien_gaucheVide() {
        this.tableHeros.insererTuple(this.tuple1);

        Table result = this.tableVide.produitCartesien(this.tableHeros);

        assertThat(result.getTuples().size(), equalTo(0));
    }

    // Table vide à droite → résultat vide
    @Test
    public void test_Table_produitCartesien_droiteVide() {
        this.tableHeros.insererTuple(this.tuple1);

        Table result = this.tableHeros.produitCartesien(this.tableVide);

        assertThat(result.getTuples().size(), equalTo(0));
    }

    // Les deux tables vides → résultat vide
    @Test
    public void test_Table_produitCartesien_deuxTablesVides() {
        Table autreVide = new Table("autreVide", new ArrayList<>());

        Table result = this.tableVide.produitCartesien(autreVide);

        assertThat(result.getTuples().size(), equalTo(0));
        assertThat(result.getAttributs().size(), equalTo(0));
    }

    // Vérification que la table originale n'est pas modifiée
    @Test
    public void test_Table_produitCartesien_sansModificationOriginale() {
        this.tableHeros.insererTuple(this.tuple1);
        this.tableHeros.insererTuple(this.tuple2);

        Attribut attributVille = new Attribut("ville", Type.VARCHAR);
        List<Attribut> attributsVille = new ArrayList<>();
        attributsVille.add(attributVille);
        Table tableVilles = new Table("villes", attributsVille);
        tableVilles.insererTuple(new Tuple(List.of(new ValeurVarchar("Paris"))));

        this.tableHeros.produitCartesien(tableVilles);

        // La table originale ne doit pas avoir été modifiée
        assertThat(this.tableHeros.getTuples().size(), equalTo(2));
        assertThat(this.tableHeros.getAttributs().size(), equalTo(3));
    }

}