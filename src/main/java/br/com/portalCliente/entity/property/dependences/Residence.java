package br.com.portalCliente.entity.property.dependences;

import br.com.portalCliente.enumeration.HomeBuildingType;
import br.com.portalCliente.enumeration.HomeType;
import br.com.portalCliente.enumeration.HomeUseType;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Residence {

    //RESIDENCIA_ID_TIPO_MORADIA
    @Column(name = "HOME_TYPE", columnDefinition = "INT(1)")
    private Integer homeType;

    //RESIDENCIA_ID_TIPO_USO
    @Column(name = "HOME_USE_TYPE", columnDefinition = "INT(1)")
    private Integer homeUseType;

    //RESIDENCIA_ID_TIPO_CONSTRUCAO
    @Column(name = "HOME_BUILDING_TYPE", columnDefinition = "INT(1)")
    private Integer homeBuildingType;

    /**
     * Método para retonar o Tipo de Moradia do objeto Imóvel
     *
     * @return the homeType
     */
    public HomeType getHomeType() {
        return HomeType.getName(homeType);
    }

    /**
     * Método para setar o Tipo de Moradia no objeto Imóvel
     *
     * @param homeType the homeType to set
     */
    public void setHomeType(HomeType homeType) {
        this.homeType = homeType.getValue();
    }

    /**
     * Método para retonar o Uso do objeto Imóvel
     *
     * @return the homeUseType
     */
    public HomeUseType getHomeUseType() {
        return HomeUseType.getName(homeUseType);
    }

    /**
     * Método para setar o Uso no objeto Imóvel
     *
     * @param homeUseType the homeUseType to set
     */
    public void setHomeUseType(HomeUseType homeUseType) {
        this.homeUseType = homeUseType.getValue();
    }

    /**
     * Método para retonar a Construção do objeto Imóvel
     *
     * @return the homeBuildingType
     */
    public HomeBuildingType getHomeBuildingType() {
        return HomeBuildingType.getName(homeBuildingType);
    }

    /**
     * Método para setar a Construção no objeto Imóvel
     *
     * @param homeBuildingTypeId the homeBuildingType to set
     */
    public void setHomeBuildingType(HomeBuildingType homeBuildingTypeId) {
        this.homeBuildingType = homeBuildingTypeId.getValue();
    }
}