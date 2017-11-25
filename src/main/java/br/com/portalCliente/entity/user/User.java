package br.com.portalCliente.entity.user;

import br.com.portalCliente.entity.customer.Customer;
import br.com.portalCliente.entity.insurer.Insurer;
import br.com.portalCliente.entity.producer.Producer;
import br.com.portalCliente.entity.property.Property;
import br.com.portalCliente.entity.proposal.Proposal;
import br.com.portalCliente.enumeration.Profile;
import br.com.portalCliente.util.dateUtilities.PortalClienteDateTransformer;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USER")
public class User extends UserAR {

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

    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "LOGIN")
    private String login;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "PROFILE", columnDefinition = "INT(1)")
    private Integer profile;

    @Column(name = "STATUS", columnDefinition = "INT(1)")
    private boolean status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Customer> customers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Insurer> insurers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Producer> producers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Property> properties;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Proposal> proposals;

    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    /**
     * Método para retonar o id do objeto Usuário
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método para setar o id no objeto Usuário
     *
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Método para retonar o nome do objeto Usuário
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Método para setar o nome no objeto Usuário
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método para retonar o login do objeto Usuário
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Método para setar o login no objeto Usuário
     *
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Método para retonar o password do objeto Usuário
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método para setar o password no objeto Usuário
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método para retonar o profile do objeto Usuário
     *
     * @return the profile
     */
    public Profile getProfile() {
        return Profile.getName(profile);
    }


    /**
     * Método para setar o profile no objeto Usuário Usuário
     *
     * @param profile the profile to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile.getValue();
    }

    /**
     * Método para retonar o status do objeto Usuário
     *
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Método para setar o status no objeto Usuário
     *
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Insurer> getInsurers() {
        return insurers;
    }

    public void setInsurers(List<Insurer> insurers) {
        this.insurers = insurers;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toJson() {
        return new JSONSerializer().exclude("*.class").transform(new PortalClienteDateTransformer("dd/MM/yyyy"), Date.class).serialize(this);
    }

    public String toJson(String[] includedParam, String[] excludedParam) {
        return new JSONSerializer().exclude("*.class").include(includedParam).
                exclude(excludedParam).serialize(this);
    }

    /**
     * Toma como entrada uma string JSON e produz um gráfico estático imprimindo
     * um objeto com a representação JSON
     *
     * @param json
     * @return
     */
    public static User fromJson(String json) {
        return new JSONDeserializer<User>().use(null, User.class).deserialize(json);
    }

    /**
     * Métodos para realização de serialização de objetos Java para JSON
     *
     * @param collection
     * @return
     */
    public static String toJsonArray(Collection<User> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

    public static String toJsonArray(Collection<User> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

    public static String toJsonArray(Collection<User> collection,
                                     String[] include, String[] exclude) {
        return new JSONSerializer().exclude("*.class").
                include(include).exclude(exclude).serialize(collection);
    }

    public static Collection<User> fromJsonArray(String json) {
        return new JSONDeserializer<List<User>>().use("values", User.class).deserialize(json);
    }

}
