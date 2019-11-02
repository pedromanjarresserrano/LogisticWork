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
import logic.Empleado;
import logic.Login;
import logic.Tipoidentificacion;

/**
 *
 * @author PedroD
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(empleado);
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

    public void edit(Empleado empleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            empleado = em.merge(empleado);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = empleado.getIdEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            em.remove(empleado);
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

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Empleado findEmpleado(String id, Tipoidentificacion tipo_id) {
        EntityManager em = getEntityManager();
        try {
            Query qy = em.createQuery("SELECT c FROM Empleado c WHERE c.identificacion =:id AND c.idTipodeidentificacion =:tipo_id");
            qy.setParameter("id", id);
            qy.setParameter("tipo_id", tipo_id);
            if (!qy.getResultList().isEmpty()) {
                return (Empleado) qy.getResultList().get(0);
            } else {
                return new Empleado();
            }
        } catch (IllegalStateException e) {
            System.err.println("Eception " + e);
        } finally {
            em.close();
        }
        return new Empleado();
    }

    public Empleado findEmpleado(String nombre) {
        EntityManager em = getEntityManager();
        try {
            Query qy = em.createQuery("SELECT c FROM Empleado c WHERE c.nombre = :nombre");
            qy.setParameter("nombre", nombre);
            if (!qy.getResultList().isEmpty()) {
                return (Empleado) qy.getResultList().get(0);
            } else {
                return new Empleado();
            }
        } catch (IllegalStateException e) {
            System.err.println("Eception " + e);
        } finally {
            em.close();
        }
        return new Empleado();
    }

    public Empleado findEmpleadoForLogin(String user) {
        EntityManager em = getEntityManager();
        try {
            Query qy = em.createQuery("SELECT c FROM Empleado c WHERE c.idLogin.user = :user");
            qy.setParameter("user", user);
            if (!qy.getResultList().isEmpty()) {
                return (Empleado) qy.getResultList().get(0);
            } else {
                return new Empleado();
            }
        } catch (IllegalStateException e) {
            System.err.println("Eception " + e);
        } finally {
            em.close();
        }
        return new Empleado();
    }

}
