/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LeoNa
 */
@Entity
@Table(name = "pedidos_detalles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PedidosDetalles.findAll", query = "SELECT p FROM PedidosDetalles p")
    , @NamedQuery(name = "PedidosDetalles.findByPedidoEncabezadoId", query = "SELECT p FROM PedidosDetalles p WHERE p.pedidoEncabezadoId = :pedidoEncabezadoId")
    , @NamedQuery(name = "PedidosDetalles.findByPrecio", query = "SELECT p FROM PedidosDetalles p WHERE p.precio = :precio")
    , @NamedQuery(name = "PedidosDetalles.findByFechaRegistro", query = "SELECT p FROM PedidosDetalles p WHERE p.fechaRegistro = :fechaRegistro")})
public class PedidosDetalles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pedido_encabezado_id")
    private Integer pedidoEncabezadoId;
    @Size(max = 255)
    private String precio;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "pedido_encabezado_id", referencedColumnName = "pedido_encabezado_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private PedidosEncabezados pedidosEncabezados;
    @JoinColumn(name = "variante_producto_id", referencedColumnName = "variante_producto_id")
    @ManyToOne
    private VariantesProductos varianteProductoId;

    public PedidosDetalles() {
    }

    public PedidosDetalles(Integer pedidoEncabezadoId) {
        this.pedidoEncabezadoId = pedidoEncabezadoId;
    }

    public Integer getPedidoEncabezadoId() {
        return pedidoEncabezadoId;
    }

    public void setPedidoEncabezadoId(Integer pedidoEncabezadoId) {
        this.pedidoEncabezadoId = pedidoEncabezadoId;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public PedidosEncabezados getPedidosEncabezados() {
        return pedidosEncabezados;
    }

    public void setPedidosEncabezados(PedidosEncabezados pedidosEncabezados) {
        this.pedidosEncabezados = pedidosEncabezados;
    }

    public VariantesProductos getVarianteProductoId() {
        return varianteProductoId;
    }

    public void setVarianteProductoId(VariantesProductos varianteProductoId) {
        this.varianteProductoId = varianteProductoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedidoEncabezadoId != null ? pedidoEncabezadoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidosDetalles)) {
            return false;
        }
        PedidosDetalles other = (PedidosDetalles) object;
        if ((this.pedidoEncabezadoId == null && other.pedidoEncabezadoId != null) || (this.pedidoEncabezadoId != null && !this.pedidoEncabezadoId.equals(other.pedidoEncabezadoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.PedidosDetalles[ pedidoEncabezadoId=" + pedidoEncabezadoId + " ]";
    }
    
}
