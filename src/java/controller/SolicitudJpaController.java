/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import logic.Solicitud;

/**
 *
 * @author PedroD
 */
public class SolicitudJpaController implements Serializable {

    public SolicitudJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solicitud solicitud) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(solicitud);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solicitud solicitud) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            solicitud = em.merge(solicitud);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = solicitud.getIdSolicitud();
                if (findSolicitud(id) == null) {
                    throw new NonexistentEntityException("The solicitud with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Solicitud solicitud;
            try {
                solicitud = em.getReference(Solicitud.class, id);
                solicitud.getIdSolicitud();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitud with id " + id + " no longer exists.", enfe);
            }
            em.remove(solicitud);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Solicitud> findSolicitudEntities() {
        return findSolicitudEntities(true, -1, -1);
    }

    public List<Solicitud> findSolicitudEntities(int maxResults, int firstResult) {
        return findSolicitudEntities(false, maxResults, firstResult);
    }

    private List<Solicitud> findSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitud.class));
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

    public Solicitud findSolicitud(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitud.class, id);
        } finally {
            em.close();
        }
    }

    public Solicitud findSolicitudForTransport(Long id) {
        EntityManager em = getEntityManager();
        try {
            Query qy = em.createQuery("SELECT c FROM Solicitud c WHERE c.idSolicitud = :id");
            qy.setParameter("id", id);
            if (qy.getResultList().isEmpty()) {
                return new Solicitud();
            } else {
                return (Solicitud) qy.getResultList().get(0);
            }
        } catch (IllegalStateException e) {
            System.err.println("Exception " + e);
        } finally {
            em.close();
        }
        return new Solicitud();
    }

    public int getSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitud> rt = cq.from(Solicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
