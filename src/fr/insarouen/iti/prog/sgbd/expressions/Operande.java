package fr.insarouen.iti.prog.sgbd.expressions;

import fr.insarouen.iti.prog.sgbd.modele.*;
import fr.insarouen.iti.prog.sgbd.exceptions.*;

public class Operande {

    public enum TypeOperande {
        COLONNE, NOMBRE, CHAINE
    }

    private TypeOperande type;
    private String nomColonne;
    private ValeurInt valeurNombre;
    private ValeurVarchar valeurChaine;

    public Operande(String nomColonne) {
        this.type = TypeOperande.COLONNE;
        this.nomColonne = nomColonne;
    }

    public Operande(ValeurInt valeur) {
        this.type = TypeOperande.NOMBRE;
        this.valeurNombre = valeur;
    }

    public Operande(ValeurVarchar valeur) {
        this.type = TypeOperande.CHAINE;
        this.valeurChaine = valeur;
    }

    public TypeOperande getType() {
        return type;
    }

    public String getNomColonne() {
        return nomColonne;
    }

    public Valeur evaluer(Tuple tuple, Table table) throws AttributInconnuException {
        switch (type) {
            case COLONNE:
                int index = table.indexAttribut(nomColonne);
                return tuple.getValeur(index);
            case NOMBRE:
                return valeurNombre;
            case CHAINE:
                return valeurChaine;
            default:
                throw new IllegalStateException("Type d'opérande inconnu : " + type);
        }
    }
}