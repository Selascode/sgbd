package fr.insarouen.iti.prog.sgbd.exceptions;
import fr.insarouen.iti.prog.sgbd.exceptions.ErreurSGBD;
/**
 *
 * @author Marwan
 */

public class ErreurTypesIncompatible extends ErreurSGBD{
    
    public ErreurTypesIncompatible(){
        super();
    }

    public ErreurTypesIncompatible(String msg){
        super(msg);
    }
}