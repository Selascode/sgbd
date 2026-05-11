package fr.insarouen.iti.prog.sgbd.modele;

import fr.insarouen.iti.prog.sgbd.exceptions.TableExistanteException;
import fr.insarouen.iti.prog.sgbd.exceptions.TableInconnueException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représentation de la base de données
 * Gestions de tables contenant des attributs et des tuples
 *
 * @author S.Maclean
 */
public class BaseDeDonnees {

    private String nom;
    private Map<String, Table> tables;
    private int compteurSerial;

    /**
     * Creation base de données vide.
     *
     * @param nom le nom de la base de données
     */
    public BaseDeDonnees(String nom) {
        this.nom = nom;
        this.tables = new HashMap<>();
        this.compteurSerial = 0;
    }

    /**
     * Vérifie si une table existe dans la base.
     *
     * @param nom le nom de la table
     * @return true si la table existe, false sinon
     */
    public boolean tableExiste(String nom) {
        return this.tables.containsKey(nom.toLowerCase());
    }

    /**
     * Crée une nouvelle table dans la base.
     *
     * @param t la table à créer
     * @throws TableExistanteException si une table de même nom existe déjà
     */
    public void creerTable(Table t) throws TableExistanteException {
        if (this.tableExiste(t.getNom())) {
            throw new TableExistanteException(t.getNom());
        }
        this.tables.put(t.getNom().toLowerCase(), t);
    }

    /**
     * Supprime une table de la base.
     *
     * @param nom le nom de la table à supprimer
     * @throws TableInconnueException si la table n'existe pas
     */
    public void supprimerTable(String nom) throws TableInconnueException {
        if (!this.tableExiste(nom)) {
            throw new TableInconnueException(nom);
        }
        this.tables.remove(nom.toLowerCase());
    }

    /**
     * Retourne une table par son nom.
     *
     * @param nom le nom de la table
     * @return la table correspondante
     * @throws TableInconnueException si la table n'existe pas
     */
    public Table getTable(String nom) throws TableInconnueException {
        if (!this.tableExiste(nom)) {
            throw new TableInconnueException(nom);
        }
        return this.tables.get(nom.toLowerCase());
    }

    /**
     * Retourne la liste des noms de toutes les tables.
     *
     * @return liste non modifiable des noms de tables
     */
    public List<String> listerTables() {
        return Collections.unmodifiableList(new ArrayList<>(this.tables.keySet()));
    }

    /**
     * Incrémente et retourne le prochain identifiant SERIAL global.
     * Le compteur est partagé entre toutes les tables.
     *
     * @return le prochain entier SERIAL disponible
     */
    public int prochainSerial() {
        this.compteurSerial++;
        return this.compteurSerial;
    }

    /**
     * Retourne le nom de la base de données.
     *
     * @return le nom
     */
    public String getNom() {
        return this.nom;
    }
}