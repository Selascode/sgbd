package fr.insarouen.iti.prog.sgbd.data;

import fr.insarouen.iti.prog.sgbd.modele.BaseDeDonnees;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;


public class LecteurSerialisation implements Lecteur {

    /**
     * Construit le lecteur.
     *
     * @param ois Le flux d'entrée de sérialisation.
     */
    private ObjectOutputStream ois;

    public LecteurSerialisation(ObjectOutputStream ois) {
        this.ois = ois; 
    }
    /**
     * Renvoie la base chargé depuis la source de données.
     *
     * @return La {@link BaseDeDonnees} chargé.
     * @throws IOException            En cas d'erreur de lecture.
     * @throws ClassNotFoundException Si une classe sérialisée est introuvable.
     */
    @Override
    public BaseDeDonnees lire(String chemin) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))
        return (BaseDeDonnees) ois.readObject(); 
    };



}