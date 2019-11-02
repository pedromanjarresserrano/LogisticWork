/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PedroD
 */
@Entity
@Table(catalog = "dbcarga", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Envio.findAll", query = "SELECT e FROM Envio e"),
    @NamedQuery(name = "Envio.findByIdEnvio", query = "SELECT e FROM Envio e WHERE e.idEnvio = :idEnvio"),
    @NamedQuery(name = "Envio.findByFechaRecogida", query = "SELECT e FROM Envio e WHERE e.fechaRecogida = :fechaRecogida"),
    @NamedQuery(name = "Envio.findByFechaEntrega", query = "SELECT e FROM Envio e WHERE e.fechaEntrega = :fechaEntrega")})
public class Envio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_envio", nullable = false)
    private Long idEnvio;
    @Column(name = "fecha_recogida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecogida;
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @JoinColumn(name = "id_camion", referencedColumnName = "id_camion", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Camion idCamion;
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id_solicitud")
    @ManyToOne(fetch = FetchType.EAGER)
    private Solicitud idSolicitud;

    public Envio() {
        this.idEnvio = new Long(0);
    }

    public Envio(Long idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Long getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Long idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Date getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(Date fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Camion getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(Camion idCamion) {
        this.idCamion = idCamion;
    }

    public Solicitud getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Solicitud idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEnvio != null ? idEnvio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Envio)) {
            return false;
        }
        Envio other = (Envio) object;
        if ((this.idEnvio == null && other.idEnvio != null) || (this.idEnvio != null && !this.idEnvio.equals(other.idEnvio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Envio{" + "idEnvio=" + idEnvio + ", fechaRecogida=" + fechaRecogida + ", fechaEntrega=" + fechaEntrega + ", idCamion=" + idCamion + ", idSolicitud=" + idSolicitud + '}';
    }


}
