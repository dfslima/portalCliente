package br.com.portalCliente.entity.proposal;

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
public class ProposalAR extends JpaUtils {

    @PersistenceContext
    transient EntityManager entityManager;

    private static CriteriaQuery<Proposal> criteria;
    private static Root<Proposal> root;
    private static List<Predicate> predicates;

    public static final EntityManager entityManager() {
        EntityManager em = new Proposal().entityManager;
        if (em == null)
            throw new IllegalStateException(
                    "Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    private static CriteriaBuilder criteriaBuilder() {
        CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        criteria = criteriaBuilder.createQuery(Proposal.class);
        root = criteria.from(Proposal.class);
        criteria.select(root);
        predicates = new ArrayList<Predicate>();
        return criteriaBuilder;
    }

    @Transactional
    public void persist() throws PortalClienteException {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }

        this.entityManager.persist(this);
    }

    @Transactional
    public void remove() throws PortalClienteException {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Proposal attached = (Proposal) this;
            attached = find(attached.getId());
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() throws PortalClienteException {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.flush();
    }

    @Transactional
    public void clear() throws PortalClienteException {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.clear();
    }

    @Transactional
    public Proposal merge() throws PortalClienteException {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Proposal merged = (Proposal) this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static List<Proposal> findAll() throws PortalClienteException {
        return entityManager().createQuery("SELECT p FROM Proposal p order by p.id asc", Proposal.class).getResultList();
    }

    public static Proposal find(int id) throws PortalClienteException {
        return entityManager().find(Proposal.class, id);
    }

    public static Proposal findByProposalNumber(String proposalNumber) throws PortalClienteException {
        try {
            return entityManager()
                    .createQuery("SELECT P FROM Proposal P WHERE P.proposalNumber = :proposalNumber", Proposal.class)
                    .setParameter("proposalNumber", proposalNumber).getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Este método serve para consulta de Propostas
     *
     * @param proposal
     * @param board
     * @param name
     * @param cpfCnpj
     * @param firstResult
     * @param maxResults
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Proposal> search(String proposal, String board, Integer customerId, String name, String cpfCnpj,
                                        Date startDate, Date endDate, Integer insurerId, Integer producerId,
                                        int firstResult, int maxResults, int userId) throws PortalClienteException {

        StringBuilder sql = new StringBuilder("SELECT DISTINCT p FROM Proposal p");
        Map<String, Object> params = new HashMap<String, Object>();

        if (board != null && !board.isEmpty()) {
            validateSql(sql, "p.property.vehicle.vehicleLicensePlate LIKE :vehicleLicensePlate");
            params.put("vehicleLicensePlate", "%" + board + "%");
        }

        if (proposal != null && !proposal.isEmpty()) {
            validateSql(sql, "p.proposalNumber LIKE :proposal");
            params.put("proposal", "%" + proposal + "%");
        }

        if (customerId != null) {
            validateSql(sql, "p.customer.id = :customerId");
            params.put("customerId", customerId);
        }

        if (name != null && !name.isEmpty()) {
            validateSql(sql, "p.customer.name LIKE :name");
            params.put("name", "%" + name + "%");
        }

        if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
            validateSql(sql, "p.customer.cpfCnpj LIKE :cpfCnpj");
            params.put("cpfCnpj", "%" + cpfCnpj + "%");
        }

        if (insurerId != null) {
            validateSql(sql, "p.insurer.id = :insurerId");
            params.put("insurerId", insurerId);
        }

        if (producerId != null) {
            validateSql(sql, "p.producer.id = :producerId");
            params.put("producerId", producerId);
        }

        if (startDate != null && endDate != null) {

            validateSql(sql, "p.createdAt BETWEEN :startTransmissionDate AND :endTransmissionDate");
            params.put("startTransmissionDate", startDate);
            params.put("endTransmissionDate", endDate);
        } else if (startDate != null || endDate != null) {

            if (startDate != null && endDate == null) {
                validateSql(sql, "p.createdAt = :startTransmissionDate");
                params.put("startTransmissionDate", startDate);
            } else if (startDate == null && endDate != null) {
                validateSql(sql, "p.createdAt = :endTransmissionDate");
                params.put("endTransmissionDate", endDate);
            }
        }

        validateSql(sql, "p.user.id = :userId");
        params.put("userId", userId);

        sql.append(" ORDER BY p.id DESC");

        TypedQuery<Proposal> query = entityManager().createQuery(sql.toString(), Proposal.class);
        setParams(params, query);

        return query
                .setFirstResult(PaginationUtils.calculateFirstResultQuery(firstResult, maxResults))
                .setMaxResults(maxResults).getResultList();
    }

    /**
     * Este método retorna a quantidade de dados existentes no BD de acordo com o
     * parâmetro informado
     *
     * @param proposal
     * @param board
     * @param name
     * @param cpfCnpj
     * @return
     */
    public static long count(String proposal, String board, Integer customerId, String name, String cpfCnpj,
                             Date startTransmissionDate, Date endTransmissionDate, Integer insurerId, Integer producerId, int userId) throws PortalClienteException {

        try {
            StringBuilder sql = new StringBuilder("SELECT DISTINCT COUNT(*) FROM Proposal p");
            Map<String, Object> params = new HashMap<String, Object>();

            if (board != null && !board.isEmpty()) {
                validateSql(sql, "p.property.vehicle.vehicleLicensePlate LIKE :vehicleLicensePlate");
                params.put("vehicleLicensePlate", "%" + board + "%");
            }

            if (proposal != null && !proposal.isEmpty()) {
                validateSql(sql, "p.proposalNumber LIKE :proposal");
                params.put("proposal", "%" + proposal + "%");
            }

            if (customerId != null) {
                validateSql(sql, "p.customer.id = :customerId");
                params.put("customerId", customerId);
            }

            if (name != null && !name.isEmpty()) {
                validateSql(sql, "p.customer.name LIKE :name");
                params.put("name", "%" + name + "%");
            }

            if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
                validateSql(sql, "p.customer.cpfCnpj LIKE :cpfCnpj");
                params.put("cpfCnpj", "%" + cpfCnpj + "%");
            }

            if (insurerId != null) {
                validateSql(sql, "p.insurer.id = :insurerId");
                params.put("insurerId", insurerId);
            }

            if (producerId != null) {
                validateSql(sql, "p.producer.id = :producerId");
                params.put("producerId", producerId);
            }

            if (startTransmissionDate != null && endTransmissionDate != null) {

                validateSql(sql, "p.createdAt BETWEEN :startTransmissionDate AND :endTransmissionDate");
                params.put("startTransmissionDate", startTransmissionDate);
                params.put("endTransmissionDate", endTransmissionDate);
            } else if (startTransmissionDate != null || endTransmissionDate != null) {

                if (startTransmissionDate != null && endTransmissionDate == null) {
                    validateSql(sql, "p.createdAt = :startTransmissionDate");
                    params.put("startTransmissionDate", startTransmissionDate);
                } else if (startTransmissionDate == null && endTransmissionDate != null) {
                    validateSql(sql, "p.createdAt = :endTransmissionDate");
                    params.put("endTransmissionDate", endTransmissionDate);
                }
            }

            validateSql(sql, "p.user.id = :userId");
            params.put("userId", userId);

            TypedQuery<Long> query = entityManager().createQuery(sql.toString(), Long.class);
            setParams(params, query);

            return query.getSingleResult();

        } catch (Exception e) {
            Logger.getLogger(Proposal.class.getName()).error("Proposal_COUNT ERROR: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Método criado para aplicar as regras de criação de contas a pagar e a receber
     * para cada proposta inserida
     *
     * @throws PortalClienteException
     * @throws PortalClienteException
     */
    @Transactional
    public static void save(Proposal proposal) throws PortalClienteException {

        Proposal ProposalConsolidated = findByProposalNumber(proposal.getProposalNumber());

        if (ProposalConsolidated == null) {

            if (existsProposalForProperty(proposal.getProperty().getId())) {

                try {

                    proposal.persist();

                } catch (PortalClienteException e) {

                    throw new PortalClienteException("Erro ao tentar salvar a proposta. Favor, tente novamente");
                }

            } else {
                throw new PortalClienteException("Já existe uma proposta vigente para esta propriedade");
            }
        } else {
            throw new PortalClienteException("A proposta número " + proposal.getProposalNumber() + " já está cadastrada.");
        }
    }

    /**
     * Validação ao selecionar uma propriedade no cadastro de proposta, para checar
     * se já existe uma proposta com vigência para o mesma, pois se existir trava-se
     * para salvamento.
     *
     * @param propertyId
     * @return
     */
    public static boolean existsProposalForProperty(int propertyId) throws PortalClienteException {

        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Proposal> criteria = criteriaBuilder.createQuery(Proposal.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Proposal> root = criteria.from(Proposal.class);
        criteria.select(root);

        predicates.add(criteriaBuilder.equal(root.join("property").get("id"), propertyId));

        criteria.where(predicates.toArray(new Predicate[]{}));

        List<Proposal> policies = entityManager().createQuery(criteria).getResultList();

        for (Proposal Proposal : policies) {

            // Verifica se a vigência da proposta encontrada para a propriedade selecionada ainda está dentro do prazo
            if (hasValidity(Proposal.getEndTerm())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Método responsável por checar a vigência de uma proposta
     *
     * @param finalDate
     * @return
     */
    public static boolean hasValidity(Date finalDate) {

        Calendar current_date = Calendar.getInstance();
        current_date.setTime(new Date());

        Calendar end_term = Calendar.getInstance();
        end_term.setTime(finalDate);
        end_term.set(Calendar.HOUR_OF_DAY, 23);
        end_term.set(Calendar.MINUTE, 59);
        end_term.set(Calendar.SECOND, 59);

        if (current_date.before(end_term)) {
            return true; // Existe uma proposta vigente para a propriedade informada
        }

        return false;
    }

    @Transactional
    public static Proposal edit(Proposal Proposal) throws PortalClienteException {

        try {
            Proposal ProposalValidate = find(Proposal.getId());
            Proposal.merge();

        } catch (Exception e) {
            throw new PortalClienteException(MESSAGE_ERROR_GENERAL);
        }

        return Proposal;
    }

    /*
     * Método criado para validar regras antes de deletar uma proposta do banco de
     * dados
     */
    public void delete(Integer id) throws PortalClienteException {

        try {
            Proposal proposal = Proposal.find(id);
            proposal.remove();

        } catch (Exception e) {
            throw new PortalClienteException(MESSAGE_ERROR_GENERAL);
        }
    }

    /**
     * Este método checa se já existe uma apólice para um cliente
     *
     * @param id
     * @return Proposal Object
     */
    public static boolean findByCustomerId(int id) throws PortalClienteException {

        String sql = "SELECT p FROM " + Proposal.class.getName() + " p " + "JOIN p.customer c " + "WHERE c.id = :id";

        try {
            TypedQuery<Proposal> query = entityManager().createQuery(sql, Proposal.class);

            if (id > 0) {
                query.setParameter("id", id);
            }

            List<Proposal> listProposal = query.getResultList();

            return listProposal.size() > 0;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    /**
     * Este método checa se já existe uma apólice para uma propriedade
     *
     * @param id
     * @return Proposal Object
     */
    public static boolean findByPropertyId(int id) {

        String sql = "SELECT p FROM Proposal p WHERE p.property.id = :id";

        try {
            TypedQuery<Proposal> query = entityManager().createQuery(sql, Proposal.class);
            query.setParameter("id", id);

            List<Proposal> lstProposal = query.getResultList();

            return lstProposal.size() > 0;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}