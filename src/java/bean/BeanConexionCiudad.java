/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CiudadJpaController;
import controller.ConexionCiudadJpaController;
import java.math.BigInteger;
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
import logic.ConexionCiudad;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanConexionCiudad {

    private String ciudad_a;
    private String ciudad_b;
    private String distancia;
    private ConexionCiudad conexionCiudad;
    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public String getCiudad_a() {
        return ciudad_a;
    }

    public void setCiudad_a(String ciudad_a) {
        this.ciudad_a = ciudad_a;
    }

    public String getCiudad_b() {
        return ciudad_b;
    }

    public void setCiudad_b(String ciudad_b) {
        this.ciudad_b = ciudad_b;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public BeanConexionCiudad() {
        this.conexionCiudad = new ConexionCiudad();
    }

    public ConexionCiudad getConexionCiudad() {
        return conexionCiudad;
    }

    public void setConexionCiudad(ConexionCiudad conexionCiudad) {
        this.conexionCiudad = conexionCiudad;
    }

    public void insertar() {
        CiudadJpaController cjc = new CiudadJpaController(utx, emf);
        ConexionCiudadJpaController ccjc = new ConexionCiudadJpaController(utx, emf);

        getConexionCiudad().setDistancia(new BigInteger(distancia));
        getConexionCiudad().setIdCiudadA(cjc.findCiudad(ciudad_a));
        getConexionCiudad().setIdCiudadB(cjc.findCiudad(ciudad_b));
        String mensaje = "Agregado con éxito";
        if (ccjc.findConexcionCiudad(getCiudad_a(), getCiudad_b()).getIdConexionCiudad() != 0
                || ccjc.findConexcionCiudad(getCiudad_b(), getCiudad_a()).getIdConexionCiudad() != 0) {
            mensaje = "Conexion ya existe, para el sistema A->B es lo mismo que B->A";
        } else {

            try {
                ccjc.create(conexionCiudad);
            } catch (Exception ex) {
                mensaje = "Error al agregar";
                Logger.getLogger(BeanConexionCiudad.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");

    }

    public List<ConexionCiudad> getConexionesCiudades() {
        ConexionCiudadJpaController ccjc = new ConexionCiudadJpaController(utx, emf);
        return ccjc.findConexcionCiudadEntities();
    }

    public void eliminarConexion() {
        CiudadJpaController cjc = new CiudadJpaController(utx, emf);
        ConexionCiudadJpaController ccjc = new ConexionCiudadJpaController(utx, emf);

        getConexionCiudad().setDistancia(new BigInteger(distancia));
        getConexionCiudad().setIdCiudadA(cjc.findCiudad(ciudad_a));
        getConexionCiudad().setIdCiudadB(cjc.findCiudad(ciudad_b));
        String mensaje = "Elimando con éxito";
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public void select() {
        ConexionCiudadJpaController ccjc = new ConexionCiudadJpaController(utx, emf);
        ConexionCiudad cc = new ConexionCiudad();
        cc = ccjc.findConexcionCiudad(getCiudad_a(), getCiudad_b());
        String mensaje = "";
        if (cc.getIdConexionCiudad() == 0) {
            mensaje = "Conexion no encontrada";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        } else {
            setCiudad_a(cc.getIdCiudadA().getNombre());
            setCiudad_b(cc.getIdCiudadB().getNombre());
            setDistancia(cc.getDistancia().toString());

        }
    }
}
