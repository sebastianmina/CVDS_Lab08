package edu.eci.cvds.sampleprj.dao;
/**
 * @author Luis Amaya
 * @author Sebastian Mina
 */
public class PersistenceException extends Exception{
    private static final long serialVersionUID = 1L;

    public PersistenceException(String message, Exception e){
        super(message+e.toString());
    }
    
}
