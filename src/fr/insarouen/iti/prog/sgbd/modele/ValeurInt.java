package fr.insarouen.iti.prog.sgbd.modele;
import fr.insarouen.iti.prog.sgbd.modele.Type;
import fr.insarouen.iti.prog.sgbd.modele.Valeur;
import fr.insarouen.iti.prog.sgbd.exceptions.ErreurTypesIncompatible;
/**
 * Valeur de type entier ({@link Type#INT}) stockée dans le SGBD.
 * <p>
 * Encapsule un {@code int} primitif et fournit les opérations définies par
 * {@link Valeur} : accès à la donnée, comparaison avec une autre valeur et
 * vérification de compatibilité de type.
 * </p>
 *
 * @see Type#INT
 * @see Valeur
 */
public class ValeurInt extends Valeur {

    private int valeur;

    /**
     * Construit une valeur entière.
     *
     * @param valeur l'entier à stocker
     */
    public ValeurInt(int valeur) {
        super(Type.INT);
        this.valeur = valeur;
    }

    /**
     * Retourne la donnée entière encapsulée.
     *
     * @return la valeur sous forme d'{@link Integer}
     */
    @Override
    public Integer getDonnee() {
        return this.valeur;
    }

    /**
     * Compare cette valeur à une autre valeur du SGBD.
     *
     * @param autre la valeur à comparer
     * @return -1, zéro ou 1 selon que cette valeur est
     *         inférieure, égale ou supérieure à {@code autre}
     * @throws ErreurTypesIncompatible si {@code autre} n'est pas un {@link ValeurInt}
     */
    public int compareA(Valeur autre) throws ErreurTypesIncompatible {
        if (!(autre instanceof ValeurInt)) {
            throw new ErreurTypesIncompatible(Type.INT, autre.getType());
        }
        ValeurInt autreInt = (ValeurInt) autre;
        return Integer.compare(valeur, autreInt.getDonnee());
    }

    /**
     * Indique si cette valeur est compatible avec le type donné.
     *
     * @param t le type à vérifier
     * @return {@code true} uniquement si {@code t} est {@link Type#INT}
     */
    public boolean estCompatible(Type t) {
        return t == Type.INT || t == Type.SERIAL;
    }
}