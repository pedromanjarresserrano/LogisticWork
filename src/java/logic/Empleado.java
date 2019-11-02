/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
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
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByIdEmpleado", query = "SELECT e FROM Empleado e WHERE e.idEmpleado = :idEmpleado")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado", nullable = false)
    private Long idEmpleado;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String nombre;
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
    @ManyToOne(fetch = FetchType.EAGER)
    private Cargo idCargo;
    @JoinColumn(name = "id_login", referencedColumnName = "id_login")
    @ManyToOne(fetch = FetchType.EAGER)
    private Login idLogin;
    @Lob
    @Size(max = 65535)
    @Column(name = "identificacion", length = 65535)
    private String identificacion;
    @JoinColumn(name = "id_tipodeidentificacion", referencedColumnName = "id_tipoidentificacion")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipoidentificacion idTipodeidentificacion;

    public Empleado() {
        this.idEmpleado = new Long(0);
    }

    public Empleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cargo getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargo = idCargo;
    }

    public Login getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Login idLogin) {
        this.idLogin = idLogin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Tipoidentificacion getIdTipodeidentificacion() {
        return idTipodeidentificacion;
    }

    public void setIdTipodeidentificacion(Tipoidentificacion idTipodeidentificacion) {
        this.idTipodeidentificacion = idTipodeidentificacion;
    }

    @Override
    public String toString() {
        return idTipodeidentificacion.getNombreTipoIdentificacion() + getIdentificacion() + ", " + nombre;
    }

}
