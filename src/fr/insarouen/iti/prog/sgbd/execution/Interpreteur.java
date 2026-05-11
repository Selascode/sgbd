package fr.insarouen.iti.prog.sgbd.execution;

import fr.insarouen.iti.prog.sgbd.parseur.SGBDParser;
import fr.insarouen.iti.prog.sgbd.parseur.ParseException;
import fr.insarouen.iti.prog.sgbd.parseur.TokenMgrError;
import fr.insarouen.iti.prog.sgbd.modele.BaseDeDonnees;
import java.io.FileInputStream;

public class Interpreteur {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(" chemin vers un fichier .sql");
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(args[0]);
            SGBDParser parser = new SGBDParser(fis);
            BaseDeDonnees db = new BaseDeDonnees("sgbd");
            parser.setDatabase(db);
            System.out.println("Analyse du fichier: " + args[0]);

            // j'appelle la règle de base (Commande) en boucle jusqu'à la fin du fichier
            while (true) {
                try {
                    parser.Commande();
                    System.out.println("[OK] Commande syntaxiquement correcte !");
                } catch (ParseException e) {
                    System.out.println("[ERREUR] Erreur de syntaxe : " + e.getMessage());
                    break;
                } catch (TokenMgrError e) {
                    System.out.println("[ERREUR Erreur lexicale : " + e.getMessage());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}