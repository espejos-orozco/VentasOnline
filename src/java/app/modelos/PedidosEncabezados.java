/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LeoNa
 */
@Entity
@Table(name = "pedidos_encabezados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PedidosEncabezados.findAll", query = "SELECT p FROM PedidosEncabezados p")
    , @NamedQuery(name = "PedidosEncabezados.findByPedidoEncabezadoId", query = "SELECT p FROM PedidosEncabezados p WHERE p.pedidoEncabezadoId = :pedidoEncabezadoId")
    , @NamedQuery(name = "PedidosEncabezados.findByFacturar", query = "SELECT p FROM PedidosEncabezados p WHERE p.facturar = :facturar")
    , @NamedQuery(name = "PedidosEncabezados.findByMonto", query = "SELECT p FROM PedidosEncabezados p WHERE p.monto = :monto")
    , @NamedQuery(name = "PedidosEncabezados.findByPagado", query = "SELECT p FROM PedidosEncabezados p WHERE p.pagado = :pagado")
    , @NamedQuery(name = "PedidosEncabezados.findByVisible", query = "SELECT p FROM PedidosEncabezados p WHERE p.visible = :visible")
    , @NamedQuery(name = "PedidosEncabezados.findByBorrado", query = "SELECT p FROM PedidosEncabezados p WHERE p.borrado = :borrado")
    , @NamedQuery(name = "PedidosEncabezados.findByFechaRegistro", query = "SELECT p FROM PedidosEncabezados p WHERE p.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "PedidosEncabezados.findByFechaPago", query = "SELECT p FROM PedidosEncabezados p WHERE p.fechaPago = :fechaPago")})
public class PedidosEncabezados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pedido_encabezado_id")
    private Integer pedidoEncabezadoId;
    private Character facturar;
    @Size(max = 255)
    private String monto;
    private Character pagado;
    private Character visible;
    private Character borrado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    @ManyToOne
    private Clientes clienteId;
    @JoinColumn(name = "direccion_cliente_id", referencedColumnName = "direccion_cliente_id")
    @ManyToOne
    private DireccionesClientes direccionClienteId;
    @JoinColumn(name = "direccion_cliente_facturacion_id", referencedColumnName = "direccion_cliente_id")
    @ManyToOne
    private DireccionesClientes direccionClienteFacturacionId;
    @JoinColumn(name = "tarjeta_cliente_id", referencedColumnName = "tarjeta_cliente_id")
    @ManyToOne
    private TarjetasClientes tarjetaClienteId;
    @JoinColumn(name = "estatus_pedido_id", referencedColumnName = "estatus_pedido_id")
    @ManyToOne
    private EstatusPedidos estatusPedidoId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedidosEncabezados")
    private PedidosDetalles pedidosDetalles;

    public PedidosEncabezados() {
    }

    public PedidosEncabezados(Integer pedidoEncabezadoId) {
        this.pedidoEncabezadoId = pedidoEncabezadoId;
    }

    public Integer getPedidoEncabezadoId() {
        return pedidoEncabezadoId;
    }

    public void setPedidoEncabezadoId(Integer pedidoEncabezadoId) {
        this.pedidoEncabezadoId = pedidoEncabezadoId;
    }

    public Character getFacturar() {
        return facturar;
    }

    public void setFacturar(Character facturar) {
        this.facturar = facturar;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public Character getPagado() {
        return pagado;
    }

    public void setPagado(Character pagado) {
        this.pagado = pagado;
    }

    public Character getVisible() {
        return visible;
    }

    public void setVisible(Character visible) {
        this.visible = visible;
    }

    public Character getBorrado() {
        return borrado;
    }

    public void setBorrado(Character borrado) {
        this.borrado = borrado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Clientes getClienteId() {
        return clienteId;
    }

    public void setClienteId(Clientes clienteId) {
        this.clienteId = clienteId;
    }

    public DireccionesClientes getDireccionClienteId() {
        return direccionClienteId;
    }

    public void setDireccionClienteId(DireccionesClientes direccionClienteId) {
        this.direccionClienteId = direccionClienteId;
    }

    public DireccionesClientes getDireccionClienteFacturacionId() {
        return direccionClienteFacturacionId;
    }

    public void setDireccionClienteFacturacionId(DireccionesClientes direccionClienteFacturacionId) {
        this.direccionClienteFacturacionId = direccionClienteFacturacionId;
    }

    public TarjetasClientes getTarjetaClienteId() {
        return tarjetaClienteId;
    }

    public void setTarjetaClienteId(TarjetasClientes tarjetaClienteId) {
        this.tarjetaClienteId = tarjetaClienteId;
    }

    public EstatusPedidos getEstatusPedidoId() {
        return estatusPedidoId;
    }

    public void setEstatusPedidoId(EstatusPedidos estatusPedidoId) {
        this.estatusPedidoId = estatusPedidoId;
    }

    public PedidosDetalles getPedidosDetalles() {
        return pedidosDetalles;
    }

    public void setPedidosDetalles(PedidosDetalles pedidosDetalles) {
        this.pedidosDetalles = pedidosDetalles;
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
        if (!(object instanceof PedidosEncabezados)) {
            return false;
        }
        PedidosEncabezados other = (PedidosEncabezados) object;
        if ((this.pedidoEncabezadoId == null && other.pedidoEncabezadoId != null) || (this.pedidoEncabezadoId != null && !this.pedidoEncabezadoId.equals(other.pedidoEncabezadoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.PedidosEncabezados[ pedidoEncabezadoId=" + pedidoEncabezadoId + " ]";
    }
    
}
