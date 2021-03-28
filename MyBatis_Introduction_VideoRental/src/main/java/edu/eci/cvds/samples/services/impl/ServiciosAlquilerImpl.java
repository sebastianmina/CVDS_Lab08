package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.mybatis.guice.transactional.Transactional;

import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

    @Inject
    private ItemDAO itemDAO;

    @Inject 
    private ClienteDAO clienteDAO;

    @Inject
    private TipoItemDAO tipoItemDAO;

    @Override
    public long valorMultaRetrasoxDia(int itemId) throws ExcepcionServiciosAlquiler {
        try{
            return itemDAO.load(itemId).getTarifaxDia();
        }catch(Exception e){
            throw new ExcepcionServiciosAlquiler("Error al obtener la tarifa x dia del item: "+itemId,e);
        }
        
    }

    @Override
    public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
        try {
            return clienteDAO.load(docu);
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el cliente",e);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
        try {
            return clienteDAO.loadItemsCliente(idcliente);
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los items del cliente",e);
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
        try {
            return clienteDAO.loadClientes();
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los clientes",e);
        }
    }

    @Override
    public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return itemDAO.load(id);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler{
        try {
            return itemDAO.consultarItemsDisponibles();
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los items disponibles", e);
        }
    }

    @Override
    public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
        try {
            List<Cliente> clientes = consultarClientes();
            for(int i=0 ; i<clientes.size() ; i++){
                ArrayList<ItemRentado> rentados = clientes.get(i).getRentados();
                for(int j=0 ; j<rentados.size() ; j++){
                    if(rentados.get(j).getItem() != null){
                        if(rentados.get(j).getItem().getId() == iditem){
                            long diasRetraso = ChronoUnit.DAYS.between(rentados.get(j).getFechafinrenta().toLocalDate(), fechaDevolucion.toLocalDate());
                            if(diasRetraso <= 0){
                                return 0;
                            }
                            return diasRetraso * valorMultaRetrasoxDia(rentados.get(j).getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar multa de item con id: "+iditem, e);
        }
		return iditem;
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return tipoItemDAO.load(id);
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el tipo de item con id: "+ id, e);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
        try {
            return tipoItemDAO.loadItems();
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los tipos de items", e);
        }
    }

    @Override
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
        try {
            if(clienteDAO.load(docu)==null){
                throw new ExcepcionServiciosAlquiler("El cliente no existe / el cliente es nulo");
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, numdias);
            clienteDAO.saveItemRentadoCliente(docu, item.getId(), date, new java.sql.Date(calendar.getTime().getTime()));
            
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al agregar item rentado al cliente con id: "+docu, e);
        }
    }
    @Transactional
    @Override
    public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
        try {
            clienteDAO.addCliente(c);
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al registrar el cliente nuevo",e);
        }
    }

    @Override
    public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
        try {
            return numdias * itemDAO.load(iditem).getTarifaxDia();
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el costo de alquiler del item: "+iditem, e);
        }
    }
    @Transactional
    @Override
    public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
        try {
            itemDAO.actualizarTarifaItem(id, tarifa);
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al actualizar la tarifa del item: "+id+" con la nueva tarifa: "+tarifa, e);
        }
    }
    @Transactional
    @Override
    public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        try {
            itemDAO.save(i);
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al registrar el nuevo item", e);
        }
    }

    @Override
    public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
        try {
            if(clienteDAO.load(docu) == null){
                throw new ExcepcionServiciosAlquiler("El cliente ingresado con el id: "+docu+" no existe / es nulo");
            }
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al vetar el cliente ingresado con el id: "+docu, e);
        }
    }
}