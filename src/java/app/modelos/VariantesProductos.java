/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LeoNa
 */
@Entity
@Table(name = "variantes_productos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VariantesProductos.findAll", query = "SELECT v FROM VariantesProductos v")
    , @NamedQuery(name = "VariantesProductos.findByVarianteProductoId", query = "SELECT v FROM VariantesProductos v WHERE v.varianteProductoId = :varianteProductoId")
    , @NamedQuery(name = "VariantesProductos.findByDescripcion", query = "SELECT v FROM VariantesProductos v WHERE v.descripcion = :descripcion")
    , @NamedQuery(name = "VariantesProductos.findByDisponibilidad", query = "SELECT v FROM VariantesProductos v WHERE v.disponibilidad = :disponibilidad")
    , @NamedQuery(name = "VariantesProductos.findByPrecio", query = "SELECT v FROM VariantesProductos v WHERE v.precio = :precio")
    , @NamedQuery(name = "VariantesProductos.findByActivo", query = "SELECT v FROM VariantesProductos v WHERE v.activo = :activo")
    , @NamedQuery(name = "VariantesProductos.findByFechaRegistro", query = "SELECT v FROM VariantesProductos v WHERE v.fechaRegistro = :fechaRegistro")})
public class VariantesProductos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "variante_producto_id")
    private Integer varianteProductoId;
    @Size(max = 255)
    private String descripcion;
    private Integer disponibilidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private BigDecimal precio;
    private Character activo;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "varianteProductoId")
    private Collection<PedidosDetalles> pedidosDetallesCollection;
    @JoinColumn(name = "producto_id", referencedColumnName = "producto_id")
    @ManyToOne
    private Productos productoId;
    @JoinColumn(name = "personal_id", referencedColumnName = "personal_id")
    @ManyToOne
    private Personal personalId;

    public VariantesProductos() {
    }

    public VariantesProductos(Integer varianteProductoId) {
        this.varianteProductoId = varianteProductoId;
    }

    public Integer getVarianteProductoId() {
        return varianteProductoId;
    }

    public void setVarianteProductoId(Integer varianteProductoId) {
        this.varianteProductoId = varianteProductoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        this.activo = activo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public Collection<PedidosDetalles> getPedidosDetallesCollection() {
        return pedidosDetallesCollection;
    }

    public void setPedidosDetallesCollection(Collection<PedidosDetalles> pedidosDetallesCollection) {
        this.pedidosDetallesCollection = pedidosDetallesCollection;
    }

    public Productos getProductoId() {
        return productoId;
    }

    public void setProductoId(Productos productoId) {
        this.productoId = productoId;
    }

    public Personal getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Personal personalId) {
        this.personalId = personalId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (varianteProductoId != null ? varianteProductoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VariantesProductos)) {
            return false;
        }
        VariantesProductos other = (VariantesProductos) object;
        if ((this.varianteProductoId == null && other.varianteProductoId != null) || (this.varianteProductoId != null && !this.varianteProductoId.equals(other.varianteProductoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.VariantesProductos[ varianteProductoId=" + varianteProductoId + " ]";
    }
    
}
