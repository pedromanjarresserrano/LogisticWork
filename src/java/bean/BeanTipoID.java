/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.TipoidentificacionJpaController;
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
import logic.Tipoidentificacion;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanTipoID {

    private String nombre;
    private Tipoidentificacion tipoidentificacion;

    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public BeanTipoID() {
        this.tipoidentificacion = new Tipoidentificacion();
    }

    public BeanTipoID(String nombre, Tipoidentificacion tipoidentificacion) {
        this.nombre = nombre;
        this.tipoidentificacion = tipoidentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipoidentificacion getTipoidentificacion() {
        return tipoidentificacion;
    }

    public void setTipoidentificacion(Tipoidentificacion tipoidentificacion) {
        this.tipoidentificacion = tipoidentificacion;
    }

    public void insertear() {
        getTipoidentificacion().setNombreTipoIdentificacion(getNombre().toUpperCase());
        String mensaje = "Agregado con exito";
        TipoidentificacionJpaController controller = new TipoidentificacionJpaController(utx, emf);
        if (controller.findTipoidentificacion(getNombre()).getIdTipoidentificacion() != 0) {
            mensaje = "Ya hay un registro con ese nombre";
        } else {
            try {
                controller.create(tipoidentificacion);
            } catch (PreexistingEntityException e) {
                mensaje = "Ya hay un registro con ese nombre";
            } catch (Exception e) {
                mensaje = "Fallo al agregar";

                Logger.getLogger(BeanCiudad.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public List<Tipoidentificacion> getTiposID() {
        return new TipoidentificacionJpaController(utx, emf).findTipoidentificacionEntities();
    }

}
