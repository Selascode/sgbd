package fr.insarouen.iti.prog.sgbd.modele;

import fr.insarouen.iti.prog.sgbd.exceptions.ErreurTableExistante;
import fr.insarouen.iti.prog.sgbd.exceptions.ErreurTableInconnue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente la base de données principale.
 * Gère l'ensemble des tables et le compteur global pour les types SERIAL
 */
public class BaseDeDonnees {
    private String nom;
    private Map<String, Tables> tables;
    private int compteurSerial;

    public BaseDeDonnees(String nom){
        this.nom = nom;
        this.tables = new HashMap<>();
        this.compteurSerial = 0;
    }


    /**
     * Donne le nom de la base de donnée
     * @return Le nom de base de donnée
     */
    public String getNom(){
        return this.nom;
    }
    

}