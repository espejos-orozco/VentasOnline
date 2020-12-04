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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p")
    , @NamedQuery(name = "Productos.findByProductoId", query = "SELECT p FROM Productos p WHERE p.productoId = :productoId")
    , @NamedQuery(name = "Productos.findByNombre", query = "SELECT p FROM Productos p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Productos.findByDescripcion", query = "SELECT p FROM Productos p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Productos.findByActivo", query = "SELECT p FROM Productos p WHERE p.activo = :activo")
    , @NamedQuery(name = "Productos.findByTipoProductoId", query = "SELECT p FROM Productos p WHERE p.tipoProductoId = :tipoProductoId")
    , @NamedQuery(name = "Productos.findByFechaRegistro", query = "SELECT p FROM Productos p WHERE p.fechaRegistro = :fechaRegistro")})
public class Productos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "producto_id")
    private Integer productoId;
    @Size(max = 255)
    private String nombre;
    @Size(max = 255)
    private String descripcion;
    private Character activo;
    @Column(name = "tipo_producto_id")
    private Integer tipoProductoId;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "personal_id", referencedColumnName = "personal_id")
    @ManyToOne
    private Personal personalId;
    @OneToMany(mappedBy = "productoId")
    private Collection<VariantesProductos> variantesProductosCollection;

    public Productos() {
    }

    public Productos(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        this.activo = activo;
    }

    public Integer getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Personal getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Personal personalId) {
        this.personalId = personalId;
    }

    @XmlTransient
    public Collection<VariantesProductos> getVariantesProductosCollection() {
        return variantesProductosCollection;
    }

    public void setVariantesProductosCollection(Collection<VariantesProductos> variantesProductosCollection) {
        this.variantesProductosCollection = variantesProductosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoId != null ? productoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.productoId == null && other.productoId != null) || (this.productoId != null && !this.productoId.equals(other.productoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.Productos[ productoId=" + productoId + " ]";
    }
    
}
