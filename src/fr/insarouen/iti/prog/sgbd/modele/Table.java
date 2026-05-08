package fr.insarouen.iti.prog.sgbd.modele;

import fr.insarouen.iti.prog.sgbd.modele.Tuple;
import fr.insarouen.iti.prog.sgbd.modele.Attribut;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une table au sein d'une base de données relationnelle.
 * Une table possède un nom unique et contient un ensemble de tuples 
 * respectant une structure d'attributs définie.
 * 
 * @author Elakoum
 * @version 1.0
 */

public class Table {
    /*Le nom de  la table dans une base de donne ce nom la est unique pour chaque table */

    private String nom_table;
    private List<Attribut> attributs;
    private List<Tuple> tuples;
   
    /**
     * Construction d'une table vide 
     * @param nom_tab nomtab a creer
     */

    public Table(String nom_tab){
        this.nom_table=nom_tab;
        this.attributs = new ArrayList<>();
        this.tuples = new ArrayList<>();
    }

    /**
     * Permet linsertion de tuple dans une table 
     * @param t Tuple qui contient des valeur d'attribut
     */
    public void insererTuple(Tuple t){
        this.tuples.add(t);
    }



    /***
     * 
     * @param tuplesASupprimer
     * @return retourne le nb tuple qui sont supprimer 
     */
    public int supprimerTuples(List<Tuple> tuplesASupprimer) {
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
    public List<Tuple> getTuples(){
        return this.tuples;


        
    }

    /**
     * @param nom
     * @return l 'attribut dont il ya le nom nom
     */
    public Attribut getAttribut(String nom){
        return null;
    }

    /***
     * 
     * @param nom
     * @return un boolean en indiquant si l attribut avec ce nom la existe ou pas 
     */
    public boolean attributExiste(String nom){
        return false;
    }

    /***
     * Pour obtenir l'indice dun attribut pour gerer apres le auto incremente  
     * @param nom
     * @return lindice de l'attribut possedent le nom nom
    */
    public int indexAttribut(String nom){
        return 0;
    }

        
}