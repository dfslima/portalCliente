package br.com.portalCliente.entity.property.dependences;

import br.com.portalCliente.enumeration.BusinessZone;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Company {

    //EMPRESA_REGIAO
    @Column(name = "BUSINESS_ZONE", columnDefinition = "INT(1)")
    private Integer businessZone;

    //EMPRESA_ID_ATIVIDADE_EMPRESARIAL
    @Column(name = "BUSINESS_ACTIVITY", length = 150)
    private String businessActivity;

    /**
     * Método para retonar o Região do objeto Empresa
     *
     * @return the businessZone
     */
    public BusinessZone getBusinessZone() {
        return BusinessZone.getName(businessZone);
    }

    /**
     * Método para setar o Região no objeto Empresa
     *
     * @param businessZone the businessZone to set
     */
    public void setBusinessZone(BusinessZone businessZone) {
        this.businessZone = businessZone.getValue();
    }

    /**
     * Método para retonar a Atividade do objeto Empresa
     *
     * @return the businessActivity
     */
    public String getBusinessActivity() {
        return businessActivity;
    }

    /**
     * Método para setar a Atividade no objeto Empresa
     *
     * @param businessActivity the businessActivity to set
     */
    public void setBusinessActivity(String businessActivity) {
        this.businessActivity = businessActivity;
    }
}