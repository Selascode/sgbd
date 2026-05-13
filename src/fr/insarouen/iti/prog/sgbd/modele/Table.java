package fr.insarouen.iti.prog.sgbd.modele;

import fr.insarouen.iti.prog.sgbd.exceptions.AttributInconnuException;
import fr.insarouen.iti.prog.sgbd.exceptions.TupleInconnuException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Représente une table au sein d'une base de données relationnelle.
 * Une table possède un nom unique et contient un ensemble de tuples
 * respectant une structure d'attributs définie.
 *
 * @author Elakoum
 * @version 1.0
 */
public class Table {
    /*
     * Le nom de la table dans une base de donne ce nom la est unique pour chaque
     * table
     */

    private String nom;
    
    private LinkedHashSet<Attribut> attributs;
    private List<Tuple> tuples;

    /**
     * Construction d'une table vide 
     * Lors de la creation dune table en fait CREATE NOM (aat1,att2,att3)
     * @param nom tab a creer
     * @param attibuts contient la liste d'attributs à ajouter
     */

    public Table(String nom, List<Attribut> attributs) { // en se basant sur lexemple de crearion d'une table de fichier exemple du moodle
    
        this.nom = nom;
        this.attributs = new LinkedHashSet<>(attributs);
        this.tuples = new ArrayList<>();
    }

    /**
     * Permet linsertion de tuple dans une table
     * 
     * @param t Tuple qui contient des valeur d'attribut
     */
    public void insererTuple(Tuple t){ // a verifier que les valeur inserer sont bien les attribut demander 
        this.tuples.add(t);
    }

    /***
     * 
     * @param tuplesASupprimer
     * @return retourne le nb tuple qui sont supprimer
     */
    public int supprimerTuples(List<Tuple> tuplesASupprimer) throws TupleInconnuException {

        // Vérfications pour eviter de perdre de l'information

        for (Tuple t : tuplesASupprimer) {
            if (!this.tuples.contains(t)) {
                throw new TupleInconnuException(String.format("Le tuple %s n'existe pas ", t.toString()));
            }
        }
        // suppression
        int tailleAvant = this.tuples.size();
        this.tuples.removeAll(tuplesASupprimer);
        int tailleApres = this.tuples.size();

        return tailleAvant - tailleApres; // La différence nous donne le nombre de lignes supprimées
    }

    /**
     * 
     * @return retourne la liste des tuples de la tables
     * 
     */
    public List<Tuple> getTuples() {
        return Collections.unmodifiableList(this.tuples);
    }

    /**
     * @param nom
     * @return l 'attribut dont il ya le nom nom
     */
    public Attribut getAttribut(String nom) throws AttributInconnuException {// la je pene il sera mieux si en ceera uen
                                                                             // exception pour dire si l'attrinbut
                                                                             // exuste ou pas

        for (Attribut att : this.attributs) {
            if (nom.equals(att.getNom())) {
                return att;
            }
        }
        throw new AttributInconnuException(nom);// ici en doit enlever
    }

    /**
     * @param
     * @return l 'attribut dont il ya le nom nom
     */

    public Set<Attribut> getAttributs() {
        return Collections.unmodifiableSet(this.attributs);
    }

    /***
     * 
     * @param nom
     * @return un boolean en indiquant si l attribut avec ce nom la existe ou pas
     */
    public boolean attributExiste(String nom) {
        for (Attribut att : this.attributs) {
            if (nom.equals(att.getNom())) {
                return true;
            }
        }
        return false; // dapres la java doc cette methode retourn true si existe false si non
    }

    /***
     * Pour obtenir l'indice dun attribut pour gerer apres le auto incremente
     * 
     * @param nom
     * @return lindice de l'attribut possedent le nom nom
     */
    public int indexAttribut(String nom) throws AttributInconnuException {
        Attribut att = this.getAttribut(nom); // lève AttributInconnuException si absent

        List<Attribut> liste = new ArrayList<>(this.attributs);
        return liste.indexOf(att);
    }

    /**
     * @return le nom de la table
     */
    public String getNom() {
        return this.nom;
    }
    
