/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.EstadoJpaController;
import controller.exceptions.PreexistingEntityException;
import java.util.List;
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
import logic.Estado;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanEstado {

    private String nombre;
    private Estado estado;

    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public BeanEstado(String nombre, Estado estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public BeanEstado() {
        this.estado = new Estado();
        this.nombre = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void insertar() {
        getEstado().setNombre(getNombre().toUpperCase());
        String mensaje = "Agregado con exito";

        EstadoJpaController controller = new EstadoJpaController(utx, emf);
        try {
            controller.create(estado);
        } catch (PreexistingEntityException e) {
            mensaje = "Ya hay un registro con ese nombre";
        } catch (Exception e) {
            mensaje = "Fallo al agregar";

            Logger.getLogger(BeanCiudad.class.getName()).log(Level.SEVERE, null, e);
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public List<Estado> getEstados() {
        EstadoJpaController controller = new EstadoJpaController(utx, emf);
        return controller.findEstadoEntities();
    }

}
