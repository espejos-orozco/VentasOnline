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
import app.modelos.Clientes;
import app.modelos.DireccionesClientes;
import app.modelos.PedidosEncabezados;
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
public class DireccionesClientesJpaController implements Serializable {

    public DireccionesClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DireccionesClientes direccionesClientes) throws PreexistingEntityException, Exception {
        if (direccionesClientes.getPedidosEncabezadosCollection() == null) {
            direccionesClientes.setPedidosEncabezadosCollection(new ArrayList<PedidosEncabezados>());
        }
        if (direccionesClientes.getPedidosEncabezadosCollection1() == null) {
            direccionesClientes.setPedidosEncabezadosCollection1(new ArrayList<PedidosEncabezados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clienteId = direccionesClientes.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getClienteId());
                direccionesClientes.setClienteId(clienteId);
            }
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollection = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezadosToAttach : direccionesClientes.getPedidosEncabezadosCollection()) {
                pedidosEncabezadosCollectionPedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollectionPedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollectionPedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollection.add(pedidosEncabezadosCollectionPedidosEncabezadosToAttach);
            }
            direccionesClientes.setPedidosEncabezadosCollection(attachedPedidosEncabezadosCollection);
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollection1 = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollection1PedidosEncabezadosToAttach : direccionesClientes.getPedidosEncabezadosCollection1()) {
                pedidosEncabezadosCollection1PedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollection1PedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollection1PedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollection1.add(pedidosEncabezadosCollection1PedidosEncabezadosToAttach);
            }
            direccionesClientes.setPedidosEncabezadosCollection1(attachedPedidosEncabezadosCollection1);
            em.persist(direccionesClientes);
            if (clienteId != null) {
                clienteId.getDireccionesClientesCollection().add(direccionesClientes);
                clienteId = em.merge(clienteId);
            }
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezados : direccionesClientes.getPedidosEncabezadosCollection()) {
                DireccionesClientes oldDireccionClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados = pedidosEncabezadosCollectionPedidosEncabezados.getDireccionClienteId();
                pedidosEncabezadosCollectionPedidosEncabezados.setDireccionClienteId(direccionesClientes);
                pedidosEncabezadosCollectionPedidosEncabezados = em.merge(pedidosEncabezadosCollectionPedidosEncabezados);
                if (oldDireccionClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados != null) {
                    oldDireccionClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados.getPedidosEncabezadosCollection().remove(pedidosEncabezadosCollectionPedidosEncabezados);
                    oldDireccionClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados = em.merge(oldDireccionClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados);
                }
            }
            for (PedidosEncabezados pedidosEncabezadosCollection1PedidosEncabezados : direccionesClientes.getPedidosEncabezadosCollection1()) {
                DireccionesClientes oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1PedidosEncabezados = pedidosEncabezadosCollection1PedidosEncabezados.getDireccionClienteFacturacionId();
                pedidosEncabezadosCollection1PedidosEncabezados.setDireccionClienteFacturacionId(direccionesClientes);
                pedidosEncabezadosCollection1PedidosEncabezados = em.merge(pedidosEncabezadosCollection1PedidosEncabezados);
                if (oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1PedidosEncabezados != null) {
                    oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1PedidosEncabezados.getPedidosEncabezadosCollection1().remove(pedidosEncabezadosCollection1PedidosEncabezados);
                    oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1PedidosEncabezados = em.merge(oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1PedidosEncabezados);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDireccionesClientes(direccionesClientes.getDireccionClienteId()) != null) {
                throw new PreexistingEntityException("DireccionesClientes " + direccionesClientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DireccionesClientes direccionesClientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DireccionesClientes persistentDireccionesClientes = em.find(DireccionesClientes.class, direccionesClientes.getDireccionClienteId());
            Clientes clienteIdOld = persistentDireccionesClientes.getClienteId();
            Clientes clienteIdNew = direccionesClientes.getClienteId();
            Collection<PedidosEncabezados> pedidosEncabezadosCollectionOld = persistentDireccionesClientes.getPedidosEncabezadosCollection();
            Collection<PedidosEncabezados> pedidosEncabezadosCollectionNew = direccionesClientes.getPedidosEncabezadosCollection();
            Collection<PedidosEncabezados> pedidosEncabezadosCollection1Old = persistentDireccionesClientes.getPedidosEncabezadosCollection1();
            Collection<PedidosEncabezados> pedidosEncabezadosCollection1New = direccionesClientes.getPedidosEncabezadosCollection1();
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getClienteId());
                direccionesClientes.setClienteId(clienteIdNew);
            }
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollectionNew = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach : pedidosEncabezadosCollectionNew) {
                pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollectionNew.add(pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach);
            }
            pedidosEncabezadosCollectionNew = attachedPedidosEncabezadosCollectionNew;
            direccionesClientes.setPedidosEncabezadosCollection(pedidosEncabezadosCollectionNew);
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollection1New = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollection1NewPedidosEncabezadosToAttach : pedidosEncabezadosCollection1New) {
                pedidosEncabezadosCollection1NewPedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollection1NewPedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollection1NewPedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollection1New.add(pedidosEncabezadosCollection1NewPedidosEncabezadosToAttach);
            }
            pedidosEncabezadosCollection1New = attachedPedidosEncabezadosCollection1New;
            direccionesClientes.setPedidosEncabezadosCollection1(pedidosEncabezadosCollection1New);
            direccionesClientes = em.merge(direccionesClientes);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getDireccionesClientesCollection().remove(direccionesClientes);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getDireccionesClientesCollection().add(direccionesClientes);
                clienteIdNew = em.merge(clienteIdNew);
            }
            for (PedidosEncabezados pedidosEncabezadosCollectionOldPedidosEncabezados : pedidosEncabezadosCollectionOld) {
                if (!pedidosEncabezadosCollectionNew.contains(pedidosEncabezadosCollectionOldPedidosEncabezados)) {
                    pedidosEncabezadosCollectionOldPedidosEncabezados.setDireccionClienteId(null);
                    pedidosEncabezadosCollectionOldPedidosEncabezados = em.merge(pedidosEncabezadosCollectionOldPedidosEncabezados);
                }
            }
            for (PedidosEncabezados pedidosEncabezadosCollectionNewPedidosEncabezados : pedidosEncabezadosCollectionNew) {
                if (!pedidosEncabezadosCollectionOld.contains(pedidosEncabezadosCollectionNewPedidosEncabezados)) {
                    DireccionesClientes oldDireccionClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados = pedidosEncabezadosCollectionNewPedidosEncabezados.getDireccionClienteId();
                    pedidosEncabezadosCollectionNewPedidosEncabezados.setDireccionClienteId(direccionesClientes);
                    pedidosEncabezadosCollectionNewPedidosEncabezados = em.merge(pedidosEncabezadosCollectionNewPedidosEncabezados);
                    if (oldDireccionClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados != null && !oldDireccionClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados.equals(direccionesClientes)) {
                        oldDireccionClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados.getPedidosEncabezadosCollection().remove(pedidosEncabezadosCollectionNewPedidosEncabezados);
                        oldDireccionClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados = em.merge(oldDireccionClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados);
                    }
                }
            }
            for (PedidosEncabezados pedidosEncabezadosCollection1OldPedidosEncabezados : pedidosEncabezadosCollection1Old) {
                if (!pedidosEncabezadosCollection1New.contains(pedidosEncabezadosCollection1OldPedidosEncabezados)) {
                    pedidosEncabezadosCollection1OldPedidosEncabezados.setDireccionClienteFacturacionId(null);
                    pedidosEncabezadosCollection1OldPedidosEncabezados = em.merge(pedidosEncabezadosCollection1OldPedidosEncabezados);
                }
            }
            for (PedidosEncabezados pedidosEncabezadosCollection1NewPedidosEncabezados : pedidosEncabezadosCollection1New) {
                if (!pedidosEncabezadosCollection1Old.contains(pedidosEncabezadosCollection1NewPedidosEncabezados)) {
                    DireccionesClientes oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1NewPedidosEncabezados = pedidosEncabezadosCollection1NewPedidosEncabezados.getDireccionClienteFacturacionId();
                    pedidosEncabezadosCollection1NewPedidosEncabezados.setDireccionClienteFacturacionId(direccionesClientes);
                    pedidosEncabezadosCollection1NewPedidosEncabezados = em.merge(pedidosEncabezadosCollection1NewPedidosEncabezados);
                    if (oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1NewPedidosEncabezados != null && !oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1NewPedidosEncabezados.equals(direccionesClientes)) {
                        oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1NewPedidosEncabezados.getPedidosEncabezadosCollection1().remove(pedidosEncabezadosCollection1NewPedidosEncabezados);
                        oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1NewPedidosEncabezados = em.merge(oldDireccionClienteFacturacionIdOfPedidosEncabezadosCollection1NewPedidosEncabezados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = direccionesClientes.getDireccionClienteId();
                if (findDireccionesClientes(id) == null) {
                    throw new NonexistentEntityException("The direccionesClientes with id " + id + " no longer exists.");
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
            DireccionesClientes direccionesClientes;
            try {
                direccionesClientes = em.getReference(DireccionesClientes.class, id);
                direccionesClientes.getDireccionClienteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direccionesClientes with id " + id + " no longer exists.", enfe);
            }
            Clientes clienteId = direccionesClientes.getClienteId();
            if (clienteId != null) {
                clienteId.getDireccionesClientesCollection().remove(direccionesClientes);
                clienteId = em.merge(clienteId);
            }
            Collection<PedidosEncabezados> pedidosEncabezadosCollection = direccionesClientes.getPedidosEncabezadosCollection();
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezados : pedidosEncabezadosCollection) {
                pedidosEncabezadosCollectionPedidosEncabezados.setDireccionClienteId(null);
                pedidosEncabezadosCollectionPedidosEncabezados = em.merge(pedidosEncabezadosCollectionPedidosEncabezados);
            }
            Collection<PedidosEncabezados> pedidosEncabezadosCollection1 = direccionesClientes.getPedidosEncabezadosCollection1();
            for (PedidosEncabezados pedidosEncabezadosCollection1PedidosEncabezados : pedidosEncabezadosCollection1) {
                pedidosEncabezadosCollection1PedidosEncabezados.setDireccionClienteFacturacionId(null);
                pedidosEncabezadosCollection1PedidosEncabezados = em.merge(pedidosEncabezadosCollection1PedidosEncabezados);
            }
            em.remove(direccionesClientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DireccionesClientes> findDireccionesClientesEntities() {
        return findDireccionesClientesEntities(true, -1, -1);
    }

    public List<DireccionesClientes> findDireccionesClientesEntities(int maxResults, int firstResult) {
        return findDireccionesClientesEntities(false, maxResults, firstResult);
    }

    private List<DireccionesClientes> findDireccionesClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DireccionesClientes.class));
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

    public DireccionesClientes findDireccionesClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DireccionesClientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionesClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DireccionesClientes> rt = cq.from(DireccionesClientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
