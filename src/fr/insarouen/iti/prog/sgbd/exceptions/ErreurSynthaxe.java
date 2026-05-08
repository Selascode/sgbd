package fr.insarouen.iti.prog.sgbd.exceptions;
import fr.insarouen.iti.prog.sgbd.exceptions.ErreurSGBD;
/**
 *
 * @author Marwan
 */

public class ErreurSynthaxe extends ErreurSGBD{
    
    public ErreurSynthaxe(){
        super();
    }

    public ErreurSynthaxe(String msg){
        super(msg);
    }
}