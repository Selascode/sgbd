package fr.insarouen.iti.prog.sgbd.exceptions;
import fr.insarouen.iti.prog.sgbd.exceptions.ErreurSGBD;
/**
 *
 * @author Marwan
 */

public class ErreurAmbiguiteAttribut extends ErreurSGBD{
    
    public ErreurAmbiguiteAttribut(){
        super();
    }

    public ErreurAmbiguiteAttribut(String msg){
        super(msg);
    }
}