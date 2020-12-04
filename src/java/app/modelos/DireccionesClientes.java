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
@Table(name = "direcciones_clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DireccionesClientes.findAll", query = "SELECT d FROM DireccionesClientes d")
    , @NamedQuery(name = "DireccionesClientes.findByDireccionClienteId", query = "SELECT d FROM DireccionesClientes d WHERE d.direccionClienteId = :direccionClienteId")
    , @NamedQuery(name = "DireccionesClientes.findByCalle", query = "SELECT d FROM DireccionesClientes d WHERE d.calle = :calle")
    , @NamedQuery(name = "DireccionesClientes.findByColonia", query = "SELECT d FROM DireccionesClientes d WHERE d.colonia = :colonia")
    , @NamedQuery(name = "DireccionesClientes.findByLocalidad", query = "SELECT d FROM DireccionesClientes d WHERE d.localidad = :localidad")
    , @NamedQuery(name = "DireccionesClientes.findByRegion", query = "SELECT d FROM DireccionesClientes d WHERE d.region = :region")
    , @NamedQuery(name = "DireccionesClientes.findByCodigoPostal", query = "SELECT d FROM DireccionesClientes d WHERE d.codigoPostal = :codigoPostal")
    , @NamedQuery(name = "DireccionesClientes.findByPrincipal", query = "SELECT d FROM DireccionesClientes d WHERE d.principal = :principal")
    , @NamedQuery(name = "DireccionesClientes.findByFechaRegistro", query = "SELECT d FROM DireccionesClientes d WHERE d.fechaRegistro = :fechaRegistro")})
public class DireccionesClientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "direccion_cliente_id")
    private Integer direccionClienteId;
    @Size(max = 255)
    private String calle;
    @Size(max = 255)
    private String colonia;
    @Size(max = 255)
    private String localidad;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    @Column(name = "codigo_postal")
    private String codigoPostal;
    private Character principal;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "direccionClienteId")
    private Collection<PedidosEncabezados> pedidosEncabezadosCollection;
    @OneToMany(mappedBy = "direccionClienteFacturacionId")
    private Collection<PedidosEncabezados> pedidosEncabezadosCollection1;
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    @ManyToOne
    private Clientes clienteId;

    public DireccionesClientes() {
    }

    public DireccionesClientes(Integer direccionClienteId) {
        this.direccionClienteId = direccionClienteId;
    }

    public Integer getDireccionClienteId() {
        return direccionClienteId;
    }

    public void setDireccionClienteId(Integer direccionClienteId) {
        this.direccionClienteId = direccionClienteId;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Character getPrincipal() {
        return principal;
    }

    public void setPrincipal(Character principal) {
        this.principal = principal;
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

    @XmlTransient
    public Collection<PedidosEncabezados> getPedidosEncabezadosCollection1() {
        return pedidosEncabezadosCollection1;
    }

    public void setPedidosEncabezadosCollection1(Collection<PedidosEncabezados> pedidosEncabezadosCollection1) {
        this.pedidosEncabezadosCollection1 = pedidosEncabezadosCollection1;
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
        hash += (direccionClienteId != null ? direccionClienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DireccionesClientes)) {
            return false;
        }
        DireccionesClientes other = (DireccionesClientes) object;
        if ((this.direccionClienteId == null && other.direccionClienteId != null) || (this.direccionClienteId != null && !this.direccionClienteId.equals(other.direccionClienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.DireccionesClientes[ direccionClienteId=" + direccionClienteId + " ]";
    }
    
}
