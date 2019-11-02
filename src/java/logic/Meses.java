/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author PedroD
 */
@Entity
public class Meses implements Serializable {
    private String año;
    private String mes;
    private String cantidad;
    @Id
    private Long id;

    public Meses() {
    }

    public Meses(String año, String mes, String cantidad) {
        this.año = año;
        this.mes = mes;
        this.cantidad = cantidad;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Meses{" + "a\u00f1o=" + año + ", mes=" + mes + ", cantidad=" + cantidad + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
