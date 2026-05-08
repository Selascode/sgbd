package fr.insarouen.iti.prog.sgbd.modele;

import fr.insarouen.iti.prog.sgbd.modele.*;
import java.util.List;

public class Tuple {
    private List<Valeur> values;
    public Tuple(List<Valeur> values){
        this.values=values;

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
        return this.values;
    }

    /**
     * @return retourn la taille dun tuple
     */
    public int taille(){
        return 0;
    }




    
}