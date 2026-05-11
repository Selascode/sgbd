package fr.insarouen.iti.prog.sgbd.modele;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.insarouen.iti.prog.sgbd.modele.TestValeur;
import fr.insarouen.iti.prog.sgbd.modele.TestTuple;
import fr.insarouen.iti.prog.sgbd.modele.TestAttribut;

@RunWith(Suite.class)
@SuiteClasses({
    TestValeur.class,
    TestTuple.class,
    TestAttribut.class,
})

public class AllTestsModele {}
