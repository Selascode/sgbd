package fr.insarouen.iti.prog.sgbd;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.insarouen.iti.prog.sgbd.modele.AllTestsModele;

@RunWith(Suite.class)
@SuiteClasses({
   AllTestsModele.class
})

public class Alltests {}
