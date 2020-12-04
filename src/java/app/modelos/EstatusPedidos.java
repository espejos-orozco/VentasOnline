/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LeoNa
 */
@Entity
@Table(name = "estatus_pedidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstatusPedidos.findAll", query = "SELECT e FROM EstatusPedidos e")
    , @NamedQuery(name = "EstatusPedidos.findByEstatusPedidoId", query = "SELECT e FROM EstatusPedidos e WHERE e.estatusPedidoId = :estatusPedidoId")
    , @NamedQuery(name = "EstatusPedidos.findByDescripcion", query = "SELECT e FROM EstatusPedidos e WHERE e.descripcion = :descripcion")})
public class EstatusPedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estatus_pedido_id")
    private Integer estatusPedidoId;
    @Size(max = 255)
    private String descripcion;
    @OneToMany(mappedBy = "estatusPedidoId")
    private Collection<PedidosEncabezados> pedidosEncabezadosCollection;

    public EstatusPedidos() {
    }

    public EstatusPedidos(Integer estatusPedidoId) {
        this.estatusPedidoId = estatusPedidoId;
    }

    public Integer getEstatusPedidoId() {
        return estatusPedidoId;
    }

    public void setEstatusPedidoId(Integer estatusPedidoId) {
        this.estatusPedidoId = estatusPedidoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<PedidosEncabezados> getPedidosEncabezadosCollection() {
        return pedidosEncabezadosCollection;
    }

    public void setPedidosEncabezadosCollection(Collection<PedidosEncabezados> pedidosEncabezadosCollection) {
        this.pedidosEncabezadosCollection = pedidosEncabezadosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estatusPedidoId != null ? estatusPedidoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstatusPedidos)) {
            return false;
        }
        EstatusPedidos other = (EstatusPedidos) object;
        if ((this.estatusPedidoId == null && other.estatusPedidoId != null) || (this.estatusPedidoId != null && !this.estatusPedidoId.equals(other.estatusPedidoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.EstatusPedidos[ estatusPedidoId=" + estatusPedidoId + " ]";
    }
    
}
