package br.com.portalCliente.entity.address;

import br.com.portalCliente.entity.customer.Customer;
import br.com.portalCliente.entity.property.Property;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "ADDRESS")
public class Address extends AddressAR {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "STREET")
    private String street;

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "SUPPLEMENT")
    private String supplement;

    @Column(name = "NEIGHBORHOOD")
    private String neighborhood;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "OBSERVATIONS")
    private String observations;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "address")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "address")
    private Property property;

    /**
     * Método para retonar o id do objeto Endereço
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método para setar o id no objeto Endereço
     *
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método para retonar o Logradouro do objeto Endereço
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Método para setar o Logradouro no objeto Endereço
     *
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Método para retonar o número do objeto Endereço
     *
     * @return the number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Método para setar o número no objeto Endereço
     *
     * @param number the number to set
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * Método para retonar o complemento do objeto Endereço
     *
     * @return the supplement
     */
    public String getSupplement() {
        return supplement;
    }

    /**
     * Método para setar o complemento no objeto Endereço
     *
     * @param supplement the supplement to set
     */
    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }

    /**
     * Método para retonar o bairro do objeto Endereço
     *
     * @return the neighborhood
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Método para setar o bairro no objeto Endereço
     *
     * @param neighborhood the neighborhood to set
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Método para retonar a cidade do objeto Endereço
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Método para setar a cidade no objeto Endereço
     *
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Método para retonar o estado do objeto Endereço
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Método para setar o estado no objeto Endereço
     *
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Método para retonar o CEP do objeto Endereço
     *
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Método para setar o CEP do objeto Endereço
     *
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Método para retonar a observações do objeto Endereço
     *
     * @return the observations
     */
    public String getObservations() {
        return observations;
    }

    /**
     * Método para setar a observações no objeto Endereço
     *
     * @param observations the observations to set
     */
    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    ////////////////////////////////metodos Json ///////////////////////////////////////////////


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
    public static Address fromJson(String json) {
        return new JSONDeserializer<Address>().use(null, Address.class).deserialize(json);
    }

    /**
     * Métodos para realização de serialização de objetos Java para JSON
     *
     * @param collection
     * @return
     */
    public static String toJsonArray(Collection<Address> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

    public static String toJsonArray(Collection<Address> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

    public static Collection<Address> fromJsonArray(String json) {
        return new JSONDeserializer<List<Address>>().use("values", Address.class).deserialize(json);
    }
}
