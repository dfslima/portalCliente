package br.com.portalCliente.entity.customer;

import br.com.portalCliente.entity.address.Address;
import br.com.portalCliente.entity.property.Property;
import br.com.portalCliente.entity.proposal.Proposal;
import br.com.portalCliente.entity.user.User;
import br.com.portalCliente.util.dateUtilities.PortalClienteDateTransformer;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
public class Customer extends CustomerAR {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @OrderBy
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "CPF_CNPJ", length = 20, unique = true)
    private String cpfCnpj;

    // TIPO
    @Column(name = "TYPE", columnDefinition = "INT(1)")
    private Integer type;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    //TELEFONE FIXO
    @Column(name = "PHONE", length = 25)
    private String phone;

    //CELULAR
    @Column(name = "MOBILE_PHONE", length = 25)
    private String mobilePhone;

    @Column(name = "EMAIL", length = 100)
    private String email;

    // PF
    // RG
    @Column(name = "GENERAL_RECORD", length = 20)
    private String generalRecord;

    // ORGÃO EXPEDIDOR
    @Column(name = "CONSIGNOR_ORGAN", length = 50)
    private String consignorOrgan;

    // CNH
    @Column(name = "DRIVER_LICENSE")
    private String driverLicense;

    // DATA DE NASCIMENTO
    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    // SEXO
    @Column(name = "GENDER", length = 10)
    private String gender;

    // ESTADO CIVIL
    @Column(name = "MARITAL_STATUS", length = 20)
    private String maritalStatus;

    // PJ

    // RAZÃO SOCIAL
    @Column(name = "CORPORATE_NAME", length = 100)
    private String corporateName;

    // SITE
    @Column(name = "WEBSITE", length = 100)
    private String website;

    // INSCRIÇÃO ESTADUAL
    @Column(name = "STATE_REGISTRATION", length = 100)
    private String stateRegistration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private Address address;

	@OneToMany(cascade=CascadeType.ALL,  mappedBy="customer")
	private List<Property> properties;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Proposal> proposals;

    @ManyToOne
    @JoinColumn(name ="USER_ID", referencedColumnName = "ID")
    private User user;

    // OBSERVAÇÃO
    @Column(name = "OBSERVATION", length = 500)
    private String observation;

    /**
     * Método para retonar o id do objeto Cliente
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método para setar o id no objeto Cliente
     *
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método para retonar o nome do objeto Cliente
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Método para setar o name no objeto Cliente
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método para retonar o cpfCnpj do objeto Cliente
     *
     * @return the cpfCnpj
     */
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    /**
     * Método para setar o cpfCnpj no objeto Cliente
     *
     * @param cpfCnpj the cpfCnpj to set
     */
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    /**
     * Método para retonar o tipo do objeto Cliente
     *
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Método para setar o tipo no objeto Cliente
     *
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Método para retonar a data de criação do objeto Cliente
     *
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Método para setar a data de criação no objeto Cliente
     *
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Método para retonar data da última alteração do objeto Cliente
     *
     * @return the updateAt
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * Método para setar a data da última alteração do objeto Cliente
     *
     * @param updateAt the updateAt to set
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Método para retonar o telefone do objeto Cliente
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Método para setar o telefone no objeto Cliente
     *
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        if (phone != null) {
            this.phone = phone.length() <= 20 ? phone : null;
        } else {
            this.phone = phone;
        }
    }

    /**
     * Método para retonar o celular do objeto Cliente
     *
     * @return the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Método para setar o celuar no objeto Cliente
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        if (mobilePhone != null) {
            this.mobilePhone = mobilePhone.length() <= 20 ? mobilePhone : null;
        } else {
            this.mobilePhone = mobilePhone;
        }
    }

    /**
     * Método para retonar o email do objeto Cliente
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método para setar o email no objeto Cliente
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método para retonar o RG do objeto Cliente
     *
     * @return the generalRecord
     */
    public String getGeneralRecord() {
        return generalRecord;
    }

    /**
     * Método para setar o RG no objeto Cliente
     *
     * @param generalRecord the generalRecord to set
     */
    public void setGeneralRecord(String generalRecord) {
        this.generalRecord = generalRecord;
    }

    /**
     * Método para retonar o órgão expeditor do objeto Cliente
     *
     * @return the consignorOrgan
     */
    public String getConsignorOrgan() {
        return consignorOrgan;
    }

    /**
     * Método para setar  o órgão expeditor no objeto Cliente
     *
     * @param consignorOrgan the consignorOrgan to set
     */
    public void setConsignorOrgan(String consignorOrgan) {
        this.consignorOrgan = consignorOrgan;
    }

    /**
     * Método para retonar o CNH do objeto Cliente
     *
     * @return the driverLicense
     */
    public String getDriverLicense() {
        return driverLicense;
    }

    /**
     * Método para setar o CNH no objeto Cliente
     *
     * @param driverLicense the driverLicense to set
     */
    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    /**
     * Método para retonar a data de nascimento do objeto Cliente
     *
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Método para setar a data de nascimento no objeto Cliente
     *
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Método para retonar o sexo do objeto Cliente
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Método para setar o sexo no objeto Cliente
     *
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Método para retonar o estado civil do objeto Cliente
     *
     * @return the maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Método para setar o estado civil no objeto Cliente
     *
     * @param maritalStatus the maritalStatus to set
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Método para retonar a razão social do objeto Cliente
     *
     * @return the corporateName
     */
    public String getCorporateName() {
        return corporateName;
    }

    /**
     * Método para setar a razão social no objeto Cliente
     *
     * @param corporateName the corporateName to set
     */
    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    /**
     * Método para retonar o website do objeto Cliente
     *
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Método para setar o website no objeto Cliente
     *
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Método para retonar o inscrição estadual do objeto Cliente
     *
     * @return the stateRegistration
     */
    public String getStateRegistration() {
        return stateRegistration;
    }

    /**
     * Método para setar o inscrição estadual no objeto Cliente
     *
     * @param stateRegistration the stateRegistration to set
     */
    public void setStateRegistration(String stateRegistration) {
        this.stateRegistration = stateRegistration;
    }

    /**
     * Método para retonar o objeto Endereço atrelado ao objeto Cliente
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Método para setar o objeto address ao objeto Cliente
     *
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Método para retonar o objeto properties atrelados ao objeto Cliente
     * @return the properties
     */
	public List<Property> getProperties() {
		return properties;
	}

    /**
     * Método para setar o objeto Propriedade no objeto Cliente
     * @param properties the properties to set
     */
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

    /**
     * Método para retonar o objeto Apólice atrelado ao objeto Cliente
     *
     * @return the policies
     */
    public List<Proposal> getProposals() {
        return proposals;
    }

    /**
     * Método para setar o objeto Apólice ao objeto Cliente
     *
     * @param policies the policies to set
     */
    public void setProposals(List<Proposal> policies) {
        this.proposals = policies;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    ///////////////////////////////////////////// Converte Json  /////////////////////////////////////////////////////////////


    private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("dd/MM/yyyy");

    public String toJson() {
        return new JSONSerializer().exclude("*.class").transform(new PortalClienteDateTransformer("dd/MM/yyyy"), Date.class).serialize(this);
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
    public static Customer fromJson(String json) {
        return new JSONDeserializer<Customer>().use(null, Customer.class).use(Date.class, DATE_TRANSFORMER).deserialize(json);
    }

    /**
     * Métodos para realização de serialização de objetos Java para JSON
     *
     * @param collection
     * @return
     */
    public static String toJsonArray(Collection<Customer> collection, String[] include, String[] exclude) {
        return new JSONSerializer().exclude("*.class").include(include).exclude(exclude).serialize(collection);
    }

    public static String toJsonArrayAutoComplete(Collection<Customer> collection) {
        return new JSONSerializer().exclude("*.class").exclude("address", "properties", "policies").serialize(collection);
    }

	public static String toJsonArray(Collection<Address> collection, String[] fields) {
		return new JSONSerializer()	.include(fields).exclude("*.class").serialize(collection);
	}

    public static Collection<Customer> fromJsonArray(String json) {
        return new JSONDeserializer<List<Customer>>().use("values", Customer.class).deserialize(json);
    }
}
