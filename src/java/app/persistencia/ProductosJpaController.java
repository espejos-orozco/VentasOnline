/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import app.modelos.Personal;
import app.modelos.Productos;
import app.modelos.VariantesProductos;
import app.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LeoNa
 */
public class ProductosJpaController implements Serializable {

    public ProductosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productos productos) {
        if (productos.getVariantesProductosCollection() == null) {
            productos.setVariantesProductosCollection(new ArrayList<VariantesProductos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personal personalId = productos.getPersonalId();
            if (personalId != null) {
                personalId = em.getReference(personalId.getClass(), personalId.getPersonalId());
                productos.setPersonalId(personalId);
            }
            Collection<VariantesProductos> attachedVariantesProductosCollection = new ArrayList<VariantesProductos>();
            for (VariantesProductos variantesProductosCollectionVariantesProductosToAttach : productos.getVariantesProductosCollection()) {
                variantesProductosCollectionVariantesProductosToAttach = em.getReference(variantesProductosCollectionVariantesProductosToAttach.getClass(), variantesProductosCollectionVariantesProductosToAttach.getVarianteProductoId());
                attachedVariantesProductosCollection.add(variantesProductosCollectionVariantesProductosToAttach);
            }
            productos.setVariantesProductosCollection(attachedVariantesProductosCollection);
            em.persist(productos);
            if (personalId != null) {
                personalId.getProductosCollection().add(productos);
                personalId = em.merge(personalId);
            }
            for (VariantesProductos variantesProductosCollectionVariantesProductos : productos.getVariantesProductosCollection()) {
                Productos oldProductoIdOfVariantesProductosCollectionVariantesProductos = variantesProductosCollectionVariantesProductos.getProductoId();
                variantesProductosCollectionVariantesProductos.setProductoId(productos);
                variantesProductosCollectionVariantesProductos = em.merge(variantesProductosCollectionVariantesProductos);
                if (oldProductoIdOfVariantesProductosCollectionVariantesProductos != null) {
                    oldProductoIdOfVariantesProductosCollectionVariantesProductos.getVariantesProductosCollection().remove(variantesProductosCollectionVariantesProductos);
                    oldProductoIdOfVariantesProductosCollectionVariantesProductos = em.merge(oldProductoIdOfVariantesProductosCollectionVariantesProductos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productos productos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos persistentProductos = em.find(Productos.class, productos.getProductoId());
            Personal personalIdOld = persistentProductos.getPersonalId();
            Personal personalIdNew = productos.getPersonalId();
            Collection<VariantesProductos> variantesProductosCollectionOld = persistentProductos.getVariantesProductosCollection();
            Collection<VariantesProductos> variantesProductosCollectionNew = productos.getVariantesProductosCollection();
            if (personalIdNew != null) {
                personalIdNew = em.getReference(personalIdNew.getClass(), personalIdNew.getPersonalId());
                productos.setPersonalId(personalIdNew);
            }
            Collection<VariantesProductos> attachedVariantesProductosCollectionNew = new ArrayList<VariantesProductos>();
            for (VariantesProductos variantesProductosCollectionNewVariantesProductosToAttach : variantesProductosCollectionNew) {
                variantesProductosCollectionNewVariantesProductosToAttach = em.getReference(variantesProductosCollectionNewVariantesProductosToAttach.getClass(), variantesProductosCollectionNewVariantesProductosToAttach.getVarianteProductoId());
                attachedVariantesProductosCollectionNew.add(variantesProductosCollectionNewVariantesProductosToAttach);
            }
            variantesProductosCollectionNew = attachedVariantesProductosCollectionNew;
            productos.setVariantesProductosCollection(variantesProductosCollectionNew);
            productos = em.merge(productos);
            if (personalIdOld != null && !personalIdOld.equals(personalIdNew)) {
                personalIdOld.getProductosCollection().remove(productos);
                personalIdOld = em.merge(personalIdOld);
            }
            if (personalIdNew != null && !personalIdNew.equals(personalIdOld)) {
                personalIdNew.getProductosCollection().add(productos);
                personalIdNew = em.merge(personalIdNew);
            }
            for (VariantesProductos variantesProductosCollectionOldVariantesProductos : variantesProductosCollectionOld) {
                if (!variantesProductosCollectionNew.contains(variantesProductosCollectionOldVariantesProductos)) {
                    variantesProductosCollectionOldVariantesProductos.setProductoId(null);
                    variantesProductosCollectionOldVariantesProductos = em.merge(variantesProductosCollectionOldVariantesProductos);
                }
            }
            for (VariantesProductos variantesProductosCollectionNewVariantesProductos : variantesProductosCollectionNew) {
                if (!variantesProductosCollectionOld.contains(variantesProductosCollectionNewVariantesProductos)) {
                    Productos oldProductoIdOfVariantesProductosCollectionNewVariantesProductos = variantesProductosCollectionNewVariantesProductos.getProductoId();
                    variantesProductosCollectionNewVariantesProductos.setProductoId(productos);
                    variantesProductosCollectionNewVariantesProductos = em.merge(variantesProductosCollectionNewVariantesProductos);
                    if (oldProductoIdOfVariantesProductosCollectionNewVariantesProductos != null && !oldProductoIdOfVariantesProductosCollectionNewVariantesProductos.equals(productos)) {
                        oldProductoIdOfVariantesProductosCollectionNewVariantesProductos.getVariantesProductosCollection().remove(variantesProductosCollectionNewVariantesProductos);
                        oldProductoIdOfVariantesProductosCollectionNewVariantesProductos = em.merge(oldProductoIdOfVariantesProductosCollectionNewVariantesProductos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productos.getProductoId();
                if (findProductos(id) == null) {
                    throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
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
            Productos productos;
            try {
                productos = em.getReference(Productos.class, id);
                productos.getProductoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productos with id " + id + " no longer exists.", enfe);
            }
            Personal personalId = productos.getPersonalId();
            if (personalId != null) {
                personalId.getProductosCollection().remove(productos);
                personalId = em.merge(personalId);
            }
            Collection<VariantesProductos> variantesProductosCollection = productos.getVariantesProductosCollection();
            for (VariantesProductos variantesProductosCollectionVariantesProductos : variantesProductosCollection) {
                variantesProductosCollectionVariantesProductos.setProductoId(null);
                variantesProductosCollectionVariantesProductos = em.merge(variantesProductosCollectionVariantesProductos);
            }
            em.remove(productos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productos> findProductosEntities() {
        return findProductosEntities(true, -1, -1);
    }

    public List<Productos> findProductosEntities(int maxResults, int firstResult) {
        return findProductosEntities(false, maxResults, firstResult);
    }

    private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos.class));
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

    public Productos findProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos> rt = cq.from(Productos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
