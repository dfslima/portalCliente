package br.com.portalCliente.entity.property.dependences;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Life {

    @Column(name = "LIFE_PROFESSIONAL_ACTIVITY_NAME", length = 100)
    private String lifeProfessionalActivityName;

    //VIDA_QUANTIDADE
    @Column(name = "LIFE_QUANTITY")
    private Integer lifeQuantity;

    // TIPO
    @Column(name = "LIFE_TYPE", columnDefinition = "INT(1)")
    private Integer type;

    /**
     * Método para retonar o nome da Atividade Profissional do objeto Vida
     *
     * @return the lifeProfessionalActivityName
     */
    public String getLifeProfessionalActivityName() {
        return lifeProfessionalActivityName;
    }

    /**
     * Método para setar o nome da Atividade Profissional no objeto Vida
     *
     * @param lifeProfessionalActivityName the lifeProfessionalActivityName to set
     */
    public void setLifeProfessionalActivityName(String lifeProfessionalActivityName) {
        this.lifeProfessionalActivityName = lifeProfessionalActivityName;
    }

    /**
     * Método para retonar a quandidade de vidas do objeto Vida
     *
     * @return the lifeQuantity
     */
    public Integer getLifeQuantity() {
        return lifeQuantity;
    }

    /**
     * Método para setar a quantidade de vidas no objeto Vida
     *
     * @param lifeQuantity the lifeQuantity to set
     */
    public void setLifeQuantity(Integer lifeQuantity) {
        this.lifeQuantity = lifeQuantity;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}