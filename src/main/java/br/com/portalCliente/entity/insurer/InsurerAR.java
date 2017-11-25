package br.com.portalCliente.entity.insurer;

import br.com.portalCliente.entity.user.UserAR;
import br.com.portalCliente.exception.PortalClienteException;
import br.com.portalCliente.util.JpaUtils;
import br.com.portalCliente.util.PaginationUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.logging.Logger;

@Configurable
public class InsurerAR extends JpaUtils {

    @PersistenceContext
    transient EntityManager entityManager;

    private static CriteriaQuery<Insurer> criteria;
    private static Root<Insurer> root;
    private static List<Predicate> predicates;

    public static final EntityManager entityManager() {
        EntityManager em = new Insurer().entityManager;
        if (em == null)
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    private static CriteriaBuilder criteriaBuilder() {
        CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        criteria = criteriaBuilder.createQuery(Insurer.class);
        root = criteria.from(Insurer.class);
        criteria.select(root);
        predicates = new ArrayList<Predicate>();
        return criteriaBuilder;
    }

    public static List<Insurer> findAll() {
        return entityManager().createQuery("SELECT i FROM " + Insurer.class.getName() + " i", Insurer.class).getResultList();
    }

    public static Insurer find(int id) {
        return entityManager().find(Insurer.class, id);
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
            Insurer attached = (Insurer) this;
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
    public Insurer merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Insurer merged = (Insurer) this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static void save(String json) throws PortalClienteException {
        try {

            Insurer insurer = Insurer.fromJson(json);
            insurer.setCreatedAt(new Date());
            insurer.persist();

        } catch (Exception e) {
            throw new PortalClienteException(MESSAGE_ERROR_GENERAL);
        }
    }

    public static void edit(String json, int id) throws PortalClienteException {
        try {

            Insurer insurer = Insurer.fromJson(json);
            insurer.setId(id);
            insurer.setUpdateAt(new Date());
            insurer.merge();

        } catch (Exception e) {
            throw new PortalClienteException(MESSAGE_ERROR_GENERAL);
        }
    }

    /**
     * Este método elimina a relação entre Seguradora e Corretora
     *
     * @return
     * @throws Exception
     */
    public static void delete(int id) throws Exception {

        try {

            Insurer insurer = Insurer.find(id);
            insurer.remove();

        } catch (Exception e) {
            throw new PortalClienteException(MESSAGE_ERROR_GENERAL);
        }
    }

    public static List<Insurer> search(String name, String cnpj, int firstResult, int maxResults, int userId) {

        StringBuilder sql = new StringBuilder("SELECT DISTINCT(i) FROM Insurer i");
        Map<String, Object> params = new HashMap<>();


        if (name != null && !name.isEmpty()) {
            validateSql(sql, "(i.corporateName LIKE :name)");
            params.put("name", "%" + name + "%");
        }

        if (cnpj != null && !cnpj.isEmpty()) {
            validateSql(sql, "i.cnpj LIKE :cnpj");
            params.put("cnpj", "%" + cnpj + "%");
        }

        validateSql(sql, "i.user.id = :userId");
        params.put("userId", userId);

        sql.append(" ORDER BY i.corporateName");

        try {
            TypedQuery<Insurer> query = entityManager().createQuery(sql.toString(), Insurer.class)
                    .setFirstResult(PaginationUtils.calculateFirstResultQuery(firstResult, maxResults))
                    .setMaxResults(maxResults);

            setParams(params, query);

            return query.getResultList();

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            Logger.getLogger(Insurer.class.getName()).warning("Erro" + e.getMessage());
            return null;
        }
    }

    public static long count(String name, String cnpj, int userId) {

        StringBuilder sql = new StringBuilder("SELECT COUNT(i) FROM Insurer i");
        Map<String, Object> params = new HashMap<String, Object>();

        if (name != null && !name.isEmpty()) {
            validateSql(sql, "(i.corporateName LIKE :name)");
            params.put("name", "%" + name + "%");
        }

        if (cnpj != null && !cnpj.isEmpty()) {
            validateSql(sql, "i.cnpj LIKE :cnpj");
            params.put("cnpj", "%" + cnpj + "%");
        }

        validateSql(sql, "i.user.id = :userId");
        params.put("userId", userId);

        sql.append(" ORDER BY i.corporateName");

        try {
            TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);
            setParams(params, query);

            return query.getSingleResult();

        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (Exception e) {
            Logger.getLogger(Insurer.class.getName()).warning("Erro" + e.getMessage());
            return 0;
        }
    }

    public static List<Insurer> findByAutoComplete(String value, Integer userId) {

        List<Insurer> listInsurers = new ArrayList<Insurer>();

        StringBuilder sql = new StringBuilder("SELECT I FROM Insurer I ");

        params = new HashMap<String, Object>();

        if (value != null && !value.isEmpty()) {
            validateWhere(sql, "(I.corporateName LIKE :value OR I.cnpj LIKE :value)");
            params.put("value", "%" + value + "%");
        }

        validateSql(sql, "i.user.id = :userId");
        params.put("userId", userId);

        sql.append(" ORDER BY I.corporateName");

        try {
            TypedQuery<Insurer> query = entityManager().createQuery(sql.toString(), Insurer.class);
            getParams(query);
            listInsurers = query.getResultList();

        } catch (EmptyResultDataAccessException e) {
            listInsurers = new ArrayList<Insurer>();
        } catch (Exception e) {
            Logger.getLogger(UserAR.class.getName()).warning("Erro" + e.getMessage());
        }

        return listInsurers;
    }
}