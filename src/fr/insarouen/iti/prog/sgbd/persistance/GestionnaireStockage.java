package fr.insarouen.iti.prog.sgbd.persistance;

import fr.insarouen.iti.prog.sgbd.exceptions.TableExistanteException;
import fr.insarouen.iti.prog.sgbd.exceptions.TableInconnueException;
import fr.insarouen.iti.prog.sgbd.modele.Attribut;
import fr.insarouen.iti.prog.sgbd.modele.BaseDeDonnees;
import fr.insarouen.iti.prog.sgbd.modele.Table;
import fr.insarouen.iti.prog.sgbd.modele.Tuple;
import fr.insarouen.iti.prog.sgbd.modele.Type;
import fr.insarouen.iti.prog.sgbd.modele.Valeur;
import fr.insarouen.iti.prog.sgbd.modele.ValeurInt;
import fr.insarouen.iti.prog.sgbd.modele.ValeurVarchar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère la sauvegarde et le chargement de la base de données.
 */
public class GestionnaireStockage {

    private static final String FICHIER_PAR_DEFAUT = "sgbd.data";

    private String cheminFichier;

    /**
     * Crée un gestionnaire utilisant le fichier de sauvegarde par defaut.
     */
    public GestionnaireStockage() {
        this(FICHIER_PAR_DEFAUT);
    }

