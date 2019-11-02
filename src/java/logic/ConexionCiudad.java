/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PedroD
 */
@Entity
@Table(name = "conexcion_ciudad", catalog = "dbcarga", schema = "")
@XmlRootElement
//@NamedQueries({
//@NamedQuery(name = "ConexcionCiudad.findAll", query = "SELECT c FROM ConexcionCiudad c"),
//@NamedQuery(name = "ConexcionCiudad.findByIdConexionCiudad", query = "SELECT c FROM ConexcionCiudad c WHERE c.idConexionCiudad = :idConexionCiudad"),
//  @NamedQuery(name = "ConexcionCiudad.findByDistancia", query = "SELECT c FROM ConexcionCiudad c WHERE c.distancia = :distancia")})
public class ConexionCiudad implements Serializable {

    @Column(name = "distancia")
    private BigInteger distancia;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_conexion_ciudad", nullable = false)
    private Long idConexionCiudad;

    @JoinColumn(name = "id_ciudad_a", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Ciudad idCiudadA;
    @JoinColumn(name = "id_ciudad_b", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Ciudad idCiudadB;

    public ConexionCiudad() {
        this.idConexionCiudad = new Long(0);
    }

    public ConexionCiudad(Long idConexionCiudad) {
        this.idConexionCiudad = idConexionCiudad;
    }

    public Long getIdConexionCiudad() {
        return idConexionCiudad;
    }

    public void setIdConexionCiudad(Long idConexionCiudad) {
        this.idConexionCiudad = idConexionCiudad;
    }

    public BigInteger getDistancia() {
        return distancia;
    }

    public void setDistancia(BigInteger distancia) {
        this.distancia = distancia;
    }

    public Ciudad getIdCiudadA() {
        return idCiudadA;
    }

    public void setIdCiudadA(Ciudad idCiudadA) {
        this.idCiudadA = idCiudadA;
    }

    public Ciudad getIdCiudadB() {
        return idCiudadB;
    }

    public void setIdCiudadB(Ciudad idCiudadB) {
        this.idCiudadB = idCiudadB;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConexionCiudad != null ? idConexionCiudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConexionCiudad)) {
            return false;
        }
        ConexionCiudad other = (ConexionCiudad) object;
        if ((this.idConexionCiudad == null && other.idConexionCiudad != null) || (this.idConexionCiudad != null && !this.idConexionCiudad.equals(other.idConexionCiudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.ConexcionCiudad[ idConexionCiudad=" + idConexionCiudad + " ]";
    }

}
