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
import app.modelos.PedidosEncabezados;
import app.modelos.TarjetasClientes;
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
public class TarjetasClientesJpaController implements Serializable {

    public TarjetasClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TarjetasClientes tarjetasClientes) throws PreexistingEntityException, Exception {
        if (tarjetasClientes.getPedidosEncabezadosCollection() == null) {
            tarjetasClientes.setPedidosEncabezadosCollection(new ArrayList<PedidosEncabezados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clienteId = tarjetasClientes.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getClienteId());
                tarjetasClientes.setClienteId(clienteId);
            }
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollection = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezadosToAttach : tarjetasClientes.getPedidosEncabezadosCollection()) {
                pedidosEncabezadosCollectionPedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollectionPedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollectionPedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollection.add(pedidosEncabezadosCollectionPedidosEncabezadosToAttach);
            }
            tarjetasClientes.setPedidosEncabezadosCollection(attachedPedidosEncabezadosCollection);
            em.persist(tarjetasClientes);
            if (clienteId != null) {
                clienteId.getTarjetasClientesCollection().add(tarjetasClientes);
                clienteId = em.merge(clienteId);
            }
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezados : tarjetasClientes.getPedidosEncabezadosCollection()) {
                TarjetasClientes oldTarjetaClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados = pedidosEncabezadosCollectionPedidosEncabezados.getTarjetaClienteId();
                pedidosEncabezadosCollectionPedidosEncabezados.setTarjetaClienteId(tarjetasClientes);
                pedidosEncabezadosCollectionPedidosEncabezados = em.merge(pedidosEncabezadosCollectionPedidosEncabezados);
                if (oldTarjetaClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados != null) {
                    oldTarjetaClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados.getPedidosEncabezadosCollection().remove(pedidosEncabezadosCollectionPedidosEncabezados);
                    oldTarjetaClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados = em.merge(oldTarjetaClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarjetasClientes(tarjetasClientes.getTarjetaClienteId()) != null) {
                throw new PreexistingEntityException("TarjetasClientes " + tarjetasClientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TarjetasClientes tarjetasClientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TarjetasClientes persistentTarjetasClientes = em.find(TarjetasClientes.class, tarjetasClientes.getTarjetaClienteId());
            Clientes clienteIdOld = persistentTarjetasClientes.getClienteId();
            Clientes clienteIdNew = tarjetasClientes.getClienteId();
            Collection<PedidosEncabezados> pedidosEncabezadosCollectionOld = persistentTarjetasClientes.getPedidosEncabezadosCollection();
            Collection<PedidosEncabezados> pedidosEncabezadosCollectionNew = tarjetasClientes.getPedidosEncabezadosCollection();
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getClienteId());
                tarjetasClientes.setClienteId(clienteIdNew);
            }
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollectionNew = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach : pedidosEncabezadosCollectionNew) {
                pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollectionNew.add(pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach);
            }
            pedidosEncabezadosCollectionNew = attachedPedidosEncabezadosCollectionNew;
            tarjetasClientes.setPedidosEncabezadosCollection(pedidosEncabezadosCollectionNew);
            tarjetasClientes = em.merge(tarjetasClientes);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getTarjetasClientesCollection().remove(tarjetasClientes);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getTarjetasClientesCollection().add(tarjetasClientes);
                clienteIdNew = em.merge(clienteIdNew);
            }
            for (PedidosEncabezados pedidosEncabezadosCollectionOldPedidosEncabezados : pedidosEncabezadosCollectionOld) {
                if (!pedidosEncabezadosCollectionNew.contains(pedidosEncabezadosCollectionOldPedidosEncabezados)) {
                    pedidosEncabezadosCollectionOldPedidosEncabezados.setTarjetaClienteId(null);
                    pedidosEncabezadosCollectionOldPedidosEncabezados = em.merge(pedidosEncabezadosCollectionOldPedidosEncabezados);
                }
            }
            for (PedidosEncabezados pedidosEncabezadosCollectionNewPedidosEncabezados : pedidosEncabezadosCollectionNew) {
                if (!pedidosEncabezadosCollectionOld.contains(pedidosEncabezadosCollectionNewPedidosEncabezados)) {
                    TarjetasClientes oldTarjetaClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados = pedidosEncabezadosCollectionNewPedidosEncabezados.getTarjetaClienteId();
                    pedidosEncabezadosCollectionNewPedidosEncabezados.setTarjetaClienteId(tarjetasClientes);
                    pedidosEncabezadosCollectionNewPedidosEncabezados = em.merge(pedidosEncabezadosCollectionNewPedidosEncabezados);
                    if (oldTarjetaClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados != null && !oldTarjetaClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados.equals(tarjetasClientes)) {
                        oldTarjetaClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados.getPedidosEncabezadosCollection().remove(pedidosEncabezadosCollectionNewPedidosEncabezados);
                        oldTarjetaClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados = em.merge(oldTarjetaClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tarjetasClientes.getTarjetaClienteId();
                if (findTarjetasClientes(id) == null) {
                    throw new NonexistentEntityException("The tarjetasClientes with id " + id + " no longer exists.");
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
            TarjetasClientes tarjetasClientes;
            try {
                tarjetasClientes = em.getReference(TarjetasClientes.class, id);
                tarjetasClientes.getTarjetaClienteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarjetasClientes with id " + id + " no longer exists.", enfe);
            }
            Clientes clienteId = tarjetasClientes.getClienteId();
            if (clienteId != null) {
                clienteId.getTarjetasClientesCollection().remove(tarjetasClientes);
                clienteId = em.merge(clienteId);
            }
            Collection<PedidosEncabezados> pedidosEncabezadosCollection = tarjetasClientes.getPedidosEncabezadosCollection();
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezados : pedidosEncabezadosCollection) {
                pedidosEncabezadosCollectionPedidosEncabezados.setTarjetaClienteId(null);
                pedidosEncabezadosCollectionPedidosEncabezados = em.merge(pedidosEncabezadosCollectionPedidosEncabezados);
            }
            em.remove(tarjetasClientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TarjetasClientes> findTarjetasClientesEntities() {
        return findTarjetasClientesEntities(true, -1, -1);
    }

    public List<TarjetasClientes> findTarjetasClientesEntities(int maxResults, int firstResult) {
        return findTarjetasClientesEntities(false, maxResults, firstResult);
    }

    private List<TarjetasClientes> findTarjetasClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TarjetasClientes.class));
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

    public TarjetasClientes findTarjetasClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TarjetasClientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarjetasClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TarjetasClientes> rt = cq.from(TarjetasClientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
