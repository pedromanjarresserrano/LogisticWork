/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PedroD
 */
@Entity
@Table(catalog = "dbcarga", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Camion.findAll", query = "SELECT c FROM Camion c"),
    @NamedQuery(name = "Camion.findByIdCamion", query = "SELECT c FROM Camion c WHERE c.idCamion = :idCamion"),
    @NamedQuery(name = "Camion.findByPlaca", query = "SELECT c FROM Camion c WHERE c.placa = :placa"),
    @NamedQuery(name = "Camion.findByDisponible", query = "SELECT c FROM Camion c WHERE c.disponible = :disponible"),
    @NamedQuery(name = "Camion.findByPesoCapacidad", query = "SELECT c FROM Camion c WHERE c.pesoCapacidad = :pesoCapacidad"),
    @NamedQuery(name = "Camion.findByVolumenCapacidad", query = "SELECT c FROM Camion c WHERE c.volumenCapacidad = :volumenCapacidad")})
public class Camion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_camion", nullable = false)
    private Long idCamion;
    @Size(max = 10)
    @Column(length = 10)
    private String placa;
    private Boolean disponible;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String modelo;
    @Column(name = "peso_capacidad")
    private BigInteger pesoCapacidad;
    @Column(name = "volumen_capacidad")
    private BigInteger volumenCapacidad;
    @JoinColumn(name = "id_ciudad_actual", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Ciudad idCiudadActual;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.EAGER)
    private Empleado idEmpleado;
    @OneToMany(mappedBy = "camion")
    private List<Camiones> camioness;

    public Camion() {
        this.idCamion = new Long(0);
    }

    public Camion(Long idCamion) {
        this.idCamion = idCamion;
    }

    public Long getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(Long idCamion) {
        this.idCamion = idCamion;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigInteger getPesoCapacidad() {
        return pesoCapacidad;
    }

    public void setPesoCapacidad(BigInteger pesoCapacidad) {
        this.pesoCapacidad = pesoCapacidad;
    }

    public BigInteger getVolumenCapacidad() {
        return volumenCapacidad;
    }

    public void setVolumenCapacidad(BigInteger volumenCapacidad) {
        this.volumenCapacidad = volumenCapacidad;
    }

    public Ciudad getIdCiudadActual() {
        return idCiudadActual;
    }

    public void setIdCiudadActual(Ciudad idCiudadActual) {
        this.idCiudadActual = idCiudadActual;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCamion != null ? idCamion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Camion)) {
            return false;
        }
        Camion other = (Camion) object;
        if ((this.idCamion == null && other.idCamion != null) || (this.idCamion != null && !this.idCamion.equals(other.idCamion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.Camion[ idCamion=" + idCamion + " ]";
    }

}
