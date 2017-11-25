package br.com.portalCliente.entity.property.dependences;

import br.com.portalCliente.enumeration.VesselType;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Vessel {

    //EMBARCACAO_ID_TIPO
    @Column(name = "BOAT_TYPE_ID")
    private Integer boatTypeId;

    //EMBARCACAO_COMPRIMENTO
    @Column(name = "BOAT_LENGTH")
    private Integer boatLength;

    //EMBARCACAO_NOME
    @Column(name = "BOAT_NAME")
    private String boatName;

    //EMBARCACAO_LGM_BASICA
    @Column(name = "BOAT_LGM")
    private Integer boatLgm;

    //EMBARCACAO_ASSISTENCIA_SALVAMENTO
    @Column(name = "BOAT_SA")
    private Integer boatSa;

    // IDADE DA EMBARCAÇÃO
    @Column(name = "AGE_VESSEL")
    private Integer ageVessel;

    //EMBARCACAO_RESPONSABILIDADE_CIVIL
    @Column(name = "BOAT_LIABILITY")
    private String boatLiability;

    //EMBARCACAO_LIMITE_NAVEGACAO
    @Column(name = "BOAT_NAVIGATION_LIMIT")
    private String boatNavigationLimit;

    //EMBARCACAO_PS_BASICA
    @Column(name = "BOAT_PS_BASIC")
    private String boatPsBasic;

    //EMBARCACAO_TEMPO_EXPERIENCIA
    @Column(name = "BOAT_EXPERIENCE_TIME")
    private String boatExperienceTime;

    /**
     * Método para retonar o Tipo do objeto Embarcação
     *
     * @return the boatTypeId
     */
    public VesselType getBoatTypeId() {
        return VesselType.getName(boatTypeId);
    }

    /**
     * Método para setar o Tipo no objeto Embarcação
     *
     * @param boatTypeId the boatTypeId to set
     */
    public void setBoatTypeId(VesselType boatTypeId) {
        this.boatTypeId = boatTypeId.getValue();
    }

    /**
     * Método para retonar o Comprimento Embarcação do objeto Embarcação
     *
     * @return the boatLength
     */
    public Integer getBoatLength() {
        return boatLength;
    }

    /**
     * Método para setar o Comprimento Embarcação no objeto Embarcação
     *
     * @param boatLength the boatLength to set
     */
    public void setBoatLength(Integer boatLength) {
        this.boatLength = boatLength;
    }

    /**
     * Método para retonar o Nome Embarcação do objeto Embarcação
     *
     * @return the boatName
     */
    public String getBoatName() {
        return boatName;
    }

    /**
     * Método para setar o Nome Embarcação no objeto Embarcação
     *
     * @param boatName the boatName to set
     */
    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    /**
     * Método para retonar o LGM Básica do objeto Embarcação
     *
     * @return the boatLgm
     */
    public Integer getBoatLgm() {
        return boatLgm;
    }

    /**
     * Método para setar o LGM Básica no objeto Embarcação
     *
     * @param boatLgm the boatLgm to set
     */
    public void setBoatLgm(Integer boatLgm) {
        this.boatLgm = boatLgm;
    }

    /**
     * Método para retonar o LGM Assistência e Salvamento do objeto Embarcação
     *
     * @return the boatSa
     */
    public Integer getBoatSa() {
        return boatSa;
    }

    /**
     * Método para setar o LGM Assistência e Salvamento no objeto Embarcação
     *
     * @param boatSa the boatSa to set
     */
    public void setBoatSa(Integer boatSa) {
        this.boatSa = boatSa;
    }

    /**
     * Método para retonar o Idade da Embarcação do objeto Embarcação
     *
     * @return the ageVessel
     */
    public Integer getAgeVessel() {
        return ageVessel;
    }

    /**
     * Método para setar o Idade da Embarcação no objeto Embarcação
     *
     * @param ageVessel the ageVessel to set
     */
    public void setAgeVessel(Integer ageVessel) {
        this.ageVessel = ageVessel;
    }

    /**
     * Método para retonar o LGM Responsabilidade Civil do objeto Embarcação
     *
     * @return the boatLiability
     */
    public String getBoatLiability() {
        return boatLiability;
    }

    /**
     * Método para setar o LGM Responsabilidade Civil no objeto Embarcação
     *
     * @param boatLiability the boatLiability to set
     */
    public void setBoatLiability(String boatLiability) {
        this.boatLiability = boatLiability;
    }

    /**
     * Método para retonar o Limite Navegação do objeto Embarcação
     *
     * @return the boatNavigationLimit
     */
    public String getBoatNavigationLimit() {
        return boatNavigationLimit;
    }

    /**
     * Método para setar o Limite Navegação no objeto Embarcação
     *
     * @param boatNavigationLimit the boatNavigationLimit to set
     */
    public void setBoatNavigationLimit(String boatNavigationLimit) {
        this.boatNavigationLimit = boatNavigationLimit;
    }

    /**
     * Método para retonar o PS Básica do objeto Embarcação
     *
     * @return the boatPsBasic
     */
    public String getBoatPsBasic() {
        return boatPsBasic;
    }

    /**
     * Método para setar o PS Básica no objeto Embarcação
     *
     * @param boatPsBasic the boatPsBasic to set
     */
    public void setBoatPsBasic(String boatPsBasic) {
        this.boatPsBasic = boatPsBasic;
    }

    /**
     * Método para retonar o Tempo Experiência do objeto Embarcação
     *
     * @return the boatExperienceTime
     */
    public String getBoatExperienceTime() {
        return boatExperienceTime;
    }

    /**
     * Método para setar o Tempo Experiência no objeto Embarcação
     *
     * @param boatExperienceTime the boatExperienceTime to set
     */
    public void setBoatExperienceTime(String boatExperienceTime) {
        this.boatExperienceTime = boatExperienceTime;
    }
}