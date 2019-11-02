/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstinado;

import controller.CamionJpaController;
import controller.CiudadJpaController;
import controller.ClienteJpaController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import logic.Camiones;
import logic.Cliente;
import logic.Meses;
import logic.Rutas;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
public class staticDAO {

    private static Connection conexion;
    private static Statement sentencia;

    private static final String url = "jdbc:mysql://localhost/dbcarga?autoReconnect=true";
    private static final String user = "root";
    private static final String pass = "";

    public static List<Cliente> consultaCliente(UserTransaction utx, EntityManagerFactory emf) throws SQLException {
        conexion = DriverManager.getConnection(url, user, pass);
        sentencia = conexion.createStatement();
        List<Cliente> cs = new ArrayList<>();
        ClienteJpaController cjc = new ClienteJpaController(utx, emf);
        ResultSet rs = sentencia.executeQuery("SELECT  cc.id_cliente FROM cliente as cc WHERE cc.id_cliente =(SELECT a.c from (SELECT s.id_cliente as c, COUNT(s.id_cliente) as w  from envio as e , solicitud as s WHERE e.id_solicitud = s.id_solicitud group by  s.id_cliente) as a WHERE w > (SELECT Avg(a.w) from (SELECT s.id_cliente as c, COUNT(s.id_cliente) as w  from envio as e , solicitud as s WHERE e.id_solicitud = s.id_solicitud group by  s.id_cliente) as a)) GROUP BY cc.id_cliente ;");

        while (rs.next()) {
            Cliente c = cjc.findCliente(new Long(rs.getString("id_cliente")));
            if (cs.indexOf(c) == -1) {
                cs.add(c);
            }

        }
        return cs;
    }

    public static void close() throws SQLException {
        conexion.close();
        sentencia.close();
    }

    public static List<Rutas> consultaRutas(UserTransaction utx, EntityManagerFactory emf, Date inicio , Date fin) throws SQLException {
        conexion = DriverManager.getConnection(url, user, pass);
        sentencia = conexion.createStatement();
        List<Rutas> cs = new ArrayList<>();
        CiudadJpaController cjc = new CiudadJpaController(utx, emf);
        ResultSet rs = sentencia.executeQuery("SELECT ss.origen, ss.destino, COUNT(Ruta) "
                + "FROM ( SELECT s.id_ciudad_origen AS origen, s.id_ciudad_destino AS destino, CONCAT( s.id_ciudad_origen, ' ', s.id_ciudad_destino )"
                + " AS Ruta FROM solicitud AS s, envio AS e WHERE s.id_solicitud = e.id_solicitud AND"
                + " e.fecha_recogida BETWEEN '"+new java.sql.Date(inicio.getTime())+"' AND '"+new java.sql.Date(fin.getTime())+"' ) AS ss GROUP BY ss.origen, ss.destino;");

        while (rs.next()) {
            Rutas r = new Rutas();
            r.setCiudad_origen(cjc.findCiudad(rs.getLong(1)));
            r.setCiudad_destino(cjc.findCiudad(rs.getLong(2)));
            r.setCantidad(rs.getString(3));
            cs.add(r);

        }
        return cs;
    }

    public static List<Camiones> consultaCamiones(UserTransaction utx, EntityManagerFactory emf) throws SQLException {
        conexion = DriverManager.getConnection(url, user, pass);
        sentencia = conexion.createStatement();
        List<Camiones> cs = new ArrayList<>();
        CamionJpaController cjc = new CamionJpaController(utx, emf);
        ResultSet rs = sentencia.executeQuery("SELECT e.id_camion, COUNT(e.id_camion) FROM envio as e GROUP BY e.id_camion ;");

        while (rs.next()) {
            Camiones r = new Camiones();
            r.setCamion(cjc.findCamion(rs.getLong(1)));
            r.setCantidad(rs.getString(2));
            cs.add(r);
        }
        return cs;
    }

    public static List<Meses> consultaMeses(Date inicio, Date fin) throws SQLException {
        conexion = DriverManager.getConnection(url, user, pass);
        sentencia = conexion.createStatement();
        List<Meses> cs = new ArrayList<>();

        ResultSet rs = sentencia.executeQuery("SELECT YEAR(e.fecha_recogida),MONTH(e.fecha_recogida), COUNT(MONTH(e.fecha_recogida)) as cuenta "
                + "FROM envio as e "
                + "WHERE e.fecha_recogida > '" + new java.sql.Date(inicio.getTime()).toString() + "' "
                + "AND e.fecha_recogida < '" + new java.sql.Date(fin.getTime()).toString()
                + "' GROUP BY MONTH(e.fecha_recogida) ORDER by cuenta DESC ;");

        while (rs.next()) {
            Meses r = new Meses();
            r.setAño(rs.getString(1));
            switch (rs.getString(2)) {
                case "1": {
                    r.setMes("Enero");
                    break;
                }
                case "2": {
                    r.setMes("Febrero");
                    break;
                }
                case "3": {
                    r.setMes("Marzo");
                    break;
                }
                case "4": {
                    r.setMes("Abril");
                    break;
                }
                case "5": {
                    r.setMes("Mayo");
                    break;
                }
                case "6": {
                    r.setMes("Junio");
                    break;
                }
                case "7": {
                    r.setMes("Julio");
                    break;
                }
                case "8": {
                    r.setMes("Agosto");
                    break;
                }
                case "9": {
                    r.setMes("Septiembre");
                    break;
                }
                case "10": {
                    r.setMes("Octubre");
                    break;
                }
                case "11": {
                    r.setMes("Noviembre");
                    break;
                }
                case "12": {
                    r.setMes("Diciembre");
                    break;
                }
            }

            r.setCantidad(rs.getString(3));
            cs.add(r);
        }

        return cs;
    }

}
