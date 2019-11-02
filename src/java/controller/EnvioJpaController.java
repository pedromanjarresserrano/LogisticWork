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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import logic.Envio;
import logic.Tipoidentificacion;

/**
 *
 * @author PedroD
 */
public class EnvioJpaController implements Serializable {

    public EnvioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Envio envio) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(envio);
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

    public void edit(Envio envio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            envio = em.merge(envio);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = envio.getIdEnvio();
                if (findEnvio(id) == null) {
                    throw new NonexistentEntityException("The envio with id " + id + " no longer exists.");
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
            Envio envio;
            try {
                envio = em.getReference(Envio.class, id);
                envio.getIdEnvio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The envio with id " + id + " no longer exists.", enfe);
            }
            em.remove(envio);
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

    public List<Envio> findEnvioEntities() {
        return findEnvioEntities(true, -1, -1);
    }

    public List<Envio> findEnvioEntities(int maxResults, int firstResult) {
        return findEnvioEntities(false, maxResults, firstResult);
    }

    private List<Envio> findEnvioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Envio.class));
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
/*
    public Envio findEnvio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Envio.class, id);
        } finally {
            em.close();
        }
    }*/

    public Envio findEnvio(Long id) {
        EntityManager em = getEntityManager();
        try {
            Query qy = em.createQuery("SELECT c FROM Envio c WHERE c.idEnvio = :id");
            qy.setParameter("id", id);
            if (qy.getResultList().isEmpty()) {
                return new Envio();
            } else {
                return (Envio) qy.getResultList().get(0);
            }
        } catch (Exception e) {

        } finally {
            em.close();
        }
        return new Envio();
    }

    public int getEnvioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Envio> rt = cq.from(Envio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
