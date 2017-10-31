package br.com.portalCliente.entity.customer;

import br.com.portalCliente.exception.PortalClienteException;
import br.com.portalCliente.util.JpaUtils;
import br.com.portalCliente.util.PaginationUtils;
import org.apache.log4j.Logger;
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

@Configurable
public abstract class CustomerAR extends JpaUtils {

    @PersistenceContext
    transient EntityManager entityManager;

    private static CriteriaQuery<Customer> criteria;
    private static Root<Customer> root;
    private static List<Predicate> predicates;

    public static final EntityManager entityManager() {
        EntityManager em = new Customer().entityManager;
        if (em == null)
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    private static CriteriaBuilder criteriaBuilder() {
        CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        criteria = criteriaBuilder.createQuery(Customer.class);
        root = criteria.from(Customer.class);
        criteria.select(root);
        predicates = new ArrayList<Predicate>();
        return criteriaBuilder;
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
            Customer attached = (Customer) this;
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
    public Customer merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Customer merged = (Customer) this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static List<Customer> findAll() {
        return entityManager().createQuery("SELECT o FROM Customer o", Customer.class).getResultList();
    }

    public static Customer find(int id) {
        return entityManager().find(Customer.class, id);
    }

    public static Customer findByCpfCnpj(String cpfCnpj) {
        try {
            CriteriaBuilder criteriaBuilder = criteriaBuilder();
            predicates.add(criteriaBuilder.equal(root.get("cpfCnpj"), cpfCnpj));
            criteria.where(predicates.toArray(new Predicate[]{}));
            return entityManager().createQuery(criteria).getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Este método serve para consulta de Clientes
     *
     * @param name
     * @param cpfCnpj
     * @param type
     * @param firstResult
     * @param maxResults
     * @return
     */
    public static List<Customer> search(String name, String cpfCnpj, Integer type, int firstResult, int maxResults) {

        StringBuilder sql = new StringBuilder("SELECT DISTINCT(c) FROM Customer c");
        Map<String, Object> params = new HashMap<String, Object>();

        if (name != null && !name.isEmpty()) {
            validateSql(sql, "(c.name LIKE :name OR c.corporateName LIKE :name)");
            params.put("name", "%" + name + "%");
        }

        if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
            validateSql(sql, "c.cpfCnpj LIKE :cpfCnpj");
            params.put("cpfCnpj", "%" + cpfCnpj + "%");
        }

        if (type != null) {
            validateSql(sql, "c.type = :type");
            params.put("type", type);
        }

        sql.append(" ORDER BY IFNULL(c.name, c.corporateName)");

        try {
            TypedQuery<Customer> query = entityManager().createQuery(sql.toString(), Customer.class)
                    .setFirstResult(PaginationUtils.calculateFirstResultQuery(firstResult, maxResults))
                    .setMaxResults(maxResults);
            setParams(params, query);

            return query.getResultList();

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            Logger.getLogger(Customer.class.getName()).warn("Erro" + e.getMessage());
            return null;
        }
    }

    /**
     * Este método retorna a quantidade de dados existentes no BD
     * de acordo com o parâmetro informado
     *
     * @param name
     * @param cpfCnpj
     * @param type
     * @return
     */
    public static long count(String name, String cpfCnpj, Integer type, Date startCreateDate, Date endCreateDate) {

        try {

            StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT c) FROM Customer c");
            Map<String, Object> params = new HashMap<String, Object>();

            if (name != null && !name.isEmpty()) {
                validateSql(sql, "(c.name LIKE :name OR c.corporateName LIKE :name)");
                params.put("name", "%" + name + "%");
            }

            if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
                validateSql(sql, "c.cpfCnpj LIKE :cpfCnpj");
                params.put("cpfCnpj", "%" + cpfCnpj + "%");
            }

            if (type != null) {
                validateSql(sql, "c.type = :type");
                params.put("type", type);
            }

            if (startCreateDate != null && endCreateDate != null) {
                validateSql(sql, "c.createdAt BETWEEN :startCreateDate AND :endCreateDate");
                params.put("startCreateDate", startCreateDate);
                params.put("endCreateDate", endCreateDate);
            }

            TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);
            setParams(params, query);

            return query.getSingleResult();

        } catch (Exception e) {
            Logger.getLogger(Customer.class.getName()).error("CUSTOMER_COUNT ERROR: " + e.getMessage() + " - Causa: " + e.getCause());
            return 0;
        }
    }

    /**
     * Este método serve como um auto complete para os dados de Cliente
     *
     * @param value
     * @return
     */
    public static List<Customer> findAutoComplete(String value) {

        String sql = "Select c From Customer c ";
        String byName = "Where c.name LIKE :value";
        String byCorportareName = "Where c.corporateName LIKE :value";
        String byCpf = "Where c.cpfCnpj LIKE :value";


        List<Customer> result = new ArrayList<>();

        if (value == null || value.isEmpty()) {
            return entityManager().createQuery(sql, Customer.class).setMaxResults(10).getResultList();
        }

        result = entityManager().createQuery(sql.concat(byName), Customer.class)
                .setParameter("value", '%' + value + '%').setMaxResults(10).getResultList();

        if (result.size() == 0) {
            result = entityManager().createQuery(sql.concat(byCorportareName), Customer.class)
                    .setParameter("value", '%' + value + '%').setMaxResults(10).getResultList();
        }

        if (result.size() == 0) {
            result = entityManager().createQuery(sql.concat(byCpf), Customer.class)
                    .setParameter("value", '%' + value + '%').setMaxResults(10).getResultList();
        }

        return result;
    }

    /**
     * Este método procura na BD um Cliente através do email ou cnpj/cpf informado
     *
     * @return Customer Object
     */
    public static Customer findByEmailAndCpfCnpj(String cpfCnpj) {
        String sql = "SELECT c FROM " + Customer.class.getName() + " c "
                + "WHERE c.cpfCnpj = :cpfCnpj";
        try {
            TypedQuery<Customer> query = entityManager().createQuery(sql, Customer.class);

            if (cpfCnpj != null) {
                query.setParameter("cpfCnpj", cpfCnpj);
            }
            return query.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            Logger.getLogger(Customer.class.getName()).warn("Erro aqui: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método responsável por atualizar dados de um Cliente cadastrado
     * na base de dados
     *
     * @param id
     * @throws PortalClienteException
     */
    public void edit(Integer id) throws PortalClienteException {

        Customer customer = (Customer) this;

        Customer existsCustomer = Customer.findByCpfCnpj(customer.getCpfCnpj());

        if (existsCustomer != null) {
            if (customer.getId().intValue() != existsCustomer.getId().intValue()) {
                throw new PortalClienteException("Já existe um cliente cadastro com o CPF/CNPJ informado. "
                        + "Verifique e tente novamente.");
            }
        }

        customer.setUpdateAt(new Date());
        customer.setId(id);
        customer.merge();
    }

    /**
     * Este método é responsável por persistir um cliente no base
     *
     * @param customer
     * @throws PortalClienteException
     */
    public static void save(Customer customer) throws PortalClienteException {

        if (Customer.findByEmailAndCpfCnpj(customer.getCpfCnpj()) != null) {
            throw new PortalClienteException("O CPF/CNPJ informado já está cadastrado");
        }

        customer.setCreatedAt(new Date());
        customer.persist();
    }
}