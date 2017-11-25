package br.com.portalCliente.entity.proposal;

import br.com.portalCliente.entity.customer.Customer;
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
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Transient
    private String insurerName;

    @Transient
    private String producerName;

    @Column(name="CREATED_AT")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name="INSURER_ID")
    private Integer insuranceId;

    @Column(name="STATUS", columnDefinition="INT(1)")
    private Integer status;

    @Column(name="PREVIOUS_STATUS", columnDefinition="INT(1)")
    private Integer previousStatus;

    @Column(name="TYPE", columnDefinition="INT(1)")
    private int type;

    // PRÊMIO LÍQUIDO
    @Column(name="NET_AWARD")
    private double netAward;

    //NÚMERO DE PARCELAS
    @Column(name="INSTALLMENSTS_NUMBER")
    private Integer installmentsNumber;

    // DESCONTO PRODUTOR
    @Column(name="DISCOUNT")
    private float discount;

    // DESCONTO CORRETORA
    @Column(name="DISCOUNT_BROKERAGE")
    private float discountBrokerage;

    // AGRAVAMENTO
    @Column(name="AGGRAVATION")
    private float aggravation;

    // NÚMERO DA PROPOSTA
    @NotNull
    @Column(name="PROPOSAL_NUMBER", unique=true)
    private String proposalNumber;

    // NÚMERO DA APÓLICE
    @Column(name="POLICY_NUMBER", unique=true)
    private String policyNumber;

    // CÓDIGO DE AUTENTICAÇÃO
    @Column(name="AUTHENTICATION_CODE", unique=true, length=40)
    private String authenticationCode;

    @Column(name="PRODUCER_ID")
    private Integer producerId;

    // COMISSÃO DO PRODUTOR
    @Column(name="PRODUCER_COMMISSION")
    private double producerCommission;

    @Column(name="PRODUCER_COMMISSION_PERCENT")
    private float producerCommissionPercent;

    // COMISSÃO DA CORRETORA
    @Column(name="BROKERAGE_COMMISSION")
    private double brokerageCommission;

    @Column(name="BROKERAGE_COMMISSION_PERCENT")
    private float brokerageCommissionPercent;

    // COMISSÃO DA APOLICE
    @Column(name="POLICY_COMMISSION")
    private double policyCommission;

    // COMISSÃO DA APOLICE
    @Column(name="POLICY_COMMISSION_PERCENT")
    private float policyCommissionPercent;

    // COMISSÃO MÁXIMA
    @Column(name="POLICY_COMMISSION_PERCENT_MAXIMUM")
    private float policyCommissionPercentMaximum;

    // INÍCIO DA VIGÊNCIA
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name="START_TERM")
    private Date startTerm;

    // FIM DA VIGÊNCIA
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name="END_TERM")
    private Date endTerm;

    // DATA DA TRANSMISSÃO
    @Temporal(TemporalType.DATE)
    @Column(name="DATE_TRANSMISSION")
    private Date dateTransmission;

    // DATA DE EMISSÃO DA APÓLICE
    @Temporal(TemporalType.DATE)
    @Column(name="DATE_TRANSMISSION_POLICY")
    private Date dateTransmissionPolicy;

    // OBSERVAÇÃO
    @Column(name="COMMENTS", length=500)
    private String comments;

    @Column(name="CANCELLATION_TYPE", columnDefinition="INT(1)")
    private Integer cancellationType;

    @Column(name="CANCELLATION_REASON", length=500)
    private String cancellationReason;

    @Temporal(TemporalType.DATE)
    @Column(name="CANCELLATION_DATE")
    private Date cancellationDate;

    @Column(name="REVERSED_PAYMENT")
    private double reversedPayment;

    @Column(name="PAYED_INSTALLMENTS")
    private int payedInstallments;

    @Column(name="ENDORSED_COMMENTS", length=500)
    private String endorsedComments;

    @Column(name="ENDORSED_TYPE", columnDefinition="INT(1)")
    private Integer endorsedType;

