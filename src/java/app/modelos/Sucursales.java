/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LeoNa
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursales.findAll", query = "SELECT s FROM Sucursales s")
    , @NamedQuery(name = "Sucursales.findBySucursalId", query = "SELECT s FROM Sucursales s WHERE s.sucursalId = :sucursalId")
    , @NamedQuery(name = "Sucursales.findByAlias", query = "SELECT s FROM Sucursales s WHERE s.alias = :alias")
    , @NamedQuery(name = "Sucursales.findByFechaInauguracion", query = "SELECT s FROM Sucursales s WHERE s.fechaInauguracion = :fechaInauguracion")
    , @NamedQuery(name = "Sucursales.findByActivo", query = "SELECT s FROM Sucursales s WHERE s.activo = :activo")})
public class Sucursales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sucursal_id")
    private Integer sucursalId;
    @Size(max = 255)
    private String alias;
    @Column(name = "fecha_inauguracion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInauguracion;
    private Character activo;
    @ManyToMany(mappedBy = "sucursalesCollection")
    private Collection<Personal> personalCollection;

    public Sucursales() {
    }

    public Sucursales(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getFechaInauguracion() {
        return fechaInauguracion;
    }

    public void setFechaInauguracion(Date fechaInauguracion) {
        this.fechaInauguracion = fechaInauguracion;
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        this.activo = activo;
    }

    @XmlTransient
    public Collection<Personal> getPersonalCollection() {
        return personalCollection;
    }

    public void setPersonalCollection(Collection<Personal> personalCollection) {
        this.personalCollection = personalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sucursalId != null ? sucursalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursales)) {
            return false;
        }
        Sucursales other = (Sucursales) object;
        if ((this.sucursalId == null && other.sucursalId != null) || (this.sucursalId != null && !this.sucursalId.equals(other.sucursalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.Sucursales[ sucursalId=" + sucursalId + " ]";
    }
    
}
