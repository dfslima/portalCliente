package br.com.portalCliente.entity.producer;

import br.com.portalCliente.entity.address.Address;
import br.com.portalCliente.util.dateUtilities.PortalClienteDateTransformer;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PRODUCER")
public class Producer extends ProducerAR {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "NAME")
    private String name;

    // RG
    @Column(name = "GENERAL_RECORD")
    private String generalRecord;

    // ÓRGÃO EXPEDIDOR
    @Column(name = "CONSIGN_ORORGAN")
    private String consignorOrgan;

    @Column(name = "PHONE", length = 16)
    private String phone;

    @Column(name = "MOBILE_PHONE", length = 16)
    private String mobilePhone;

    @Column(name = "GENDER", length = 10)
    private String gender;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private Address address;

    /**
     * Método para retonar o id do objeto Produtor
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método para setar o id no objeto Produtor
     *
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método para retonar o cpf do objeto Produtor
     *
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Método para setar o cpf no objeto Produtor
     *
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Método para retonar o nome do objeto Produtor
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Método para setar o nome no objeto Produtor
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método para retonar o RG do objeto Produtor
     *
     * @return the generalRecord
     */
    public String getGeneralRecord() {
        return generalRecord;
    }

    /**
     * Método para setar o RG no objeto Produtor
     *
     * @param generalRecord the generalRecord to set
     */
    public void setGeneralRecord(String generalRecord) {
        this.generalRecord = generalRecord;
    }

    /**
     * Método para retonar o Orgão Expedidor do objeto Produtor
     *
     * @return the consignorOrgan
     */
    public String getConsignorOrgan() {
        return consignorOrgan;
    }

    /**
     * Método para setar o Orgão Expedidor no objeto Produtor
     *
     * @param consignorOrgan the consignorOrgan to set
     */
    public void setConsignorOrgan(String consignorOrgan) {
        this.consignorOrgan = consignorOrgan;
    }

    /**
     * Método para retonar o Telefone do objeto Produtor
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Método para setar o Telefone no objeto Produtor
     *
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Método para retonar o Celular do objeto Produtor
     *
     * @return the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Método para setar o Celular no objeto Produtor
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Método para retonar o Sexo do objeto Produtor
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Método para setar o Sexo no objeto Produtor
     *
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Método para retonar o email do objeto Produtor
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método para setar o email no objeto Produtor
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método para retonar a data de criação do objeto Produtor
     *
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Método para setar a data de criação no objeto Produtor
     *
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Método para retonar a data de alteração do objeto Produtor
     *
     * @return the updateAt
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * Método para setar a dat de alteração no objeto Produtor
     *
     * @param updateAt the updateAt to set
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Método para retonar o objeto Endereço atralado ao objeto Produtor
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Método para setar o objeto Endereço no objeto Produtor
     *
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }


    ///////////////////////////////////////////// Converte Json  ///////////////////////////////////////////////////////

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
    public static Producer fromJson(String json) {
        return new JSONDeserializer<Producer>().use(null, Producer.class).use(Date.class, DATE_TRANSFORMER).deserialize(json);
    }

    /**
     * Métodos para realização de serialização de objetos Java para JSON
     *
     * @param collection
     * @return
     */
    public static String toJsonArray(Collection<Producer> collection,
                                     String[] include, String[] exclude) {
        return new JSONSerializer().exclude("*.class").
                include(include).exclude(exclude).serialize(collection);
    }

    public static String toJsonArray(Collection<Address> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

	/*public static String toJsonArray(Collection<Producer> collection, String[] fields) {
        return new JSONSerializer()	.include(fields).exclude("*.class").serialize(collection);
	}*/

    public static Collection<Producer> fromJsonArray(String json) {
        return new JSONDeserializer<List<Producer>>().use("values", Producer.class).deserialize(json);
    }
}
