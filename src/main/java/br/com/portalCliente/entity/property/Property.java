package br.com.portalCliente.entity.property;

import br.com.portalCliente.entity.address.Address;
import br.com.portalCliente.entity.customer.Customer;
import br.com.portalCliente.entity.property.dependences.*;
import br.com.portalCliente.enumeration.PropertyType;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PROPERTY")
public class Property extends PropertyAR {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    //CONTATO
    @Column(name = "CONTACT")
    private String contact;

    //TELEFONE
    @Column(name = "PHONE")
    private String phone;

    //CELULAR
    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

    //TIPO_PROPRIEDADE
    @Column(name = "PROPERTY_TYPE")
    private Integer propertyType;

    //OBSERVACAO
    @Column(name = "DESCRIPTION")
    private String description;

    //DATA_CRIACAO
    @Column(name = "CREATED_AT")
    private Date createdAt;

    //DATA_ATUALIZACAO
    @Column(name = "UPDATED_AT")
    private Date updateAt;

    @Column(name = "STATUS", columnDefinition = "INT(1)")
    private boolean status;

    @Embedded
    private Vehicle vehicle;
    @Embedded
    private Company company;
    @Embedded
    private Equipment equipment;
    @Embedded
    private Life life;
    @Embedded
    private Residence residence;
    @Embedded
    private Vessel vessel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    /**
     * Método para retonar o id do objeto Propriedade
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método para setar o id no objeto Propriedade
     *
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método para retonar o contato do objeto Propriedade
     *
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Método para setar o contato no objeto Propriedade
     *
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Método para retonar o telefone do objeto Propriedade
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Método para setar o telefone no objeto Propriedade
     *
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Método para retonar o celular do objeto Propriedade
     *
     * @return the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Método para setar o celular no objeto Propriedade
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Método para retonar o tipo de propriedade do objeto Propriedade
     *
     * @return the propertyType
     */
    public PropertyType getPropertyType() {
        return PropertyType.getFromValue(propertyType);
    }

    /**
     * Método para setar o tipo de propriedade no objeto Propriedade
     *
     * @param propertyType the propertyType to set
     */
    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType.getValue();
    }

    /**
     * Método para retonar a descrição do objeto Propriedade
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Método para setar a descrição no objeto Propriedade
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Método para retonar o data de criação do objeto Propriedade
     *
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Método para setar o data de criação no objeto Propriedade
     *
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Método para retonar a data da última atualização do objeto Propriedade
     *
     * @return the updateAt
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * Método para setar a data da última atualização no objeto Propriedade
     *
     * @param updateAt the updateAt to set
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Método para retonar o status do objeto Propriedade
     *
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Método para setar o status no objeto Propriedade
     *
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Método para retonar um objeto Veículo atrelado a um objeto Propriedade
     *
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Método para setar um objeto Veículo ao objeto Propriedade
     *
     * @param vehicle the vehicle to set
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Método para retonar um objeto Empresa atrelado a um objeto Propriedade
     *
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Método para setar um obejto Empresa ao objeto Propriedade
     *
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Método para retonar um objeto Equipamento atrelado a um objeto Propriedade
     *
     * @return the equipment
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * Método para setar um objeto Equipamento ao objeto Propriedade
     *
     * @param equipment the equipment to set
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /**
     * Método para retonar um objeto Vida atrelado a um objeto Propriedade
     *
     * @return the life
     */
    public Life getLife() {
        return life;
    }

    /**
     * Método para setar um objeto Vida ao objeto Propriedade
     *
     * @param life the life to set
     */
    public void setLife(Life life) {
        this.life = life;
    }

    /**
     * Método para retonar um objeto Imóvel atrelado a um objeto Propriedade
     *
     * @return the residence
     */
    public Residence getResidence() {
        return residence;
    }

    /**
     * Método para setar um objeto Imóvel ao objeto Propriedade
     *
     * @param residence the residence to set
     */
    public void setResidence(Residence residence) {
        this.residence = residence;
    }

    /**
     * Método para retonar um objeto Embarcação atrelado a um objeto Propriedade
     *
     * @return the vessel
     */
    public Vessel getVessel() {
        return vessel;
    }

    /**
     * Método para setar um objeto Embarcação ao objeto Propriedade
     *
     * @param vessel the vessel to set
     */
    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    /**
     * Método para retonar um objeto Endereço atrelado a um objeto Propriedade
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Método para setar um objeto Endereço ao objeto Propriedade
     *
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }


    /**
     * Método para retonar um objeto Cliente atrelado a um objeto Propriedade
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Método para setar um objeto Cliente ao objeto Propriedade
     *
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    ///////////////////////////////////////////// Converte Json  /////////////////////////////////////////////////////////////

    public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

    public String toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
    }

    /**
     * Toma como entrada uma string JSON e produz um gráfico estático imprimindo
     * um objeto com a representação JSON
     *
     * @param json
     * @return
     */
    public static Property fromJson(String json) {
        return new JSONDeserializer<Property>().use(null, Property.class).deserialize(json);
    }

    /**
     * Métodos para realização de serialização de objetos Java para JSON
     *
     * @param collection
     * @return
     */
    public static String toJsonArray(Collection<Property> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

    public static String toJsonArrayAutoComplete(Collection<Property> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").exclude("updateAt", "createdAt", "customer").serialize(collection);
    }

    public static String toJsonArray(Collection<Property> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

    public static Collection<Property> fromJsonArray(String json) {
        return new JSONDeserializer<List<Property>>().use("values", Property.class).deserialize(json);
    }
}