/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.report;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import logic.Camiones;
import logic.Cliente;
import logic.Meses;
import logic.Rutas;
import obstinado.staticDAO;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanReport implements Serializable {

    private String promedio;
    private Date fecha_incio;
    private Date fecha_fin;
    private List<Meses> meses;
    private List<Rutas> rutas;
    private static Connection conexion;
    private static Statement sentencia;
    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public List<Rutas> getRutas() throws SQLException {
        return rutas;
    }

    public List<Cliente> getClientes() throws SQLException {
        return staticDAO.consultaCliente(utx, emf);
    }

    public List<Camiones> getCamiones() throws SQLException {
        return staticDAO.consultaCamiones(utx, emf);
    }

    public String getPromedio() {
        return promedio;
    }

    public Date getFecha_incio() {
        return fecha_incio;
    }

    public void setFecha_incio(Date fecha_incio) {
        this.fecha_incio = fecha_incio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public List<Meses> getMeses() {
        return meses;
    }

    public void setMeses(List<Meses> meses) {
        this.meses = meses;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    public void consultaMeses() throws SQLException {

        if (fecha_fin == null || fecha_incio == null) {
            String mensaje = "Seleccione el rango de fechas";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        } else {
            String mensaje = "Generado";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
            this.meses = staticDAO.consultaMeses(fecha_incio, fecha_fin);

        }
    }

    public void consultaRutas() throws SQLException {
        if (fecha_fin == null || fecha_incio == null) {
            String mensaje = "Seleccione el rango de fechas";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        } else {
            String mensaje = "Generado";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
            this.rutas = staticDAO.consultaRutas(utx, emf, fecha_incio, fecha_fin);

        }
    }

    public BeanReport() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        conexion = DriverManager.getConnection("jdbc:mysql://localhost/dbcarga?autoReconnect=true", "root", "");
        sentencia = conexion.createStatement();
        ResultSet result = sentencia.executeQuery("SELECT Avg(a.w) from (SELECT s.id_cliente, COUNT(s.id_cliente) as w from envio as e , solicitud as s WHERE e.id_solicitud = s.id_solicitud group by  s.id_cliente) as a ");
        try {
            result.next();
            this.promedio = result.getString("Avg(a.w)");

        } catch (Exception e) {

        }
    }

}
