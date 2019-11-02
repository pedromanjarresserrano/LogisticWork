/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CargoJpaController;
import controller.EmpleadoJpaController;
import controller.LoginJpaController;
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
import logic.Camion;
import logic.Empleado;
import logic.Login;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanEmpleado {

    private String tipo_id;
    private String id;
    private String nombre;
    private String user_login;
    private String idcargo;
    private String pass;
    private String email;
    private String permisos;
    private Empleado empleado;

    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdcargo() {
        return idcargo;
    }

    public void setIdcargo(String idcargo) {
        this.idcargo = idcargo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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

    public BeanEmpleado() {
        this.empleado = new Empleado();
        this.email = "";
        this.id = "";
        this.idcargo = "";
        this.nombre = "";
        this.pass = "";
        this.permisos = "";
        this.tipo_id = "";
        this.user_login = "";
    }

    private boolean vacio() {

        if ("".equals(email) || email == null) {
            return true;
        }
        if ("".equals(id) || id == null) {
            return true;
        }
        if ("".equals(idcargo) || idcargo == null) {
            return true;
        }
        if ("".equals(nombre) || nombre == null) {
            return true;
        }
        if ("".equals(pass) || pass == null) {
            return true;
        }
        if ("".equals(permisos) || permisos == null) {
            return true;
        }
        if ("".equals(tipo_id) || tipo_id == null) {
            return true;
        }
        if ("".equals(user_login) || user_login == null) {
            return true;
        }
        return false;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    public void insertar() {
        String mensaje = "";
        if (vacio()) {
            mensaje = "Llene todos los campos";
        } else {
            LoginJpaController ljc = new LoginJpaController(utx, emf);
            CargoJpaController cjc = new CargoJpaController(utx, emf);
            EmpleadoJpaController ejc = new EmpleadoJpaController(utx, emf);
            TipoidentificacionJpaController tjc = new TipoidentificacionJpaController(utx, emf);

            if (!ljc.findLogin(getUser_login()).isEmpty()) {
                mensaje = "Usuario de sesion ya existe";
            } else {
                if (!ljc.findLoginEmail(getEmail()).isEmpty()) {
                    mensaje = "E-mail ya existe";
                } else {
                    if (ejc.findEmpleado(getId(), tjc.findTipoidentificacion(getTipo_id())).getIdEmpleado() != 0) {
                        mensaje = "ya existe un registro con esa identificación";
                    } else {
                        try {
                            Login login = new Login();
                            login.setEmail(getEmail());
                            login.setUser(user_login);
                            login.setPass(pass);
                            login.setPermiso(permisos);

                            ljc.create(login);
                            getEmpleado().setIdCargo(cjc.findCargo(getIdcargo()));
                            getEmpleado().setIdLogin(ljc.findLogin(getUser_login()).get(0));
                            getEmpleado().setNombre(getNombre());
                            getEmpleado().setIdTipodeidentificacion(tjc.findTipoidentificacion(getTipo_id()));
                            getEmpleado().setIdentificacion(getId());
                            ejc.create(empleado);
                            mensaje = "Agregado con exito";
                            RequestContext requestContext = RequestContext.getCurrentInstance();
                            requestContext.execute("limpiarForm()");
                        } catch (PreexistingEntityException e) {
                            mensaje = "Ya hay un usuario con ese codigo";
                        } catch (Exception ex) {
                            mensaje = "Fallo al agregar";
                        }
                    }
                }
            }
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");

    }

    public void update() {
        String mensaje = "";
        TipoidentificacionJpaController tjc = new TipoidentificacionJpaController(utx, emf);
        LoginJpaController ljc = new LoginJpaController(utx, emf);
        CargoJpaController cjc = new CargoJpaController(utx, emf);
        EmpleadoJpaController ejc = new EmpleadoJpaController(utx, emf);
        setEmpleado(ejc.findEmpleado(getId(), tjc.findTipoidentificacion(getTipo_id())));
        getEmpleado().setIdCargo(cjc.findCargo(getIdcargo()));
        getEmpleado().setNombre(getNombre());
        getEmpleado().getIdLogin().setPermiso(getPermisos());
        try {
            ljc.edit(getEmpleado().getIdLogin());
            ejc.edit(getEmpleado());
            mensaje = "Datos actualizado con exito" + getEmpleado().toString();
        } catch (Exception ex) {
            mensaje = "Fallo al actualizar";
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

    public void select() {
        EmpleadoJpaController ejc = new EmpleadoJpaController(utx, emf);
        TipoidentificacionJpaController tjc = new TipoidentificacionJpaController(utx, emf);
        setEmpleado(ejc.findEmpleado(getId(), tjc.findTipoidentificacion(getTipo_id())));
        String mensaje = "";
        if (getEmpleado().getIdEmpleado() == 0) {
            mensaje = "Usuario no encontrado";
            RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
            RequestContext.getCurrentInstance().update("acceptedMessage");
        } else {
            setNombre(getEmpleado().getNombre());
            setIdcargo(getEmpleado().getIdCargo().getNombre());
            setEmail(getEmpleado().getIdLogin().getEmail());
            setPermisos(getEmpleado().getIdLogin().getPermiso());
            setUser_login(getEmpleado().getIdLogin().getUser());
            setPass(getEmpleado().getIdLogin().getPass());

        }

    }

    public List<Empleado> getEmpelados() {
        EmpleadoJpaController emjc = new EmpleadoJpaController(utx, emf);
        return emjc.findEmpleadoEntities();
    }
}
