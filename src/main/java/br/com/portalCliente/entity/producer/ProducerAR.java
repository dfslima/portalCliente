package br.com.portalCliente.entity.producer;

import br.com.portalCliente.entity.user.UserAR;
import br.com.portalCliente.enumeration.StatusProducer;
import br.com.portalCliente.util.JpaUtils;
import br.com.portalCliente.util.PaginationUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Configurable
public class ProducerAR extends JpaUtils {

    @PersistenceContext
    transient EntityManager entityManager;

    private static CriteriaQuery<Producer> criteria;
    private static Root<Producer> root;
    private static List<Predicate> predicates;

    public static final EntityManager entityManager() {
        EntityManager em = new Producer().entityManager;
        if (em == null)
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    private static CriteriaBuilder criteriaBuilder() {
        CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        criteria = criteriaBuilder.createQuery(Producer.class);
        root = criteria.from(Producer.class);
        criteria.select(root);
        predicates = new ArrayList<Predicate>();
        return criteriaBuilder;
    }


    public static List<Producer> findAll() {
        return entityManager().createQuery("SELECT p FROM Producer p", Producer.class).getResultList();
    }

    public static Producer find(int id) {
        return entityManager().find(Producer.class, id);
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.persist(this);
    }

    /**
     * Este método salva um produtor direto pelo Admin
     */
    public static void save(String json) {
        Producer producer = Producer.fromJson(json);
        producer.setCreatedAt(new Date());
        producer.persist();
    }

    @Transactional
    public void remove() {

        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }

        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Producer attached = (Producer) this;
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
    public Producer merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Producer merged = (Producer) this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    /**
     * Este método lista os Produtores de acordo com os valores
     * passados por parâmetro
     *
     * @param name
     * @param cpf
     * @param email
     * @param status
     * @param brokerage
     * @param firstResult
     * @param maxResults
     * @return
     */
    public static List<Producer> search(String name, String cpf, String email, StatusProducer status,
                                        int brokerage, int firstResult, int maxResults) {

        String sql = "SELECT p FROM Producer p ";

        if (name != null && !name.isEmpty()) {
            if (brokerage > 0) {
                sql = sql.concat("AND ");
            } else {
                sql = sql.concat("WHERE ");
            }
            sql = sql.concat("name LIKE :name ");
        }

        if (cpf != null && !cpf.isEmpty()) {
            if (brokerage > 0 || name != null) {
                sql = sql.concat("AND ");
            } else {
                sql = sql.concat("WHERE ");
            }
            sql = sql.concat("cpf LIKE :cpf ");

        }

        if (email != null && !email.isEmpty()) {
            if (brokerage > 0 || name != null || cpf != null) {
                sql = sql.concat("AND ");
            } else {
                sql = sql.concat("WHERE ");
            }
            sql = sql.concat("email LIKE :email ");
        }

        if (status != null) {
            if (brokerage > 0 || name != null || cpf != null || email != null) {
                sql = sql.concat("AND ");
            } else {
                sql = sql.concat("WHERE ");
            }
            sql = sql.concat("status = :status ");
        }

        sql = sql.concat("ORDER BY name");

        try {
            TypedQuery<Producer> query = entityManager().createQuery(sql, Producer.class);

            if (name != null && !name.isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (cpf != null && !cpf.isEmpty()) {
                query.setParameter("cpf", "%" + cpf + "%");
            }

            if (email != null && !email.isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }

            if (status != null) {
                query.setParameter("status", status.getValue());
            }

            if (brokerage > 0) {
                query.setParameter("brokerage", brokerage);
            }

            return query.setFirstResult(PaginationUtils.calculateFirstResultQuery(firstResult, maxResults))
                    .setMaxResults(maxResults).getResultList();

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            Logger.getLogger(Producer.class.getName()).warning("Erro" + e.getMessage());
            return null;
        }
    }

    /**
     * Este método retorna a quantidade de dados existentes no BD
     * de acordo com o parâmetro informado
     *
     * @param name
     * @param cpf
     * @param email
     * @param status
     * @param brokerage
     * @return
     */
    public static long count(String name, String cpf, String email, StatusProducer status, int brokerage) {

        String sql = "Select count(*) FROM Producer p ";

        if (name != null && !name.isEmpty()) {
            if (brokerage > 0) {
                sql = sql.concat("AND ");
            } else {
                sql = sql.concat("WHERE ");
            }
            sql = sql.concat("name LIKE :name ");
        }

        if (cpf != null && !cpf.isEmpty()) {
            if (brokerage > 0 || name != null) {
                sql = sql.concat("AND ");
            } else {
                sql = sql.concat("WHERE ");
            }
            sql = sql.concat("cpf LIKE :cpf ");

        }

        if (email != null && !email.isEmpty()) {
            if (brokerage > 0 || name != null || cpf != null) {
                sql = sql.concat("AND ");
            } else {
                sql = sql.concat("WHERE ");
            }
            sql = sql.concat("email LIKE :email ");
        }

        if (status != null) {
            if (brokerage > 0 || name != null || cpf != null || email != null) {
                sql = sql.concat("AND ");
            } else {
                sql = sql.concat("WHERE ");
            }
            sql = sql.concat("status = :status ");
        }

        try {

            TypedQuery<Long> query = entityManager().createQuery(sql, Long.class);

            if (name != null && !name.isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }

            if (cpf != null && !cpf.isEmpty()) {
                query.setParameter("cpf", "%" + cpf + "%");
            }

            if (email != null && !email.isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }

            if (status != null) {
                query.setParameter("status", status.getValue());
            }

            if (brokerage > 0) {
                query.setParameter("brokerage", brokerage);
            }

            return query.getSingleResult();

        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (Exception e) {
            Logger.getLogger(Producer.class.getName()).warning("Count: " + e.getMessage());
            return 0;
        }
    }

    public static List<Producer> findByAutoComplete(String name, Integer brokerageId) {

        List<Producer> listProducers = new ArrayList<Producer>();

        StringBuilder sql = new StringBuilder("SELECT p FROM Producer p ");

        params = new HashMap<String, Object>();

        if (name != null && !name.isEmpty()) {
            validateWhere(sql, "(p.name LIKE :value OR p.cpf LIKE :value)");
            params.put("value", "%" + name + "%");
        }

        sql.append(" ORDER BY p.name");

        try {
            TypedQuery<Producer> query = entityManager().createQuery(sql.toString(), Producer.class);
            getParams(query);
            listProducers = query.getResultList();

        } catch (EmptyResultDataAccessException e) {
            listProducers = new ArrayList<Producer>();
        } catch (Exception e) {
            Logger.getLogger(UserAR.class.getName()).warning("Erro" + e.getMessage());
        }

        return listProducers;
    }

    /**
     * Este método procura na BD um produtor através do email ou cpf informado
     */
    public static Producer findByCpfAndEmail(String cpf, String email) {
        Producer producer = null;
        String sql = "SELECT p FROM Producer p ";

        if (cpf != null && !cpf.isEmpty()) {
            sql = sql.concat("WHERE p.cpf = :cpf ");
        }

        if (email != null && !email.isEmpty()) {
            if (cpf != null && !cpf.isEmpty())
                sql = sql.concat("AND ");
            else
                sql = sql.concat("WHERE ");

            sql = sql.concat("p.email = :email ");
        }

        Query query = entityManager().createQuery(sql);

        if (cpf != null && !cpf.isEmpty()) {
            query.setParameter("cpf", cpf);
        }

        if (email != null && !email.isEmpty()) {
            query.setParameter("email", email);
        }

        try {
            producer = (Producer) query.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            producer = null;
        } catch (Exception e) {
            Logger.getLogger(UserAR.class.getName()).warning("Erro" + e.getMessage());
        }

        return producer;
    }
}