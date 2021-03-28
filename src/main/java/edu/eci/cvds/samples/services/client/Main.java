package edu.eci.cvds.samples.services.client;

import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import edu.eci.cvds.samples.services.impl.ServiciosAlquilerItemsStub;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws SQLException, ExcepcionServiciosAlquiler, ParseException{
        System.out.println(ServiciosAlquilerItemsStub.getInstance().consultarCliente(1026585664));
    }
}
