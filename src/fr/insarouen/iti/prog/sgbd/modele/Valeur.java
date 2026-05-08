package fr.insarouen.iti.prog.sgbd.modele;

import fr.insarouen.iti.prog.sgbd.modele.Type;

import fr.insarouen.iti.prog.sgbd.exceptions.ErreurTypesIncompatible;


public abstract class Valeur {
    
    private Type type; 

    public Valeur(Type type){
        this.type = type; 

    }

    public Type getType(){
        return type;
    }

    public abstract Object getDonnee();
    public abstract int compareA(Valeur autre) throws ErreurTypesIncompatible;
    public abstract boolean estCompatible(Type t);
    
}