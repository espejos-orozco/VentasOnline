/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistencia;

import app.modelos.Personal;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import app.modelos.Sucursales;
import java.util.ArrayList;
import java.util.Collection;
import app.modelos.Productos;
import app.modelos.VariantesProductos;
import app.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LeoNa
 */
public class PersonalJpaController implements Serializable {

    public PersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personal personal) {
        if (personal.getSucursalesCollection() == null) {
            personal.setSucursalesCollection(new ArrayList<Sucursales>());
        }
        if (personal.getProductosCollection() == null) {
            personal.setProductosCollection(new ArrayList<Productos>());
        }
        if (personal.getVariantesProductosCollection() == null) {
            personal.setVariantesProductosCollection(new ArrayList<VariantesProductos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Sucursales> attachedSucursalesCollection = new ArrayList<Sucursales>();
            for (Sucursales sucursalesCollectionSucursalesToAttach : personal.getSucursalesCollection()) {
                sucursalesCollectionSucursalesToAttach = em.getReference(sucursalesCollectionSucursalesToAttach.getClass(), sucursalesCollectionSucursalesToAttach.getSucursalId());
                attachedSucursalesCollection.add(sucursalesCollectionSucursalesToAttach);
            }
            personal.setSucursalesCollection(attachedSucursalesCollection);
            Collection<Productos> attachedProductosCollection = new ArrayList<Productos>();
            for (Productos productosCollectionProductosToAttach : personal.getProductosCollection()) {
                productosCollectionProductosToAttach = em.getReference(productosCollectionProductosToAttach.getClass(), productosCollectionProductosToAttach.getProductoId());
                attachedProductosCollection.add(productosCollectionProductosToAttach);
            }
            personal.setProductosCollection(attachedProductosCollection);
            Collection<VariantesProductos> attachedVariantesProductosCollection = new ArrayList<VariantesProductos>();
            for (VariantesProductos variantesProductosCollectionVariantesProductosToAttach : personal.getVariantesProductosCollection()) {
                variantesProductosCollectionVariantesProductosToAttach = em.getReference(variantesProductosCollectionVariantesProductosToAttach.getClass(), variantesProductosCollectionVariantesProductosToAttach.getVarianteProductoId());
                attachedVariantesProductosCollection.add(variantesProductosCollectionVariantesProductosToAttach);
            }
            personal.setVariantesProductosCollection(attachedVariantesProductosCollection);
            em.persist(personal);
            for (Sucursales sucursalesCollectionSucursales : personal.getSucursalesCollection()) {
                sucursalesCollectionSucursales.getPersonalCollection().add(personal);
                sucursalesCollectionSucursales = em.merge(sucursalesCollectionSucursales);
            }
            for (Productos productosCollectionProductos : personal.getProductosCollection()) {
                Personal oldPersonalIdOfProductosCollectionProductos = productosCollectionProductos.getPersonalId();
                productosCollectionProductos.setPersonalId(personal);
                productosCollectionProductos = em.merge(productosCollectionProductos);
                if (oldPersonalIdOfProductosCollectionProductos != null) {
                    oldPersonalIdOfProductosCollectionProductos.getProductosCollection().remove(productosCollectionProductos);
                    oldPersonalIdOfProductosCollectionProductos = em.merge(oldPersonalIdOfProductosCollectionProductos);
                }
            }
            for (VariantesProductos variantesProductosCollectionVariantesProductos : personal.getVariantesProductosCollection()) {
                Personal oldPersonalIdOfVariantesProductosCollectionVariantesProductos = variantesProductosCollectionVariantesProductos.getPersonalId();
                variantesProductosCollectionVariantesProductos.setPersonalId(personal);
                variantesProductosCollectionVariantesProductos = em.merge(variantesProductosCollectionVariantesProductos);
                if (oldPersonalIdOfVariantesProductosCollectionVariantesProductos != null) {
                    oldPersonalIdOfVariantesProductosCollectionVariantesProductos.getVariantesProductosCollection().remove(variantesProductosCollectionVariantesProductos);
                    oldPersonalIdOfVariantesProductosCollectionVariantesProductos = em.merge(oldPersonalIdOfVariantesProductosCollectionVariantesProductos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personal personal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personal persistentPersonal = em.find(Personal.class, personal.getPersonalId());
            Collection<Sucursales> sucursalesCollectionOld = persistentPersonal.getSucursalesCollection();
            Collection<Sucursales> sucursalesCollectionNew = personal.getSucursalesCollection();
            Collection<Productos> productosCollectionOld = persistentPersonal.getProductosCollection();
            Collection<Productos> productosCollectionNew = personal.getProductosCollection();
            Collection<VariantesProductos> variantesProductosCollectionOld = persistentPersonal.getVariantesProductosCollection();
            Collection<VariantesProductos> variantesProductosCollectionNew = personal.getVariantesProductosCollection();
            Collection<Sucursales> attachedSucursalesCollectionNew = new ArrayList<Sucursales>();
            for (Sucursales sucursalesCollectionNewSucursalesToAttach : sucursalesCollectionNew) {
                sucursalesCollectionNewSucursalesToAttach = em.getReference(sucursalesCollectionNewSucursalesToAttach.getClass(), sucursalesCollectionNewSucursalesToAttach.getSucursalId());
                attachedSucursalesCollectionNew.add(sucursalesCollectionNewSucursalesToAttach);
            }
            sucursalesCollectionNew = attachedSucursalesCollectionNew;
            personal.setSucursalesCollection(sucursalesCollectionNew);
            Collection<Productos> attachedProductosCollectionNew = new ArrayList<Productos>();
            for (Productos productosCollectionNewProductosToAttach : productosCollectionNew) {
                productosCollectionNewProductosToAttach = em.getReference(productosCollectionNewProductosToAttach.getClass(), productosCollectionNewProductosToAttach.getProductoId());
                attachedProductosCollectionNew.add(productosCollectionNewProductosToAttach);
            }
            productosCollectionNew = attachedProductosCollectionNew;
            personal.setProductosCollection(productosCollectionNew);
            Collection<VariantesProductos> attachedVariantesProductosCollectionNew = new ArrayList<VariantesProductos>();
            for (VariantesProductos variantesProductosCollectionNewVariantesProductosToAttach : variantesProductosCollectionNew) {
                variantesProductosCollectionNewVariantesProductosToAttach = em.getReference(variantesProductosCollectionNewVariantesProductosToAttach.getClass(), variantesProductosCollectionNewVariantesProductosToAttach.getVarianteProductoId());
                attachedVariantesProductosCollectionNew.add(variantesProductosCollectionNewVariantesProductosToAttach);
            }
            variantesProductosCollectionNew = attachedVariantesProductosCollectionNew;
            personal.setVariantesProductosCollection(variantesProductosCollectionNew);
            personal = em.merge(personal);
            for (Sucursales sucursalesCollectionOldSucursales : sucursalesCollectionOld) {
                if (!sucursalesCollectionNew.contains(sucursalesCollectionOldSucursales)) {
                    sucursalesCollectionOldSucursales.getPersonalCollection().remove(personal);
                    sucursalesCollectionOldSucursales = em.merge(sucursalesCollectionOldSucursales);
                }
            }
            for (Sucursales sucursalesCollectionNewSucursales : sucursalesCollectionNew) {
                if (!sucursalesCollectionOld.contains(sucursalesCollectionNewSucursales)) {
                    sucursalesCollectionNewSucursales.getPersonalCollection().add(personal);
                    sucursalesCollectionNewSucursales = em.merge(sucursalesCollectionNewSucursales);
                }
            }
            for (Productos productosCollectionOldProductos : productosCollectionOld) {
                if (!productosCollectionNew.contains(productosCollectionOldProductos)) {
                    productosCollectionOldProductos.setPersonalId(null);
                    productosCollectionOldProductos = em.merge(productosCollectionOldProductos);
                }
            }
            for (Productos productosCollectionNewProductos : productosCollectionNew) {
                if (!productosCollectionOld.contains(productosCollectionNewProductos)) {
                    Personal oldPersonalIdOfProductosCollectionNewProductos = productosCollectionNewProductos.getPersonalId();
                    productosCollectionNewProductos.setPersonalId(personal);
                    productosCollectionNewProductos = em.merge(productosCollectionNewProductos);
                    if (oldPersonalIdOfProductosCollectionNewProductos != null && !oldPersonalIdOfProductosCollectionNewProductos.equals(personal)) {
                        oldPersonalIdOfProductosCollectionNewProductos.getProductosCollection().remove(productosCollectionNewProductos);
                        oldPersonalIdOfProductosCollectionNewProductos = em.merge(oldPersonalIdOfProductosCollectionNewProductos);
                    }
                }
            }
            for (VariantesProductos variantesProductosCollectionOldVariantesProductos : variantesProductosCollectionOld) {
                if (!variantesProductosCollectionNew.contains(variantesProductosCollectionOldVariantesProductos)) {
                    variantesProductosCollectionOldVariantesProductos.setPersonalId(null);
                    variantesProductosCollectionOldVariantesProductos = em.merge(variantesProductosCollectionOldVariantesProductos);
                }
            }
            for (VariantesProductos variantesProductosCollectionNewVariantesProductos : variantesProductosCollectionNew) {
                if (!variantesProductosCollectionOld.contains(variantesProductosCollectionNewVariantesProductos)) {
                    Personal oldPersonalIdOfVariantesProductosCollectionNewVariantesProductos = variantesProductosCollectionNewVariantesProductos.getPersonalId();
                    variantesProductosCollectionNewVariantesProductos.setPersonalId(personal);
                    variantesProductosCollectionNewVariantesProductos = em.merge(variantesProductosCollectionNewVariantesProductos);
                    if (oldPersonalIdOfVariantesProductosCollectionNewVariantesProductos != null && !oldPersonalIdOfVariantesProductosCollectionNewVariantesProductos.equals(personal)) {
                        oldPersonalIdOfVariantesProductosCollectionNewVariantesProductos.getVariantesProductosCollection().remove(variantesProductosCollectionNewVariantesProductos);
                        oldPersonalIdOfVariantesProductosCollectionNewVariantesProductos = em.merge(oldPersonalIdOfVariantesProductosCollectionNewVariantesProductos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personal.getPersonalId();
                if (findPersonal(id) == null) {
                    throw new NonexistentEntityException("The personal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personal personal;
            try {
                personal = em.getReference(Personal.class, id);
                personal.getPersonalId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personal with id " + id + " no longer exists.", enfe);
            }
            Collection<Sucursales> sucursalesCollection = personal.getSucursalesCollection();
            for (Sucursales sucursalesCollectionSucursales : sucursalesCollection) {
                sucursalesCollectionSucursales.getPersonalCollection().remove(personal);
                sucursalesCollectionSucursales = em.merge(sucursalesCollectionSucursales);
            }
            Collection<Productos> productosCollection = personal.getProductosCollection();
            for (Productos productosCollectionProductos : productosCollection) {
                productosCollectionProductos.setPersonalId(null);
                productosCollectionProductos = em.merge(productosCollectionProductos);
            }
            Collection<VariantesProductos> variantesProductosCollection = personal.getVariantesProductosCollection();
            for (VariantesProductos variantesProductosCollectionVariantesProductos : variantesProductosCollection) {
                variantesProductosCollectionVariantesProductos.setPersonalId(null);
                variantesProductosCollectionVariantesProductos = em.merge(variantesProductosCollectionVariantesProductos);
            }
            em.remove(personal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personal> findPersonalEntities() {
        return findPersonalEntities(true, -1, -1);
    }

    public List<Personal> findPersonalEntities(int maxResults, int firstResult) {
        return findPersonalEntities(false, maxResults, firstResult);
    }

    private List<Personal> findPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personal.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Personal findPersonal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personal.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personal> rt = cq.from(Personal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
