package br.com.portalCliente.enumeration;

public enum PropertyType {
    COMPANY(1),
    EQUIPMENT(2),
    LIFE(3),
    RESIDENCE(4),
    CONDOMINIUM(5),
    VEHICLE(6),
    VESSEL(7);

    private final int value;

    private PropertyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PropertyType getFromValue(Integer value) {

        if (value == null) {
            return null;
        }

        PropertyType result = null;
        for (PropertyType c : PropertyType.values()) {
            if (c.value == value) {
                result = c;
                break;
            }
        }
        return result;
    }
}
