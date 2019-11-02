/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CargoJpaController;
import controller.CiudadJpaController;
import controller.ClienteJpaController;
import controller.EnvioJpaController;
import controller.SolicitudJpaController;
import controller.TipoidentificacionJpaController;
import controller.exceptions.PreexistingEntityException;
import java.util.Date;
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
import logic.Cliente;
import logic.Envio;
import logic.Solicitud;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanSolicitud {

    private String tipo_id;
    private String id;
    private String nombre;
    private String telefono;
    private String ciudad;
    private String direccion;
    private Cliente cliente;
    private String pesomercancia;
    private String volumenmercancia;
    private Date creacion;
    private String placa_camion;
    private Date recogida;
    private Date entregado;
    private String id_solicitud;
    private String id_envio;
    private String id_envio_select;
    private Solicitud solicitud;
    private Envio envio;
    private Camion camion;
    private String ciudadorigen;
    private String ciudaddestino;

    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public String getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(String tipo_id) {
        this.tipo_id = tipo_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getPesomercancia() {
        return pesomercancia;
    }

    public void setPesomercancia(String pesomercancia) {
        this.pesomercancia = pesomercancia;
    }

    public String getVolumenmercancia() {
        return volumenmercancia;
    }

    public void setVolumenmercancia(String volumenmercancia) {
        this.volumenmercancia = volumenmercancia;
    }

    public String getPlaca_camion() {
        return placa_camion;
    }

    public void setPlaca_camion(String placa_camion) {
        this.placa_camion = placa_camion;
    }

    public Date getRecogida() {
        return recogida;
    }

    public void setRecogida(Date recogida) {
        this.recogida = recogida;
    }

    public Date getEntregado() {
        return entregado;
    }

    public void setEntregado(Date entregado) {
        this.entregado = entregado;
    }

    public String getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getId_envio() {
        return id_envio;
    }

    public void setId_envio(String id_envio) {
        this.id_envio = id_envio;
    }

    public String getId_envio_select() {
        return id_envio_select;
    }

    public void setId_envio_select(String id_envio_select) {
        this.id_envio_select = id_envio_select;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public String getCiudadorigen() {
        return ciudadorigen;
    }

    public void setCiudadorigen(String ciudadorigen) {
        this.ciudadorigen = ciudadorigen;
    }

    public String getCiudaddestino() {
        return ciudaddestino;
    }

    public void setCiudaddestino(String ciudaddestino) {
        this.ciudaddestino = ciudaddestino;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void create_Solicitud_Envio() {
        String mensaje = "";
        if (getSolicitud() == null) {
            mensaje = "Fallo al crear envio";

        } else {
            if (getSolicitud().getIdSolicitud() == 0) {
                mensaje = "Primero busque una solicitud";
            } else {
                getEnvio().setFechaRecogida(getRecogida());
                getEnvio().setIdCamion(getCamion());
                getEnvio().setIdSolicitud(getSolicitud());
                EnvioJpaController ejc = new EnvioJpaController(utx, emf);
                getEnvio().setIdEnvio(new Long(ejc.getEnvioCount() + 1));
                SolicitudJpaController sjc = new SolicitudJpaController(utx, emf);
                try {

                    mensaje = "Agregado con exito";
                } catch (Exception e) {
                    mensaje = "Fallo al agregar";

                    Logger.getLogger(BeanCiudad.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        callBack(mensaje);

    }

    public void select() {
        String mensaje = "";

        if (getId_solicitud() == null) {
            mensaje = "Digite numero de solicitud";
            callBack(mensaje);
        } else {
            setId_solicitud(getId_solicitud().trim());
            if (getId_solicitud().isEmpty()) {
                mensaje = "Digite numero de solicitud";
                callBack(mensaje);
            } else {
                SolicitudJpaController sjc = new SolicitudJpaController(utx, emf);
                try {
                    setSolicitud(sjc.findSolicitudForTransport(Long.valueOf(getId_solicitud())));
                    if (getSolicitud().getIdSolicitud() == 0) {
                        mensaje = "Solicitud no encontrada";
                        callBack(mensaje);
                    } else {
                        setNombre(getSolicitud().getIdCliente().getNombre());
                        setDireccion(getSolicitud().getIdCliente().getDireccion());
                        setTipo_id(getSolicitud().getIdCliente().getIdTipoidentificacion().getNombreTipoIdentificacion());
                        setId(getSolicitud().getIdCliente().getIdentificacion());
                        setTelefono(getSolicitud().getIdCliente().getTelefono());
                        setCiudaddestino(getSolicitud().getIdCiudadDestino().getNombre());
                        setCiudadorigen(getSolicitud().getIdCiudadOrigen().getNombre());
                        setPesomercancia(getSolicitud().getPesoMercancia());
                        setVolumenmercancia(getSolicitud().getVolumenMercancia());
                        setCreacion(getSolicitud().getFecha());
                        setCiudad(getSolicitud().getIdCliente().getIdCiudad().getNombre());
                    }
                } catch (Exception e) {
                    mensaje = "Solicitud no encontrada";
                    callBack(mensaje);
                }

            }

        }

    }

    public List<Solicitud> getSolicitudes() {
        return new SolicitudJpaController(utx, emf).findSolicitudEntities();
    }

    private void callBack(String mensaje) {
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }
}
