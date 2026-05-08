package fr.insarouen.iti.prog.sgbd.modele;

import org.junit.Test;
import org.junit.Before;
 
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import fr.insarouen.iti.prog.sgbd.exceptions.ErreurTypesIncompatible;


public class TestValeur {

    private ValeurInt vInt1;
    private ValeurInt vInt2;
    private ValeurInt vInt3;
    private ValeurVarchar vVC1;
    private ValeurVarchar vVC2;
    private ValeurVarchar vVC3;
    @Before
    public void avantTest() {
        // Test qui reprenent test_type.sql
        this.vInt1 = new ValeurInt(5);
        this.vInt2 = new ValeurInt(10);
        this.vInt3 = new ValeurInt(10);
        this.vVC1 = new ValeurVarchar("Apple");
        this.vVC2 = new ValeurVarchar("Banana");
        this.vVC3 = new ValeurVarchar("Banana");
    }

    // Valeurs Int 
    @Test
    public void test_ValeurInt_getType() {
        assertThat(this.vInt1.getType(), equalTo(Type.INT));
    }
 
    @Test
    public void test_ValeurInt_getDonnee() {
        assertThat(this.vInt1.getDonnee(), equalTo(5));
    }
 
    @Test
    public void test_ValeurInt_getDonnee_zero() {
        ValeurInt zero = new ValeurInt(0);
        assertThat(zero.getDonnee(), equalTo(0));
    }
 
    @Test
    public void test_ValeurInt_getDonnee_negatif() {
        ValeurInt negatif = new ValeurInt(-10);
        assertThat(negatif.getDonnee(), equalTo(-10));
    }
 
 
    @Test
    public void test_ValeurInt_compareA_egal() throws ErreurTypesIncompatible {
        assertThat(this.vInt2.compareA(this.vInt3), equalTo(0));
    }
 
    @Test
    public void test_ValeurInt_compareA_inferieur() throws ErreurTypesIncompatible {
        assertThat(this.vInt1.compareA(this.vInt2) < 0, equalTo(true));
    }
 
    @Test
    public void test_ValeurInt_compareA_superieur() throws ErreurTypesIncompatible {
        assertThat(this.vInt2.compareA(this.vInt1) > 0, equalTo(true));
    }
 
    @Test(expected = ErreurTypesIncompatible.class)
    public void test_ValeurInt_compareA_typesIncompatibles() throws ErreurTypesIncompatible {
        this.vInt1.compareA(this.vVC1);
    }
 
    @Test
    public void test_ValeurInt_estCompatible_INT() {
        assertThat(this.vInt1.estCompatible(Type.INT), equalTo(true));
    }
 
    @Test
    public void test_ValeurInt_estCompatible_VARCHAR() {
        assertThat(this.vInt1.estCompatible(Type.VARCHAR), equalTo(false));
    }
 
    @Test
    public void test_ValeurInt_estCompatible_SERIAL() {
        assertThat(this.vInt1.estCompatible(Type.SERIAL), equalTo(true));
    }
 
    //======================================
    // Tests Varchar 
    //======================================
 
    @Test
    public void test_ValeurVarchar_getType() {
        assertThat(this.vVC1.getType(), equalTo(Type.VARCHAR));
    }
 
    @Test
    public void test_ValeurVarchar_getDonnee() {
        assertThat(this.vVC1.getDonnee(), equalTo("Apple"));
    }
 
    @Test
    public void test_ValeurVarchar_getDonnee_chaineVide() {
        ValeurVarchar vide = new ValeurVarchar("");
        assertThat(vide.getDonnee(), equalTo(""));
    }
 
    @Test
    public void test_ValeurVarchar_getDonnee_chainePleine() {
        ValeurVarchar vide = new ValeurVarchar("chainePleine");
        assertThat(vide.getDonnee(), equalTo("chainePleine"));
    }

    @Test
    public void test_ValeurVarchar_compareA_egal() throws ErreurTypesIncompatible {
        assertThat(this.vVC2.compareA(this.vVC3), equalTo(0));
    }
 
    @Test
    public void test_ValeurVarchar_compareA_inferieur() throws ErreurTypesIncompatible {
        // normalement "Apple" < "Banana" 
        assertThat(this.vVC1.compareA(this.vVC2) < 0, equalTo(true));
    }
 
    @Test
    public void test_ValeurVarchar_compareA_superieur() throws ErreurTypesIncompatible {
        // normalement "Banana" > "Apple" 
        assertThat(this.vVC2.compareA(this.vVC1) > 0, equalTo(true));
    }
 
    @Test(expected = ErreurTypesIncompatible.class)
    public void test_ValeurVarchar_compareA_typesIncompatibles() throws ErreurTypesIncompatible {
        this.vVC1.compareA(this.vInt1);
    }

    @Test
    public void test_ValeurVarchar_estCompatible_VARCHAR() {
        assertThat(this.vVC1.estCompatible(Type.VARCHAR), equalTo(true));
    }
 
    @Test
    public void test_ValeurVarchar_estCompatible_INT() {
        assertThat(this.vVC1.estCompatible(Type.INT), equalTo(false));
    }
 
    @Test
    public void test_ValeurVarchar_estCompatible_SERIAL() {
        assertThat(this.vVC1.estCompatible(Type.SERIAL), equalTo(false));
    }    
}