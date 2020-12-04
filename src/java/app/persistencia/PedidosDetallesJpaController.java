/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistencia;

import app.modelos.PedidosDetalles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import app.modelos.PedidosEncabezados;
import app.modelos.VariantesProductos;
import app.persistencia.exceptions.IllegalOrphanException;
import app.persistencia.exceptions.NonexistentEntityException;
import app.persistencia.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LeoNa
 */
public class PedidosDetallesJpaController implements Serializable {

    public PedidosDetallesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PedidosDetalles pedidosDetalles) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        PedidosEncabezados pedidosEncabezadosOrphanCheck = pedidosDetalles.getPedidosEncabezados();
        if (pedidosEncabezadosOrphanCheck != null) {
            PedidosDetalles oldPedidosDetallesOfPedidosEncabezados = pedidosEncabezadosOrphanCheck.getPedidosDetalles();
            if (oldPedidosDetallesOfPedidosEncabezados != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The PedidosEncabezados " + pedidosEncabezadosOrphanCheck + " already has an item of type PedidosDetalles whose pedidosEncabezados column cannot be null. Please make another selection for the pedidosEncabezados field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidosEncabezados pedidosEncabezados = pedidosDetalles.getPedidosEncabezados();
            if (pedidosEncabezados != null) {
                pedidosEncabezados = em.getReference(pedidosEncabezados.getClass(), pedidosEncabezados.getPedidoEncabezadoId());
                pedidosDetalles.setPedidosEncabezados(pedidosEncabezados);
            }
            VariantesProductos varianteProductoId = pedidosDetalles.getVarianteProductoId();
            if (varianteProductoId != null) {
                varianteProductoId = em.getReference(varianteProductoId.getClass(), varianteProductoId.getVarianteProductoId());
                pedidosDetalles.setVarianteProductoId(varianteProductoId);
            }
            em.persist(pedidosDetalles);
            if (pedidosEncabezados != null) {
                pedidosEncabezados.setPedidosDetalles(pedidosDetalles);
                pedidosEncabezados = em.merge(pedidosEncabezados);
            }
            if (varianteProductoId != null) {
                varianteProductoId.getPedidosDetallesCollection().add(pedidosDetalles);
                varianteProductoId = em.merge(varianteProductoId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPedidosDetalles(pedidosDetalles.getPedidoEncabezadoId()) != null) {
                throw new PreexistingEntityException("PedidosDetalles " + pedidosDetalles + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PedidosDetalles pedidosDetalles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidosDetalles persistentPedidosDetalles = em.find(PedidosDetalles.class, pedidosDetalles.getPedidoEncabezadoId());
            PedidosEncabezados pedidosEncabezadosOld = persistentPedidosDetalles.getPedidosEncabezados();
            PedidosEncabezados pedidosEncabezadosNew = pedidosDetalles.getPedidosEncabezados();
            VariantesProductos varianteProductoIdOld = persistentPedidosDetalles.getVarianteProductoId();
            VariantesProductos varianteProductoIdNew = pedidosDetalles.getVarianteProductoId();
            List<String> illegalOrphanMessages = null;
            if (pedidosEncabezadosNew != null && !pedidosEncabezadosNew.equals(pedidosEncabezadosOld)) {
                PedidosDetalles oldPedidosDetallesOfPedidosEncabezados = pedidosEncabezadosNew.getPedidosDetalles();
                if (oldPedidosDetallesOfPedidosEncabezados != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The PedidosEncabezados " + pedidosEncabezadosNew + " already has an item of type PedidosDetalles whose pedidosEncabezados column cannot be null. Please make another selection for the pedidosEncabezados field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pedidosEncabezadosNew != null) {
                pedidosEncabezadosNew = em.getReference(pedidosEncabezadosNew.getClass(), pedidosEncabezadosNew.getPedidoEncabezadoId());
                pedidosDetalles.setPedidosEncabezados(pedidosEncabezadosNew);
            }
            if (varianteProductoIdNew != null) {
                varianteProductoIdNew = em.getReference(varianteProductoIdNew.getClass(), varianteProductoIdNew.getVarianteProductoId());
                pedidosDetalles.setVarianteProductoId(varianteProductoIdNew);
            }
            pedidosDetalles = em.merge(pedidosDetalles);
            if (pedidosEncabezadosOld != null && !pedidosEncabezadosOld.equals(pedidosEncabezadosNew)) {
                pedidosEncabezadosOld.setPedidosDetalles(null);
                pedidosEncabezadosOld = em.merge(pedidosEncabezadosOld);
            }
            if (pedidosEncabezadosNew != null && !pedidosEncabezadosNew.equals(pedidosEncabezadosOld)) {
                pedidosEncabezadosNew.setPedidosDetalles(pedidosDetalles);
                pedidosEncabezadosNew = em.merge(pedidosEncabezadosNew);
            }
            if (varianteProductoIdOld != null && !varianteProductoIdOld.equals(varianteProductoIdNew)) {
                varianteProductoIdOld.getPedidosDetallesCollection().remove(pedidosDetalles);
                varianteProductoIdOld = em.merge(varianteProductoIdOld);
            }
            if (varianteProductoIdNew != null && !varianteProductoIdNew.equals(varianteProductoIdOld)) {
                varianteProductoIdNew.getPedidosDetallesCollection().add(pedidosDetalles);
                varianteProductoIdNew = em.merge(varianteProductoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedidosDetalles.getPedidoEncabezadoId();
                if (findPedidosDetalles(id) == null) {
                    throw new NonexistentEntityException("The pedidosDetalles with id " + id + " no longer exists.");
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
            PedidosDetalles pedidosDetalles;
            try {
                pedidosDetalles = em.getReference(PedidosDetalles.class, id);
                pedidosDetalles.getPedidoEncabezadoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedidosDetalles with id " + id + " no longer exists.", enfe);
            }
            PedidosEncabezados pedidosEncabezados = pedidosDetalles.getPedidosEncabezados();
            if (pedidosEncabezados != null) {
                pedidosEncabezados.setPedidosDetalles(null);
                pedidosEncabezados = em.merge(pedidosEncabezados);
            }
            VariantesProductos varianteProductoId = pedidosDetalles.getVarianteProductoId();
            if (varianteProductoId != null) {
                varianteProductoId.getPedidosDetallesCollection().remove(pedidosDetalles);
                varianteProductoId = em.merge(varianteProductoId);
            }
            em.remove(pedidosDetalles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PedidosDetalles> findPedidosDetallesEntities() {
        return findPedidosDetallesEntities(true, -1, -1);
    }

    public List<PedidosDetalles> findPedidosDetallesEntities(int maxResults, int firstResult) {
        return findPedidosDetallesEntities(false, maxResults, firstResult);
    }

    private List<PedidosDetalles> findPedidosDetallesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PedidosDetalles.class));
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

    public PedidosDetalles findPedidosDetalles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidosDetalles.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidosDetallesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PedidosDetalles> rt = cq.from(PedidosDetalles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
