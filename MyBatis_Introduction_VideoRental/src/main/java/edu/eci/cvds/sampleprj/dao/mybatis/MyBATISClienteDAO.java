package edu.eci.cvds.sampleprj.dao.mybatis;

import java.util.List;

import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;

/**
 *  @author Luis Amaya
 *  @author Sebastian Mina 
 */
public class MyBATISClienteDAO implements ClienteDAO{

	@Override
	public void saveItemRentadoCliente() throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente load(long id) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> loadClientes() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ItemRentado> loadItemsCliente(long idCliente) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCliente(Cliente c) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vetarCliente(long docu, boolean estado) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}
    
}
