package fr.insarouen.iti.prog.sgbd.data;
import fr.insarouen.iti.prog.sgbd.modele.BaseDeDonnees;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;


/**
 * Implémentation de {@link Enregistreur} par sérialisation Java.
 * <p>
 * Écrit la {@link BaseDeDonnees} puis les conditions de fin dans un
 * {@link ObjectOutputStream}.
 * </p>
 */
public class EnregistreurSerialisation  implements Enregistreur{
    /** Le flux de sortie vers lequel les objets sont sérialisés. */
    private ObjectOutputStream oos;

    /**
     * Construit l'enregistreur.
     *
     * @param oos Le flux de sortie de sérialisation.
     */
    public EnregistreurSerialisation(ObjectOutputStream oos) {
        this.oos = oos; 
    }
    /**
     * Sérialise le monde puis les conditions de fin dans le flux.
     *
     * @param bd          La base de donnée à sauvegarder.
     * @param conditionsDeFin Les conditions de fin à sauvegarder.
     * @throws IOException En cas d'erreur d'écriture.
     */
    @Override
    public void enregistrer(BaseDeDonnees bd, String chemin )throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(chemin))
        this.oos.writeObject(bd);
    }

    
    
}
