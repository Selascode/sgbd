package fr.insarouen.iti.prog.sgbd.modele;

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


    
}