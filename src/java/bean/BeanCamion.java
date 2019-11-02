/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CamionJpaController;
import controller.CiudadJpaController;
import controller.EmpleadoJpaController;
import controller.exceptions.PreexistingEntityException;
import java.math.BigInteger;
import java.util.ArrayList;
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
import logic.Camion;
import logic.Empleado;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanCamion {

    private String placa;
    private String disponible;
    private String ciudad;
    private String modelo;
    private String peso_capacidad;
    private String volumen_capacidad;
    private String empleado;
    private Camion camion;
    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public String getPlaca() {
        return placa;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPeso_capacidad() {
        return peso_capacidad;
    }

    public void setPeso_capacidad(String peso_capacidad) {
        this.peso_capacidad = peso_capacidad;
    }

    public String getVolumen_capacidad() {
        return volumen_capacidad;
    }

    public void setVolumen_capacidad(String volumen_capacidad) {
        this.volumen_capacidad = volumen_capacidad;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public UserTransaction getUtx() {
        return utx;
    }

    public void setUtx(UserTransaction utx) {
        this.utx = utx;
    }

    public BeanCamion() {
        this.camion = new Camion();
    }

    public void insetar() {
        String mensaje = "";
        CiudadJpaController cjc = new CiudadJpaController(utx, emf);
        CamionJpaController cajc = new CamionJpaController(utx, emf);
        EmpleadoJpaController ejc = new EmpleadoJpaController(utx, emf);
        if (cajc.findCamion(getPlaca()).getIdCamion() != 0) {
            mensaje = "Ya hay un registro de esa placa";
        } else {
            getCamion().setDisponible(Boolean.TRUE);
            getCamion().setIdCiudadActual(cjc.findCiudad(getCiudad()));
            for (Empleado c : ejc.findEmpleadoEntities()) {
                if (c.toString() == null ? getEmpleado() == null : c.toString().equals(getEmpleado())) {
                    getCamion().setIdEmpleado(c);
                }
            }

            getCamion().setModelo(getModelo());
            getCamion().setPlaca(getPlaca());
            getCamion().setPesoCapacidad(BigInteger.valueOf(Long.valueOf(getPeso_capacidad())));
            getCamion().setVolumenCapacidad(BigInteger.valueOf(Long.valueOf(getVolumen_capacidad())));
            try {
                cajc.create(camion);
                mensaje = "Agregado con exito";
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.execute("limpiarForm()");
            } catch (PreexistingEntityException e) {
                Logger.getLogger(BeanEmpleado.class.getName()).log(Level.INFO, null, e);
                Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, e);

            } catch (Exception ex) {
                mensaje = "Fallo al agregar";
                Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public List<Camion> getCamiones() {
        CamionJpaController cjc = new CamionJpaController(utx, emf);
        return cjc.findCamionEntities();
    }

    public void update() {
        String mensaje = "";
        CiudadJpaController cjc = new CiudadJpaController(utx, emf);
        CamionJpaController cajc = new CamionJpaController(utx, emf);
        EmpleadoJpaController ejc = new EmpleadoJpaController(utx, emf);

        getCamion().setDisponible(Boolean.TRUE);
        getCamion().setIdCiudadActual(cjc.findCiudad(getCiudad()));
        for (Empleado c : ejc.findEmpleadoEntities()) {
            if (c.toString() == null ? getEmpleado() == null : c.toString().equals(getEmpleado())) {
                getCamion().setIdEmpleado(c);
            }
        }
        getCamion().setIdEmpleado(ejc.findEmpleado(getEmpleado()));
        getCamion().setModelo(getModelo());
        getCamion().setPlaca(getPlaca());
        try {
            getCamion().setPesoCapacidad(BigInteger.valueOf(Long.valueOf(getPeso_capacidad())));
            getCamion().setVolumenCapacidad(BigInteger.valueOf(Long.valueOf(getVolumen_capacidad())));
        } catch (Exception e) {
            mensaje = "Fallo al Actualizar (Datos incorrecto)";

        }

        try {
            cajc.edit(camion);
            mensaje = "Datos actualizados con exito";
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("limpiarForm()");
        } catch (PreexistingEntityException e) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.INFO, null, e);
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, e);
            mensaje = "Ya hay un registro con ese codigo";
        } catch (Exception ex) {
            mensaje = "Fallo al agregar";
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public void select() {
        CamionJpaController cajc = new CamionJpaController(utx, emf);
        setCamion(cajc.findCamion(getPlaca()));
        String mensaje = "";
        if (getCamion().getIdCamion() == 0) {
            mensaje = "Camion no encontrado";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        } else {
            setCiudad(getCamion().getIdCiudadActual().getNombre());
            if (getCamion().getDisponible()) {
                setDisponible("Si");
            } else {
                setDisponible("No");
            }
            setEmpleado(getCamion().getIdEmpleado().toString());
            setModelo(getCamion().getModelo());
            setPeso_capacidad(getCamion().getPesoCapacidad().toString());
            setVolumen_capacidad(getCamion().getVolumenCapacidad().toString());

        }

    }

    public List<Camion> getCamionesDisponibles() {
        CamionJpaController cjc = new CamionJpaController(utx, emf);
        List<Camion> cd = new ArrayList<>();
        for (Camion c : cjc.findCamionEntities()) {
            if (c.getDisponible()) {
                cd.add(c);
            }
        }
        return cd;
    }
}
