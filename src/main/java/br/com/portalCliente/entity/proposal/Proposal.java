package br.com.portalCliente.entity.proposal;

import br.com.portalCliente.entity.customer.Customer;
import br.com.portalCliente.entity.insurer.Insurer;
import br.com.portalCliente.entity.producer.Producer;
import br.com.portalCliente.entity.property.Property;
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
@Table(name = "PROPOSAL")
public class Proposal extends ProposalAR {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    // PRÊMIO LÍQUIDO
    @Column(name = "NET_AWARD")
    private double netAward;

    //NÚMERO DE PARCELAS
    @Column(name = "INSTALLMENSTS_NUMBER")
    private Integer installmentsNumber;

    // DESCONTO PRODUTOR
    @Column(name = "DISCOUNT")
    private float discount;

    // DESCONTO CORRETORA
    @Column(name = "DISCOUNT_BROKERAGE")
    private float discountBrokerage;

    // AGRAVAMENTO
    @Column(name = "AGGRAVATION")
    private float aggravation;

    // NÚMERO DA PROPOSTA
    @NotNull
    @Column(name = "PROPOSAL_NUMBER", unique = true)
    private String proposalNumber;

    // CÓDIGO DE AUTENTICAÇÃO
    @Column(name = "AUTHENTICATION_CODE", unique = true, length = 40)
    private String authenticationCode;

    // COMISSÃO DO PRODUTOR
    @Column(name = "PRODUCER_COMMISSION")
    private double producerCommission;

    @Column(name = "PRODUCER_COMMISSION_PERCENT")
    private float producerCommissionPercent;

    // COMISSÃO DA CORRETORA
    @Column(name = "BROKERAGE_COMMISSION")
    private double brokerageCommission;

    @Column(name = "BROKERAGE_COMMISSION_PERCENT")
    private float brokerageCommissionPercent;

    // COMISSÃO DA APOLICE
    @Column(name = "PROPOSAL_COMMISSION")
    private double proposalCommission;

    // COMISSÃO DA APOLICE
    @Column(name = "PROPOSAL_COMMISSION_PERCENT")
    private float proposalCommissionPercent;

