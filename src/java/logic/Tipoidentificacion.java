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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
    @NamedQuery(name = "Tipoidentificacion.findAll", query = "SELECT t FROM Tipoidentificacion t"),
    @NamedQuery(name = "Tipoidentificacion.findByIdTipoidentificacion", query = "SELECT t FROM Tipoidentificacion t WHERE t.idTipoidentificacion = :idTipoidentificacion")})
public class Tipoidentificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipoidentificacion", nullable = false)
    private Long idTipoidentificacion;
    @Lob
    @Size(max = 65535)
    @Column(name = "nombre_tipo_identificacion", length = 65535)
    private String nombreTipoIdentificacion;

    public Tipoidentificacion() {
        this.idTipoidentificacion = new Long(0);
    }

    public Tipoidentificacion(Long idTipoidentificacion) {
        this.idTipoidentificacion = idTipoidentificacion;
    }

    public Long getIdTipoidentificacion() {
        return idTipoidentificacion;
    }

    public void setIdTipoidentificacion(Long idTipoidentificacion) {
        this.idTipoidentificacion = idTipoidentificacion;
    }

    public String getNombreTipoIdentificacion() {
        return nombreTipoIdentificacion;
    }

    public void setNombreTipoIdentificacion(String nombreTipoIdentificacion) {
        this.nombreTipoIdentificacion = nombreTipoIdentificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoidentificacion != null ? idTipoidentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoidentificacion)) {
            return false;
        }
        Tipoidentificacion other = (Tipoidentificacion) object;
        if ((this.idTipoidentificacion == null && other.idTipoidentificacion != null) || (this.idTipoidentificacion != null && !this.idTipoidentificacion.equals(other.idTipoidentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreTipoIdentificacion;
    }

}
