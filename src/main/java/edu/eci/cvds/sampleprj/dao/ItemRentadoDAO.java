package edu.eci.cvds.sampleprj.dao;

import java.util.List;

import edu.eci.cvds.samples.entities.ItemRentado;
/**
 * @author Luis Amaya
 * @author Sebastian Mina
 */
public interface ItemRentadoDAO {
    
    public List<ItemRentado> loadItems() throws PersistenceException;

    public ItemRentado load(int id) throws PersistenceException;
}
