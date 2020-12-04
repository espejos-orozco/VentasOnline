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
import app.modelos.TarjetasClientes;
import app.modelos.EstatusPedidos;
import app.modelos.PedidosDetalles;
import app.modelos.PedidosEncabezados;
import app.persistencia.exceptions.IllegalOrphanException;
import app.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LeoNa
 */
public class PedidosEncabezadosJpaController implements Serializable {

    public PedidosEncabezadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PedidosEncabezados pedidosEncabezados) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clienteId = pedidosEncabezados.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getClienteId());
                pedidosEncabezados.setClienteId(clienteId);
            }
            DireccionesClientes direccionClienteId = pedidosEncabezados.getDireccionClienteId();
            if (direccionClienteId != null) {
                direccionClienteId = em.getReference(direccionClienteId.getClass(), direccionClienteId.getDireccionClienteId());
                pedidosEncabezados.setDireccionClienteId(direccionClienteId);
            }
            DireccionesClientes direccionClienteFacturacionId = pedidosEncabezados.getDireccionClienteFacturacionId();
            if (direccionClienteFacturacionId != null) {
                direccionClienteFacturacionId = em.getReference(direccionClienteFacturacionId.getClass(), direccionClienteFacturacionId.getDireccionClienteId());
                pedidosEncabezados.setDireccionClienteFacturacionId(direccionClienteFacturacionId);
            }
            TarjetasClientes tarjetaClienteId = pedidosEncabezados.getTarjetaClienteId();
            if (tarjetaClienteId != null) {
                tarjetaClienteId = em.getReference(tarjetaClienteId.getClass(), tarjetaClienteId.getTarjetaClienteId());
                pedidosEncabezados.setTarjetaClienteId(tarjetaClienteId);
            }
            EstatusPedidos estatusPedidoId = pedidosEncabezados.getEstatusPedidoId();
            if (estatusPedidoId != null) {
                estatusPedidoId = em.getReference(estatusPedidoId.getClass(), estatusPedidoId.getEstatusPedidoId());
                pedidosEncabezados.setEstatusPedidoId(estatusPedidoId);
            }
            PedidosDetalles pedidosDetalles = pedidosEncabezados.getPedidosDetalles();
            if (pedidosDetalles != null) {
                pedidosDetalles = em.getReference(pedidosDetalles.getClass(), pedidosDetalles.getPedidoEncabezadoId());
                pedidosEncabezados.setPedidosDetalles(pedidosDetalles);
            }
            em.persist(pedidosEncabezados);
            if (clienteId != null) {
                clienteId.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                clienteId = em.merge(clienteId);
            }
            if (direccionClienteId != null) {
                direccionClienteId.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                direccionClienteId = em.merge(direccionClienteId);
            }
            if (direccionClienteFacturacionId != null) {
                direccionClienteFacturacionId.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                direccionClienteFacturacionId = em.merge(direccionClienteFacturacionId);
            }
            if (tarjetaClienteId != null) {
                tarjetaClienteId.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                tarjetaClienteId = em.merge(tarjetaClienteId);
            }
            if (estatusPedidoId != null) {
                estatusPedidoId.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                estatusPedidoId = em.merge(estatusPedidoId);
            }
            if (pedidosDetalles != null) {
                PedidosEncabezados oldPedidosEncabezadosOfPedidosDetalles = pedidosDetalles.getPedidosEncabezados();
                if (oldPedidosEncabezadosOfPedidosDetalles != null) {
                    oldPedidosEncabezadosOfPedidosDetalles.setPedidosDetalles(null);
                    oldPedidosEncabezadosOfPedidosDetalles = em.merge(oldPedidosEncabezadosOfPedidosDetalles);
                }
                pedidosDetalles.setPedidosEncabezados(pedidosEncabezados);
                pedidosDetalles = em.merge(pedidosDetalles);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PedidosEncabezados pedidosEncabezados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidosEncabezados persistentPedidosEncabezados = em.find(PedidosEncabezados.class, pedidosEncabezados.getPedidoEncabezadoId());
            Clientes clienteIdOld = persistentPedidosEncabezados.getClienteId();
            Clientes clienteIdNew = pedidosEncabezados.getClienteId();
            DireccionesClientes direccionClienteIdOld = persistentPedidosEncabezados.getDireccionClienteId();
            DireccionesClientes direccionClienteIdNew = pedidosEncabezados.getDireccionClienteId();
            DireccionesClientes direccionClienteFacturacionIdOld = persistentPedidosEncabezados.getDireccionClienteFacturacionId();
            DireccionesClientes direccionClienteFacturacionIdNew = pedidosEncabezados.getDireccionClienteFacturacionId();
            TarjetasClientes tarjetaClienteIdOld = persistentPedidosEncabezados.getTarjetaClienteId();
            TarjetasClientes tarjetaClienteIdNew = pedidosEncabezados.getTarjetaClienteId();
            EstatusPedidos estatusPedidoIdOld = persistentPedidosEncabezados.getEstatusPedidoId();
            EstatusPedidos estatusPedidoIdNew = pedidosEncabezados.getEstatusPedidoId();
            PedidosDetalles pedidosDetallesOld = persistentPedidosEncabezados.getPedidosDetalles();
            PedidosDetalles pedidosDetallesNew = pedidosEncabezados.getPedidosDetalles();
            List<String> illegalOrphanMessages = null;
            if (pedidosDetallesOld != null && !pedidosDetallesOld.equals(pedidosDetallesNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PedidosDetalles " + pedidosDetallesOld + " since its pedidosEncabezados field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getClienteId());
                pedidosEncabezados.setClienteId(clienteIdNew);
            }
            if (direccionClienteIdNew != null) {
                direccionClienteIdNew = em.getReference(direccionClienteIdNew.getClass(), direccionClienteIdNew.getDireccionClienteId());
                pedidosEncabezados.setDireccionClienteId(direccionClienteIdNew);
            }
            if (direccionClienteFacturacionIdNew != null) {
                direccionClienteFacturacionIdNew = em.getReference(direccionClienteFacturacionIdNew.getClass(), direccionClienteFacturacionIdNew.getDireccionClienteId());
                pedidosEncabezados.setDireccionClienteFacturacionId(direccionClienteFacturacionIdNew);
            }
            if (tarjetaClienteIdNew != null) {
                tarjetaClienteIdNew = em.getReference(tarjetaClienteIdNew.getClass(), tarjetaClienteIdNew.getTarjetaClienteId());
                pedidosEncabezados.setTarjetaClienteId(tarjetaClienteIdNew);
            }
            if (estatusPedidoIdNew != null) {
                estatusPedidoIdNew = em.getReference(estatusPedidoIdNew.getClass(), estatusPedidoIdNew.getEstatusPedidoId());
                pedidosEncabezados.setEstatusPedidoId(estatusPedidoIdNew);
            }
            if (pedidosDetallesNew != null) {
                pedidosDetallesNew = em.getReference(pedidosDetallesNew.getClass(), pedidosDetallesNew.getPedidoEncabezadoId());
                pedidosEncabezados.setPedidosDetalles(pedidosDetallesNew);
            }
            pedidosEncabezados = em.merge(pedidosEncabezados);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                clienteIdNew = em.merge(clienteIdNew);
            }
            if (direccionClienteIdOld != null && !direccionClienteIdOld.equals(direccionClienteIdNew)) {
                direccionClienteIdOld.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                direccionClienteIdOld = em.merge(direccionClienteIdOld);
            }
            if (direccionClienteIdNew != null && !direccionClienteIdNew.equals(direccionClienteIdOld)) {
                direccionClienteIdNew.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                direccionClienteIdNew = em.merge(direccionClienteIdNew);
            }
            if (direccionClienteFacturacionIdOld != null && !direccionClienteFacturacionIdOld.equals(direccionClienteFacturacionIdNew)) {
                direccionClienteFacturacionIdOld.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                direccionClienteFacturacionIdOld = em.merge(direccionClienteFacturacionIdOld);
            }
            if (direccionClienteFacturacionIdNew != null && !direccionClienteFacturacionIdNew.equals(direccionClienteFacturacionIdOld)) {
                direccionClienteFacturacionIdNew.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                direccionClienteFacturacionIdNew = em.merge(direccionClienteFacturacionIdNew);
            }
            if (tarjetaClienteIdOld != null && !tarjetaClienteIdOld.equals(tarjetaClienteIdNew)) {
                tarjetaClienteIdOld.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                tarjetaClienteIdOld = em.merge(tarjetaClienteIdOld);
            }
            if (tarjetaClienteIdNew != null && !tarjetaClienteIdNew.equals(tarjetaClienteIdOld)) {
                tarjetaClienteIdNew.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                tarjetaClienteIdNew = em.merge(tarjetaClienteIdNew);
            }
            if (estatusPedidoIdOld != null && !estatusPedidoIdOld.equals(estatusPedidoIdNew)) {
                estatusPedidoIdOld.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                estatusPedidoIdOld = em.merge(estatusPedidoIdOld);
            }
            if (estatusPedidoIdNew != null && !estatusPedidoIdNew.equals(estatusPedidoIdOld)) {
                estatusPedidoIdNew.getPedidosEncabezadosCollection().add(pedidosEncabezados);
                estatusPedidoIdNew = em.merge(estatusPedidoIdNew);
            }
            if (pedidosDetallesNew != null && !pedidosDetallesNew.equals(pedidosDetallesOld)) {
                PedidosEncabezados oldPedidosEncabezadosOfPedidosDetalles = pedidosDetallesNew.getPedidosEncabezados();
                if (oldPedidosEncabezadosOfPedidosDetalles != null) {
                    oldPedidosEncabezadosOfPedidosDetalles.setPedidosDetalles(null);
                    oldPedidosEncabezadosOfPedidosDetalles = em.merge(oldPedidosEncabezadosOfPedidosDetalles);
                }
                pedidosDetallesNew.setPedidosEncabezados(pedidosEncabezados);
                pedidosDetallesNew = em.merge(pedidosDetallesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedidosEncabezados.getPedidoEncabezadoId();
                if (findPedidosEncabezados(id) == null) {
                    throw new NonexistentEntityException("The pedidosEncabezados with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PedidosEncabezados pedidosEncabezados;
            try {
                pedidosEncabezados = em.getReference(PedidosEncabezados.class, id);
                pedidosEncabezados.getPedidoEncabezadoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedidosEncabezados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            PedidosDetalles pedidosDetallesOrphanCheck = pedidosEncabezados.getPedidosDetalles();
            if (pedidosDetallesOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PedidosEncabezados (" + pedidosEncabezados + ") cannot be destroyed since the PedidosDetalles " + pedidosDetallesOrphanCheck + " in its pedidosDetalles field has a non-nullable pedidosEncabezados field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes clienteId = pedidosEncabezados.getClienteId();
            if (clienteId != null) {
                clienteId.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                clienteId = em.merge(clienteId);
            }
            DireccionesClientes direccionClienteId = pedidosEncabezados.getDireccionClienteId();
            if (direccionClienteId != null) {
                direccionClienteId.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                direccionClienteId = em.merge(direccionClienteId);
            }
            DireccionesClientes direccionClienteFacturacionId = pedidosEncabezados.getDireccionClienteFacturacionId();
            if (direccionClienteFacturacionId != null) {
                direccionClienteFacturacionId.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                direccionClienteFacturacionId = em.merge(direccionClienteFacturacionId);
            }
            TarjetasClientes tarjetaClienteId = pedidosEncabezados.getTarjetaClienteId();
            if (tarjetaClienteId != null) {
                tarjetaClienteId.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                tarjetaClienteId = em.merge(tarjetaClienteId);
            }
            EstatusPedidos estatusPedidoId = pedidosEncabezados.getEstatusPedidoId();
            if (estatusPedidoId != null) {
                estatusPedidoId.getPedidosEncabezadosCollection().remove(pedidosEncabezados);
                estatusPedidoId = em.merge(estatusPedidoId);
            }
            em.remove(pedidosEncabezados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PedidosEncabezados> findPedidosEncabezadosEntities() {
        return findPedidosEncabezadosEntities(true, -1, -1);
    }

    public List<PedidosEncabezados> findPedidosEncabezadosEntities(int maxResults, int firstResult) {
        return findPedidosEncabezadosEntities(false, maxResults, firstResult);
    }

    private List<PedidosEncabezados> findPedidosEncabezadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PedidosEncabezados.class));
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

    public PedidosEncabezados findPedidosEncabezados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidosEncabezados.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidosEncabezadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PedidosEncabezados> rt = cq.from(PedidosEncabezados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
