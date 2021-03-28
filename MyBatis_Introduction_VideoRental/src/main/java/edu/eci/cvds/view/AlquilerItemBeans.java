package edu.eci.cvds.view;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.google.inject.Inject;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;

@ManagedBean(name = "AlquilerItem")
@ApplicationScoped
public class AlquilerItemBeans extends BasePageBean{
    @Inject
    private ServiciosAlquiler serviciosAlquiler;
    private Cliente cliente;
    private long costo;

     /**
      * Este metodo registra al nuevo cliente
      * @param nombre nombre del nuevo cliente
      * @param documeto documento de identidad del nuevo cliente
      * @param telefono telefono del nuevo cliente
      * @param direccion direccion del nuevo cliente
      * @param email email del nuevo cliente
      * 
      */
    public void registrar(String nombre, long documento, String telefono, String direccion, String email) throws ExcepcionServiciosAlquiler{
        try {
            Cliente newCliente = new Cliente(nombre,documento,telefono,direccion,email);
            serviciosAlquiler.registrarCliente(newCliente);
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler(e.toString());
        }
    }

    /**
     * Este metodo consulta todos los cliente por medio del metodo de serviciosAlquiler
     * @return todos los clientes de la base de datos
     * @throws ExcepcionServiciosAlquiler
     */
    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler{
        try {
            return serviciosAlquiler.consultarClientes();   
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar todos los clientes, OPS!");
        }
    }

    /**
     * Este metodo retorna el costo del alquiler
     * @return un long el cual es el costo de alquilar el item
     */
    public long getCosto(){
        return costo;
    }

    /**
     * Este metodo nos consulta los items del cliente seleccionado
     */
    public List<ItemRentado> consultarItemsCliente(long documento) throws ExcepcionServiciosAlquiler{
        try {
            return serviciosAlquiler.consultarItemsCliente(documento);
        } catch (Exception e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los items del cliente con documeto "+documento+", OPS!");
        }
    }

    /**
     * Este metodo retorna el cliente 
     */
    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
    * Este método registra al cliente.
    * @param idItem
    * @param numdias
    */
    public void registrarAlquiler(int idItem , int numdias) throws ExcepcionServiciosAlquiler {
        Item item = serviciosAlquiler.consultarItem(idItem);
        serviciosAlquiler.registrarAlquilerCliente(new Date(System.currentTimeMillis()),cliente.getDocumento(),item,numdias);
    }

    /**
    * Este método consulta la multa.
    * @param idItem
    */

    public long consultarMulta(int iditem) throws ExcepcionServiciosAlquiler {
        try {
            return serviciosAlquiler.consultarMultaAlquiler(iditem, new Date(System.currentTimeMillis()));
        } catch (ExcepcionServiciosAlquiler excepcionServiciosAlquiler) {
            throw new ExcepcionServiciosAlquiler("Error al consultar multa alquiler");
        }
    }

    /**
    * Este método consulta los costos.
    * @param idItem
    * @param numdias
    */
    public void consultarCosto(int iditem , int numdias) throws ExcepcionServiciosAlquiler {
        try {
            this.costo = serviciosAlquiler.consultarCostoAlquiler(iditem, numdias);
        } catch (ExcepcionServiciosAlquiler excepcionServiciosAlquiler) {
            throw new ExcepcionServiciosAlquiler("Error al consultar costo alquiler");
        }
    }
}
