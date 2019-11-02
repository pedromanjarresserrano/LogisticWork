/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CiudadJpaController;
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
import logic.Ciudad;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanCiudad {

    private String nombre;
    private String newnombre;
    private Ciudad ciudad;
    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public BeanCiudad() {
        this.ciudad = new Ciudad();
        //    this.listciudad = new ArrayList<>();
    }

    public String insertar() {
        String mensaje = "Agregado con exito";
        if (getNombre() == null) {
            mensaje = "Fallo al agregar";
        } else {
            if (getNombre().isEmpty()) {
                mensaje = "Digite el nombre de la Ciudad";
            } else {
                getCiudad().setNombre(getNombre().toUpperCase());
                CiudadJpaController cjc = new CiudadJpaController(utx, emf);
                if (cjc.findCiudad(getNombre()).getId() == 0) {
                    try {
                        cjc.create(ciudad);
                        this.nombre = "";
                    } catch (PreexistingEntityException e) {
                        mensaje = "Ya hay un registro con ese nombre";
                    } catch (Exception e) {

                        Logger.getLogger(BeanCiudad.class.getName()).log(Level.SEVERE, null, e);
                    }
                } else {
                    mensaje = "Ya hay un registro con ese nombre";
                }
            }
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
        return "OK";

    }

    public List<Ciudad> getCiudades() {
        return new CiudadJpaController(utx, emf).findCiudadEntities();
    }

    public void eliminar() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getNewnombre() {
        return newnombre;
    }

    public void setNewnombre(String newnombre) {
        this.newnombre = newnombre;
    }

}
