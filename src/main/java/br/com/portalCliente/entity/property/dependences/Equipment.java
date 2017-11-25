package br.com.portalCliente.entity.property.dependences;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Equipment {

    //EQUIPAMENTO DESCRIÇÃO
    @Column(name = "EQUIPMENT_DETAILS")
    private String equipmentDetails;

    //EQUIPAMENTO MODELO
    @Column(name = "EQUIPMENT_MODEL")
    private String equipmentModel;

    //EQUIPAMENTO FABRICANTE
    @Column(name = "EQUIPMENT_MAKE")
    private String equipmentMake;

    //EQUIPAMENTO ANO FABRICACAO
    @Column(name = "EQUIPMENT_REGISTER_YEAR", length = 4)
    private String equipmentRegisterYear;

    /**
     * Método para retonar o Número de série do objeto Equipamento
     *
     * @return the equipmentDetails
     */
    public String getEquipmentDetails() {
        return equipmentDetails;
    }

    /**
     * Método para setar o Número de série no objeto Equipamento
     *
     * @param equipmentDetails the equipmentDetails to set
     */
    public void setEquipmentDetails(String equipmentDetails) {
        this.equipmentDetails = equipmentDetails;
    }

    /**
     * Método para retonar o Modelo do objeto Equipamento
     *
     * @return the equipmentModel
     */
    public String getEquipmentModel() {
        return equipmentModel;
    }

    /**
     * Método para setar o Modelo no objeto Equipamento
     *
     * @param equipmentModel the equipmentModel to set
     */
    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    /**
     * Método para retonar o Fabricante do objeto Equipamento
     *
     * @return the equipmentMake
     */
    public String getEquipmentMake() {
        return equipmentMake;
    }

    /**
     * Método para setar o Fabricante no objeto Equipamento
     *
     * @param equipmentMake the equipmentMake to set
     */
    public void setEquipmentMake(String equipmentMake) {
        this.equipmentMake = equipmentMake;
    }

    /**
     * Método para retonar o Ano de Fabricação do objeto Equipamento
     *
     * @return the equipmentRegisterYear
     */
    public String getEquipmentRegisterYear() {
        return equipmentRegisterYear;
    }

    /**
     * Método para setar o Ano de Fabricação no objeto Equipamento
     *
     * @param equipmentRegisterYear the equipmentRegisterYear to set
     */
    public void setEquipmentRegisterYear(String equipmentRegisterYear) {
        this.equipmentRegisterYear = equipmentRegisterYear;
    }
}