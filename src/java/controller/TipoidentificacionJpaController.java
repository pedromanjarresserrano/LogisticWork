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
import logic.Tipoidentificacion;

/**
 *
 * @author PedroD
 */
public class TipoidentificacionJpaController implements Serializable {

    public TipoidentificacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipoidentificacion tipoidentificacion) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(tipoidentificacion);
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

    public void edit(Tipoidentificacion tipoidentificacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            tipoidentificacion = em.merge(tipoidentificacion);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoidentificacion.getIdTipoidentificacion();
                if (findTipoidentificacion(id) == null) {
                    throw new NonexistentEntityException("The tipoidentificacion with id " + id + " no longer exists.");
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
            Tipoidentificacion tipoidentificacion;
            try {
                tipoidentificacion = em.getReference(Tipoidentificacion.class, id);
                tipoidentificacion.getIdTipoidentificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoidentificacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoidentificacion);
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

    public List<Tipoidentificacion> findTipoidentificacionEntities() {
        return findTipoidentificacionEntities(true, -1, -1);
    }

    public List<Tipoidentificacion> findTipoidentificacionEntities(int maxResults, int firstResult) {
        return findTipoidentificacionEntities(false, maxResults, firstResult);
    }

    private List<Tipoidentificacion> findTipoidentificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipoidentificacion.class));
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

    public Tipoidentificacion findTipoidentificacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipoidentificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoidentificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipoidentificacion> rt = cq.from(Tipoidentificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Tipoidentificacion findTipoidentificacion(String name) {
        EntityManager em = getEntityManager();
        try {
            Query qy = em.createQuery("SELECT c FROM Tipoidentificacion c WHERE c.nombreTipoIdentificacion = :name");
            qy.setParameter("name", name);
            if (qy.getResultList().isEmpty()) {
                return new Tipoidentificacion();
            } else {
                return (Tipoidentificacion) qy.getResultList().get(0);
            }
        } catch (Exception e) {

        } finally {
            em.close();
        }
        return new Tipoidentificacion();
    }

}