    /**
     * Crée un gestionnaire utilisant le fichier indique.
     *
     * @param cheminFichier le chemin du fichier de sauvegarde
     */
    public GestionnaireStockage(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    /**
     * Sauvegarde l'état courant de la base de donnees.
     *
     * @param db la base de données à sauvegarder
     */
    public void sauvegarder(BaseDeDonnees db) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.cheminFichier))) {
            writer.write("DATABASE " + db.getNom());
            writer.newLine();
            writer.write("SERIAL " + db.getCompteurSerial());
            writer.newLine();
            writer.newLine();

            for (String nomTable : db.listerTables()) {
                Table table = db.getTable(nomTable);
                sauvegarderTable(writer, table);
                writer.newLine();
            }
        } catch (IOException | TableInconnueException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la base.", e);
        }
    }

    /**
     * Charge une base de données sauvegardee.
     *
     * @return la base de données chargée
     */
    public BaseDeDonnees charger() {
        File fichier = new File(this.cheminFichier);

        if (!fichier.exists()) {
            return new BaseDeDonnees("laBaseDeDonnees");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne = lireLigneNonVide(reader);

            if (ligne == null || !ligne.startsWith("DATABASE ")) {
                throw new IllegalStateException("Fichier de sauvegarde invalide : DATABASE attendu.");
            }

            String nomBase = ligne.substring("DATABASE ".length());
            BaseDeDonnees db = new BaseDeDonnees(nomBase);

            ligne = lireLigneNonVide(reader);
            if (ligne == null || !ligne.startsWith("SERIAL ")) {
                throw new IllegalStateException("Fichier de sauvegarde invalide : SERIAL attendu.");
            }

            int compteurSerial = Integer.parseInt(ligne.substring("SERIAL ".length()));
            db.restaurerCompteurSerial(compteurSerial);

            while ((ligne = lireLigneNonVide(reader)) != null) {
                if (ligne.startsWith("TABLE ")) {
                    Table table = chargerTable(reader, ligne);
                    db.creerTable(table);
                }
            }

            return db;
        } catch (IOException | TableExistanteException e) {
            throw new RuntimeException("Erreur lors du chargement de la base.", e);
        }
    }

    private void sauvegarderTable(BufferedWriter writer, Table table) throws IOException {
        writer.write("TABLE " + table.getNom());
        writer.newLine();
        writer.write("COLUMNS " + encoderAttributs(table.getAttributs()));
        writer.newLine();

        for (Tuple tuple : table.getTuples()) {
            writer.write("ROW " + encoderValeurs(tuple.getValeurs()));
            writer.newLine();
        }

        writer.write("ENDTABLE");
        writer.newLine();
    }

    private Table chargerTable(BufferedReader reader, String ligneTable) throws IOException {
        String nomTable = ligneTable.substring("TABLE ".length());
        String ligneColonnes = lireLigneNonVide(reader);

        if (ligneColonnes == null || !ligneColonnes.startsWith("COLUMNS ")) {
            throw new IllegalStateException("Fichier de sauvegarde invalide : COLUMNS attendu.");
        }

        List<Attribut> attributs = decoderAttributs(ligneColonnes.substring("COLUMNS ".length()));
        Table table = new Table(nomTable, attributs);

        String ligne;
        while ((ligne = lireLigneNonVide(reader)) != null) {
            if (ligne.equals("ENDTABLE")) {
                return table;
            }

            if (!ligne.startsWith("ROW ")) {
                throw new IllegalStateException("Fichier de sauvegarde invalide : ROW ou ENDTABLE attendu.");
            }

            List<Valeur> valeurs = decoderValeurs(ligne.substring("ROW ".length()), attributs);
            table.insererTuple(new Tuple(valeurs));
        }

        throw new IllegalStateException("Fichier de sauvegarde invalide : ENDTABLE manquant.");
    }

    private String encoderAttributs(List<Attribut> attributs) {
        List<String> elements = new ArrayList<>();

        for (Attribut attribut : attributs) {
            elements.add(attribut.getNom() + ":" + attribut.getType());
        }

        return String.join(",", elements);
    }

    private List<Attribut> decoderAttributs(String texte) {
        List<Attribut> attributs = new ArrayList<>();

        if (texte.isEmpty()) {
            return attributs;
        }

        String[] elements = texte.split(",");
        for (String element : elements) {
            String[] morceaux = element.split(":");
            if (morceaux.length != 2) {
                throw new IllegalStateException("Attribut invalide : " + element);
            }

            attributs.add(new Attribut(morceaux[0], Type.valueOf(morceaux[1])));
        }

        return attributs;
    }

    private String encoderValeurs(List<Valeur> valeurs) {
        List<String> elements = new ArrayList<>();

        for (Valeur valeur : valeurs) {
            if (valeur instanceof ValeurVarchar) {
                elements.add(encoderChaine((String) valeur.getDonnee()));
            } else {
                elements.add(String.valueOf(valeur.getDonnee()));
            }
        }

        return String.join(",", elements);
    }

    private List<Valeur> decoderValeurs(String texte, List<Attribut> attributs) {
        List<String> elements = decouperValeurs(texte);

        if (elements.size() != attributs.size()) {
            throw new IllegalStateException("Nombre de valeurs different du nombre d'attributs.");
        }

        List<Valeur> valeurs = new ArrayList<>();

        for (int i = 0; i < elements.size(); i++) {
            Type type = attributs.get(i).getType();
            String element = elements.get(i);

            if (type == Type.VARCHAR) {
                valeurs.add(new ValeurVarchar(decoderChaine(element)));
            } else {
                valeurs.add(new ValeurInt(Integer.parseInt(element)));
            }
        }

        return valeurs;
    }

    private List<String> decouperValeurs(String texte) {
        List<String> valeurs = new ArrayList<>();
        StringBuilder courant = new StringBuilder();
        boolean dansChaine = false;
        boolean echappe = false;

        for (int i = 0; i < texte.length(); i++) {
            char c = texte.charAt(i);

            if (echappe) {
                courant.append(c);
                echappe = false;
            } else if (c == '\\') {
                courant.append(c);
                echappe = true;
            } else if (c == '"') {
                courant.append(c);
                dansChaine = !dansChaine;
            } else if (c == ',' && !dansChaine) {
                valeurs.add(courant.toString());
                courant.setLength(0);
            } else {
                courant.append(c);
            }
        }

        valeurs.add(courant.toString());
        return valeurs;
    }

    private String encoderChaine(String valeur) {
        String resultat = valeur
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
        return "\"" + resultat + "\"";
    }

    private String decoderChaine(String valeur) {
        if (valeur.length() < 2 || valeur.charAt(0) != '"' || valeur.charAt(valeur.length() - 1) != '"') {
            throw new IllegalStateException("Chaine invalide : " + valeur);
        }

        String contenu = valeur.substring(1, valeur.length() - 1);
        StringBuilder resultat = new StringBuilder();
        boolean echappe = false;

        for (int i = 0; i < contenu.length(); i++) {
            char c = contenu.charAt(i);

            if (echappe) {
                resultat.append(c);
                echappe = false;
            } else if (c == '\\') {
                echappe = true;
            } else {
                resultat.append(c);
            }
        }

        if (echappe) {
            resultat.append('\\');
        }

        return resultat.toString();
    }

    private String lireLigneNonVide(BufferedReader reader) throws IOException {
        String ligne;

        while ((ligne = reader.readLine()) != null) {
            ligne = ligne.trim();
            if (!ligne.isEmpty()) {
                return ligne;
            }
        }

        return null;
    }
}