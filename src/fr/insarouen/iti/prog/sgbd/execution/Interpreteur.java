package fr.insarouen.iti.prog.sgbd.execution;

import fr.insarouen.iti.prog.sgbd.parseur.SGBDParser;
import fr.insarouen.iti.prog.sgbd.parseur.ParseException;
import fr.insarouen.iti.prog.sgbd.parseur.TokenMgrError;
import fr.insarouen.iti.prog.sgbd.data.EnregistreurSerialisation;
import fr.insarouen.iti.prog.sgbd.data.GestionnaireStockage;
import fr.insarouen.iti.prog.sgbd.data.LecteurSerialisation;
import fr.insarouen.iti.prog.sgbd.exceptions.BaseDeDonneesExistanteException;
import fr.insarouen.iti.prog.sgbd.modele.BaseDeDonnees;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Scanner;
public class Interpreteur{
    private String name ;




    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // dossier où les bases sont sauvegardées
        String dossier = "saves/";
        new File(dossier).mkdirs(); // crée le dossier si il n'existe pas

        GestionnaireStockage gestionnaire = new GestionnaireStockage(
            new LecteurSerialisation(null),
            new EnregistreurSerialisation(null)
        );

        BaseDeDonnees bd = null;

        while (bd == null) {
            // affiche les fichiers .ser existants dans le dossier saves/
            afficherBasesExistantes(dossier);

            System.out.println("1 - Créer une nouvelle base");
            System.out.println("2 - Charger une base existante");
            System.out.println("3 - Afficher les bases existantes");
            System.out.println("4 - Quitter"); 
            System.out.print("Ton choix : ");
            String choix = sc.nextLine().trim();

            if (choix.equals("1")) {
                System.out.print("Nom de la nouvelle base : ");
                String nom = sc.nextLine().trim();
                String chemin = dossier + nom + ".ser";

                if (GestionnaireStockage.baseExiste(chemin)) {
                    System.out.println("[ERREUR] Une base '" + nom + "' existe déjà sur le disque.");
                    continue;
                }

                try {
                    bd = new BaseDeDonnees(nom);
                    System.out.println("Base '" + nom + "' créée.");
                } catch (Exception e) {
                    System.out.println("[ERREUR] " + e.getMessage());
                }

            
                } else if (choix.equals("3")) {
                    // affiche et recommence la boucle
                    afficherBasesExistantes(dossier);

                }else if (choix.equals("4") || choix.equalsIgnoreCase("quit") || choix.equalsIgnoreCase("exit")) {
            
                    System.out.println("Au revoir ;)");
                    return;  // quitte le main

                } else if (choix.equals("2")) {

                System.out.print("Nom de la base à charger : ");
                String nom = sc.nextLine().trim();
                String chemin = dossier + nom + ".ser";

                if (!GestionnaireStockage.baseExiste(chemin)) {
                    System.out.println("[ERREUR] Aucune base '" + nom + "' trouvée.");
                    continue;
                }

                try {
                    bd = gestionnaire.charger(chemin);
                    System.out.println("Base '" + nom + "' chargée.");
                } catch (Exception e) {
                    System.out.println("[ERREUR] " + e.getMessage());
                }
            }
        }

        if (args.length == 0) {
            interpreteurConsole(bd, gestionnaire, dossier + bd.getNom() + ".ser");
        } else {
            interpreteurFichier(args[0], bd, gestionnaire, dossier + bd.getNom() + ".ser");
        }
    }

    
    private static void afficherBasesExistantes(String dossier) {
        File[] fichiers = new File(dossier).listFiles(f -> f.getName().endsWith(".ser"));
        if (fichiers == null || fichiers.length == 0) {
            System.out.println("Aucune base sauvegardée.");
            return;
        }
        System.out.println("Bases existantes :");
        for (File f : fichiers) {
            System.out.println("  - " + f.getName().replace(".ser", ""));
        }
    }


    public static void interpreteurConsole(BaseDeDonnees db, GestionnaireStockage gestionnaire, String chemin) {
        Scanner sc = new Scanner(System.in);
        String commande = "";

        System.out.print("sgbd> ");

        while (sc.hasNextLine()) {
            commande += sc.nextLine();

            String commandeLower = commande.trim().toLowerCase();
            if (commandeLower.equals("quit") || commandeLower.equals("exit") ||
                commandeLower.equals("quit;") || commandeLower.equals("exit;")) {
                System.out.println("Au revoir ;)");
                return; // etourne au main 
            }

            if (commande.trim().endsWith(";")) {
                commande = commande.trim();

                byte[] bytes = commande.getBytes();
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                SGBDParser parser = new SGBDParser(bais);
                parser.setDatabase(db);

                try {
                    parser.Commande();
                    gestionnaire.sauvegarder(db, chemin); // sauvegarde après chaque commande
                } catch (ParseException e) {
                    System.out.println("[ERREUR] Syntaxe incorrecte : " + e.getMessage());
                } catch (TokenMgrError e) {
                    System.out.println("[ERREUR] Caractère inconnu : " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("[ERREUR] Sauvegarde : " + e.getMessage());
                }

                commande = "";
                System.out.print("sgbd> ");
            }
        }
    }
    public static void interpreteurFichier(String cheminFichier, BaseDeDonnees db, GestionnaireStockage gestionnaire, String chemin) {
        try {
            FileInputStream fis = new FileInputStream(cheminFichier);
            SGBDParser parser = new SGBDParser(fis);
            parser.setDatabase(db);
            System.out.println("Analyse du fichier: " + cheminFichier);

            while (true) {
                try {
                    parser.Commande();

                } catch (ParseException e) {
                    if (e.getMessage() != null && e.getMessage().contains("EOF")) {
                        break; // fin normale du fichier
                    }
                    System.out.println("[ERREUR] " + e.getMessage());
                    break;
                } catch (TokenMgrError e) {
                    System.out.println("[ERREUR] " + e.getMessage());
                    break;
                }
            }

            gestionnaire.sauvegarder(db, chemin); // sauvegarde à la fin du fichier
            System.out.println("Au revoir ;)");

        } catch (java.io.FileNotFoundException e) {
            System.out.println("[ERREUR] Fichier introuvable : " + cheminFichier);
        } catch (Exception e) {
            System.out.println("[ERREUR] " + e.getMessage());
        }
    }

    


}

