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
import app.modelos.Productos;
import app.modelos.Personal;
import app.modelos.PedidosDetalles;
import app.modelos.VariantesProductos;
import app.persistencia.exceptions.NonexistentEntityException;
import app.persistencia.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LeoNa
 */
public class VariantesProductosJpaController implements Serializable {

    public VariantesProductosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VariantesProductos variantesProductos) throws PreexistingEntityException, Exception {
        if (variantesProductos.getPedidosDetallesCollection() == null) {
            variantesProductos.setPedidosDetallesCollection(new ArrayList<PedidosDetalles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos productoId = variantesProductos.getProductoId();
            if (productoId != null) {
                productoId = em.getReference(productoId.getClass(), productoId.getProductoId());
                variantesProductos.setProductoId(productoId);
            }
            Personal personalId = variantesProductos.getPersonalId();
            if (personalId != null) {
                personalId = em.getReference(personalId.getClass(), personalId.getPersonalId());
                variantesProductos.setPersonalId(personalId);
            }
            Collection<PedidosDetalles> attachedPedidosDetallesCollection = new ArrayList<PedidosDetalles>();
            for (PedidosDetalles pedidosDetallesCollectionPedidosDetallesToAttach : variantesProductos.getPedidosDetallesCollection()) {
                pedidosDetallesCollectionPedidosDetallesToAttach = em.getReference(pedidosDetallesCollectionPedidosDetallesToAttach.getClass(), pedidosDetallesCollectionPedidosDetallesToAttach.getPedidoEncabezadoId());
                attachedPedidosDetallesCollection.add(pedidosDetallesCollectionPedidosDetallesToAttach);
            }
            variantesProductos.setPedidosDetallesCollection(attachedPedidosDetallesCollection);
            em.persist(variantesProductos);
            if (productoId != null) {
                productoId.getVariantesProductosCollection().add(variantesProductos);
                productoId = em.merge(productoId);
            }
            if (personalId != null) {
                personalId.getVariantesProductosCollection().add(variantesProductos);
                personalId = em.merge(personalId);
            }
            for (PedidosDetalles pedidosDetallesCollectionPedidosDetalles : variantesProductos.getPedidosDetallesCollection()) {
                VariantesProductos oldVarianteProductoIdOfPedidosDetallesCollectionPedidosDetalles = pedidosDetallesCollectionPedidosDetalles.getVarianteProductoId();
                pedidosDetallesCollectionPedidosDetalles.setVarianteProductoId(variantesProductos);
                pedidosDetallesCollectionPedidosDetalles = em.merge(pedidosDetallesCollectionPedidosDetalles);
                if (oldVarianteProductoIdOfPedidosDetallesCollectionPedidosDetalles != null) {
                    oldVarianteProductoIdOfPedidosDetallesCollectionPedidosDetalles.getPedidosDetallesCollection().remove(pedidosDetallesCollectionPedidosDetalles);
                    oldVarianteProductoIdOfPedidosDetallesCollectionPedidosDetalles = em.merge(oldVarianteProductoIdOfPedidosDetallesCollectionPedidosDetalles);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVariantesProductos(variantesProductos.getVarianteProductoId()) != null) {
                throw new PreexistingEntityException("VariantesProductos " + variantesProductos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VariantesProductos variantesProductos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VariantesProductos persistentVariantesProductos = em.find(VariantesProductos.class, variantesProductos.getVarianteProductoId());
            Productos productoIdOld = persistentVariantesProductos.getProductoId();
            Productos productoIdNew = variantesProductos.getProductoId();
            Personal personalIdOld = persistentVariantesProductos.getPersonalId();
            Personal personalIdNew = variantesProductos.getPersonalId();
            Collection<PedidosDetalles> pedidosDetallesCollectionOld = persistentVariantesProductos.getPedidosDetallesCollection();
            Collection<PedidosDetalles> pedidosDetallesCollectionNew = variantesProductos.getPedidosDetallesCollection();
            if (productoIdNew != null) {
                productoIdNew = em.getReference(productoIdNew.getClass(), productoIdNew.getProductoId());
                variantesProductos.setProductoId(productoIdNew);
            }
            if (personalIdNew != null) {
                personalIdNew = em.getReference(personalIdNew.getClass(), personalIdNew.getPersonalId());
                variantesProductos.setPersonalId(personalIdNew);
            }
            Collection<PedidosDetalles> attachedPedidosDetallesCollectionNew = new ArrayList<PedidosDetalles>();
            for (PedidosDetalles pedidosDetallesCollectionNewPedidosDetallesToAttach : pedidosDetallesCollectionNew) {
                pedidosDetallesCollectionNewPedidosDetallesToAttach = em.getReference(pedidosDetallesCollectionNewPedidosDetallesToAttach.getClass(), pedidosDetallesCollectionNewPedidosDetallesToAttach.getPedidoEncabezadoId());
                attachedPedidosDetallesCollectionNew.add(pedidosDetallesCollectionNewPedidosDetallesToAttach);
            }
            pedidosDetallesCollectionNew = attachedPedidosDetallesCollectionNew;
            variantesProductos.setPedidosDetallesCollection(pedidosDetallesCollectionNew);
            variantesProductos = em.merge(variantesProductos);
            if (productoIdOld != null && !productoIdOld.equals(productoIdNew)) {
                productoIdOld.getVariantesProductosCollection().remove(variantesProductos);
                productoIdOld = em.merge(productoIdOld);
            }
            if (productoIdNew != null && !productoIdNew.equals(productoIdOld)) {
                productoIdNew.getVariantesProductosCollection().add(variantesProductos);
                productoIdNew = em.merge(productoIdNew);
            }
            if (personalIdOld != null && !personalIdOld.equals(personalIdNew)) {
                personalIdOld.getVariantesProductosCollection().remove(variantesProductos);
                personalIdOld = em.merge(personalIdOld);
            }
            if (personalIdNew != null && !personalIdNew.equals(personalIdOld)) {
                personalIdNew.getVariantesProductosCollection().add(variantesProductos);
                personalIdNew = em.merge(personalIdNew);
            }
            for (PedidosDetalles pedidosDetallesCollectionOldPedidosDetalles : pedidosDetallesCollectionOld) {
                if (!pedidosDetallesCollectionNew.contains(pedidosDetallesCollectionOldPedidosDetalles)) {
                    pedidosDetallesCollectionOldPedidosDetalles.setVarianteProductoId(null);
                    pedidosDetallesCollectionOldPedidosDetalles = em.merge(pedidosDetallesCollectionOldPedidosDetalles);
                }
            }
            for (PedidosDetalles pedidosDetallesCollectionNewPedidosDetalles : pedidosDetallesCollectionNew) {
                if (!pedidosDetallesCollectionOld.contains(pedidosDetallesCollectionNewPedidosDetalles)) {
                    VariantesProductos oldVarianteProductoIdOfPedidosDetallesCollectionNewPedidosDetalles = pedidosDetallesCollectionNewPedidosDetalles.getVarianteProductoId();
                    pedidosDetallesCollectionNewPedidosDetalles.setVarianteProductoId(variantesProductos);
                    pedidosDetallesCollectionNewPedidosDetalles = em.merge(pedidosDetallesCollectionNewPedidosDetalles);
                    if (oldVarianteProductoIdOfPedidosDetallesCollectionNewPedidosDetalles != null && !oldVarianteProductoIdOfPedidosDetallesCollectionNewPedidosDetalles.equals(variantesProductos)) {
                        oldVarianteProductoIdOfPedidosDetallesCollectionNewPedidosDetalles.getPedidosDetallesCollection().remove(pedidosDetallesCollectionNewPedidosDetalles);
                        oldVarianteProductoIdOfPedidosDetallesCollectionNewPedidosDetalles = em.merge(oldVarianteProductoIdOfPedidosDetallesCollectionNewPedidosDetalles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = variantesProductos.getVarianteProductoId();
                if (findVariantesProductos(id) == null) {
                    throw new NonexistentEntityException("The variantesProductos with id " + id + " no longer exists.");
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
            VariantesProductos variantesProductos;
            try {
                variantesProductos = em.getReference(VariantesProductos.class, id);
                variantesProductos.getVarianteProductoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The variantesProductos with id " + id + " no longer exists.", enfe);
            }
            Productos productoId = variantesProductos.getProductoId();
            if (productoId != null) {
                productoId.getVariantesProductosCollection().remove(variantesProductos);
                productoId = em.merge(productoId);
            }
            Personal personalId = variantesProductos.getPersonalId();
            if (personalId != null) {
                personalId.getVariantesProductosCollection().remove(variantesProductos);
                personalId = em.merge(personalId);
            }
            Collection<PedidosDetalles> pedidosDetallesCollection = variantesProductos.getPedidosDetallesCollection();
            for (PedidosDetalles pedidosDetallesCollectionPedidosDetalles : pedidosDetallesCollection) {
                pedidosDetallesCollectionPedidosDetalles.setVarianteProductoId(null);
                pedidosDetallesCollectionPedidosDetalles = em.merge(pedidosDetallesCollectionPedidosDetalles);
            }
            em.remove(variantesProductos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VariantesProductos> findVariantesProductosEntities() {
        return findVariantesProductosEntities(true, -1, -1);
    }

    public List<VariantesProductos> findVariantesProductosEntities(int maxResults, int firstResult) {
        return findVariantesProductosEntities(false, maxResults, firstResult);
    }

    private List<VariantesProductos> findVariantesProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VariantesProductos.class));
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

    public VariantesProductos findVariantesProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VariantesProductos.class, id);
        } finally {
            em.close();
        }
    }

    public int getVariantesProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VariantesProductos> rt = cq.from(VariantesProductos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