    /***
     * fait la projection sur les atrributs demandé
     * @param nomsColonnes
     * @return la table apres projection
    
    */
    public Table projection(List<String> nomsColonnes) throws AttributInconnuException{
            // On verifie que les colonnes existe
            for (String nomCol : nomsColonnes) {
            boolean existe = false;
            for (Attribut attr : this.attributs) {
                if (attr.getNom().equals(nomCol)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                throw new AttributInconnuException("Colonne " + nomCol + " inconnue");
            }
        }
        // on garde les attribut demandés
        List<Attribut> nouvelleAttributs = new ArrayList<>();
        for (String nomCol : nomsColonnes) {
            for (Attribut attr : this.attributs) {
                if (attr.getNom().equals(nomCol)) {
                    nouvelleAttributs.add(attr);
                    break;
                }
            }
        }

        Table nouvelleTable = new Table(this.nom, nouvelleAttributs);

       
        List<Attribut> liste = new ArrayList<>(this.attributs);


        for (Tuple ancien : this.tuples) {
            List<Valeur> nouvellesValeurs = new ArrayList<>();
            for (String nomCol : nomsColonnes) {
                for (int i = 0; i < this.attributs.size(); i++) {
                    if (liste.get(i).getNom().equals(nomCol)) {
                        nouvellesValeurs.add(ancien.getValeur(i));
                        break;
                    }
                }
            }
            nouvelleTable.insererTuple(new Tuple(nouvellesValeurs));
        }

        return nouvelleTable;
}

    public Table produitCartesien(Table autre){
        // on crée une liste avec tous les attributs préfixés par leur nom de table
        List<Attribut> attributsFusion = new ArrayList<>();
        for (Attribut attr : this.attributs) {
            String nouveauNom = attr.getNom().contains(".") ? attr.getNom() : this.nom + "." + attr.getNom();
            attributsFusion.add(new Attribut(nouveauNom, attr.getType()));
        }
        for (Attribut attr : autre.attributs) {
            String nouveauNom = attr.getNom().contains(".") ? attr.getNom() : autre.nom + "." + attr.getNom();
            attributsFusion.add(new Attribut(nouveauNom, attr.getType()));
        }
        // on crée la nouvelle table
        String nomproduitcartesien = this.nom + "_" + autre.nom;
        Table newtable = new Table(nomproduitcartesien , attributsFusion);
        // On parcours les tuples de la premiere table
        for (Tuple tupleThis : this.tuples){
            // On parcours les tuples de la deuxieme table
            for(Tuple tupleAutre : autre.tuples){
                //on crée la liste des attributs des deux tables
                List<Valeur> valeursFusionnees = new ArrayList<>();
                // on ajoute les valeurs du premier tuple
                for (int i = 0; i < tupleThis.taille(); i++) {
                    valeursFusionnees.add(tupleThis.getValeur(i));
                }
                // on ajouter les valeurs du second tuple
                for (int i = 0; i < tupleAutre.taille(); i++) {
                    valeursFusionnees.add(tupleAutre.getValeur(i));
                }
                // Créer et insérer le nouveau tuple
                Tuple nouveauTuple = new Tuple(valeursFusionnees);
                newtable.insererTuple(nouveauTuple);
            }
        }
        return newtable;
    }

    /**
     * pplique une sélection sur les tuples en conservant uniquement ceux qui vérifient la condit iob
     * @param conditiob L'expression logique à évaluer
     * @return une nouvelle table contenant les tuples filtrés
     */
    public Table selection(ExpressionLogique condition) throws AttributInconnuException, TypesIncompatibleException {
        Table nouvelleTable = new Table(this.nom, new ArrayList<>(this.attributs));
        for (Tuple t : this.tuples) {
            if (condition.evaluerexpression(t, this)) {
                nouvelleTable.insererTuple(t);
            }
        }
        return nouvelleTable;
    }
    
    
    /**
     * Permet d'ajouter un attribut (unbe colonne) au schma de la table
     * 
     * @param a L'attribut à ajouter
     */
    public void addAttribut(Attribut a) {
        this.attributs.add(a);
    }

}