/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PedroD
 */
@Entity
@Table(catalog = "dbcarga", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s"),
    @NamedQuery(name = "Solicitud.findByIdSolicitud", query = "SELECT s FROM Solicitud s WHERE s.idSolicitud = :idSolicitud"),
    @NamedQuery(name = "Solicitud.findByFecha", query = "SELECT s FROM Solicitud s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "Solicitud.findByAceptada", query = "SELECT s FROM Solicitud s WHERE s.aceptada = :aceptada")})
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicitud", nullable = false)
    private Long idSolicitud;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Size(max = 65535)
    @Column(name = "peso_mercancia", length = 65535)
    private String pesoMercancia;
    @Lob
    @Size(max = 65535)
    @Column(name = "volumen_mercancia", length = 65535)
    private String volumenMercancia;
    private Boolean aceptada;
    @JoinColumn(name = "id_ciudad_origen", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Ciudad idCiudadOrigen;
    @JoinColumn(name = "id_ciudad_destino", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Ciudad idCiudadDestino;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente idCliente;

    public Solicitud() {
        this.idSolicitud = new Long(0);
    }

    public Solicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPesoMercancia() {
        return pesoMercancia;
    }

    public void setPesoMercancia(String pesoMercancia) {
        this.pesoMercancia = pesoMercancia;
    }

    public String getVolumenMercancia() {
        return volumenMercancia;
    }

    public void setVolumenMercancia(String volumenMercancia) {
        this.volumenMercancia = volumenMercancia;
    }

    public Boolean getAceptada() {
        return aceptada;
    }

    public void setAceptada(Boolean aceptada) {
        this.aceptada = aceptada;
    }

    public Ciudad getIdCiudadOrigen() {
        return idCiudadOrigen;
    }

    public void setIdCiudadOrigen(Ciudad idCiudadOrigen) {
        this.idCiudadOrigen = idCiudadOrigen;
    }

    public Ciudad getIdCiudadDestino() {
        return idCiudadDestino;
    }

    public void setIdCiudadDestino(Ciudad idCiudadDestino) {
        this.idCiudadDestino = idCiudadDestino;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.idSolicitud == null && other.idSolicitud != null) || (this.idSolicitud != null && !this.idSolicitud.equals(other.idSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.Solicitud[ idSolicitud=" + idSolicitud + " ]";
    }

}