    // INÍCIO DA VIGÊNCIA
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "START_TERM")
    private Date startTerm;

    // FIM DA VIGÊNCIA
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "END_TERM")
    private Date endTerm;

    // DATA DA TRANSMISSÃO
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TRANSMISSION")
    private Date dateTransmission;

    // DATA DE EMISSÃO DA APÓLICE
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TRANSMISSION_PROPOSAL")
    private Date dateTransmissionProposal;

    // OBSERVAÇÃO
    @Column(name = "COMMENTS", length = 500)
    private String comments;

    @Column(name = "PAYED_INSTALLMENTS")
    private int payedInstallments;

    @OneToOne
    @JoinColumn(name = "PROPERTY_ID", referencedColumnName = "ID")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "INSURER_ID", referencedColumnName = "ID")
    private Insurer insurer;

    @ManyToOne
    @JoinColumn(name = "PRODUCER_ID", referencedColumnName = "ID")
    private Producer producer;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;


    /**
     * Método para retonar o id do objeto Apólice
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método para setar o id no objeto Apólice
     *
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método para retonar o Prêmio Líquido do objeto Apólice
     *
     * @return the netAward
     */
    public double getNetAward() {
        return netAward;
    }

    /**
     * Método para setar o Prêmio Líquido no objeto Apólice
     *
     * @param netAward the netAward to set
     */
    public void setNetAward(double netAward) {
        this.netAward = netAward;
    }

    /**
     * Método para retonar o Nº de Parcelas do objeto Apólice
     *
     * @return the installmentsNumber
     */
    public Integer getInstallmentsNumber() {
        return installmentsNumber;
    }

    /**
     * Método para setar o Nº de Parcelas no objeto Apólice
     *
     * @param installmentsNumber the installmentsNumber to set
     */
    public void setInstallmentsNumber(Integer installmentsNumber) {
        this.installmentsNumber = installmentsNumber;
    }

    /**
     * Método para retonar o desconto do objeto Apólice
     *
     * @return the discount
     */
    public float getDiscount() {
        return discount;
    }

    /**
     * Método para setar o desconto no objeto Apólice
     *
     * @param discount the discount to set
     */
    public void setDiscount(float discount) {
        this.discount = discount;
    }

    /**
     * Método para recuperar o valor de desconto aplicado na comissão da corretora
     *
     * @return
     */
    public float getDiscountBrokerage() {
        return discountBrokerage;
    }

    /**
     * Método para setar o valor de desconto aplicado na comissão da corretora
     *
     * @param discountBrokerage
     */
    public void setDiscountBrokerage(float discountBrokerage) {
        this.discountBrokerage = discountBrokerage;
    }

    /**
     * Método para retornar o valor do agravamento inserido na proposta
     *
     * @return the value aggravation
     */
    public float getAggravation() {
        return aggravation;
    }


    /**
     * Método para setar o valor do agravamento da proposta
     *
     * @param aggravation
     */
    public void setAggravation(float aggravation) {
        this.aggravation = aggravation;
    }

    /**
     * Método para retonar o Nº da Proposta do objeto Apólice
     *
     * @return the proposalNumber
     */
    public String getProposalNumber() {
        return proposalNumber;
    }

    /**
     * Método para setar o Nº da Proposta no objeto Apólice
     *
     * @param proposalNumber the proposalNumber to set
     */
    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    /**
     * Método para retonar o Código de Identificação (CI) do objeto Apólice
     *
     * @return the authenticationCode
     */
    public String getAuthenticationCode() {
        return authenticationCode;
    }

    /**
     * Método para setar o Código de Identificação (CI) no objeto Apólice
     *
     * @param authenticationCode the authenticationCode to set
     */
    public void setAuthenticationCode(String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }

    /**
     * Método para retonar o Comissão do Produtor do objeto Apólice
     *
     * @return the producerCommission
     */
    public double getProducerCommission() {
        return producerCommission;
    }

    /**
     * Método para setar o Comissão do Produtor no objeto Apólice
     *
     * @param producerCommission the producerCommission to set
     */
    public void setProducerCommission(double producerCommission) {
        this.producerCommission = producerCommission;
    }

    public float getProducerCommissionPercent() {
        return producerCommissionPercent;
    }

    public void setProducerCommissionPercent(float producerCommissionPercent) {
        this.producerCommissionPercent = producerCommissionPercent;
    }

    /**
     * Método para retonar o Comissão da Corretora do objeto Apólice
     *
     * @return the brokerageCommission
     */
    public double getBrokerageCommission() {
        return brokerageCommission;
    }

    /**
     * Método para setar o Comissão da Corretora no objeto Apólice
     *
     * @param brokerageCommission the brokerageCommission to set
     */
    public void setBrokerageCommission(double brokerageCommission) {
        this.brokerageCommission = brokerageCommission;
    }

    public float getBrokerageCommissionPercent() {
        return brokerageCommissionPercent;
    }

    public void setBrokerageCommissionPercent(float brokerageCommissionPercent) {
        this.brokerageCommissionPercent = brokerageCommissionPercent;
    }

    /**
     * Método para retonar o Início da Vigência do objeto Apólice
     *
     * @return the startTerm
     */
    public Date getStartTerm() {
        return startTerm;
    }

    /**
     * Método para setar o Início da Vigência no objeto Apólice
     *
     * @param startTerm the startTerm to set
     */
    public void setStartTerm(Date startTerm) {
        this.startTerm = startTerm;
    }

    /**
     * Método para retonar o Fim da Vigência do objeto Apólice
     *
     * @return the endTerm
     */
    public Date getEndTerm() {
        return endTerm;
    }

    /**
     * Método para setar o Fim da Vigência no objeto Apólice
     *
     * @param endTerm the endTerm to set
     */
    public void setEndTerm(Date endTerm) {
        this.endTerm = endTerm;
    }

    /**
     * Método para retonar o Data de Transmissão da proposta
     *
     * @return the dateTransmission
     */
    public Date getDateTransmission() {
        return dateTransmission;
    }

    /**
     * Método para setar o Data de Transmissão da proposta
     *
     * @param dateTransmission the dateTransmission to set
     */
    public void setDateTransmission(Date dateTransmission) {
        this.dateTransmission = dateTransmission;
    }

    /**
     * Método para retonar a Data de Transmissão da apólice
     *
     * @return
     */
    public Date getDateTransmissionProposal() {
        return dateTransmissionProposal;
    }

    /**
     * Método para setar o Data de Transmissão da apólice
     *
     * @param dateTransmissionProposal
     */
    public void setDateTransmissionProposal(Date dateTransmissionProposal) {
        this.dateTransmissionProposal = dateTransmissionProposal;
    }

    /**
     * Método para retonar o Observações do objeto Apólice
     *
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Método para setar o Observações no objeto Apólice
     *
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Método para retonar o objeto Propriedade atrelado ao objeto Apólice
     *
     * @return the property
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Método para setar o objeto Propriedade ao objeto Apólice
     *
     * @param property the property to set
     */
    public void setProperty(Property property) {
        this.property = property;
    }


    /**
     * Método para retonar o objeto Cliente atrelado ao objeto Apólice
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Método para setar o objeto Cliente ao objeto Apólice
     *
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getProposalCommission() {
        return proposalCommission;
    }

    public void setProposalCommission(double proposalCommission) {
        this.proposalCommission = proposalCommission;
    }

    public float getProposalCommissionPercent() {
        return proposalCommissionPercent;
    }

    public void setProposalCommissionPercent(float proposalCommissionPercent) {
        this.proposalCommissionPercent = proposalCommissionPercent;
    }

    public int getPayedInstallments() {
        return payedInstallments;
    }

    public void setPayedInstallments(int payedInstallments) {
        this.payedInstallments = payedInstallments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Insurer getInsurer() {
        return insurer;
    }

    public void setInsurer(Insurer insurer) {
        this.insurer = insurer;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    private static DateTransformer DATE_TRANSFORM = new DateTransformer("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static DateTransformer DATE_TRANSFORM_BRL = new DateTransformer("dd/MM/yyyy");

    public String toJson() {
        return new JSONSerializer().exclude("*.class").transform(DATE_TRANSFORM, Date.class).serialize(this);
    }

    public String toJson(String[] included, String[] exclude) {
        return new JSONSerializer().include(included).exclude(exclude).exclude("*.class").transform(DATE_TRANSFORM_BRL, Date.class).serialize(this);
    }

    /**
     * Toma como entrada uma string JSON e produz um gráfico estático imprimindo
     * um objeto com a representação JSON
     *
     * @param json
     * @return
     */
    public static Proposal fromJson(String json) {

        return new JSONDeserializer<Proposal>().use(null, Proposal.class)
                .use(Date.class, new PortalClienteDateTransformer("dd/MM/yyyy", "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss.SSS")).deserialize(json);
    }

    /**
     * Métodos para realização de serialização de objetos Java para JSON
     *
     * @param collection
     * @return
     */
    public static String toJsonArray(Collection<Proposal> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

    public static String toJsonArray(Collection<Proposal> collection, String[] included, String[] exclude) {
        return new JSONSerializer().include(included).exclude("*.class").exclude(exclude).serialize(collection);
    }

    public static Collection<Proposal> fromJsonArray(String json) {
        return new JSONDeserializer<List<Proposal>>().use("values", Proposal.class).deserialize(json);
    }
}