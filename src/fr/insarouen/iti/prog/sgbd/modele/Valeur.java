package fr.insarouen.iti.prog.sgbd.modele;

import fr.insarouen.iti.prog.sgbd.modele.Type;

public abstract class Valeur {
    
    private Type type; 

    public Valeur(Type type){
        this.type = type; 

    }

    public Type getType(){
        return type;
    }

    public abstract Object getDonnee();
    public abstract int compareA(Valeur autre);
    public abstract boolean estCompatible(Type t);
    
}