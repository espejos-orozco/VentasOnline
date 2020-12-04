/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistencia;

import app.modelos.EstatusPedidos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import app.modelos.PedidosEncabezados;
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
public class EstatusPedidosJpaController implements Serializable {

    public EstatusPedidosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstatusPedidos estatusPedidos) {
        if (estatusPedidos.getPedidosEncabezadosCollection() == null) {
            estatusPedidos.setPedidosEncabezadosCollection(new ArrayList<PedidosEncabezados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollection = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezadosToAttach : estatusPedidos.getPedidosEncabezadosCollection()) {
                pedidosEncabezadosCollectionPedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollectionPedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollectionPedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollection.add(pedidosEncabezadosCollectionPedidosEncabezadosToAttach);
            }
            estatusPedidos.setPedidosEncabezadosCollection(attachedPedidosEncabezadosCollection);
            em.persist(estatusPedidos);
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezados : estatusPedidos.getPedidosEncabezadosCollection()) {
                EstatusPedidos oldEstatusPedidoIdOfPedidosEncabezadosCollectionPedidosEncabezados = pedidosEncabezadosCollectionPedidosEncabezados.getEstatusPedidoId();
                pedidosEncabezadosCollectionPedidosEncabezados.setEstatusPedidoId(estatusPedidos);
                pedidosEncabezadosCollectionPedidosEncabezados = em.merge(pedidosEncabezadosCollectionPedidosEncabezados);
                if (oldEstatusPedidoIdOfPedidosEncabezadosCollectionPedidosEncabezados != null) {
                    oldEstatusPedidoIdOfPedidosEncabezadosCollectionPedidosEncabezados.getPedidosEncabezadosCollection().remove(pedidosEncabezadosCollectionPedidosEncabezados);
                    oldEstatusPedidoIdOfPedidosEncabezadosCollectionPedidosEncabezados = em.merge(oldEstatusPedidoIdOfPedidosEncabezadosCollectionPedidosEncabezados);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstatusPedidos estatusPedidos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstatusPedidos persistentEstatusPedidos = em.find(EstatusPedidos.class, estatusPedidos.getEstatusPedidoId());
            Collection<PedidosEncabezados> pedidosEncabezadosCollectionOld = persistentEstatusPedidos.getPedidosEncabezadosCollection();
            Collection<PedidosEncabezados> pedidosEncabezadosCollectionNew = estatusPedidos.getPedidosEncabezadosCollection();
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollectionNew = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach : pedidosEncabezadosCollectionNew) {
                pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollectionNew.add(pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach);
            }
            pedidosEncabezadosCollectionNew = attachedPedidosEncabezadosCollectionNew;
            estatusPedidos.setPedidosEncabezadosCollection(pedidosEncabezadosCollectionNew);
            estatusPedidos = em.merge(estatusPedidos);
            for (PedidosEncabezados pedidosEncabezadosCollectionOldPedidosEncabezados : pedidosEncabezadosCollectionOld) {
                if (!pedidosEncabezadosCollectionNew.contains(pedidosEncabezadosCollectionOldPedidosEncabezados)) {
                    pedidosEncabezadosCollectionOldPedidosEncabezados.setEstatusPedidoId(null);
                    pedidosEncabezadosCollectionOldPedidosEncabezados = em.merge(pedidosEncabezadosCollectionOldPedidosEncabezados);
                }
            }
            for (PedidosEncabezados pedidosEncabezadosCollectionNewPedidosEncabezados : pedidosEncabezadosCollectionNew) {
                if (!pedidosEncabezadosCollectionOld.contains(pedidosEncabezadosCollectionNewPedidosEncabezados)) {
                    EstatusPedidos oldEstatusPedidoIdOfPedidosEncabezadosCollectionNewPedidosEncabezados = pedidosEncabezadosCollectionNewPedidosEncabezados.getEstatusPedidoId();
                    pedidosEncabezadosCollectionNewPedidosEncabezados.setEstatusPedidoId(estatusPedidos);
                    pedidosEncabezadosCollectionNewPedidosEncabezados = em.merge(pedidosEncabezadosCollectionNewPedidosEncabezados);
                    if (oldEstatusPedidoIdOfPedidosEncabezadosCollectionNewPedidosEncabezados != null && !oldEstatusPedidoIdOfPedidosEncabezadosCollectionNewPedidosEncabezados.equals(estatusPedidos)) {
                        oldEstatusPedidoIdOfPedidosEncabezadosCollectionNewPedidosEncabezados.getPedidosEncabezadosCollection().remove(pedidosEncabezadosCollectionNewPedidosEncabezados);
                        oldEstatusPedidoIdOfPedidosEncabezadosCollectionNewPedidosEncabezados = em.merge(oldEstatusPedidoIdOfPedidosEncabezadosCollectionNewPedidosEncabezados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estatusPedidos.getEstatusPedidoId();
                if (findEstatusPedidos(id) == null) {
                    throw new NonexistentEntityException("The estatusPedidos with id " + id + " no longer exists.");
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
            EstatusPedidos estatusPedidos;
            try {
                estatusPedidos = em.getReference(EstatusPedidos.class, id);
                estatusPedidos.getEstatusPedidoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estatusPedidos with id " + id + " no longer exists.", enfe);
            }
            Collection<PedidosEncabezados> pedidosEncabezadosCollection = estatusPedidos.getPedidosEncabezadosCollection();
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezados : pedidosEncabezadosCollection) {
                pedidosEncabezadosCollectionPedidosEncabezados.setEstatusPedidoId(null);
                pedidosEncabezadosCollectionPedidosEncabezados = em.merge(pedidosEncabezadosCollectionPedidosEncabezados);
            }
            em.remove(estatusPedidos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstatusPedidos> findEstatusPedidosEntities() {
        return findEstatusPedidosEntities(true, -1, -1);
    }

    public List<EstatusPedidos> findEstatusPedidosEntities(int maxResults, int firstResult) {
        return findEstatusPedidosEntities(false, maxResults, firstResult);
    }

    private List<EstatusPedidos> findEstatusPedidosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstatusPedidos.class));
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

    public EstatusPedidos findEstatusPedidos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstatusPedidos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstatusPedidosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstatusPedidos> rt = cq.from(EstatusPedidos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
