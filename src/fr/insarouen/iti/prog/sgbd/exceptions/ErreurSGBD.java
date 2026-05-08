package fr.insarouen.iti.prog.sgbd.exceptions;
/**
 * 
 * @author Marwan
 */

public class ErreurSGBD extends Exception{
    
    public ErreurSGBD(){
        super();
    }

    public ErreurSGBD(String msg){
        super(msg);
    }
}