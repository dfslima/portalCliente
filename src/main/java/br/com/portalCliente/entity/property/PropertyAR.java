package br.com.portalCliente.entity.property;

import br.com.portalCliente.entity.customer.Customer;
import br.com.portalCliente.enumeration.PropertyType;
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
public abstract class PropertyAR extends JpaUtils {

    @PersistenceContext
    transient EntityManager entityManager;

    private static CriteriaQuery<Property> criteria;
    private static Root<Property> root;
    private static List<Predicate> predicates;

    public static final EntityManager entityManager() {
        EntityManager em = new Property().entityManager;
        if (em == null)
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    private static CriteriaBuilder criteriaBuilder() {
        CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        criteria = criteriaBuilder.createQuery(Property.class);
        root = criteria.from(Property.class);
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
            Property attached = (Property) this;
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
    public Property merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Property merged = (Property) this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static Property find(int id) {
        return entityManager().find(Property.class, id);
    }

    public static Property findByLicensePlate(String licensePlate) {
        try {
            String sql = "SELECT P FROM " + Property.class.getName() + " AS P "
                    + " WHERE P.vehicle.vehicleLicensePlate = :licensePlate ";

            TypedQuery<Property> query = entityManager().createQuery(sql, Property.class)
                    .setParameter("licensePlate", licensePlate);

            return query.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public static List<Property> findAll() {
        String sql = "Select p From Property p";
        return entityManager().createQuery(sql, Property.class).getResultList();
    }

    /**
     * Este método retorna a quantidade de dados existentes no BD
     * de acordo com o parâmetro informado
     *
     * @param propertyType
     * @param customerId
     * @param cpfCnpj
     * @param street
     * @param city
     * @param equipmentModel
     * @param vehicleLicensePlate
     * @param vehicleModelName
     * @param boatName
     * @param status
     * @param vehicleChassis
     * @return
     */
    public static long count(PropertyType propertyType, Integer customerId, String cpfCnpj, String street,
                             String city, String equipmentModel, String vehicleLicensePlate, String vehicleModelName,
                             String boatName, boolean status, String vehicleChassis, String vehicleCodeFipe) {

        try {

            CriteriaBuilder criteriaBuilder = criteriaBuilder();

            predicates.add(criteriaBuilder.equal(root.get("propertyType"), propertyType.getValue()));
            predicates.add(criteriaBuilder.equal(root.get("status"), status));

            if (customerId != null) {
                predicates.add(criteriaBuilder.equal(root.get("customer").<Integer>get("id"), customerId));
            }

            if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("customer").<String>get("cpfCnpj"), "%" + cpfCnpj + "%"));
            }

            if (street != null && !street.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address").<String>get("street"), "%" + street + "%"));
            }

            if (city != null && !city.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address").<String>get("city"), "%" + city + "%"));
            }

            if (equipmentModel != null && !equipmentModel.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("equipment").<String>get("equipmentModel"), "%" + cpfCnpj + "%"));
            }

            if (vehicleLicensePlate != null && !vehicleLicensePlate.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("vehicle").<String>get("vehicleLicensePlate"), "%" + vehicleLicensePlate + "%"));
            }

            if (vehicleModelName != null && !vehicleModelName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("vehicle").<String>get("vehicleModelName"), "%" + vehicleModelName + "%"));
            }

            if (boatName != null && !boatName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("vessel").<String>get("boatName"), "%" + boatName + "%"));
            }

            if (vehicleChassis != null && !vehicleChassis.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("vehicle").<String>get("vehicleChassis"), "%" + vehicleChassis + "%"));
            }

            if (vehicleCodeFipe != null && !vehicleCodeFipe.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("vehicle").<String>get("vehicleCodeFipe"), "%" + vehicleCodeFipe + "%"));
            }

            CriteriaQuery<Long> criteriaCount = criteriaBuilder.createQuery(Long.class);
            criteriaCount.select(criteriaBuilder.count(criteriaCount.from(Property.class)));
            entityManager().createQuery(criteriaCount);
            criteriaCount.where(predicates.toArray(new Predicate[]{}));

            return entityManager().createQuery(criteriaCount).getSingleResult();

        } catch (Exception e) {
            Logger.getLogger(Property.class.getName()).error("PROPERTY_COUNT ERROR: " + e.getMessage());
            return 0;
        }
    }


    /**
     * Este método serve para consulta de Propriedades
     *
     * @param propertyType
     * @param customerId
     * @param cpfCnpj
     * @param street
     * @param city
     * @param equipmentModel
     * @param vehicleLicensePlate
     * @param vehicleModelName
     * @param boatName
     * @param status
     * @param vehicleChassis
     * @param firstResult
     * @param maxResults
     * @return
     */
    public static List<Property> search(PropertyType propertyType, Integer customerId, String cpfCnpj,
                                        String street, String city, String equipmentModel, String vehicleLicensePlate,
                                        String vehicleModelName, String boatName, boolean status, String vehicleChassis, String vehicleCodeFipe,
                                        int firstResult, int maxResults) {

        CriteriaBuilder criteriaBuilder = criteriaBuilder();

        predicates.add(criteriaBuilder.equal(root.get("propertyType"), propertyType.getValue()));
        predicates.add(criteriaBuilder.equal(root.get("status"), status));

        if (customerId != null) {
            predicates.add(criteriaBuilder.equal(root.join("customer").<Integer>get("id"), customerId));
        }

        if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.join("customer").<String>get("cpfCnpj"), "%" + cpfCnpj + "%"));
        }

        if (street != null && !street.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.join("address").<String>get("street"), "%" + street + "%"));
        }

        if (city != null && !city.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.join("address").<String>get("city"), "%" + city + "%"));
        }

        if (equipmentModel != null && !equipmentModel.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("equipment").<String>get("equipmentModel"), "%" + cpfCnpj + "%"));
        }

        if (vehicleLicensePlate != null && !vehicleLicensePlate.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("vehicle").<String>get("vehicleLicensePlate"), "%" + vehicleLicensePlate + "%"));
        }

        if (vehicleModelName != null && !vehicleModelName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("vehicle").<String>get("vehicleModelName"), "%" + vehicleModelName + "%"));
        }

        if (boatName != null && !boatName.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("vessel").<String>get("boatName"), "%" + boatName + "%"));
        }

        if (vehicleChassis != null && !vehicleChassis.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("vehicle").<String>get("vehicleChassis"), "%" + vehicleChassis + "%"));
        }

        if (vehicleCodeFipe != null && !vehicleCodeFipe.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("vehicle").<String>get("vehicleCodeFipe"), "%" + vehicleCodeFipe + "%"));
        }

        try {
            criteria.where(predicates.toArray(new Predicate[]{}));
            criteria.orderBy(criteriaBuilder.desc(root.get("id")));
            return entityManager().createQuery(criteria).setFirstResult(PaginationUtils.calculateFirstResultQuery(firstResult, maxResults))
                    .setMaxResults(maxResults).getResultList();

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            Logger.getLogger(Property.class.getName()).error("Erro" + e.getMessage());
            return null;
        }
    }


    /**
     * Este método serve como um auto complete para os dados do objeto Veículo
     *
     * @param value
     * @param propertyType
     * @param customerId
     * @return
     */
    public static List<Property> findAutoComplete(String value, PropertyType propertyType, Integer customerId) {

        StringBuilder sql = new StringBuilder("SELECT P FROM Property P");
        params = new HashMap<>();

        validateSql(sql, "P.propertyType = :propertyType");
        params.put("propertyType", propertyType.getValue());

        if (customerId != null && customerId != 0) {
            validateSql(sql, "P.customer.id = :customerId");
            params.put("customerId", customerId);
        }

        String sqlTemp = sql.toString();

        List<Property> result = new ArrayList<Property>();

        int valueAttribute = 0;
        while (result.isEmpty()) {
            valueAttribute += 1;
            String newAttribute = getAttributeAdrress(valueAttribute, propertyType);
            if (!newAttribute.equals("")) {
                sql = new StringBuilder(sqlTemp);
                if (value != null && !value.isEmpty()) {
                    validateSql(sql, newAttribute);
                    params.put("value", "%" + value + "%");
                }
                TypedQuery<Property> query = entityManager().createQuery(sql.toString(), Property.class);
                getParams(query);
                result = query.getResultList();
            } else {
                break;
            }
        }
        return result;
    }


    /**
     * Este método retorna o nome da propriedade do objeto Endereço
     * de acordo com o valor informado por parâmetro
     *
     * @param value
     * @param propertyType
     * @return
     */
    private static String getAttributeAdrress(int value, PropertyType propertyType) {

        String v = "";
        if (propertyType.equals(PropertyType.VEHICLE)) {
            if (value == 1) {
                v = "P.vehicle.vehicleLicensePlate LIKE :value";
            }
        } else if (propertyType.equals(PropertyType.CONDOMINIUM) || propertyType.equals(PropertyType.RESIDENCE)
                || propertyType.equals(PropertyType.COMPANY)) {

            if (value == 1) {
                v = "P.address.street LIKE :value";
            } else if (value == 2) {
                v = "P.address.neighborhood LIKE :value";
            } else if (value == 3) {
                v = "P.address.city LIKE :value";
            } else if (value == 4) {
                v = "P.address.state LIKE :value";
            }
        } else if (propertyType.equals(PropertyType.VESSEL)) {
            if (value == 1) {
                v = "P.vessel.boatName LIKE :value";
            }

        } else if (propertyType.equals(PropertyType.LIFE)) {
            if (value == 1) {
                v = "P.life.lifeProfessionalActivityName LIKE :value";
            }
        } else if (propertyType.equals(PropertyType.EQUIPMENT)) {
            if (value == 1) {
                v = "P.equipment.equipmentMake LIKE :value";
            }
        }

        return v;
    }

    @Transactional
    public static Property save(Property property) throws PortalClienteException {

        try {

            if (Property.findByLicensePlateBool(property.getVehicle().getVehicleLicensePlate(), null)) {
                throw new PortalClienteException("Um auto com esta placa já está cadastrado");
            }

            if (Property.findByChassis(property.getVehicle().getVehicleChassis(), null)) {
                throw new PortalClienteException("Um auto com este chassi já está cadastrado");
            }

            property.setCreatedAt(new Date());
            Customer customer = Customer.find(property.getCustomer().getId());
            customer.setProperties(new ArrayList<Property>());
            customer.getProperties().add(property);
            property.setCustomer(customer);

            property.persist();

            return property;

        } catch (PortalClienteException p) {
            throw new PortalClienteException(p.getMessage());
        } catch (Exception e) {
            throw new PortalClienteException("Ops! Algo de errado aconteceu. Atualize a página e tente novamente");
        }
    }


    /**
     * Este método procura na BD uma Propriedade através da placa informada
     *
     * @param licensePlate
     * @return
     */
    public static boolean findByLicensePlateBool(String licensePlate, Integer id) {
        String sql = "SELECT p FROM " + Property.class.getName() + " p WHERE p.vehicle.vehicleLicensePlate = :licensePlate ";

        TypedQuery<Property> query = null;

        try {
            query = entityManager().createQuery(sql, Property.class);
            query.setParameter("licensePlate", licensePlate);

            List<Property> listProperty = query.getResultList();
            List<Property> listPropertyByLicensePlate = null;

            if (id != null) {
                sql = sql.concat("AND p.id = :id");
                query = entityManager().createQuery(sql, Property.class);
                query.setParameter("licensePlate", licensePlate);
                query.setParameter("id", id);
                listPropertyByLicensePlate = query.getResultList();
            }

            if (listProperty.size() > 0 && id == null)
                return true;
            else if (listProperty.size() > 0 && id != null && listPropertyByLicensePlate.size() > 0)
                return false;
            else if (listProperty.size() == 0 && id == null)
                return false;
            else if (listProperty.size() > 0 && listPropertyByLicensePlate.size() == 0)
                return true;
            else if (listProperty.size() == 0 && listPropertyByLicensePlate.size() == 0)
                return false;
            else
                return false;

        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Este método procura na BD uma Propriedade através do chassi informado
     *
     * @param chassis
     * @return
     */
    public static boolean findByChassis(String chassis, Integer id) {
        String sql = "SELECT p FROM " + Property.class.getName() + " p WHERE p.vehicle.vehicleChassis = :chassis ";

        TypedQuery<Property> query = null;

        try {
            query = entityManager().createQuery(sql, Property.class);
            query.setParameter("chassis", chassis);

            List<Property> listProperty = query.getResultList();
            List<Property> listPropertyByChassis = null;

            if (id != null) {
                sql = sql.concat("AND p.id = :id");
                query = entityManager().createQuery(sql, Property.class);
                query.setParameter("chassis", chassis);
                query.setParameter("id", id);
                listPropertyByChassis = query.getResultList();
            }

            if (listProperty.size() > 0 && id == null)
                return true;
            else if (listProperty.size() > 0 && id != null && listPropertyByChassis.size() > 0)
                return false;
            else if (listProperty.size() == 0 && id == null)
                return false;
            else if (listProperty.size() > 0 && listPropertyByChassis.size() == 0)
                return true;
            else if (listProperty.size() == 0 && listPropertyByChassis.size() == 0)
                return false;
            else
                return false;

        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Este método checa se já existe uma propriedade para um cliente
     *
     * @param id
     * @return Property Object
     */
    public static boolean findByCustomerId(int id) {
        String sql = "SELECT p FROM " + Property.class.getName() + " p "
                + "JOIN p.customer c "
                + "WHERE c.id = :id";
        try {
            TypedQuery<Property> query = entityManager().createQuery(sql, Property.class);

            if (id > 0)
                query.setParameter("id", id);

            List<Property> listProperty = query.getResultList();

            return (listProperty.size() > 0) ? true : false;

        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (Exception e) {
            Logger.getLogger(Property.class.getName()).error("Erro aqui: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public Property edit() throws PortalClienteException {

        Property property = (Property) this;

        if (Objects.equals(property.getPropertyType(), PropertyType.VEHICLE)) {
            if (Property.findByLicensePlateBool(property.getVehicle().getVehicleLicensePlate(), property.getId())) {
                throw new PortalClienteException("Um veículo com esta placa já está cadastrado");
            }

            if (Property.findByChassis(property.getVehicle().getVehicleChassis(), property.getId())) {
                throw new PortalClienteException("Um veículo com este chassi já está cadastrado");
            }
        }

        property.setUpdateAt(new Date());
        property.merge();
        return property;
    }
}