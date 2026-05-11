package fr.insarouen.iti.prog.sgbd.modele;

import java.util.Objects;

public class Attribut {
    private String nom;
    private Type type;


    /**
     * Chaque attribut est caracterise par un nom et un type
     * @param nom
     * @param type
     */

    public Attribut(String nom,Type type){
        this.nom=nom;
        this.type=type;

    }
    /**
     * 
     * @return retourne le nom de l'attribut
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * 
     * @return retourne le type de l'attribut
     */
    public Type getType(){
        return this.type;
    }

    /**
     * 
     * @return Verifie l'égalité avec nom et type
     */
    public boolean equals(Object obj){
        if (this == obj) return true;
        if(!(obj instanceof Attribut)) return false;
        Attribut autre = (Attribut)obj;
        return this.nom.equals(autre.nom) && this.type == autre.type; 
    }

     /**
     * 
     * @return retourne la représentation textuelle de l'attribut 
     */
    public int hashCode() {
    return Objects.hash(this.nom, this.type);
}

    /**
     * 
     * @return retourne la représentation textuelle de l'attribut 
     */
    public String toString() {
        return String.format("%s %s",this.nom, this.type);
    }
}