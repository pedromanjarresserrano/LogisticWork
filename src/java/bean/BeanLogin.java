/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.LoginJpaController;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import java.util.Objects;
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
import logic.Login;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@ManagedBean
@RequestScoped
public class BeanLogin {

    private String user;
    private String pass;
    private String email;
    private String permisos;
    private Login login;

    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public BeanLogin() {
        this.login = new Login();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public void insertar() {
        getLogin().setUser(getUser());
        getLogin().setEmail(getEmail());
        getLogin().setPass(getPass());
        getLogin().setPermiso(getPermisos());

        String mensaje = "Agregado con exito";

        LoginJpaController controller = new LoginJpaController(utx, emf);
        if (controller.findLogin(user).size() > 0) {
            mensaje = "Nombre de usuario ya existe";

        } else {
            if (controller.findLoginEmail(email).size() > 0) {
                mensaje = "E-mail ya esta registro";
            } else {
                try {
                    controller.create(login);
                } catch (PreexistingEntityException e) {
                    mensaje = "Ya hay un aire con ese nombre";
                } catch (Exception e) {
                    mensaje = "Fallo al agregar";

                    Logger.getLogger(BeanCiudad.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");

    }

    public void select() {
        String mensaje = "";
        LoginJpaController controller = new LoginJpaController(utx, emf);
        try {
            login = controller.findLogin(getUser()).get(0);
            user = login.getUser();
            pass = login.getPass();
            email = login.getEmail();
            permisos = login.getPermiso();
        } catch (Exception e) {
            try {
                login = controller.findLoginEmail(getEmail()).get(0);

                user = login.getUser();
                pass = login.getPass();
                email = login.getEmail();
                permisos = login.getPermiso();
            } catch (Exception e2) {
                mensaje = "Usuario no encontrado";
                RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
                RequestContext.getCurrentInstance().update("acceptedMessage");
            }
        }

    }

    public void update() {
        LoginJpaController controller = new LoginJpaController(utx, emf);
        String mensaje = "";
        if (Objects.equals(login.getIdLogin(), (long) 0)) {
            mensaje = "Loguee antes";
        } else {
            getLogin().setEmail(getEmail());
            getLogin().setPass(getPass());
            getLogin().setPermiso(getPermisos());
            getLogin().setUser(getUser());
            try {
                controller.edit(login);
                mensaje = "Datos Actualizados";
            } catch (RollbackFailureException ex) {
                Logger.getLogger(BeanLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(BeanLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        mensaje = "Usuario no encontrado";
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");
    }

}
