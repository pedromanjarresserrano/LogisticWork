/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CiudadJpaController;
import controller.ClienteJpaController;
import controller.LoginJpaController;
import controller.TipoidentificacionJpaController;
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
import logic.Cliente;
import logic.Login;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanCliente {

    private String tipo_id;
    private String id;
    private String nombre;
    private String telefono;
    private String ciudad;
    private String direccion;
    private String user_login;
    private String email;
    private Cliente cliente;
    private String pass;

    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public BeanCliente() {
        this.cliente = new Cliente();
        this.tipo_id = "";
        this.id = "";
        this.nombre = "";
        this.telefono = "";
        this.ciudad = "";
        this.direccion = "";
        this.user_login = "";
        this.email = "";
        this.pass = "";

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

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void insertar() {
        ClienteJpaController cljc = new ClienteJpaController(utx, emf);
        LoginJpaController ljc = new LoginJpaController(utx, emf);
        CiudadJpaController cjc = new CiudadJpaController(utx, emf);
        TipoidentificacionJpaController tjc = new TipoidentificacionJpaController(utx, emf);
        getCliente().setDireccion(getDireccion());
        getCliente().setIdCiudad(cjc.findCiudad(getCiudad()));
        getCliente().setIdTipoidentificacion(tjc.findTipoidentificacion(getTipo_id()));
        getCliente().setIdentificacion(getId());
        getCliente().setNombre(getNombre());
        getCliente().setTelefono(getTelefono());
        Login login = new Login();
        login.setUser(getUser_login());
        login.setPass(getPass());
        login.setPermiso("CLIENTE");
        login.setEmail(getEmail());
        String mensaje = "Agregado con éxito";
        if (!ljc.findLogin(getUser_login()).isEmpty()) {
            mensaje = "Usuario de Sesion ya existe";
        } else {
            if (!ljc.findLogin(getEmail()).isEmpty()) {
                mensaje = "E-mail de cliente ya esta registado";
            } else {
                try {
                    ljc.create(login);
                    getCliente().setIdLogin(ljc.findLogin(getUser_login()).get(0));
                    cljc.create(cliente);
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.execute("limpiarForm()");
                } catch (Exception ex) {
                    mensaje = "Error al agregar";
                    Logger.getLogger(BeanConexionCiudad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public List<Cliente> getClientes() {
        ClienteJpaController cljc = new ClienteJpaController(utx, emf);
        return cljc.findClienteEntities();
    }

    public void select() {
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
                user_login = (getCliente().getIdLogin().getUser());
                email = (getCliente().getIdLogin().getEmail());
            }
        } else {
            mensaje = "Cliente no encontrado";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        }
    }

    public void update() {
        ClienteJpaController cljc = new ClienteJpaController(utx, emf);
        CiudadJpaController cjc = new CiudadJpaController(utx, emf);
        TipoidentificacionJpaController tjc = new TipoidentificacionJpaController(utx, emf);
        setCliente(cljc.findCliente(tjc.findTipoidentificacion(getTipo_id()), getId()));
        String mensaje = "";
        if (cljc.findCliente(tjc.findTipoidentificacion(getTipo_id()), getId()).getIdCliente() == 0) {
            mensaje = "Busque primero el cliente a actulizar ";
        } else {

            try {
                getCliente().setDireccion(getDireccion());
                getCliente().setIdCiudad(cjc.findCiudad(getCiudad()));
                getCliente().setNombre(getNombre());
                getCliente().setTelefono(getTelefono());
                cljc.edit(cliente);
                mensaje = "Datos actulizados con éxito " + getCliente().toString();
            } catch (Exception ex) {
                mensaje = "Error al agregar";
                Logger.getLogger(BeanConexionCiudad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
