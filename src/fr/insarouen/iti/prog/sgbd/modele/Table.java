package fr.insarouen.iti.prog.sgbd.modele;

import fr.insarouen.iti.prog.sgbd.modele.Tuple;
import fr.insarouen.iti.prog.sgbd.modele.Attribut;
import fr.insarouen.iti.prog.sgbd.modele.Tuple;
import fr.insarouen.iti.prog.sgbd.exceptions.AttributInconnuException;
import fr.insarouen.iti.prog.sgbd.exceptions.TupleInconnuException;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Représente une table au sein d'une base de données relationnelle.
 * Une table possède un nom unique et contient un ensemble de tuples 
 * respectant une structure d'attributs définie.
 * 
 * @author Elakoum
 * @version 1.0
 */

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

    private String nom;
    private List<Attribut> attributs;
    private List<Tuple> tuples;
   
    /**
     * Construction d'une table vide 
     * @param nom tab a creer
     * @param attibuts contient la liste d'attributs à ajouter
     */

    public Table(String nom, List<Attribut> attributs){
        this.nom=nom;
        this.attributs = new ArrayList<>(attributs);
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
    public int supprimerTuples(List<Tuple> tuplesASupprimer) throws TupleInconnuException {

        // Vérfications pour eviter de perdre de l'information

        for (Tuple t : tuplesASupprimer){
            if (!this.tuples.contains(t)){
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
    public List<Tuple> getTuples(){
        return Collections.unmodifiableList(this.tuples); 
    }

    /**
     * @param nom
     * @return l 'attribut dont il ya le nom nom
     */
    public Attribut getAttribut(String nom) throws AttributInconnuException{//la je pene il sera mieux si en ceera uen exception pour dire si l'attrinbut exuste ou pas
       
            for( Attribut att :this.attributs){
                if(nom.equals(att.getNom()) ){
                    return att;
                }
            }
            throw new AttributInconnuException(nom);//ici en doit enlever 
    }
     /**
     * @param 
     * @return l 'attribut dont il ya le nom nom
     */

    public List<Attribut> getAttributs(){
        return Collections.unmodifiableList(this.attributs);
    }
    /***
     * 
     * @param nom
     * @return un boolean en indiquant si l attribut avec ce nom la existe ou pas 
     */
    public boolean attributExiste(String nom){
        for (Attribut att : this.attributs){
            if (nom.equals(att.getNom())){
                return true; 
            }
        }
        return false; // dapres la java doc cette methode retourn true si existe false si non 
    }

    /***
     * Pour obtenir l'indice dun attribut pour gerer apres le auto incremente  
     * @param nom
     * @return lindice de l'attribut possedent le nom nom
    */
    public int indexAttribut(String nom) throws AttributInconnuException {//la encore il faut ajouter lexceprion ou bien throws car en utilise un methode qui est get attribut qui peut lever une exceprion 
        Attribut att=this.getAttribut(nom);
        int index= this.attributs.indexOf(att);//dapres la java doc (Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the elemen)
        return index;
    }

    /**
     * @return le nom de la table
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Permet d'ajouter un attribut (unbe colonne) au schma de la table
     * @param a L'attribut à ajouter
     */
    public void addAttribut(Attribut a) {
        this.attributs.add(a);
    }

        
}