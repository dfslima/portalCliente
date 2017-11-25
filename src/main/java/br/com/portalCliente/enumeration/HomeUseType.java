package br.com.portalCliente.enumeration;

public enum HomeUseType {

    COMERCIO(1),
    CONDOMINIO(2),
    RESIDENCIA(3);

    private final int value;

    HomeUseType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static HomeUseType getName(int value) {
        HomeUseType result = null;
        for (HomeUseType c : HomeUseType.values()) {
            if (c.value == value) {
                result = c;
                break;
            }
        }
        return result;
    }

}
