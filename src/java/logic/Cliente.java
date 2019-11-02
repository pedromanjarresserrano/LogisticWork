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
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByIdentificacion", query = "SELECT c FROM Cliente c WHERE c.identificacion = :identificacion")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    @Size(max = 100)
    @Column(length = 100)
    private String identificacion;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String nombre;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String telefono;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String direccion;
    @JoinColumn(name = "id_ciudad", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Ciudad idCiudad;
    @JoinColumn(name = "id_login", referencedColumnName = "id_login", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Login idLogin;
    @JoinColumn(name = "id_tipoidentificacion", referencedColumnName = "id_tipoidentificacion")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipoidentificacion idTipoidentificacion;

    public Cliente() {
        this.idCliente = new Long(0);

    }

    public Cliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Ciudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Ciudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Login getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Login idLogin) {
        this.idLogin = idLogin;
    }

    public Tipoidentificacion getIdTipoidentificacion() {
        return idTipoidentificacion;
    }

    public void setIdTipoidentificacion(Tipoidentificacion idTipoidentificacion) {
        this.idTipoidentificacion = idTipoidentificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", identificacion=" + identificacion + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion + ", idCiudad=" + idCiudad + ", idLogin=" + idLogin + ", idTipoidentificacion=" + idTipoidentificacion + '}';
    }

}
