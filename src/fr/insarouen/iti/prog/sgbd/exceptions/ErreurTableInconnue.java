package fr.insarouen.iti.prog.sgbd.exceptions;
import fr.insarouen.iti.prog.sgbd.exceptions.ErreurSGBD;
/**
 *
 * @author Marwan
 */

public class ErreurTableInconnue extends ErreurSGBD{
    
    public ErreurTableInconnue(){
        super();
    }

    public ErreurTableInconnue(String msg){
        super(msg);
    }
}