//    @OneToOne
//    @JoinColumn(name = "PROPERTY_ID", referencedColumnName = "ID")
//    private Property property;
//
    @ManyToOne
    @JoinColumn(name ="CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;
//
//    @OneToMany(cascade=CascadeType.ALL)
//    @JoinColumn(name ="POLICY_ID")
//    private List<Movement> movements;
//
//    @OneToMany(cascade=CascadeType.ALL, mappedBy="policy")
//    private List<File> files;
//
//    @OneToMany(cascade=CascadeType.ALL, mappedBy="policy")
//    private List<PolicyCoverage> policyCoverages;
//
//    @OneToMany(cascade=CascadeType.ALL, mappedBy="policy")
//    private List<PolicyFranchise> policyFranchises;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "policy")
//    private List<Fleet> fleets;
//
//    @OneToOne
//    @JoinColumn(name = "POLICY_RENOVATION_ID", referencedColumnName = "ID")
//    private Policy policyRenovation;

    @Column(name="STATUS_RENOVATION", columnDefinition="INT(1)")
    private boolean statusRenovation;

    public float getPolicyCommissionPercentMaximum() {
        return policyCommissionPercentMaximum;
    }

    public void setPolicyCommissionPercentMaximum(
            float policyCommissionPercentMaximum) {
        this.policyCommissionPercentMaximum = policyCommissionPercentMaximum;
    }

    /**
     * Método para retonar o id do objeto Apólice
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método para retornar o nome de uma seguradora
     *
     * @return insurerName
     */
    public String getInsurerName() {
        return insurerName;
    }

    /**
     * este método seta o nome da seguradora
     * @param insurerName
     *
     * */
    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    /**
     * Método para setar o id no objeto Apólice
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }

    /**
     * Método para retonar o id da Seguradora do objeto Apólice
     * @return the insurerId
     */
    public Integer getInsuranceId() {
        return insuranceId;
    }

    /**
     * Método para setar o id da seguradora no objeto Apólice
     * @param insuranceId the insurerId to set
     */
    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

//    /**
//     * Método para retonar o status do objeto Apólice
//     * true = Apólice
//     * false = Proposta
//     * @return the status
//     */
//    public PolicyStatus getStatus() {
//        return PolicyStatus.fromValue(status);
//    }
//
//    /**
//     * Método para setar o status no objeto Apólice
//     * true = Apólice
//     * false = Proposta
//     * @param policyStatus the status to set
//     */
//    public void setStatus(PolicyStatus policyStatus) {
//        this.status = policyStatus.getValue();
//    }
//
//    /**
//     * Método para retonar o status anterior do objeto Apólice
//     * @return o status anterior
//     */
//    public PolicyStatus getPreviousStatus() {
//        return PolicyStatus.fromValue(previousStatus);
//    }
//
//    /**
//     * Método para setar o status anterior no objeto Apólice
//     * @param policyStatus anterior
//     */
//    public void setPreviousStatus(PolicyStatus policyStatus) {
//        this.previousStatus = policyStatus.getValue();
//    }
//
//    public PolicyType getType() {
//        return PolicyType.fromValue(type);
//    }
//
//    public void setType(PolicyType type) {
//        this.type = type.getValue();
//    }

    /**
     * Método para retonar o Prêmio Líquido do objeto Apólice
     * @return the netAward
     */
    public double getNetAward() {
        return netAward;
    }

    /**
     * Método para setar o Prêmio Líquido no objeto Apólice
     * @param netAward the netAward to set
     */
    public void setNetAward(double netAward) {
        this.netAward = netAward;
    }

    /**
     * Método para retonar o Nº de Parcelas do objeto Apólice
     * @return the installmentsNumber
     */
    public Integer getInstallmentsNumber() {
        return installmentsNumber;
    }

    /**
     * Método para setar o Nº de Parcelas no objeto Apólice
     * @param installmentsNumber the installmentsNumber to set
     */
    public void setInstallmentsNumber(Integer installmentsNumber) {
        this.installmentsNumber = installmentsNumber;
    }

    /**
     * Método para retonar o desconto do objeto Apólice
     * @return the discount
     */
    public float getDiscount() {
        return discount;
    }

    /**
     * Método para setar o desconto no objeto Apólice
     * @param discount the discount to set
     */
    public void setDiscount(float discount) {
        this.discount = discount;
    }

    /**
     * Método para recuperar o valor de desconto aplicado na comissão da corretora
     * @return
     */
    public float getDiscountBrokerage() {
        return discountBrokerage;
    }

    /**
     * Método para setar o valor de desconto aplicado na comissão da corretora
     * @param discountBrokerage
     */
    public void setDiscountBrokerage(float discountBrokerage) {
        this.discountBrokerage = discountBrokerage;
    }

    /**
     * Método para retornar o valor do agravamento inserido na proposta
     * @return the value aggravation
     */
    public float getAggravation() {
        return aggravation;
    }


    /**
     * Método para setar o valor do agravamento da proposta
     * @param aggravation
     */
    public void setAggravation(float aggravation) {
        this.aggravation = aggravation;
    }

    /**
     * Método para retonar o Nº da Proposta do objeto Apólice
     * @return the proposalNumber
     */
    public String getProposalNumber() {
        return proposalNumber;
    }

    /**
     * Método para setar o Nº da Proposta no objeto Apólice
     * @param proposalNumber the proposalNumber to set
     */
    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    /**
     * Método para retonar o Nº da Apólice do objeto Apólice
     * @return the policyNumber
     */
    public String getPolicyNumber() {
        return policyNumber;
    }

    /**
     * Método para setar o Nº da Apólice no objeto Apólice
     * @param policyNumber the policyNumber to set
     */
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    /**
     * Método para retonar o Código de Identificação (CI) do objeto Apólice
     * @return the authenticationCode
     */
    public String getAuthenticationCode() {
        return authenticationCode;
    }

    /**
     * Método para setar o Código de Identificação (CI) no objeto Apólice
     * @param authenticationCode the authenticationCode to set
     */
    public void setAuthenticationCode(String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }

    /**
     * Método para retonar o Comissão do Produtor do objeto Apólice
     * @return the producerCommission
     */
    public double getProducerCommission() {
        return producerCommission;
    }

    /**
     * Método para setar o Comissão do Produtor no objeto Apólice
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
     * @return the brokerageCommission
     */
    public double getBrokerageCommission() {
        return brokerageCommission;
    }

    /**
     * Método para setar o Comissão da Corretora no objeto Apólice
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
     * @return the startTerm
     */
    public Date getStartTerm() {
        return startTerm;
    }

    /**
     * Método para setar o Início da Vigência no objeto Apólice
     * @param startTerm the startTerm to set
     */
    public void setStartTerm(Date startTerm) {
        this.startTerm = startTerm;
    }

    /**
     * Método para retonar o Fim da Vigência do objeto Apólice
     * @return the endTerm
     */
    public Date getEndTerm() {
        return endTerm;
    }

    /**
     * Método para setar o Fim da Vigência no objeto Apólice
     * @param endTerm the endTerm to set
     */
    public void setEndTerm(Date endTerm) {
        this.endTerm = endTerm;
    }

    /**
     * Método para retonar o Data de Transmissão da proposta
     * @return the dateTransmission
     */
    public Date getDateTransmission() {
        return dateTransmission;
    }

    /**
     * Método para setar o Data de Transmissão da proposta
     * @param dateTransmission the dateTransmission to set
     */
    public void setDateTransmission(Date dateTransmission) {
        this.dateTransmission = dateTransmission;
    }

    /**
     * Método para retonar a Data de Transmissão da apólice
     * @return
     */
    public Date getDateTransmissionPolicy() {
        return dateTransmissionPolicy;
    }

    /**
     * Método para setar o Data de Transmissão da apólice
     * @param dateTransmissionPolicy
     */
    public void setDateTransmissionPolicy(Date dateTransmissionPolicy) {
        this.dateTransmissionPolicy = dateTransmissionPolicy;
    }

    /**
     * Método para retonar o Observações do objeto Apólice
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Método para setar o Observações no objeto Apólice
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

//    public Policy getPolicy() {
//        return policy;
//    }
//
//    public void setPolicy(Policy policy) {
//        this.policy = policy;
//    }
//
//    /**
//     * Método para retonar o objeto Propriedade atrelado ao objeto Apólice
//     * @return the property
//     */
//    public Property getProperty() {
//        return property;
//    }
//
//    /**
//     * Método para setar o objeto Propriedade ao objeto Apólice
//     * @param property the property to set
//     */
//    public void setProperty(Property property) {
//        this.property = property;
//    }
//
    /**
     * Método para retonar o objeto Cliente atrelado ao objeto Apólice
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Método para setar o objeto Cliente ao objeto Apólice
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getPolicyCommission() {
        return policyCommission;
    }

    public void setPolicyCommission(double policyCommission) {
        this.policyCommission = policyCommission;
    }

    public float getPolicyCommissionPercent() {
        return policyCommissionPercent;
    }

    public void setPolicyCommissionPercent(float policyCommissionPercent) {
        this.policyCommissionPercent = policyCommissionPercent;
    }

    public Integer getCancellationType() {
        return cancellationType;
    }

    public void setCancellationType(Integer cancellationType) {
        this.cancellationType = cancellationType;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public double getReversedPayment() {
        return reversedPayment;
    }

    public void setReversedPayment(double reversedPayment) {
        this.reversedPayment = reversedPayment;
    }

    public int getPayedInstallments() {
        return payedInstallments;
    }

    public void setPayedInstallments(int payedInstallments) {
        this.payedInstallments = payedInstallments;
    }

//    public List<Movement> getMovements() {
//        return movements;
//    }
//
//    public void setMovements(List<Movement> movements) {
//        this.movements = movements;
//    }


    public String getEndorsedComments() {
        return endorsedComments;
    }

    public void setEndorsedComments(String endorsedComments) {
        this.endorsedComments = endorsedComments;
    }

//    public EndorsedType getEndorsedType() {
//        return EndorsedType.fromValue(endorsedType);
//    }
//
//    public void setEndorsedType(EndorsedType endorsedType) {
//        this.endorsedType = endorsedType.getValue();
//    }


//    /**
//     * Este método retorna uma lista de arquivos que pertencem a uma apólice
//     * @return
//     */
//    public List<File> getFiles() {
//        return files;
//    }
//
//    /**
//     * Este método seta arquivos ao objeto de apólice
//     * @param files
//     */
//    public void setFiles(List<File> files) {
//        this.files = files;
//    }
//
//    /**
//     * Este método retorna uma lista com as cobertuas pertencentes a uma apólice
//     * @return policyCoverages
//     */
//    public List<PolicyCoverage> getPolicyCoverages() {
//        return policyCoverages;
//    }
//
//    /**
//     * Este método seta uma lista de corberturas no objeto de apólice
//     * @param policyCoverages
//     */
//    public void setPolicyCoverages(List<PolicyCoverage> policyCoverages) {
//        this.policyCoverages = policyCoverages;
//    }
//
//    public List<PolicyFranchise> getPolicyFranchises() {
//        return policyFranchises;
//    }
//
//    public void setPolicyFranchises(List<PolicyFranchise> policyFranchises) {
//        this.policyFranchises = policyFranchises;
//    }
//
//    public List<Fleet> getFleets() {
//        return fleets;
//    }
//
//    public void setFleets(List<Fleet> fleets) {
//        this.fleets = fleets;
//    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

//    public Policy getPolicyRenovation() {
//        return policyRenovation;
//    }

//    public void setPolicyRenovation(Policy policyRenovation) {
//        this.policyRenovation = policyRenovation;
//    }

    public boolean isStatusRenovation() {
        return statusRenovation;
    }

    public void setStatusRenovation(boolean statusRenovation) {
        this.statusRenovation = statusRenovation;
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
     * @param json
     * @return
     */
    public static Proposal fromJson(String json) {

        return new JSONDeserializer<Proposal>().use(null, Proposal.class)
                .use(Date.class, new PortalClienteDateTransformer("dd/MM/yyyy")).deserialize(json);
    }

    /**
     * Métodos para realização de serialização de objetos Java para JSON
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