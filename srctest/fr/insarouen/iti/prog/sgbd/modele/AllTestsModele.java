package fr.insarouen.iti.prog.sgbd.modele;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.insarouen.iti.prog.sgbd.modele.TestValeur;
import fr.insarouen.iti.prog.sgbd.modele.TestTuple;

@RunWith(Suite.class)
@SuiteClasses({
    TestValeur.class,
    TestTuple.class
})

public class AllTestsModele {}
