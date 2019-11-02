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
@Table(name = "estados_envio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadosEnvio.findAll", query = "SELECT e FROM EstadosEnvio e"),
    @NamedQuery(name = "EstadosEnvio.findByIdEstadoEnvio", query = "SELECT e FROM EstadosEnvio e WHERE e.idEstadoEnvio = :idEstadoEnvio"),
    @NamedQuery(name = "EstadosEnvio.findByFecha", query = "SELECT e FROM EstadosEnvio e WHERE e.fecha = :fecha")})
public class EstadosEnvio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_envio")
    private Long idEstadoEnvio;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(fetch = FetchType.EAGER)
    private Empleado idEmpleado;
    @JoinColumn(name = "id_envio", referencedColumnName = "id_envio")
    @ManyToOne(fetch = FetchType.EAGER)
    private Envio idEnvio;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado idEstado;

    public EstadosEnvio() {
        this.idEstadoEnvio = new Long(0);
    }

    public EstadosEnvio(Long idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public Long getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(Long idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Envio getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Envio idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoEnvio != null ? idEstadoEnvio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadosEnvio)) {
            return false;
        }
        EstadosEnvio other = (EstadosEnvio) object;
        if ((this.idEstadoEnvio == null && other.idEstadoEnvio != null) || (this.idEstadoEnvio != null && !this.idEstadoEnvio.equals(other.idEstadoEnvio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.EstadosEnvio[ idEstadoEnvio=" + idEstadoEnvio + " ]";
    }

}
