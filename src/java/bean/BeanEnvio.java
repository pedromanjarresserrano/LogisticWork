/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CamionJpaController;
import controller.CiudadJpaController;
import controller.ClienteJpaController;
import controller.EmpleadoJpaController;
import controller.EnvioJpaController;
import controller.EstadoJpaController;
import controller.EstadosEnvioJpaController;
import controller.SolicitudJpaController;
import controller.TipoidentificacionJpaController;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import logic.Camion;
import logic.Cliente;
import logic.Envio;
import logic.Estado;
import logic.EstadosEnvio;
import logic.Solicitud;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanEnvio {

    private String new_estado;

    private String tipo_id;
    private String id;
    private String nombre;
    private String telefono;
    private String ciudad;
    private String direccion;
    private Cliente cliente;
    private String pesomercancia;
    private String volumenmercancia;

    private String placa_camion;
    private Date recogida;
    private Date entregado;
    private String id_solicitud;
    private String id_envio;
    private String id_envio_select;
    private Envio envio;
    private Camion camion;
    private String ciudadorigen;
    private String ciudaddestino;

    private Solicitud solicitud;
    @ManagedProperty(value = "#{beanSession}")
    private BeanSession sessionBean;

    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public BeanEnvio() {
        this.solicitud = new Solicitud();
        this.cliente = new Cliente();
        this.recogida = new Date();
        this.entregado = new Date();
        this.camion = new Camion();
        this.envio = new Envio();

    }

    public String getNew_estado() {
        return new_estado;
    }

    public void setNew_estado(String new_estado) {
        this.new_estado = new_estado;
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

    public String getId_envio_select() {
        return id_envio_select;
    }

    public void setId_envio_select(String id_envio_select) {
        this.id_envio_select = id_envio_select;
    }

    public void setPlaca_camion(String placa_camion) {
        this.placa_camion = placa_camion;
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

    public Date getRecogida() {
        return recogida;
    }

    public void setRecogida(Date recogida) {
        this.recogida = recogida;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
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
        EnvioJpaController enjc = new EnvioJpaController(utx, emf);
        this.id_envio = String.valueOf(enjc.getEnvioCount() + 1);
        return id_envio;
    }

    public void setId_envio(String id_envio) {
        this.id_envio = id_envio;
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

    public BeanSession getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(BeanSession sessionBean) {
        this.sessionBean = sessionBean;
    }

    public void insertar() throws Exception {
        String mensaje = "";
        SolicitudJpaController sjc = new SolicitudJpaController(utx, emf);
        CamionJpaController cajc = new CamionJpaController(utx, emf);
        EnvioJpaController enjc = new EnvioJpaController(utx, emf);
        CiudadJpaController cjc = new CiudadJpaController(utx, emf);
        ClienteJpaController cljc = new ClienteJpaController(utx, emf);
        TipoidentificacionJpaController tjc = new TipoidentificacionJpaController(utx, emf);

        if (camion == null || camion.getIdCamion() == 0) {
            mensaje = "Selecione un camion";
        } else {
            if (clienteVacio()) {
                mensaje = "Llene los datos del Cliente";
            } else {
                if (mercanciaVacia()) {
                    mensaje = "Llene los datos de la Mercancia a Transportar";
                } else {
                    if (getCiudadorigen() == null ? getCiudaddestino() == null : getCiudadorigen().equals(getCiudaddestino())) {
                        mensaje = "No se hacen transportes dentro de una ciudad";
                    } else {
                        try {
                            getEnvio().setFechaEntrega(null);
                            getEnvio().setFechaRecogida(getRecogida());
                            getEnvio().setIdCamion(getCamion());
                            getSolicitud().setIdSolicitud(new Long(sjc.getSolicitudCount() + 1));
                            getSolicitud().setAceptada(Boolean.TRUE);
                            getSolicitud().setFecha(getRecogida());
                            getSolicitud().setIdCiudadDestino(cjc.findCiudad(getCiudaddestino()));
                            getSolicitud().setIdCiudadOrigen(cjc.findCiudad(getCiudadorigen()));
                            if (cljc.findCliente(tjc.findTipoidentificacion(getTipo_id()), getId()).getIdCliente() == 0) {
                                Cliente c = new Cliente();
                                c.setIdTipoidentificacion(tjc.findTipoidentificacion(getTipo_id()));
                                c.setTelefono(getTelefono());
                                c.setIdCiudad(cjc.findCiudad(getCiudad()));
                                c.setNombre(getNombre());
                                c.setDireccion(getDireccion());
                                c.setIdentificacion(id);
                                cljc.create(c);
                                getSolicitud().setIdCliente(c);
                            } else {
                                getSolicitud().setIdCliente(cljc.findCliente(tjc.findTipoidentificacion(getTipo_id()), getId()));
                            }
                            getSolicitud().setPesoMercancia(getPesomercancia());
                            getSolicitud().setVolumenMercancia(getVolumenmercancia());
                            sjc.create(getSolicitud());
                            getEnvio().setIdSolicitud(sjc.findSolicitud(getSolicitud().getIdSolicitud()));
                            enjc.create(envio);
                            getCamion().setDisponible(Boolean.FALSE);
                            cajc.edit(getCamion());
                            mensaje = "Agregado con exito";
                        } catch (Exception ex) {
                            mensaje = "Error al agregar" + ex;
                            Logger.getLogger(BeanEnvio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    private Boolean mercanciaVacia() {
        if (pesomercancia == null) {
            return true;
        } else {
            if (pesomercancia.isEmpty()) {
                return true;
            }
        }
        if (volumenmercancia == null) {
            return true;
        } else {
            if (volumenmercancia.isEmpty()) {
                return true;
            }
            return false;
        }

    }

    private Boolean clienteVacio() {
        if (getTipo_id().isEmpty()) {
            return true;
        }
        if (getId().isEmpty()) {
            return true;
        }
        if (getCiudad().isEmpty()) {
            return true;
        }
        if (getNombre().isEmpty()) {
            return true;
        }
        if (getTelefono().isEmpty()) {
            return true;
        }
        if (getDireccion().isEmpty()) {
            return true;
        }
        return false;
    }

    public void select() {
        EnvioJpaController enjc = new EnvioJpaController(utx, emf);

        try {
            envio = enjc.findEnvio(new Long(getId_envio_select()));
        } catch (Exception e) {
        }

        if (getEnvio().getIdEnvio() != 0) {
            setEntregado(getEnvio().getFechaEntrega());
            setRecogida(getEnvio().getFechaRecogida());
            setId_solicitud(getEnvio().getIdSolicitud().getIdSolicitud().toString());
            setPlaca_camion(getEnvio().getIdCamion().getPlaca());
            setCamion(getEnvio().getIdCamion());
            setCiudadorigen(getEnvio().getIdSolicitud().getIdCiudadOrigen().getNombre());
            setCiudaddestino(getEnvio().getIdSolicitud().getIdCiudadDestino().getNombre());
            setPesomercancia(getEnvio().getIdSolicitud().getPesoMercancia());
            setVolumenmercancia(getEnvio().getIdSolicitud().getVolumenMercancia());
            setDireccion(getEnvio().getIdSolicitud().getIdCliente().getDireccion());
            setNombre(getEnvio().getIdSolicitud().getIdCliente().getNombre());
            setTelefono(getEnvio().getIdSolicitud().getIdCliente().getTelefono());
            setId(getEnvio().getIdSolicitud().getIdCliente().getIdentificacion());
            setCiudad(getEnvio().getIdSolicitud().getIdCliente().getIdCiudad().getNombre());
            setTipo_id(getEnvio().getIdSolicitud().getIdCliente().getIdTipoidentificacion().getNombreTipoIdentificacion());

        } else {
            String mensaje = "Envio no encontrado";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        }

    }

    public void select_update() {
        EnvioJpaController enjc = new EnvioJpaController(utx, emf);

        try {
            envio = enjc.findEnvio(new Long(getId_envio_select()));
        } catch (Exception e) {
        }

        if (getEnvio().getIdEnvio() != 0) {

            setRecogida(getEnvio().getFechaRecogida());
            setId_solicitud(getEnvio().getIdSolicitud().getIdSolicitud().toString());
            setPlaca_camion(getEnvio().getIdCamion().getPlaca());
            setCamion(getEnvio().getIdCamion());
            setCiudadorigen(getEnvio().getIdSolicitud().getIdCiudadOrigen().getNombre());
            setCiudaddestino(getEnvio().getIdSolicitud().getIdCiudadDestino().getNombre());
            setPesomercancia(getEnvio().getIdSolicitud().getPesoMercancia());
            setVolumenmercancia(getEnvio().getIdSolicitud().getVolumenMercancia());
            setDireccion(getEnvio().getIdSolicitud().getIdCliente().getDireccion());
            setNombre(getEnvio().getIdSolicitud().getIdCliente().getNombre());
            setTelefono(getEnvio().getIdSolicitud().getIdCliente().getTelefono());
            setId(getEnvio().getIdSolicitud().getIdCliente().getIdentificacion());
            setCiudad(getEnvio().getIdSolicitud().getIdCliente().getIdCiudad().getNombre());
            setTipo_id(getEnvio().getIdSolicitud().getIdCliente().getIdTipoidentificacion().getNombreTipoIdentificacion());

        } else {
            String mensaje = "Envio no encontrado";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        }

    }

    private void selectInter() {
        EnvioJpaController enjc = new EnvioJpaController(utx, emf);

        try {
            envio = enjc.findEnvio(new Long(getId_envio_select()));
        } catch (Exception e) {
        }

        if (getEnvio().getIdEnvio() != 0) {
            setId_solicitud(getEnvio().getIdSolicitud().getIdSolicitud().toString());
            setPlaca_camion(getEnvio().getIdCamion().getPlaca());
            setCamion(getEnvio().getIdCamion());
            setCiudadorigen(getEnvio().getIdSolicitud().getIdCiudadOrigen().getNombre());
            setCiudaddestino(getEnvio().getIdSolicitud().getIdCiudadDestino().getNombre());
            setPesomercancia(getEnvio().getIdSolicitud().getPesoMercancia());
            setVolumenmercancia(getEnvio().getIdSolicitud().getVolumenMercancia());
            setDireccion(getEnvio().getIdSolicitud().getIdCliente().getDireccion());
            setNombre(getEnvio().getIdSolicitud().getIdCliente().getNombre());
            setTelefono(getEnvio().getIdSolicitud().getIdCliente().getTelefono());
            setId(getEnvio().getIdSolicitud().getIdCliente().getIdentificacion());
            setCiudad(getEnvio().getIdSolicitud().getIdCliente().getIdCiudad().getNombre());
            setTipo_id(getEnvio().getIdSolicitud().getIdCliente().getIdTipoidentificacion().getNombreTipoIdentificacion());

        } else {
            String mensaje = "Envio no encontrado";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        }

    }

    public void update() {

        String mensaje = "";
        selectInter();
        if (envio.getIdEnvio() == 0) {
            mensaje = "Realize la busqueda del envio a actulizar " + envio.toString();
        } else {
            EstadosEnvioJpaController eejc = new EstadosEnvioJpaController(utx, emf);
            EmpleadoJpaController ejc = new EmpleadoJpaController(utx, emf);
            EstadoJpaController esjc = new EstadoJpaController(utx, emf);
            try {
                getEnvio().setFechaEntrega(getEntregado());
                if (getEntregado() != null) {
                    envio.getIdCamion().setDisponible(Boolean.TRUE);
                    CamionJpaController cjc = new CamionJpaController(utx, emf);
                    cjc.edit(envio.getIdCamion());
                    EnvioJpaController enjc = new EnvioJpaController(utx, emf);
                    enjc.edit(getEnvio());
                }
                EstadosEnvio ee = new EstadosEnvio();
                ee.setFecha(new Date());
                ee.setIdEnvio(getEnvio());
                ee.setIdEmpleado(ejc.findEmpleadoForLogin(sessionBean.getUser()));
                ee.setIdEstado(esjc.findEstado(getNew_estado()));
                eejc.create(ee);
                mensaje = "Datos actualizados con exito" + getEnvio().toString();
            } catch (Exception ex) {
                mensaje = "-----Error al actualizar-----\n" + getNew_estado();
                Logger.getLogger(BeanEnvio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public List<Envio> getEnvios() {
        EnvioJpaController enjc = new EnvioJpaController(utx, emf);
        return enjc.findEnvioEntities();
    }

    public void selectCliente() {
        String mensaje = "";
        ClienteJpaController cljc = new ClienteJpaController(utx, emf);
        TipoidentificacionJpaController tjc = new TipoidentificacionJpaController(utx, emf);
        cliente = (cljc.findCliente(tjc.findTipoidentificacion(getTipo_id()), getId()));
        if (cliente != null) {
            if (getCliente().getIdCliente() == 0) {
                mensaje = "Cliente no encontrado";
                RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
                RequestContext.getCurrentInstance().update("acceptedMessage");
            } else {

                ciudad = getCliente().getIdCiudad().getNombre();
                direccion = getCliente().getDireccion();
                id = getCliente().getIdentificacion();
                tipo_id = getCliente().getIdTipoidentificacion().getNombreTipoIdentificacion();
                nombre = (getCliente().getNombre());
                telefono = (getCliente().getTelefono());
            }
        } else {
            mensaje = "Cliente no encontrado";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        }
    }

    public List<EstadosEnvio> estadosEnvio() {
        EstadosEnvioJpaController eejc = new EstadosEnvioJpaController(utx, emf);
        return eejc.findEstadosEnvio(envio);
    }

}
