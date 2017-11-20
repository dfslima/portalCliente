package br.com.portalCliente.entity.insurer;

import br.com.portalCliente.entity.address.Address;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "INSURER")
public class Insurer extends InsurerAR {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @NotNull
    @Column(name = "CNPJ", length = 20, unique = true)
    private String cnpj;

    // RAZÃO SOCIAL
    @Column(name = "CORPORATE_NAME")
    private String corporateName;

    // NOME FANTASIA
    @Column(name = "TRADE_NAME")
    private String tradeName;

    // EMAIL
    @Column(name = "EMAIL", unique = true)
    private String email;

    //TELEFONE FIXO
    @Column(name = "PHONE", length = 11)
    private String phone;

    //CELULAR
    @Column(name = "MOBILE_PHONE", length = 11)
    private String mobilePhone;

    // SITE
    @Column(name = "WEBSITE")
    private String website;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID")
    private Address address;

    /**
     * Método para retonar o id do objeto Seguradora
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método para setar o id no objeto Seguradora
     *
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método para recuperar a data de criação do registro
     * @return
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Método para recuperar a data de atualização do registro
     * @return
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Método para retonar o cnpj do objeto Seguradora
     *
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Método para setar o cnpj no objeto Seguradora
     *
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * Método para retonar o Razão Social do objeto Seguradora
     *
     * @return the corporateName
     */
    public String getCorporateName() {
        return corporateName;
    }

    /**
     * Método para setar o Razão Social no objeto Seguradora
     *
     * @param corporateName the corporateName to set
     */
    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    /**
     * Método para retonar o Nome Fantasia do objeto Seguradora
     *
     * @return the tradeName
     */
    public String getTradeName() {
        return tradeName;
    }

    /**
     * Método para setar o Nome Fantasia no objeto Seguradora
     *
     * @param tradeName the tradeName to set
     */
    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    /**
     * Método para retonar o email do objeto Seguradora
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método para setar o email no objeto Seguradora
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método para retonar o Telefone do objeto Seguradora
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Método para setar o Telefone no objeto Seguradora
     *
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Método para retonar o Celular do objeto Seguradora
     *
     * @return the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Método para setar o Celular no objeto Seguradora
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Método para retonar o website do objeto Seguradora
     *
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Método para setar o website no objeto Seguradora
     *
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Método para retonar o objeto Endereço atrelado ao objeto Seguradora
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Método para setar o objeto Endereço ao objeto Seguradora
     *
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
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
    public static Insurer fromJson(String json) {
        return new JSONDeserializer<Insurer>().use(null, Insurer.class).deserialize(json);
    }

    /**
     * Métodos para realização de serialização de objetos Java para JSON
     *
     * @param collection
     * @return
     */
    public static String toJsonArray(Collection<Insurer> collection,
                                     String[] includeParam, String[] excludeParam) {
        return new JSONSerializer().exclude("*.class")
                .include(includeParam).exclude(excludeParam).serialize(collection);
    }

    public static String toJsonArray(Collection<Insurer> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

    public static Collection<Insurer> fromJsonArray(String json) {
        return new JSONDeserializer<List<Insurer>>().use("values", Insurer.class).deserialize(json);
    }
}