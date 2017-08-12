package com.example.java.web.heroku.template.daos;

import com.example.java.web.heroku.template.entities.BaseEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Andres Canavesi
 * @param <T>
 */
public abstract class DaoBase<T extends BaseEntity> {

    private static final Logger LOG = Logger.getLogger(DaoBase.class.getName());

    /**
     *
     */
    protected EntityManagerFactory entityManagerFactory;

    /**
     *
     */
    protected EntityManager entityManager;

    /**
     *
     */
    protected Class<T> type;

    private DaoBase() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    private void tryCloseEntityManager() {
        try {
            if (entityManager != null) {
                entityManager.close();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error closing entity manager", e);
        }

    }

    /**
     * Default constructor
     *
     * @param type entity class
     */
    public DaoBase(Class<T> type) {
        this();
        this.type = type;
    }

    /**
     *
     * @param entities
     */
    public void detach(List<T> entities) {
        for (T t : entities) {
            detach(t);
        }
    }

    /**
     *
     * @param entity
     */
    public void detach(T entity) {
        entityManager.detach(entity);
    }

    /**
     *
     * @param entity
     * @return
     */
    public boolean isDetached(T entity) {
        return !entityManager.contains(entity);
    }

    /**
     * Stores an instance of the entity class in the database
     *
     * @param t
     * @return
     */
    private T create(T t) {
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        entityManager.refresh(t);
        return t;

    }

    /**
     * Updates the entity instance
     *
     * @param t
     * @return the object that is updated
     */
    private T update(T t) {
        entityManager.getTransaction().begin();
        T entity = (T) entityManager.merge(t);
        entityManager.getTransaction().commit();
        return entity;

    }

    /**
     *
     * @param list
     * @param owner
     */
    private void create(List<T> list) {
        for (T t : list) {
            if (t.getId() == null) {
                t.setCreatedAt(new Date());
                t.setUpdatedAt(new Date());
            } else {
                t.setUpdatedAt(new Date());
            }
            entityManager.persist(t);
        }
        entityManager.flush();
    }

    /**
     *
     * @param entity
     */
    public void save(T entity) {
        LOG.log(Level.INFO, "Saving {0} ...", type.getSimpleName());
        if (entity.getId() == null) {
            create(entity);
        } else {
            update(entity);
        }
        LOG.log(Level.INFO, "{0} saved", type.getSimpleName());
    }

    /**
     *
     * @param queryString
     * @param parameters
     * @param start
     * @param end
     * @return
     */
    public List<T> findWithQueryString(String queryString, Map parameters, int start, int end) {
        try {
            Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
            Query query = entityManager.createQuery(queryString);
            query.setMaxResults(end - start);
            query.setFirstResult(start);
            for (Map.Entry<String, Object> entry : rawParameters) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList();
        }

    }
}
