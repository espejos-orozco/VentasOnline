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
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByClienteId", query = "SELECT c FROM Clientes c WHERE c.clienteId = :clienteId")
    , @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Clientes.findByApellidos", query = "SELECT c FROM Clientes c WHERE c.apellidos = :apellidos")
    , @NamedQuery(name = "Clientes.findByCorreoElectronico", query = "SELECT c FROM Clientes c WHERE c.correoElectronico = :correoElectronico")
    , @NamedQuery(name = "Clientes.findByPassword", query = "SELECT c FROM Clientes c WHERE c.password = :password")
    , @NamedQuery(name = "Clientes.findByTelefono", query = "SELECT c FROM Clientes c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Clientes.findByActivo", query = "SELECT c FROM Clientes c WHERE c.activo = :activo")
    , @NamedQuery(name = "Clientes.findByFechaRegistro", query = "SELECT c FROM Clientes c WHERE c.fechaRegistro = :fechaRegistro")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cliente_id")
    private Integer clienteId;
    @Size(max = 255)
    private String nombre;
    @Size(max = 255)
    private String apellidos;
    @Size(max = 255)
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @Size(max = 255)
    private String password;
    @Size(max = 255)
    private String telefono;
    private Character activo;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "clienteId")
    private Collection<PedidosEncabezados> pedidosEncabezadosCollection;
    @OneToMany(mappedBy = "clienteId")
    private Collection<TarjetasClientes> tarjetasClientesCollection;
    @OneToMany(mappedBy = "clienteId")
    private Collection<DireccionesClientes> direccionesClientesCollection;

    public Clientes() {
    }

    public Clientes(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    @XmlTransient
    public Collection<TarjetasClientes> getTarjetasClientesCollection() {
        return tarjetasClientesCollection;
    }

    public void setTarjetasClientesCollection(Collection<TarjetasClientes> tarjetasClientesCollection) {
        this.tarjetasClientesCollection = tarjetasClientesCollection;
    }

    @XmlTransient
    public Collection<DireccionesClientes> getDireccionesClientesCollection() {
        return direccionesClientesCollection;
    }

    public void setDireccionesClientesCollection(Collection<DireccionesClientes> direccionesClientesCollection) {
        this.direccionesClientesCollection = direccionesClientesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteId != null ? clienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.clienteId == null && other.clienteId != null) || (this.clienteId != null && !this.clienteId.equals(other.clienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.Clientes[ clienteId=" + clienteId + " ]";
    }
    
}
