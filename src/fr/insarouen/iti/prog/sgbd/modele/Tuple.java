package fr.insarouen.iti.prog.sgbd.modele;

import fr.insarouen.iti.prog.sgbd.modele.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Représente un tuple (une ligne de données) dans une table.
 * Un tuple est une collection ordonnée de valeurs correspondant aux attributs de la table.
 * * @author Elakoum
 * @version 1.0
 */


public class Tuple {
    private List<Valeur> values;
    public Tuple(List<Valeur> values){
        this.values=new ArrayList<>(values);

    }

    /**
     * 
     * @param index indice de l'attribut  dans la table 
     * @return la valeur associer a cette attribut
     */
    public Valeur getValeur(int index_attribut){

        return this.values.get(index_attribut);//selon la java doc (Returns the element at the specified position in this list.)

    }
    /**
     * 
     * @return retourne les valeurs dun tuple
     */
    public List<Valeur> getValeurs(){
        return Collections.unmodifiableList(this.values);// Comme dans le projet aventure pour avoir une copie pas une liste avec la même adresse
    }

    /**
     * @return retourn la taille dun tuple
     */
    public int taille(){
        return this.values.size();
    }
    
     /**
     * @return contenu d'un tuple (déboggage)
     */
    public String toString() {
        return this.values.stream()
        .map(v -> v.getDonnee().toString())
        .collect(Collectors.joining(", ", "[", "]"));
    }
}