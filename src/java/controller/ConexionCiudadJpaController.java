/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import logic.ConexionCiudad;

/**
 *
 * @author PedroD
 */
public class ConexionCiudadJpaController implements Serializable {

    public ConexionCiudadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConexionCiudad conexcionCiudad) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(conexcionCiudad);
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

    public void edit(ConexionCiudad conexcionCiudad) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            conexcionCiudad = em.merge(conexcionCiudad);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = conexcionCiudad.getIdConexionCiudad();
                if (findConexcionCiudad(id) == null) {
                    throw new NonexistentEntityException("The conexcionCiudad with id " + id + " no longer exists.");
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
            ConexionCiudad conexcionCiudad;
            try {
                conexcionCiudad = em.getReference(ConexionCiudad.class, id);
                conexcionCiudad.getIdConexionCiudad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conexcionCiudad with id " + id + " no longer exists.", enfe);
            }
            em.remove(conexcionCiudad);
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

    public List<ConexionCiudad> findConexcionCiudadEntities() {
        return findConexcionCiudadEntities(true, -1, -1);
    }

    public List<ConexionCiudad> findConexcionCiudadEntities(int maxResults, int firstResult) {
        return findConexcionCiudadEntities(false, maxResults, firstResult);
    }

    private List<ConexionCiudad> findConexcionCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConexionCiudad.class));
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

    public ConexionCiudad findConexcionCiudad(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConexionCiudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getConexcionCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConexionCiudad> rt = cq.from(ConexionCiudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ConexionCiudad findConexcionCiudad(String ciudad_A, String ciudad_B) {
        EntityManager em = getEntityManager();
        try {
            Query qy = em.createQuery("SELECT c FROM ConexionCiudad c WHERE c.idCiudadA.nombre = :ciudad_A AND c.idCiudadB.nombre =:ciudad_B");
            qy.setParameter("ciudad_A", ciudad_A);
            qy.setParameter("ciudad_B", ciudad_B);
            if (qy.getResultList().isEmpty()) {
                return new ConexionCiudad();
            } else {
                return (ConexionCiudad) qy.getResultList().get(0);
            }
        } catch (IllegalStateException e) {
            System.err.println("Eception " + e);

        } finally {
            em.close();
        }
        return new ConexionCiudad();
    }
}
