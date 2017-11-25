package br.com.portalCliente.enumeration;

public enum HomeBuildingType {

    ALVENARIA(1),
    CASA_EM_CONDOMINIO(2),
    CASA_HABITUAL(3),
    EDIFICIO_HABITUAL(4);

    private final int value;

    HomeBuildingType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static HomeBuildingType getName(int value) {
        HomeBuildingType result = null;
        for (HomeBuildingType c : HomeBuildingType.values()) {
            if (c.value == value) {
                result = c;
                break;
            }
        }
        return result;
    }

}
