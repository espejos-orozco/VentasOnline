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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Personal.findAll", query = "SELECT p FROM Personal p")
    , @NamedQuery(name = "Personal.findByPersonalId", query = "SELECT p FROM Personal p WHERE p.personalId = :personalId")
    , @NamedQuery(name = "Personal.findByNombre", query = "SELECT p FROM Personal p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Personal.findByApellido", query = "SELECT p FROM Personal p WHERE p.apellido = :apellido")
    , @NamedQuery(name = "Personal.findByFechaNacimientoDate", query = "SELECT p FROM Personal p WHERE p.fechaNacimientoDate = :fechaNacimientoDate")
    , @NamedQuery(name = "Personal.findByFechaIngresoDate", query = "SELECT p FROM Personal p WHERE p.fechaIngresoDate = :fechaIngresoDate")
    , @NamedQuery(name = "Personal.findByAccesoSistema", query = "SELECT p FROM Personal p WHERE p.accesoSistema = :accesoSistema")
    , @NamedQuery(name = "Personal.findByUsuario", query = "SELECT p FROM Personal p WHERE p.usuario = :usuario")
    , @NamedQuery(name = "Personal.findByPassword", query = "SELECT p FROM Personal p WHERE p.password = :password")
    , @NamedQuery(name = "Personal.findByActivo", query = "SELECT p FROM Personal p WHERE p.activo = :activo")
    , @NamedQuery(name = "Personal.findByFechaRegistro", query = "SELECT p FROM Personal p WHERE p.fechaRegistro = :fechaRegistro")})
public class Personal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "personal_id")
    private Integer personalId;
    @Size(max = 255)
    private String nombre;
    @Size(max = 255)
    private String apellido;
    @Column(name = "fecha_nacimiento_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimientoDate;
    @Column(name = "fecha_ingreso_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngresoDate;
    @Column(name = "acceso_sistema")
    private Character accesoSistema;
    @Size(max = 255)
    private String usuario;
    @Size(max = 255)
    private String password;
    private Character activo;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinTable(name = "personal_sucursal", joinColumns = {
        @JoinColumn(name = "personal_id", referencedColumnName = "personal_id")}, inverseJoinColumns = {
        @JoinColumn(name = "sucursal_id", referencedColumnName = "sucursal_id")})
    @ManyToMany
    private Collection<Sucursales> sucursalesCollection;
    @OneToMany(mappedBy = "personalId")
    private Collection<Productos> productosCollection;
    @OneToMany(mappedBy = "personalId")
    private Collection<VariantesProductos> variantesProductosCollection;

    public Personal() {
    }

    public Personal(Integer personalId) {
        this.personalId = personalId;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimientoDate() {
        return fechaNacimientoDate;
    }

    public void setFechaNacimientoDate(Date fechaNacimientoDate) {
        this.fechaNacimientoDate = fechaNacimientoDate;
    }

    public Date getFechaIngresoDate() {
        return fechaIngresoDate;
    }

    public void setFechaIngresoDate(Date fechaIngresoDate) {
        this.fechaIngresoDate = fechaIngresoDate;
    }

    public Character getAccesoSistema() {
        return accesoSistema;
    }

    public void setAccesoSistema(Character accesoSistema) {
        this.accesoSistema = accesoSistema;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public Collection<Sucursales> getSucursalesCollection() {
        return sucursalesCollection;
    }

    public void setSucursalesCollection(Collection<Sucursales> sucursalesCollection) {
        this.sucursalesCollection = sucursalesCollection;
    }

    @XmlTransient
    public Collection<Productos> getProductosCollection() {
        return productosCollection;
    }

    public void setProductosCollection(Collection<Productos> productosCollection) {
        this.productosCollection = productosCollection;
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
        hash += (personalId != null ? personalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personal)) {
            return false;
        }
        Personal other = (Personal) object;
        if ((this.personalId == null && other.personalId != null) || (this.personalId != null && !this.personalId.equals(other.personalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.modelos.Personal[ personalId=" + personalId + " ]";
    }
    
}
