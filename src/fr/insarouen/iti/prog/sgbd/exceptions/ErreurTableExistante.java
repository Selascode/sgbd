package fr.insarouen.iti.prog.sgbd.exceptions;
import fr.insarouen.iti.prog.sgbd.exceptions.ErreurSGBD;
/**
 *
 * @author Marwan
 */

public class ErreurTableExistante extends ErreurSGBD{
    
    public ErreurTableExistante(){
        super();
    }

    public ErreurTableExistante(String msg){
        super(msg);
    }
}