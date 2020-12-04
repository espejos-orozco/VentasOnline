/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.persistencia;

import app.modelos.Clientes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import app.modelos.PedidosEncabezados;
import java.util.ArrayList;
import java.util.Collection;
import app.modelos.TarjetasClientes;
import app.modelos.DireccionesClientes;
import app.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LeoNa
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) {
        if (clientes.getPedidosEncabezadosCollection() == null) {
            clientes.setPedidosEncabezadosCollection(new ArrayList<PedidosEncabezados>());
        }
        if (clientes.getTarjetasClientesCollection() == null) {
            clientes.setTarjetasClientesCollection(new ArrayList<TarjetasClientes>());
        }
        if (clientes.getDireccionesClientesCollection() == null) {
            clientes.setDireccionesClientesCollection(new ArrayList<DireccionesClientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollection = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezadosToAttach : clientes.getPedidosEncabezadosCollection()) {
                pedidosEncabezadosCollectionPedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollectionPedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollectionPedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollection.add(pedidosEncabezadosCollectionPedidosEncabezadosToAttach);
            }
            clientes.setPedidosEncabezadosCollection(attachedPedidosEncabezadosCollection);
            Collection<TarjetasClientes> attachedTarjetasClientesCollection = new ArrayList<TarjetasClientes>();
            for (TarjetasClientes tarjetasClientesCollectionTarjetasClientesToAttach : clientes.getTarjetasClientesCollection()) {
                tarjetasClientesCollectionTarjetasClientesToAttach = em.getReference(tarjetasClientesCollectionTarjetasClientesToAttach.getClass(), tarjetasClientesCollectionTarjetasClientesToAttach.getTarjetaClienteId());
                attachedTarjetasClientesCollection.add(tarjetasClientesCollectionTarjetasClientesToAttach);
            }
            clientes.setTarjetasClientesCollection(attachedTarjetasClientesCollection);
            Collection<DireccionesClientes> attachedDireccionesClientesCollection = new ArrayList<DireccionesClientes>();
            for (DireccionesClientes direccionesClientesCollectionDireccionesClientesToAttach : clientes.getDireccionesClientesCollection()) {
                direccionesClientesCollectionDireccionesClientesToAttach = em.getReference(direccionesClientesCollectionDireccionesClientesToAttach.getClass(), direccionesClientesCollectionDireccionesClientesToAttach.getDireccionClienteId());
                attachedDireccionesClientesCollection.add(direccionesClientesCollectionDireccionesClientesToAttach);
            }
            clientes.setDireccionesClientesCollection(attachedDireccionesClientesCollection);
            em.persist(clientes);
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezados : clientes.getPedidosEncabezadosCollection()) {
                Clientes oldClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados = pedidosEncabezadosCollectionPedidosEncabezados.getClienteId();
                pedidosEncabezadosCollectionPedidosEncabezados.setClienteId(clientes);
                pedidosEncabezadosCollectionPedidosEncabezados = em.merge(pedidosEncabezadosCollectionPedidosEncabezados);
                if (oldClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados != null) {
                    oldClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados.getPedidosEncabezadosCollection().remove(pedidosEncabezadosCollectionPedidosEncabezados);
                    oldClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados = em.merge(oldClienteIdOfPedidosEncabezadosCollectionPedidosEncabezados);
                }
            }
            for (TarjetasClientes tarjetasClientesCollectionTarjetasClientes : clientes.getTarjetasClientesCollection()) {
                Clientes oldClienteIdOfTarjetasClientesCollectionTarjetasClientes = tarjetasClientesCollectionTarjetasClientes.getClienteId();
                tarjetasClientesCollectionTarjetasClientes.setClienteId(clientes);
                tarjetasClientesCollectionTarjetasClientes = em.merge(tarjetasClientesCollectionTarjetasClientes);
                if (oldClienteIdOfTarjetasClientesCollectionTarjetasClientes != null) {
                    oldClienteIdOfTarjetasClientesCollectionTarjetasClientes.getTarjetasClientesCollection().remove(tarjetasClientesCollectionTarjetasClientes);
                    oldClienteIdOfTarjetasClientesCollectionTarjetasClientes = em.merge(oldClienteIdOfTarjetasClientesCollectionTarjetasClientes);
                }
            }
            for (DireccionesClientes direccionesClientesCollectionDireccionesClientes : clientes.getDireccionesClientesCollection()) {
                Clientes oldClienteIdOfDireccionesClientesCollectionDireccionesClientes = direccionesClientesCollectionDireccionesClientes.getClienteId();
                direccionesClientesCollectionDireccionesClientes.setClienteId(clientes);
                direccionesClientesCollectionDireccionesClientes = em.merge(direccionesClientesCollectionDireccionesClientes);
                if (oldClienteIdOfDireccionesClientesCollectionDireccionesClientes != null) {
                    oldClienteIdOfDireccionesClientesCollectionDireccionesClientes.getDireccionesClientesCollection().remove(direccionesClientesCollectionDireccionesClientes);
                    oldClienteIdOfDireccionesClientesCollectionDireccionesClientes = em.merge(oldClienteIdOfDireccionesClientesCollectionDireccionesClientes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getClienteId());
            Collection<PedidosEncabezados> pedidosEncabezadosCollectionOld = persistentClientes.getPedidosEncabezadosCollection();
            Collection<PedidosEncabezados> pedidosEncabezadosCollectionNew = clientes.getPedidosEncabezadosCollection();
            Collection<TarjetasClientes> tarjetasClientesCollectionOld = persistentClientes.getTarjetasClientesCollection();
            Collection<TarjetasClientes> tarjetasClientesCollectionNew = clientes.getTarjetasClientesCollection();
            Collection<DireccionesClientes> direccionesClientesCollectionOld = persistentClientes.getDireccionesClientesCollection();
            Collection<DireccionesClientes> direccionesClientesCollectionNew = clientes.getDireccionesClientesCollection();
            Collection<PedidosEncabezados> attachedPedidosEncabezadosCollectionNew = new ArrayList<PedidosEncabezados>();
            for (PedidosEncabezados pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach : pedidosEncabezadosCollectionNew) {
                pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach = em.getReference(pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach.getClass(), pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach.getPedidoEncabezadoId());
                attachedPedidosEncabezadosCollectionNew.add(pedidosEncabezadosCollectionNewPedidosEncabezadosToAttach);
            }
            pedidosEncabezadosCollectionNew = attachedPedidosEncabezadosCollectionNew;
            clientes.setPedidosEncabezadosCollection(pedidosEncabezadosCollectionNew);
            Collection<TarjetasClientes> attachedTarjetasClientesCollectionNew = new ArrayList<TarjetasClientes>();
            for (TarjetasClientes tarjetasClientesCollectionNewTarjetasClientesToAttach : tarjetasClientesCollectionNew) {
                tarjetasClientesCollectionNewTarjetasClientesToAttach = em.getReference(tarjetasClientesCollectionNewTarjetasClientesToAttach.getClass(), tarjetasClientesCollectionNewTarjetasClientesToAttach.getTarjetaClienteId());
                attachedTarjetasClientesCollectionNew.add(tarjetasClientesCollectionNewTarjetasClientesToAttach);
            }
            tarjetasClientesCollectionNew = attachedTarjetasClientesCollectionNew;
            clientes.setTarjetasClientesCollection(tarjetasClientesCollectionNew);
            Collection<DireccionesClientes> attachedDireccionesClientesCollectionNew = new ArrayList<DireccionesClientes>();
            for (DireccionesClientes direccionesClientesCollectionNewDireccionesClientesToAttach : direccionesClientesCollectionNew) {
                direccionesClientesCollectionNewDireccionesClientesToAttach = em.getReference(direccionesClientesCollectionNewDireccionesClientesToAttach.getClass(), direccionesClientesCollectionNewDireccionesClientesToAttach.getDireccionClienteId());
                attachedDireccionesClientesCollectionNew.add(direccionesClientesCollectionNewDireccionesClientesToAttach);
            }
            direccionesClientesCollectionNew = attachedDireccionesClientesCollectionNew;
            clientes.setDireccionesClientesCollection(direccionesClientesCollectionNew);
            clientes = em.merge(clientes);
            for (PedidosEncabezados pedidosEncabezadosCollectionOldPedidosEncabezados : pedidosEncabezadosCollectionOld) {
                if (!pedidosEncabezadosCollectionNew.contains(pedidosEncabezadosCollectionOldPedidosEncabezados)) {
                    pedidosEncabezadosCollectionOldPedidosEncabezados.setClienteId(null);
                    pedidosEncabezadosCollectionOldPedidosEncabezados = em.merge(pedidosEncabezadosCollectionOldPedidosEncabezados);
                }
            }
            for (PedidosEncabezados pedidosEncabezadosCollectionNewPedidosEncabezados : pedidosEncabezadosCollectionNew) {
                if (!pedidosEncabezadosCollectionOld.contains(pedidosEncabezadosCollectionNewPedidosEncabezados)) {
                    Clientes oldClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados = pedidosEncabezadosCollectionNewPedidosEncabezados.getClienteId();
                    pedidosEncabezadosCollectionNewPedidosEncabezados.setClienteId(clientes);
                    pedidosEncabezadosCollectionNewPedidosEncabezados = em.merge(pedidosEncabezadosCollectionNewPedidosEncabezados);
                    if (oldClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados != null && !oldClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados.equals(clientes)) {
                        oldClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados.getPedidosEncabezadosCollection().remove(pedidosEncabezadosCollectionNewPedidosEncabezados);
                        oldClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados = em.merge(oldClienteIdOfPedidosEncabezadosCollectionNewPedidosEncabezados);
                    }
                }
            }
            for (TarjetasClientes tarjetasClientesCollectionOldTarjetasClientes : tarjetasClientesCollectionOld) {
                if (!tarjetasClientesCollectionNew.contains(tarjetasClientesCollectionOldTarjetasClientes)) {
                    tarjetasClientesCollectionOldTarjetasClientes.setClienteId(null);
                    tarjetasClientesCollectionOldTarjetasClientes = em.merge(tarjetasClientesCollectionOldTarjetasClientes);
                }
            }
            for (TarjetasClientes tarjetasClientesCollectionNewTarjetasClientes : tarjetasClientesCollectionNew) {
                if (!tarjetasClientesCollectionOld.contains(tarjetasClientesCollectionNewTarjetasClientes)) {
                    Clientes oldClienteIdOfTarjetasClientesCollectionNewTarjetasClientes = tarjetasClientesCollectionNewTarjetasClientes.getClienteId();
                    tarjetasClientesCollectionNewTarjetasClientes.setClienteId(clientes);
                    tarjetasClientesCollectionNewTarjetasClientes = em.merge(tarjetasClientesCollectionNewTarjetasClientes);
                    if (oldClienteIdOfTarjetasClientesCollectionNewTarjetasClientes != null && !oldClienteIdOfTarjetasClientesCollectionNewTarjetasClientes.equals(clientes)) {
                        oldClienteIdOfTarjetasClientesCollectionNewTarjetasClientes.getTarjetasClientesCollection().remove(tarjetasClientesCollectionNewTarjetasClientes);
                        oldClienteIdOfTarjetasClientesCollectionNewTarjetasClientes = em.merge(oldClienteIdOfTarjetasClientesCollectionNewTarjetasClientes);
                    }
                }
            }
            for (DireccionesClientes direccionesClientesCollectionOldDireccionesClientes : direccionesClientesCollectionOld) {
                if (!direccionesClientesCollectionNew.contains(direccionesClientesCollectionOldDireccionesClientes)) {
                    direccionesClientesCollectionOldDireccionesClientes.setClienteId(null);
                    direccionesClientesCollectionOldDireccionesClientes = em.merge(direccionesClientesCollectionOldDireccionesClientes);
                }
            }
            for (DireccionesClientes direccionesClientesCollectionNewDireccionesClientes : direccionesClientesCollectionNew) {
                if (!direccionesClientesCollectionOld.contains(direccionesClientesCollectionNewDireccionesClientes)) {
                    Clientes oldClienteIdOfDireccionesClientesCollectionNewDireccionesClientes = direccionesClientesCollectionNewDireccionesClientes.getClienteId();
                    direccionesClientesCollectionNewDireccionesClientes.setClienteId(clientes);
                    direccionesClientesCollectionNewDireccionesClientes = em.merge(direccionesClientesCollectionNewDireccionesClientes);
                    if (oldClienteIdOfDireccionesClientesCollectionNewDireccionesClientes != null && !oldClienteIdOfDireccionesClientesCollectionNewDireccionesClientes.equals(clientes)) {
                        oldClienteIdOfDireccionesClientesCollectionNewDireccionesClientes.getDireccionesClientesCollection().remove(direccionesClientesCollectionNewDireccionesClientes);
                        oldClienteIdOfDireccionesClientesCollectionNewDireccionesClientes = em.merge(oldClienteIdOfDireccionesClientesCollectionNewDireccionesClientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientes.getClienteId();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getClienteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            Collection<PedidosEncabezados> pedidosEncabezadosCollection = clientes.getPedidosEncabezadosCollection();
            for (PedidosEncabezados pedidosEncabezadosCollectionPedidosEncabezados : pedidosEncabezadosCollection) {
                pedidosEncabezadosCollectionPedidosEncabezados.setClienteId(null);
                pedidosEncabezadosCollectionPedidosEncabezados = em.merge(pedidosEncabezadosCollectionPedidosEncabezados);
            }
            Collection<TarjetasClientes> tarjetasClientesCollection = clientes.getTarjetasClientesCollection();
            for (TarjetasClientes tarjetasClientesCollectionTarjetasClientes : tarjetasClientesCollection) {
                tarjetasClientesCollectionTarjetasClientes.setClienteId(null);
                tarjetasClientesCollectionTarjetasClientes = em.merge(tarjetasClientesCollectionTarjetasClientes);
            }
            Collection<DireccionesClientes> direccionesClientesCollection = clientes.getDireccionesClientesCollection();
            for (DireccionesClientes direccionesClientesCollectionDireccionesClientes : direccionesClientesCollection) {
                direccionesClientesCollectionDireccionesClientes.setClienteId(null);
                direccionesClientesCollectionDireccionesClientes = em.merge(direccionesClientesCollectionDireccionesClientes);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
