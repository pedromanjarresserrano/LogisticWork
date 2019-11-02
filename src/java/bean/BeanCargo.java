/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CargoJpaController;
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
import logic.Cargo;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanCargo {

    private String nombre;
    private Cargo cargo;

    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public BeanCargo(String nombre) {
        this.nombre = nombre;
    }

    public BeanCargo() {
        this.cargo = new Cargo();
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void insertar() {

        getCargo().setNombre(getNombre().toUpperCase());
        String mensaje = "Agregado con exito";

        CargoJpaController controller = new CargoJpaController(utx, emf);
        if (controller.findCargo(getNombre()).getIdCargo() != 0) {
            mensaje = "Ya hay un registro con ese nombre";
        } else {
            try {
                controller.create(cargo);
            } catch (PreexistingEntityException e) {
                mensaje = "Ya hay un aire con ese nombre";
            } catch (Exception e) {
                mensaje = "Fallo al agregar";

                Logger.getLogger(BeanCiudad.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public List<Cargo> getCargos() {
        CargoJpaController controller = new CargoJpaController(utx, emf);
        return controller.findCargoEntities();
    }
}
