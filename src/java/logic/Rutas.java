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

public class Rutas implements Serializable {
    private Ciudad ciudad_origen;
    private Ciudad ciudad_destino;
    private String cantidad;
    @Id
    private Long id;

    public Rutas() {
    }

    public Ciudad getCiudad_origen() {
        return ciudad_origen;
    }

    public void setCiudad_origen(Ciudad ciudad_origen) {
        this.ciudad_origen = ciudad_origen;
    }

    public Ciudad getCiudad_destino() {
        return ciudad_destino;
    }

    public void setCiudad_destino(Ciudad ciudad_destino) {
        this.ciudad_destino = ciudad_destino;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Rutas(Ciudad ciudad_origen, Ciudad ciudad_destino, String cantidad) {
        this.ciudad_origen = ciudad_origen;
        this.ciudad_destino = ciudad_destino;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Rutas{" + "ciudad_origen=" + ciudad_origen + ", ciudad_destino=" + ciudad_destino + ", cantidad=" + cantidad + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
            
}
