/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.samples.services.client;



import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemRentadoMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
/**
 *
 * @author hcadavid
 */
public class MyBatisExample {

    /**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     * @throws PersistenceException
     */
    public static void main(String args[]) throws SQLException, PersistenceException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();

        SqlSession sqlss = sessionfact.openSession();

        ClienteMapper cm = sqlss.getMapper(ClienteMapper.class);
        ItemMapper im = sqlss.getMapper(ItemMapper.class);
        TipoItemMapper tim = sqlss.getMapper(TipoItemMapper.class);
        ItemRentadoMapper irm = sqlss.getMapper(ItemRentadoMapper.class);
        System.out.println("Consultar Clientes PA");
        System.out.println("-----------------------");
        System.out.println(cm.consultarClientes());
        System.out.println("-----------------------");
        System.out.println("Consultar Clientes ID");
        System.out.println(cm.consultarCliente(5498));
        System.out.println("-----------------------");
        System.out.println("Consultar Items Pa");
        System.out.println(im.consultarItems());
        System.out.println("-----------------------");
        System.out.println("Consultar Item con ID 1234");
        System.out.println(im.consultarItem(1234));
        System.out.println("-----------------------");
        System.out.println("Consultar todos los items rentados");
        System.out.println(irm.consultarItemsRentados());
        System.out.println("-----------------------");
        System.out.println("Consultar Item Rentado con id 2132738");
        System.out.println(irm.consultarItemRentado(2132738));
        System.out.println("-----------------------");
        System.out.println("Consultar todos los tipos de items");
        System.out.println(tim.getTiposItems());
        System.out.println("-----------------------");
        System.out.println("Consultar tipo de item con Id 90");
        System.out.println(tim.getTipoItem(90));
        System.out.println("-----------------------");
        try {
            cm.agregarItemRentadoACliente(4, 2, new SimpleDateFormat("yyyy-MM-dd").parse("2021-03-17"), new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-17"));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            Item item = new Item(new TipoItem(4, "Videojogos"), 2160802, "test", "description", new SimpleDateFormat("yyyy-MM-dd").parse("2021-03-17"),(long)9000,"test","test");
            im.insertarItem(item);
        } catch (Exception e) {
            //TODO: handle exception
        }
        sqlss.commit();
        sqlss.close();

    }

 //mvn exec:java -Dexec.mainClass="edu.eci.cvds.sampleprj.jdbc.example.JDBCExample"
}
