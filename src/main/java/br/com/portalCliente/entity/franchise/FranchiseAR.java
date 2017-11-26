package br.com.portalCliente.entity.franchise;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

@Configurable
public class FranchiseAR {
    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new Franchise().entityManager;
        if (em == null)
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static Franchise find(int id) {
        return entityManager().find(Franchise.class, id);
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.persist(this);
    }

    @Transactional
    public void remove() {

        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }

        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Franchise attached = (Franchise) this;
            attached = find(attached.getId());
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.flush();
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.clear();
    }

    @Transactional
    public Franchise merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }

        Franchise merged = (Franchise) this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static List<Franchise> findByProposal(int proposalId) {
        String sql = "SELECT f FROM " + Franchise.class.getName() + " f WHERE f.proposal.id = :proposalId";

        TypedQuery<Franchise> result = entityManager().createQuery(sql, Franchise.class).setParameter("proposalId", proposalId);
        try {
            return result.getResultList();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
