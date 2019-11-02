/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author PedroD
 */
@Entity
public class Camiones implements Serializable {
    @ManyToOne
    private Camion camion;
    
    private String cantidad;
    @Id
    private Long id;

    public Camiones() {
    }

    public Camiones(Camion camion, String cantidad) {
        this.camion = camion;
        this.cantidad = cantidad;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Camiones{" + "camion=" + camion + ", cantidad=" + cantidad + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
