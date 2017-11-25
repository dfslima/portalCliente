package br.com.portalCliente.enumeration;

public enum BusinessZone {

    CENTRO(1),
    LESTE(2),
    NORTE(3),
    OESTE(4),
    SUL(5);

    private final int value;

    private BusinessZone(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BusinessZone getName(Integer value) {
        if (value == null || value == 0) {
            return null;
        }

        BusinessZone result = null;
        for (BusinessZone c : BusinessZone.values()) {
            if (c.value == value) {
                result = c;
                break;
            }
        }
        return result;
    }

}
