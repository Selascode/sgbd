package fr.insarouen.iti.prog.sgbd.exceptions;
import fr.insarouen.iti.prog.sgbd.exceptions.ErreurSGBD;
/**
 *
 * @author Marwan
 */

public class ErreurAttributInconnu extends ErreurSGBD{
    
    public ErreurAttributInconnu(){
        super();
    }

    public ErreurAttributInconnu(String msg){
        super(msg);
    }
}