package br.com.portalCliente.entity.property.dependences;

import br.com.portalCliente.enumeration.VehicleType;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Vehicle {

    @Column(name = "VEHICLE_MAKE_NAME", length = 100)
    private String vehicleMakeName;

    @Column(name = "VEHICLE_MODEL_NAME", length = 100)
    private String vehicleModelName;

    @Column(name = "VEHICLE_TYPE", columnDefinition = "INT(1)")
    private Integer vehicleType;

    //VEICULO_ANO_FABRICACAO
    @Column(name = "VEHICLE_REGISTER_YEAR", length = 4)
    private String vehicleRegisterYear;

    //VEICULO_ANO_MODELO
    @Column(name = "VEHICLE_MODEL_YEAR", length = 4)
    private String vehicleModelYear;

    //VEICULO_PLACA
    @Column(name = "VEHICLE_LICENSE_PLATE")
    private String vehicleLicensePlate;

    //VEICULO_CHASSI
    @Column(name = "VEHICLE_CHASSIS")
    private String vehicleChassis;

    //VEICULO_RENAVAN
    @Column(name = "VEHICLE_LICENSE_NUMBER")
    private String vehicleLicenseNumber;

    // CÓDIGO DA TABELA FIP PARA UM VEÍCULO
    @Column(name = "VEHICLE_CODE_FIPE")
    private String vehicleCodeFipe;

    /**
     * Método para retonar o Tipo do veiculo do objeto Veículo
     *
     * @return the vehicleType
     */
    public VehicleType getVehicleType() {
        if (vehicleType == null) {
            return VehicleType.CAR;
        }
        return VehicleType.get(vehicleType);
    }

    /**
     * Método para setar o Tipo do veiculo no objeto Veículo
     *
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType.getValue();
    }

    /**
     * Método para retonar o Ano Fabricação do objeto Veículo
     *
     * @return the vehicleRegisterYear
     */
    public String getVehicleRegisterYear() {
        return vehicleRegisterYear;
    }

    /**
     * Método para setar o Ano Fabricação no objeto Veículo
     *
     * @param vehicleRegisterYear the vehicleRegisterYear to set
     */
    public void setVehicleRegisterYear(String vehicleRegisterYear) {
        this.vehicleRegisterYear = vehicleRegisterYear;
    }

    /**
     * Método para retonar o Ano Modelo do objeto Veículo
     *
     * @return the vehicleModelYear
     */
    public String getVehicleModelYear() {
        return vehicleModelYear;
    }

    /**
     * Método para setar o Ano Modelo no objeto Veículo
     *
     * @param vehicleModelYear the vehicleModelYear to set
     */
    public void setVehicleModelYear(String vehicleModelYear) {
        this.vehicleModelYear = vehicleModelYear;
    }

    /**
     * Método para retonar a Placa do objeto Veículo
     *
     * @return the vehicleLicensePlate
     */
    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    /**
     * Método para setar a Placa no objeto Veículo
     *
     * @param vehicleLicensePlate the vehicleLicensePlate to set
     */
    public void setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
    }

    /**
     * Método para retonar o Chassi do objeto Veículo
     *
     * @return the vehicleChassis
     */
    public String getVehicleChassis() {
        return vehicleChassis;
    }

    /**
     * Método para setar o Chassi no objeto Veículo
     *
     * @param vehicleChassis the vehicleChassis to set
     */
    public void setVehicleChassis(String vehicleChassis) {
        this.vehicleChassis = vehicleChassis;
    }

    /**
     * Método para retonar o Código RENAVAM do objeto Veículo
     *
     * @return the vehicleLicenseNumber
     */
    public String getVehicleLicenseNumber() {
        return vehicleLicenseNumber;
    }

    /**
     * Método para setar o Código RENAVAM no objeto Veículo
     *
     * @param vehicleLicenseNumber the vehicleLicenseNumber to set
     */
    public void setVehicleLicenseNumber(String vehicleLicenseNumber) {
        this.vehicleLicenseNumber = vehicleLicenseNumber;
    }

    public String getVehicleCodeFipe() {
        return vehicleCodeFipe;
    }

    public void setVehicleCodeFipe(String vehicleCodeFipe) {
        this.vehicleCodeFipe = vehicleCodeFipe;
    }

    public String getVehicleMakeName() {
        return vehicleMakeName;
    }

    public void setVehicleMakeName(String vehicleMakeName) {
        this.vehicleMakeName = vehicleMakeName;
    }

    public String getVehicleModelName() {
        return vehicleModelName;
    }

    public void setVehicleModelName(String vehicleModelName) {
        this.vehicleModelName = vehicleModelName;
    }

}