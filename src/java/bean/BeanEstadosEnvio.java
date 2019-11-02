/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.EmpleadoJpaController;
import controller.EnvioJpaController;
import controller.EstadoJpaController;
import controller.EstadosEnvioJpaController;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import logic.EstadosEnvio;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanEstadosEnvio {

    private String estado;
    private String id_envio;
    private String empleado;
    private Date fecha;

    private EstadosEnvio estadoenvio;
    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public BeanEstadosEnvio() {
        this.estadoenvio = new EstadosEnvio();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId_envio() {
        return id_envio;
    }

    public void setId_envio(String id_envio) {
        this.id_envio = id_envio;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadosEnvio getEstadoenvio() {
        return estadoenvio;
    }

    public void setEstadoenvio(EstadosEnvio estadoenvio) {
        this.estadoenvio = estadoenvio;
    }

    public void insertar() {
        EnvioJpaController enjc = new EnvioJpaController(utx, emf);
        EstadosEnvioJpaController eejc = new EstadosEnvioJpaController(utx, emf);
        EmpleadoJpaController ejc = new EmpleadoJpaController(utx, emf);
        EstadoJpaController esjc = new EstadoJpaController(utx, emf);
        getEstadoenvio().setFecha(getFecha());
        getEstadoenvio().setIdEmpleado(ejc.findEmpleado(getEmpleado()));
        getEstadoenvio().setIdEnvio(enjc.findEnvio(Long.valueOf(getId_envio())));
        getEstadoenvio().setIdEstado(esjc.findEstado(getEstado()));
        String mensaje = "";

        try {
            eejc.create(estadoenvio);
            mensaje = "Agregado con exito";

        } catch (Exception ex) {
            mensaje = "-----Error al agregar-----\n" + ex;

            Logger.getLogger(BeanEstadosEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

}
