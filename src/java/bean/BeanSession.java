/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.login.LoginDAO;
import bean.login.SessionUtils;
import controller.EmpleadoJpaController;
import controller.LoginJpaController;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import logic.Login;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PedroD
 */
@SessionScoped
@ManagedBean
public class BeanSession implements Serializable {

    private String nombre;
    private String user;
    private String pass;
    private Login login;
    private boolean remenber;

    @PersistenceUnit(unitName = "webcargaPU")
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;

    public BeanSession() {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public boolean isRemenber() {
        return remenber;
    }

    public void setRemenber(boolean remenber) {
        this.remenber = remenber;
    }

    public String log_in() throws IOException {
        LoginJpaController ljc = new LoginJpaController(utx, emf);
        String mensaje = "";
        mensaje = "Usuario o contraseña erronea";
        if (user == null) {
            mensaje = "Digite usuario";
        } else {
            if (user.isEmpty()) {
                mensaje = "Digite usuario";
            } else {
                if (pass == null) {
                    mensaje = "Digite ccontraseña";
                } else {
                    if (pass.isEmpty()) {
                        mensaje = "Digite contraseña";
                    } else {

                        if (LoginDAO.validate(user, pass, utx, emf)) {
                            EmpleadoJpaController ejc = new EmpleadoJpaController(utx, emf);
                            setLogin(new LoginJpaController(utx, emf).findLoginSession(user, pass));
                            setNombre(ejc.findEmpleadoForLogin(getUser()).getNombre());

                            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                            session.setAttribute("user", user);
                            return "index";

                        } else {
                            mensaje = "Usuario o contraseña erronea";
                            this.setUser("");
                            this.setPass("");
                            this.setLogin(new Login());
                            this.setNombre("");

                        }
                    }
                }
            }
        }
        this.setUser("");
        this.setPass("");
        this.setLogin(new Login());
        this.setNombre("");
        RequestContext.getCurrentInstance().addCallbackParam("isValid", true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        RequestContext.getCurrentInstance().update("acceptedMessage");

        return "login";
    }

    public void checkSession() {

        if (user == null || pass == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(BeanSession.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (user.isEmpty() && pass.isEmpty()) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(BeanSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public Boolean GodPower() {
        if (login != null) {
            if (login.getIdLogin() != 0) {
                if (login.getPermiso().equalsIgnoreCase("ADMINISTRADOR")) {
                    return true;
                }
            }
        }
        return false;
    }

    public String log_out() throws IOException {

        HttpSession session = SessionUtils.getSession();
        user = "";
        pass = "";
        login = new Login();
        nombre = "";

        session.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        return "login.xhtml";
    }

}
