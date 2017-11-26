package br.com.portalCliente.entity.franchise;

import br.com.portalCliente.entity.proposal.Proposal;
import br.com.portalCliente.enumeration.FranchiseType;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "FRANCHISE")
public class Franchise extends FranchiseAR {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;

	@Column(name="FRANCHISE_TYPE")
	private Integer franchiseType;

	@ManyToOne
	@JoinColumn(name ="PROPOSAL_ID", referencedColumnName = "ID")
	private Proposal proposal;
	
	@Column(name="VALUE")
	private double value;
	
	/************** GETTERS AND SETTERS **************/


	/**
	 * Este método retorna a descrição da cobertura
	 * @return
	 */
	public FranchiseType getFranchiseType() {
		return FranchiseType.getName(franchiseType);
	}

	/**
	 * Este método seta a descrição da cobertura
	 * @param franchiseType
	 */
	public void setFranchiseType(FranchiseType franchiseType) {
		this.franchiseType = franchiseType.getValue();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	///////////////////////////////////////// Converte Json  /////////////////////////////////////////



	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
	}

	public static Franchise fromJson(String json) {
		return new JSONDeserializer<Franchise>().use(null, Franchise.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Franchise> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<Franchise> collection, String[] fields) {
		return new JSONSerializer()	.include(fields).exclude("*.class").serialize(collection);
	}

	public static Collection<Franchise> fromJsonArray(String json) {
		return new JSONDeserializer<List<Franchise>>().use("values", Franchise.class).deserialize(json);
	}
	
}
