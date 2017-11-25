package br.com.portalCliente.enumeration;

public enum HomeType {

    APARTAMENTO_HABITUAL(1),
    CASA_HABITUAL(2),
    EDIFICIO_HABITUAL(3);

    private final int value;

    HomeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static HomeType getName(int value) {
        HomeType result = null;
        for (HomeType c : HomeType.values()) {
            if (c.value == value) {
                result = c;
                break;
            }
        }
        return result;
    }

}
