package fr.insarouen.iti.prog.sgbd.modele;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class TestTuple {
    private Tuple tupleInt;
    private Tuple tupleVarchar;
    private Tuple tupleMixte;
    private Tuple tupleSingleton;
    private Tuple tupleVide;

    @Before
    public void avantTest(){
        List<Valeur> valeursInt = new ArrayList<>();
        valeursInt.add(new ValeurInt(5));
        valeursInt.add(new ValeurInt(10));
        valeursInt.add(new ValeurInt(15));
        this.tupleInt = new Tuple(valeursInt);

        List<Valeur> valeursVarchar = new ArrayList<>();
        valeursVarchar.add(new ValeurVarchar("Selasi"));
        valeursVarchar.add(new ValeurVarchar("Youssef"));
        valeursVarchar.add(new ValeurVarchar("Marwan"));
        this.tupleVarchar = new Tuple(valeursVarchar);

        List<Valeur> valeursMixte = new ArrayList<>();
        valeursMixte.add(new ValeurInt(42));
        valeursMixte.add(new ValeurVarchar("Test"));
        valeursMixte.add(new ValeurInt(100));
        this.tupleMixte = new Tuple(valeursMixte);

        List<Valeur> valeursSingleton = new ArrayList<>();
        valeursSingleton.add(new ValeurInt(7));
        this.tupleSingleton = new Tuple(valeursSingleton);

        this.tupleVide = new Tuple(new ArrayList<>());
    }

    @Test
    public void test_Tuple_Int_index0() {
        assertThat(this.tupleInt.getValeur(0), equalTo(new ValeurInt(5)));
    }
    
    @Test
    public void test_Tuple_Int_index1() {
        assertThat(this.tupleInt.getValeur(1), equalTo(new ValeurInt(10)));
    }
    
    @Test
    public void test_Tuple_Int_index2() {
        assertThat(this.tupleInt.getValeur(2), equalTo(new ValeurInt(15)));
    }
    
    @Test
    public void test_Tuple_Int_taille() {
        assertThat(this.tupleInt.taille(), equalTo(3));
    }
    
    @Test
    public void test_Tuple_Int_getValeurs() {
        List<Valeur> valeurs = this.tupleInt.getValeurs();
        assertThat(valeurs.size(), equalTo(3));
        assertThat(valeurs.get(0), equalTo(new ValeurInt(5)));
        assertThat(valeurs.get(1), equalTo(new ValeurInt(10)));
        assertThat(valeurs.get(2), equalTo(new ValeurInt(15)));
    }

    @Test
    public void test_Tuple_Varchar_index0() {
        assertThat(this.tupleVarchar.getValeur(0), equalTo(new ValeurVarchar("Selasi")));
    }
    
    @Test
    public void test_Tuple_Varchar_index1() {
        assertThat(this.tupleVarchar.getValeur(1), equalTo(new ValeurVarchar("Youssef")));
    }
    
    @Test
    public void test_Tuple_Varchar_index2() {
        assertThat(this.tupleVarchar.getValeur(2), equalTo(new ValeurVarchar("Marwan")));
    }
    
    @Test
    public void test_Tuple_Varchar_taille() {
        assertThat(this.tupleVarchar.taille(), equalTo(3));
    }
    
    @Test
    public void test_Tuple_Varchar_getValeurs() {
        List<Valeur> valeurs = this.tupleVarchar.getValeurs();
        assertThat(valeurs.size(), equalTo(3));
        assertThat(valeurs.get(0), equalTo(new ValeurVarchar("Selasi")));
        assertThat(valeurs.get(1), equalTo(new ValeurVarchar("Youssef")));
        assertThat(valeurs.get(2), equalTo(new ValeurVarchar("Marwan")));
    }

        @Test
    public void test_Tuple_Mixte_getValeur_int() {
        assertThat(this.tupleMixte.getValeur(0), equalTo(new ValeurInt(42)));
    }
    
    @Test
    public void test_Tuple_Mixte_getValeur_varchar() {
        assertThat(this.tupleMixte.getValeur(1), equalTo(new ValeurVarchar("Test")));
    }
    
    @Test
    public void test_Tuple_Mixte_getValeur_int2() {
        assertThat(this.tupleMixte.getValeur(2), equalTo(new ValeurInt(100)));
    }
    
    @Test
    public void test_Tuple_Mixte_taille() {
        assertThat(this.tupleMixte.taille(), equalTo(3));
    }
    
    
    
    @Test
    public void test_Tuple_Singleton_taille() {
        assertThat(this.tupleSingleton.taille(), equalTo(1));
    }
    
    @Test
    public void test_Tuple_Singleton_getValeur() {
        assertThat(this.tupleSingleton.getValeur(0), equalTo(new ValeurInt(7)));
    }
    
    @Test
    public void test_Tuple_Vide_taille() {
        assertThat(this.tupleVide.taille(), equalTo(0));
    }
    
    @Test
    public void test_Tuple_Vide_getValeurs() {
        assertThat(this.tupleVide.getValeurs().size(), equalTo(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_Tuple_getValeurs_immuable(){
        this.tupleInt.getValeurs().add(new ValeurInt(10)); 
    }

    @Test
    public void test_Tuple_constructeur_copie(){
        List<Valeur> valeurs = new ArrayList<>();
        valeurs.add(new ValeurInt(1));
        Tuple t = new Tuple(valeurs);
        valeurs.add(new ValeurInt(2));
        assertThat(t.taille(), equalTo(1));
    }
    @Test
    public void test_Tuple_Mixte_getValeurs() {
        List<Valeur> valeurs = this.tupleMixte.getValeurs();
        assertThat(valeurs.size(), equalTo(3));
}
}