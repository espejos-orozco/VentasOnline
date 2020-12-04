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
@Table(name = "tarjetas_clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TarjetasClientes.findAll", query = "SELECT t FROM TarjetasClientes t")
    , @NamedQuery(name = "TarjetasClientes.findByTarjetaClienteId", query = "SELECT t FROM TarjetasClientes t WHERE t.tarjetaClienteId = :tarjetaClienteId")
    , @NamedQuery(name = "TarjetasClientes.findByNumeroTarjeta", query = "SELECT t FROM TarjetasClientes t WHERE t.numeroTarjeta = :numeroTarjeta")
    , @NamedQuery(name = "TarjetasClientes.findByNombre", query = "SELECT t FROM TarjetasClientes t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TarjetasClientes.findByFechaExpiracion", query = "SELECT t FROM TarjetasClientes t WHERE t.fechaExpiracion = :fechaExpiracion")
    , @NamedQuery(name = "TarjetasClientes.findByCodigoSeguridad", query = "SELECT t FROM TarjetasClientes t WHERE t.codigoSeguridad = :codigoSeguridad")
    , @NamedQuery(name = "TarjetasClientes.findByActivo", query = "SELECT t FROM TarjetasClientes t WHERE t.activo = :activo")
    , @NamedQuery(name = "TarjetasClientes.findByFechaRegistro", query = "SELECT t FROM TarjetasClientes t WHERE t.fechaRegistro = :fechaRegistro")})
public class TarjetasClientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tarjeta_cliente_id")
    private Integer tarjetaClienteId;
    @Size(max = 255)
    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;
    @Size(max = 255)
    private String nombre;
    @Size(max = 10)
    @Column(name = "fecha_expiracion")
    private String fechaExpiracion;
    @Column(name = "codigo_seguridad")
    private Integer codigoSeguridad;
    private Character activo;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "tarjetaClienteId")
    private Collection<PedidosEncabezados> pedidosEncabezadosCollection;
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    @ManyToOne
    private Clientes clienteId;

    public TarjetasClientes() {
    }

    public TarjetasClientes(Integer tarjetaClienteId) {
        this.tarjetaClienteId = tarjetaClienteId;
    }

    public Integer getTarjetaClienteId() {
        return tarjetaClienteId;
    }

    public void setTarjetaClienteId(Integer tarjetaClienteId) {
        this.tarjetaClienteId = tarjetaClienteId;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Integer getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(Integer codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
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
    public Collection<PedidosEncabezados> getPedidosEncabezadosCollection() {
        return pedidosEncabezadosCollection;
    }

    public void setPedidosEncabezadosCollection(Collection<PedidosEncabezados> pedidosEncabezadosCollection) {
        this.pedidosEncabezadosCollection = pedidosEncabezadosCollection;
    }

    public Clientes getClienteId() {
        return clienteId;
    }

    public void setClienteId(Clientes clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tarjetaClienteId != null ? tarjetaClienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetasClientes)) {
            return false;
        }
        TarjetasClientes other = (TarjetasClientes) object;
        if ((this.tarjetaClienteId == null && other.tarjetaClienteId != null) || (this.tarjetaClienteId != null && !this.tarjetaClienteId.equals(other.tarjetaClienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.TarjetasClientes[ tarjetaClienteId=" + tarjetaClienteId + " ]";
    }
    
}
