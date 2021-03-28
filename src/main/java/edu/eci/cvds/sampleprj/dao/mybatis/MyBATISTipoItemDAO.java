package edu.eci.cvds.sampleprj.dao.mybatis;

import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;

/**
 * @author Luis Amaya
 * @author Sebastian Mina
 */
public class MyBATISTipoItemDAO implements TipoItemDAO{
	@Inject
	private TipoItemMapper tipoItemMapper;

	@Override
	public List<TipoItem> loadItems() throws PersistenceException {
		try {
			return tipoItemMapper.getTiposItems();
		} catch (Exception e) {
			throw new PersistenceException("Error al consultar los tipos de items", e);
		}
	}

	@Override
	public TipoItem load(int id) throws PersistenceException {
		try{
			return tipoItemMapper.getTipoItem(id);
		}catch(Exception e){
			throw new PersistenceException("Error al consultar el item con el id: "+id, e);
		}
	}

	@Override
	public void save(TipoItem tipoItem) throws PersistenceException {
		try{
			tipoItemMapper.addTipoItem(tipoItem);
		}catch(Exception e){
			throw new PersistenceException("Error al agregar el nuevo tipo de item", e);
		}
	}
    
}
