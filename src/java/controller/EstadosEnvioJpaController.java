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
import logic.Envio;
import logic.EstadosEnvio;

/**
 *
 * @author PedroD
 */
public class EstadosEnvioJpaController implements Serializable {

    public EstadosEnvioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadosEnvio estadosEnvio) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(estadosEnvio);
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

    public void edit(EstadosEnvio estadosEnvio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            estadosEnvio = em.merge(estadosEnvio);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estadosEnvio.getIdEstadoEnvio();
                if (findEstadosEnvio(id) == null) {
                    throw new NonexistentEntityException("The estadosEnvio with id " + id + " no longer exists.");
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
            EstadosEnvio estadosEnvio;
            try {
                estadosEnvio = em.getReference(EstadosEnvio.class, id);
                estadosEnvio.getIdEstadoEnvio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadosEnvio with id " + id + " no longer exists.", enfe);
            }
            em.remove(estadosEnvio);
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

    public List<EstadosEnvio> findEstadosEnvioEntities() {
        return findEstadosEnvioEntities(true, -1, -1);
    }

    public List<EstadosEnvio> findEstadosEnvioEntities(int maxResults, int firstResult) {
        return findEstadosEnvioEntities(false, maxResults, firstResult);
    }

    private List<EstadosEnvio> findEstadosEnvioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadosEnvio.class));
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

    public EstadosEnvio findEstadosEnvio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadosEnvio.class, id);
        } finally {
            em.close();
        }
    }

    public List<EstadosEnvio> findEstadosEnvio(Envio envio) {
        EntityManager em = getEntityManager();
        try {
            Query qy = em.createQuery("SELECT c FROM EstadosEnvio c WHERE c.idEnvio = :envio");
            qy.setParameter("envio", envio);
            if (qy.getResultList().isEmpty()) {
                return new ArrayList<>();
            } else {
                return qy.getResultList();
            }
        } catch (IllegalStateException e) {
               System.err.println("Exception " + e);
        } finally {
            em.close();
        }
        return new ArrayList<>();
    }

    public int getEstadosEnvioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadosEnvio> rt = cq.from(EstadosEnvio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
