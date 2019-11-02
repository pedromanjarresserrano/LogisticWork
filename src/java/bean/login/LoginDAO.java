/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.login;

import controller.LoginJpaController;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import logic.Login;

/**
 *
 * @author PedroD
 */
public class LoginDAO {

    public static boolean validate(String user, String password, UserTransaction utx, EntityManagerFactory emf) {
        LoginJpaController ljc = new LoginJpaController(utx, emf);

        try {
            for (Login l : ljc.findLoginEntities()) {
                if (l.getUser().equalsIgnoreCase(user) && l.getPass().equalsIgnoreCase(password)) {
                    if (!"CLIENTE".equals(l.getPermiso())) {
                        return true;
                    }
                }
            }

        } catch (Exception ex) {
        }
        return false;
    }
}
