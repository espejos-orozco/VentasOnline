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
import app.modelos.Sucursales;
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
public class SucursalesJpaController implements Serializable {

    public SucursalesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sucursales sucursales) {
        if (sucursales.getPersonalCollection() == null) {
            sucursales.setPersonalCollection(new ArrayList<Personal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Personal> attachedPersonalCollection = new ArrayList<Personal>();
            for (Personal personalCollectionPersonalToAttach : sucursales.getPersonalCollection()) {
                personalCollectionPersonalToAttach = em.getReference(personalCollectionPersonalToAttach.getClass(), personalCollectionPersonalToAttach.getPersonalId());
                attachedPersonalCollection.add(personalCollectionPersonalToAttach);
            }
            sucursales.setPersonalCollection(attachedPersonalCollection);
            em.persist(sucursales);
            for (Personal personalCollectionPersonal : sucursales.getPersonalCollection()) {
                personalCollectionPersonal.getSucursalesCollection().add(sucursales);
                personalCollectionPersonal = em.merge(personalCollectionPersonal);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sucursales sucursales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursales persistentSucursales = em.find(Sucursales.class, sucursales.getSucursalId());
            Collection<Personal> personalCollectionOld = persistentSucursales.getPersonalCollection();
            Collection<Personal> personalCollectionNew = sucursales.getPersonalCollection();
            Collection<Personal> attachedPersonalCollectionNew = new ArrayList<Personal>();
            for (Personal personalCollectionNewPersonalToAttach : personalCollectionNew) {
                personalCollectionNewPersonalToAttach = em.getReference(personalCollectionNewPersonalToAttach.getClass(), personalCollectionNewPersonalToAttach.getPersonalId());
                attachedPersonalCollectionNew.add(personalCollectionNewPersonalToAttach);
            }
            personalCollectionNew = attachedPersonalCollectionNew;
            sucursales.setPersonalCollection(personalCollectionNew);
            sucursales = em.merge(sucursales);
            for (Personal personalCollectionOldPersonal : personalCollectionOld) {
                if (!personalCollectionNew.contains(personalCollectionOldPersonal)) {
                    personalCollectionOldPersonal.getSucursalesCollection().remove(sucursales);
                    personalCollectionOldPersonal = em.merge(personalCollectionOldPersonal);
                }
            }
            for (Personal personalCollectionNewPersonal : personalCollectionNew) {
                if (!personalCollectionOld.contains(personalCollectionNewPersonal)) {
                    personalCollectionNewPersonal.getSucursalesCollection().add(sucursales);
                    personalCollectionNewPersonal = em.merge(personalCollectionNewPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sucursales.getSucursalId();
                if (findSucursales(id) == null) {
                    throw new NonexistentEntityException("The sucursales with id " + id + " no longer exists.");
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
            Sucursales sucursales;
            try {
                sucursales = em.getReference(Sucursales.class, id);
                sucursales.getSucursalId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursales with id " + id + " no longer exists.", enfe);
            }
            Collection<Personal> personalCollection = sucursales.getPersonalCollection();
            for (Personal personalCollectionPersonal : personalCollection) {
                personalCollectionPersonal.getSucursalesCollection().remove(sucursales);
                personalCollectionPersonal = em.merge(personalCollectionPersonal);
            }
            em.remove(sucursales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sucursales> findSucursalesEntities() {
        return findSucursalesEntities(true, -1, -1);
    }

    public List<Sucursales> findSucursalesEntities(int maxResults, int firstResult) {
        return findSucursalesEntities(false, maxResults, firstResult);
    }

    private List<Sucursales> findSucursalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursales.class));
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

    public Sucursales findSucursales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursales.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursales> rt = cq.from(Sucursales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
